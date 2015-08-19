package com.kana.dev.springboot.contacts.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kana.dev.springboot.contacts.model.Video;
import com.kana.dev.springboot.contacts.service.VideoService;

@Controller
@RequestMapping("/video")
public class BulkUploadDownloadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BulkUploadDownloadController.class.getCanonicalName());

	private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
	private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
	private static final String RESP_FILE_EXISTS = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 102, \"message\": \"Uploaded file exists.\"}, \"id\" : \"id\"}";
	public static final String JSON = "application/json";
	public static final int BUF_SIZE = 2 * 1024;
	public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

	@Autowired VideoService videoService;
	
	@RequestMapping(value="/upload", method = { RequestMethod.POST}, headers="content-type != multipart/form-data")
	public void uploadVideo(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

//		LOGGER.info(">>>>>..... Insdide {}.{}", this.getClass().getSimpleName(), "uploadVideo");
//		LOGGER.info(">>>>..... using {} as a temporary storage area.", TEMP_DIR);
		int chunk = -1;
		int chunks = -2;
		String name = null;
		String user;
		String time;
		File dst = null;
		final Video videoPart = new Video();
		String responseString = RESP_SUCCESS;
		final boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (isMultipart) {
			final ServletFileUpload upload = new ServletFileUpload();
			try {
				final FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
					final FileItemStream item = iter.next();
					final InputStream in = item.openStream();
					// Handle a form field.
					if (item.isFormField()) {
						final String fieldName = item.getFieldName();
						final String value = Streams.asString(in);
						if ("name".equals(fieldName)) {
							videoPart.setFileName(value);
							name = value;
							LOGGER.debug("name: {}", name);
						} else if ("chunks".equals(fieldName)) {
							videoPart.setChunks(value);
							chunks = Integer.parseInt(value);
							LOGGER.debug("chunks: {}", chunks);
						} else if ("chunk".equals(fieldName)) {
							videoPart.setChunk(value);
							chunk = Integer.parseInt(value);
							LOGGER.debug("chunk: {}", chunk);
						} else if ("user".equals(fieldName)) {
							videoPart.setUserName(value);
							user = value;
							LOGGER.debug("user: {}", user);
						} else if ("time".equals(fieldName)) {
							time = value;
							LOGGER.debug("time: {}", time);
						} else if ("id".equals(fieldName)) {
							LOGGER.debug("id: {}", value);
						}
					} else { // Handle a multi-part MIME encoded file.
						final File dstFile = new File(TEMP_DIR);
						if (!dstFile.exists()) {
							dstFile.mkdirs();
						}

						//File dst = new File(dstFile.getPath() + "/" + this.name); // upload and create only 1 file
						dst = new File(dstFile.getPath() + File.separator + name + "~." + (chunk + 1) + "~." + chunks); // create each upload separately and use get to build as 1 file
						if ( dst.exists()){
							// two possibilities: 
							// 1. either there is already files exist by that name
							// 2. previous upload did not complete, partial upload exists
							final String [] parts = dst.getName().split("~.");
							
							// case 1
							final String lastChunkName = dstFile.getPath() + File.separator + name + "~." + (chunks + 1) + "~." + chunks;
							
							if ( new File(lastChunkName).exists()){
								// file with same name already uploaded
								responseString = RESP_FILE_EXISTS;
								break;
							}else{
								// possibly partial upload exists then resume;
								//LOGGER.info("------------------->> Partial file exists. Skipping {} ", dst.getAbsolutePath());
								break;
							}
						}
						
						try {
							try (OutputStream out = new BufferedOutputStream(new FileOutputStream(dst, dst.exists()), BUF_SIZE)) {
								IOUtils.copy(in, out);
							}	
						} catch (final Exception e) {
							dst.delete();
							LOGGER.error("User may have aborted upload, so delete this chunk, file: {}", dst.getAbsoluteFile());
						}
					}
				}
			} catch (final Exception e) {
				responseString = RESP_ERROR;
				e.printStackTrace();
			}
		} else { // Not a multi-part MIME request.
			responseString = RESP_ERROR;
		}

		if (chunk == chunks - 1) {
			LOGGER.info("Uploaded filename: " + name);
			videoService.uploadVideoFile(videoPart, TEMP_DIR);
		}
		
		
		resp.setContentType(JSON);
		final byte[] responseBytes = responseString.getBytes();
		resp.setContentLength(responseBytes.length);
		final ServletOutputStream output = resp.getOutputStream();
		output.write(responseBytes);
		output.flush();
	}

	private void saveUploadFile(final InputStream input, final File dst) throws IOException {
		OutputStream out = null;
		try {
			if (dst.exists()) {
				out = new BufferedOutputStream(new FileOutputStream(dst, true), BUF_SIZE);
			} else {
				out = new BufferedOutputStream(new FileOutputStream(dst), BUF_SIZE);
			}
			final byte[] buffer = new byte[BUF_SIZE];
			int len = 0;
			while ((len = input.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
			if (null != out) {
				try {
					out.close();
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

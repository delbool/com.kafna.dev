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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Scope("prototype")
@RequestMapping("/videos")
public class VideoUploadDownloadController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoUploadDownloadController.class.getCanonicalName());

	private static final long serialVersionUID = 3447685998419256747L;
	private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
	private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
	public static final String JSON = "application/json";
	public static final int BUF_SIZE = 2 * 1024;
	public static final String FileDir = "c:/tmp/uploads/";

	private int chunk;
	private int chunks;
	private String name;
	private String user;
	private String time;

	@RequestMapping(value="get", method = { RequestMethod.GET })
	public String uploadVideoGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.info(">>>>>..... Insdide {}.{}", this.getClass().getSimpleName(), "uploadVideoGet");
		return "redirect:/video/upload";
	}

	@RequestMapping(value="/upload", method = { RequestMethod.POST})
	public void uploadVideo(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {

		LOGGER.info(">>>>>..... Insdide {}.{}", this.getClass().getSimpleName(), "uploadVideo");
		String responseString = RESP_SUCCESS;
		final boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (isMultipart) {
			final ServletFileUpload upload = new ServletFileUpload();
			try {
				final FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
					final FileItemStream item = iter.next();
					final InputStream input = item.openStream();
					// Handle a form field.
					if (item.isFormField()) {
						final String fileName = item.getFieldName();
						final String value = Streams.asString(input);
						if ("name".equals(fileName)) {
							this.name = value;
							LOGGER.debug("name: " + name);
						} else if ("chunks".equals(fileName)) {
							this.chunks = Integer.parseInt(value);
							LOGGER.debug("chunks:" + chunks);
						} else if ("chunk".equals(fileName)) {
							this.chunk = Integer.parseInt(value);
							LOGGER.debug("chunk: " + chunk);
						} else if ("user".equals(fileName)) {
							this.user = value;
							LOGGER.debug("user: " + user);
						} else if ("time".equals(fileName)) {
							this.time = value;
							LOGGER.debug("time: " + time);
						}
					} else { // Handle a multi-part MIME encoded file.
						final File dstFile = new File(FileDir);
						if (!dstFile.exists()) {
							dstFile.mkdirs();
						}

						final File dst = new File(dstFile.getPath() + "/" + this.name);
						saveUploadFile(input, dst);
					}
				}
			} catch (final Exception e) {
				responseString = RESP_ERROR;
				e.printStackTrace();
			}
		} else { // Not a multi-part MIME request.
			responseString = RESP_ERROR;
		}

		if (this.chunk == this.chunks - 1) {
			LOGGER.info("Uploaded filename: " + this.name);
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

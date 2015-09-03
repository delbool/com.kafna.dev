package com.kana.dev.springboot.contacts.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItemIterator;
import org.apache.tomcat.util.http.fileupload.FileItemStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kana.dev.springboot.contacts.service.HbaseOutputStream;

@Controller
@RequestMapping("/video")
public class VideoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class.getCanonicalName());

	private static final String RESP_SUCCESS = "{\"jsonrpc\" : \"2.0\", \"result\" : \"success\", \"id\" : \"id\"}";
	private static final String RESP_ERROR = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 101, \"message\": \"Failed to open input stream.\"}, \"id\" : \"id\"}";
	private static final String RESP_FILE_EXISTS = "{\"jsonrpc\" : \"2.0\", \"error\" : {\"code\": 102, \"message\": \"Uploaded file exists.\"}, \"id\" : \"id\"}";
	public static final String JSON = "application/json";
	public static final int BUF_SIZE = 2 * 1024;
	public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");


	@RequestMapping(value = "/upload", method = { RequestMethod.POST }, headers = "content-type != multipart/form-data")
	public void uploadVideo(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.info(">>>>>..... Insdide {}.{}", this.getClass().getSimpleName(), "uploadVideo");
		LOGGER.info(">>>>..... using {} as a temporary storage area.", TEMP_DIR);
		int chunk = -1;
		int chunks = -2;
		String name = null;

		String responseString = RESP_SUCCESS;
		final boolean isMultipart = ServletFileUpload.isMultipartContent(req);

		if (isMultipart) {
			final ServletFileUpload upload = new ServletFileUpload();
			try {
				final FileItemIterator iter = upload.getItemIterator(req);
				while (iter.hasNext()) {
					final FileItemStream item = iter.next();
					if (!item.isFormField()) {
						final InputStream in = item.openStream();
						HbaseOutputStream outputStream = new HbaseOutputStream();
						BufferedInputStream bis = new BufferedInputStream(in, 64*1024);
						int count = 0;
						while((count = in.read()) != -1){
							//System.out.println("Cuurent value read from Input Stream : " + read);
							outputStream.write(count);
						}
						System.out.println("Is this a negative one?  [" + count + "]");
						outputStream.write(-1);
						in.close();
						outputStream.close();
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
			// videoService.uploadVideoFile(videoPart, TEMP_DIR);
		}

		resp.setContentType(JSON);
		final byte[] responseBytes = responseString.getBytes();
		resp.setContentLength(responseBytes.length);
		final ServletOutputStream output = resp.getOutputStream();
		output.write(responseBytes);
		output.flush();
	}

	public void downloadVideo(HttpServletRequest request, HttpServletResponse response, String fileName, int chunks) {
		int bufferSize = 64 * 1024; // One HBase block
		String sourceDirName = System.getProperty("java.io.tmpdir");
		File sourceDir = new File(sourceDirName);

		File outputFile = new File("C:/Temp/Data/" + fileName);
		try {
			for (int i = 1; i <= chunks; i++) {
				File inputSource = new File(sourceDir.getPath() + File.separator + fileName + "~." + i + "~." + chunks);
				InputStream in = new BufferedInputStream(new FileInputStream(inputSource));

				try (OutputStream out = new BufferedOutputStream(new FileOutputStream(outputFile, outputFile.exists()), bufferSize)) {
					IOUtils.copy(in, out);
				}
			}
		} catch (final Exception e) {
			outputFile.delete();
			LOGGER.error("User may have aborted upload, so delete this chunk, file: {}", outputFile.getAbsoluteFile());
		}

	}
}

package com.kana.dev.springboot.contacts.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/videos")
public class VideoController {

	private static final Logger LOGGER = LoggerFactory.getLogger(VideoController.class.getCanonicalName());

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody String uploadVideo(@RequestParam("files") final MultipartFile[] files, final HttpServletRequest request,
			final HttpServletResponse Response, final Model model) throws Exception {
		LOGGER.info(">>>>>..... Insdide {}.{}", this.getClass().getSimpleName(), "uploadVideo");
		final String fileName = "image_" + System.currentTimeMillis() + ".jpg";
		if (files != null) {
			LOGGER.info(">>>>>..... File Exists");
			for (final MultipartFile file : files) {
				try {
					final byte[] bytes = file.getBytes();

					// Creating the directory to store file
					final String rootPath = System.getProperty("catalina.home");
					final File dir = new File(rootPath + File.separator + "tmpFiles");
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					final File serverFile = new File(dir.getAbsolutePath() + File.separator + fileName);
					final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					LOGGER.info("Server File Location=" + serverFile.getAbsolutePath());

					return "You successfully uploaded file=" + fileName;
				} catch (final Exception e) {
					return "You failed to upload " + fileName + " => " + e.getMessage();
				}
			}
		} else {
			LOGGER.info(">>>>>..... Does not exist");
			return "You failed to upload " + fileName + " because the file was empty.";
		}
		return "";
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getUploadVideoForm() {
		LOGGER.info(">>>>>..... Insdide {}.{}", this.getClass().getSimpleName(), "getUploadVideoForm(..)");
		return "uploadForm";
	}

}

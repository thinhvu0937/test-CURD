package edu.poly.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;
 
public class UploadUtils {
	public static String processUploadField(String fieldName, HttpServletRequest request,
			String storeFolder, String storeFilename) throws IOException, ServletException {
		Part filePart  = request.getPart(fieldName);
		if(filePart == null || filePart.getSize() == 0) {
			return "";
		}
		if(storeFolder == null) {
			storeFolder = "/uploads";
		}
		if(storeFilename == null) {
			storeFilename = Path.of(filePart.getSubmittedFileName()).getFileName().toString();
		}else {
			storeFilename += "." + FilenameUtils.getExtension(Path.of(filePart.getSubmittedFileName()).toString());
		}
		String uploadFolder = request.getServletContext().getRealPath(storeFolder);
		
		Path uploadPath = Paths.get(uploadFolder);
		if(!Files.exists(uploadPath)) {
			Files.createDirectory(uploadPath);
		}
		filePart.write(Paths.get(uploadPath.toString(),storeFilename).toString());
		return storeFilename;
	}
}

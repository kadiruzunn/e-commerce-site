package com.bilgeadam.stok2.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	
	public static final String UPLOAD_DIRECTORY = System.getProperty("user.dir") + 
			"/src/main/resources/static/img";
	
	public void save(MultipartFile file) throws IOException {
		
		Path filePath = Paths.get(UPLOAD_DIRECTORY, file.getOriginalFilename());
		Files.write(filePath, file.getBytes());
		
	}

	private final Path root = Paths.get(UPLOAD_DIRECTORY);
	
	public Resource load(String fileName) {
		Path file = root.resolve(fileName);
		Resource res = null;
		
		try {
			res = new UrlResource(file.toUri());
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
			
			

}

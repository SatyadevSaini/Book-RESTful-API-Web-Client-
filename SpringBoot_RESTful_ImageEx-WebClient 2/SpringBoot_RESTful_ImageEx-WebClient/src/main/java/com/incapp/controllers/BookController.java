package com.incapp.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Book;

@Controller
public class BookController {
	String URL="http://localhost:7878/";
	
	RestTemplate restTemplate=new RestTemplate();
	
	@ModelAttribute
	public void commonValue(Model m) {
		m.addAttribute("appName", "Incapp's BOOK APP with Photo");
	}
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/allBooks")
	public String getAllBooks(Model m) {
		String API= "books";
		List<Book> books=restTemplate.getForObject(URL+API, List.class);
		if(!books.isEmpty()) {
			m.addAttribute("allBooks", books);
			return "Books_Details";
		}else {
			m.addAttribute("allBooks", "No Book Exist!");
			return "index";
		}
	}
	
	@PostMapping("/addBook")
	public String addBook(@ModelAttribute Book b,@RequestPart("image1") MultipartFile image1, Model m) {
		String API="book";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		LinkedMultiValueMap<String, Object> data=new LinkedMultiValueMap<>();
		data.add("image1", convert(image1));
		data.add("book", b);
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity=new HttpEntity(data,header);
		
		ResponseEntity<String> result=restTemplate.postForEntity(URL+API,requestEntity, String.class);
		if(result.getStatusCode()==HttpStatus.OK) {
			m.addAttribute("addResult", b.getName()+" Added Successfully!");
		}else {
			m.addAttribute("addResult", b.getName()+" Already Exist!");
		}
		return "index";
	}
	
	//convert MultipartFile to FileSystemResource
	public static FileSystemResource convert(MultipartFile file) {
		File convFile=new File(file.getOriginalFilename());
		try {
			convFile.createNewFile();
			FileOutputStream fos=new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return new FileSystemResource(convFile);
	}
	//end
	
	@RequestMapping("/getPhoto")
	public void getPhoto(String name,HttpServletResponse response) {
		try {
			String API="getPhoto"+"/" + name;
			byte[] b=restTemplate.getForObject(URL+API,byte[].class);
			response.getOutputStream().write(b);
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/updateImage")
	public String updateImage(String name,@RequestPart("image1") MultipartFile image1, Model m) {
		String API="updateImage";
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.MULTIPART_FORM_DATA);
		
		LinkedMultiValueMap<String, Object> data=new LinkedMultiValueMap<>();
		data.add("image1", convert(image1));
		data.add("name", name);
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity=new HttpEntity(data,header);
		
		ResponseEntity<String> result=restTemplate.exchange(URL+API,HttpMethod.PUT,requestEntity, String.class);
		if(result.getStatusCode()==HttpStatus.OK) {
			m.addAttribute("updateResult", "Image Updated Successfully!");
		}else {
			m.addAttribute("updateResult", "Image Updated Failed!");
		}
		
		API= "books";
		List<Book> books=restTemplate.getForObject(URL+API, List.class);
		m.addAttribute("allBooks", books);
		
		return "Books_Details";
	}
	
}

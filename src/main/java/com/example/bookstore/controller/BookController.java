package com.example.bookstore.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookstore.entity.Book;
import com.example.bookstore.service.BookService;


@RestController
@RequestMapping("/book")
public class BookController {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	BookService bookService;

	@GetMapping
	public List<Book> getAllBook() {
		log.info("The whole book is printing.."+ bookService.getAllBook());
		return bookService.getAllBook();
	}
	
	@GetMapping("getById/{id}")
	public ResponseEntity<?>getBookById(@PathVariable Long id) {
		
		if(id==null) {
		return  ResponseEntity.badRequest().body("Please Provide Valid Book Id");
		}
		
		Optional<Book> book = bookService.getBookById(id);
		if(book.isPresent()) {
			return ResponseEntity.ok(book.get());
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found with the ");
		}
	}
	
	@PostMapping
	public String addbook(@RequestBody Book book) {
		if(book!=null) {
		bookService.createBook(book); 
		log.info("Book Added SuccessFully..");
		return "Book is Added SuccessFully..";
		}
		else {
			return "Book can`t Added";
		}
	}
	
	@PutMapping("{id}")
	public String updateBook(@PathVariable Long id, @RequestBody Book book) {
		if(book!=null) {
		bookService.updateBook(id,book);
		return "Book updated successfully..";
		}
		else {
			return "Please provide the updated status..";
		}
		
	}
	
	@DeleteMapping("delete/{id}")
	public String deleteBook(@PathVariable Long id) {
		if(id!=null) {
			bookService.deleteBook(id);
			log.info("Book deleted Successfully");
			return "Book deleted Successfully";
			
		}
		else {
			return "Book can`t deleted..";
		}
	}
	
}

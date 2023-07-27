package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@RestController
public class BookController {

	@Autowired
	private BookRepository bookRepository;

	@GetMapping("/books")
	public List<Book> getAllBooks() {
	return bookRepository.findAll();
	}

	@PostMapping("/books")
	public Book createBook(@RequestBody Book book) {
	return bookRepository.save(book);
	}

	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long bookId) {
	Optional<Book> book = bookRepository.findById(bookId);
	if (book.isPresent()) {
	return ResponseEntity.ok().body(book.get());
	} else {
	return ResponseEntity.notFound().build();
	}
	}

	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId,
	@RequestBody Book bookDetails) {
	Optional<Book> book = bookRepository.findById(bookId);
	if (book.isPresent()) {
	Book existingBook = book.get();
	existingBook.setTitle(bookDetails.getTitle());
	existingBook.setAuthor(bookDetails.getAuthor());
	existingBook.setDescription(bookDetails.getDescription());
	final Book updatedBook = bookRepository.save(existingBook);
	return ResponseEntity.ok(updatedBook);
	} else {
	return ResponseEntity.notFound().build();
	}
	}

	@DeleteMapping("/books/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable(value = "id") Long bookId) {
	Optional<Book> book = bookRepository.findById(bookId);
	if (book.isPresent()) {
	bookRepository.delete(book.get());
	return ResponseEntity.noContent().build();
	} else {
	return ResponseEntity.notFound().build();
	}
	}
	}
	
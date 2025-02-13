package com.example.spring_boot_crud.controller;

import com.example.spring_boot_crud.model.Book;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.spring_boot_crud.repo.BookRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
@Tag(name="Book api", description = "user realed api")
public class Bookcontrol {

    @Autowired
    public BookRepo bookRepo;




@Operation(summary = "get all boooks")
    @GetMapping("/getallbooks")
    public ResponseEntity<List<Book>> getAllbooks(){
        try{
            List<Book> bookList = new ArrayList<Book>();
            bookRepo.findAll().forEach(book -> bookList.add(book));

            if(bookList.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(bookList, HttpStatus.OK);

        } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get book by ID")
    @GetMapping("/getBookbyId/{id}")
    public  ResponseEntity<Book> getbookbyid(@PathVariable Long id){
       Optional <Book> bookData= bookRepo.findById(id);

        if(bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new book")
    @PostMapping("/addbook")
    public ResponseEntity<Book> addbook(@RequestBody Book book) {
        if (book.getTitle() == null || book.getAuthor() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Book savedBook = bookRepo.save(book);

        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    @Operation(summary = "delete a new book")
    @DeleteMapping("/deletebookbyid/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        if (bookRepo.existsById(id)) {
            bookRepo.deleteById(id);
            return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Book not found", HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a book by ID")
    @PutMapping("/updatebook/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        Optional<Book> existingBook = bookRepo.findById(id);

        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());

            Book updatedBook = bookRepo.save(book);
            return new ResponseEntity<>(updatedBook, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



}

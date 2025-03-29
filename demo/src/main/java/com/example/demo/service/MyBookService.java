package com.example.demo.service;

import com.example.demo.entities.MyBook;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyBookService {

    @Autowired
    private BookRepository bookRepository;

    public List<MyBook> getAllBooks() {
        return bookRepository.findAll();
    }

    public MyBook getBookById(long petId) {
        Optional<MyBook> pet = bookRepository.findById(petId);
        return pet.orElse(null);
    }

    public MyBook saveBook(MyBook myBook) {
        return bookRepository.save(myBook);
    }

}

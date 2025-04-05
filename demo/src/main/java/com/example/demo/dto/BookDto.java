package com.example.demo.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookDto {

    @NotEmpty (message = "Författaren är obligatorisk")
    private String author;
    @NotEmpty (message = "Bokens titel är obligatorisk")
    private String title;
    @NotNull (message = "År är obligatorisk")
    private Integer year;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}

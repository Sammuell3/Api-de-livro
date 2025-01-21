package com.example.konkin.DTO;

import lombok.Data;


@Data
public class BookRequestDTO {

   
    private String title;
  
    private String imageUrl;

    private String author;

    private String description;

    private String publisher;

    private String edition;

    private String genre;  // Pode ser um valor String representando o Enum

    private String language;

    private String status;  // Pode ser o valor da string do Enum
  
    private String publishYear;

    private String isbn;
}

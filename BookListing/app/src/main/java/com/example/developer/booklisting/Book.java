package com.example.developer.booklisting;

/**
 * Created by binar on 12/11/2017.
 */

public class Book {
    private String image;
    private String title;
    private String author;
    private String url;


    public  Book(String image,String title,String author,String url)
    {

        this.image=image;
        this.author=author;
        this.title=title;
        this.url=url;

    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
    public  String getUrl() {return url;}
}

package controllers

import models.Book
class BookApi {
    private var books = ArrayList<Book>()

    fun add(book: Book): Boolean{
        return books.add(book)
    }

    fun listAllBooks(): String {
        return if (books.isEmpty()) {
            "No books stored"
        } else {
            var listOfBook = ""
            for (i in books.indices) {
                listOfBook += "${i}: ${books[i]} \n"
            }
            listOfBook
        }
    }
}
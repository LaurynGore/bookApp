package controllers

import models.Book
class BookApi {
    private var books = ArrayList<Book>()

    fun add(book: Book): Boolean{
        return books.add(book)
    }
}
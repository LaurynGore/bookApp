package controllers

import models.Book
import java.util.ArrayList
class BookApi {
    private var books = ArrayList<Book>()

    fun add(book: Book): Boolean{
        return books.add(book)
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, books);
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
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

    fun findBook(index: Int): Book? {
        return if (isValidListIndex(index, books)) {
            books[index]
        } else null
    }
    fun numberOfBooks(): Int {
        return books.size
    }
    fun updateBook(indexToUpdate: Int, book: Book?): Boolean {
        //find the note object by the index number
        val foundBook = findBook(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundBook != null) && (book != null)) {
            foundBook.bookTitle = book.bookTitle
            foundBook.isbn = book.isbn
            foundBook.author = book.author
            foundBook.chapters = book.chapters
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }
}
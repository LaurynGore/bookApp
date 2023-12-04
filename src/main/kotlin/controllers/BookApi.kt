package controllers

import models.Book
import utils.Utilities.formatListString
import java.util.ArrayList

class BookApi() {

    private var books = ArrayList<Book>()
    private var lastId = 0
    private fun getId() = lastId++

    // ----------------------------------------------
    //  CRUD METHODS FOR NOTE ArrayList
    // ----------------------------------------------
    fun add(book: Book): Boolean {
        book.bookId = getId()
        return books.add(book)
    }

    fun delete(id: Int) = books.removeIf { book -> book.bookId == id }

    fun update(id: Int, book: Book?): Boolean {
        // find the note object by the index number
        val foundBook = findBook(id)

        // if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundBook != null) && (book != null)) {
            foundBook.bookTitle = book.bookTitle
            foundBook.bookAuthor = book.bookAuthor
            foundBook.bookId = book.bookId
            return true
        }

        return false
    }

    fun archiveBook(id: Int): Boolean {
        val foundBook = findBook(id)
        if (( foundBook != null) && (!foundBook.isBookArchived)
        //  && ( foundNote.checkNoteCompletionStatus())
        ){
            foundBook.isBookArchived = true
            return true
        }
        return false
    }


    fun listAllBooks() =
        if (books.isEmpty()) "No books stored"
        else formatListString(books)

//    fun listBooksByAuthor() =
//        searchBooksByAuthor(books.toString())
////        if (numberOfBooksByAuthor() == 0) "No books stored"
////        else formatListString(books -> !book)

    fun listActiveBooks() =
        if (numberOfActiveBooks() == 0) "No active books stored"
        else formatListString(books.filter { book -> !book.isBookArchived })

    fun listArchivedBooks() =
        if (numberOfArchivedBooks() == 0) "No archived notes stored"
        else formatListString(books.filter { book -> book.isBookArchived })

    fun numberOfBooks() = books.size
    fun numberOfArchivedBooks(): Int = books.count { book: Book -> book.isBookArchived }
    fun numberOfActiveBooks(): Int = books.count { book: Book -> !book.isBookArchived }




    fun findBook(bookId: Int) = books.find { book -> book.bookId == bookId }

    fun searchBooksByAuthor(searchString: String) =
        formatListString(books.filter { book -> book.bookAuthor.contains(searchString, ignoreCase = true) })
}


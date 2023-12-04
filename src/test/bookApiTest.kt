package controllers

import models.Book
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertEquals

class BookAPITest {

    private var bookId: Book? = null
    private var bookTitle: Book? = null
    private var bookAuthor: Book? = null
    private var bookPriority: Book? = null
    private var bookGenre: Book? = null
    private var isBookArchived: BookAPI? = BookAPI()
    private var chapters: BookAPI? = BookAPI()

    @BeforeEach
    fun setup(){
        bookId = Book("Learning Kotlin", 5, "College", false)
        bookTitle = Book("Summer Holiday to France", 1, "Holiday", false)
        bookAuthor = Book("Code App", 4, "Work", true)
        bookPriority = Book("Test App", 4, "Work", false)
        bookGenre = Book("Swim - Pool", 3, "Hobby", true)

        //adding 5 Note to the notes api
        populatedNotes!!.add(bookId)
        populatedNotes!!.add(bookTitle)
        populatedNotes!!.add(bookAuthor)
        populatedNotes!!.add(bookPriority)
        populatedNotes!!.add(bookGenre)
    }

    @AfterEach
    fun tearDown(){
        bookId = null
        bookTitle = null
        bookAuthor = null
        bookPriority = null
        bookGenre = null
        populatedNotes = null
        emptyNotes = null
    }



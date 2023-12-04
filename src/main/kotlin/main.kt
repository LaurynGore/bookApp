import controllers.BookApi
import models.Chapters
import models.Book
//import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import kotlin.system.exitProcess

private val bookApi = BookApi()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addBook()
            2 -> listBooks()
            3 -> updateBook()
            4 -> deleteBook()
            //6 -> addItemToNote()
            //7 -> updateItemContentsInNote()
            //8 -> deleteAnItem()
            //9 -> markItemStatus()
            10 -> searchBooks()
            //15 -> searchItems()
            //16 -> listToDoItems()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  NOTE KEEPER APP                  |
         > -----------------------------------------------------  
         > | Book MENU                                         |
         > |   1) Add a book                                   |
         > |   2) List books                                   |
         > |   3) Update a book                                |
         > |   4) Delete a book                                |
         > -----------------------------------------------------  
         > | Book MENU                                         | 
         > |   6) Add chapter to a book                        |
         > |   7) Update chapter contents on a note            |
         > |   8) Delete chapter from a note                   |
         > |   9) Search for all books (by book author)        |  
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
//NOTE MENU
//------------------------------------
fun addBook() {
    val bookTitle = readNextLine("Enter a title for the book: ")
    val bookAuthor = readNextLine("Enter an Author: ")
    val isbn = readNextLine("Enter the isbn: ")
    val isAdded = bookApi.add(Book(bookTitle = bookTitle, bookAuthor = bookAuthor, isbn = isbn))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listBooks() {
    if (bookApi.numberOfBooks() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL books          |
                  > |   2) View books by Author    |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllBooks()
            2 -> listBooksByAuthor()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllBooks() = println(bookApi.listAllBooks())
fun listBooksByAuthor() = println(bookApi.listBooksByAuthor())

fun updateBook() {
    listBooks()
    if (bookApi.numberOfBooks() > 0) {
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the note to update: ")
        if (bookApi.findBook(id) != null) {
            val bookTitle = readNextLine("Enter a title for the note: ")
            val bookAuthor = readNextLine("Enter an author")
            val isbn = readNextLine("Enter a category for the note: ")

            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (bookApi.update(id, Book(0, bookTitle, bookAuthor, isbn))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no books for this index number")
        }
    }
}

fun deleteBook() {
    listBooks()
    if (bookApi.numberOfBooks() > 0) {
        // only ask the user to choose the note to delete if notes exist
        val id = readNextInt("Enter the id of the note to delete: ")
        // pass the index of the note to NoteAPI for deleting and check for success.
        val bookToDelete = bookApi.delete(id)
        if (bookToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}


//-------------------------------------------
//ITEM MENU (only available for active notes)
//-------------------------------------------

//TODO

fun searchBooks() {
    val searchAuthor = readNextLine("Enter the author you want to search by: ")
    val searchResults = bookApi.searchBooksByAuthor(searchAuthor)
    if (searchResults.isEmpty()) {
        println("No Books found")
    } else {
        println(searchResults)
    }
}
fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}






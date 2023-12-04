import controllers.BookApi
import models.Chapter
import models.Book
import utils.ScannerInput.readNextChar
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
            5 -> archiveBook()
            6 -> addChapterToBook()
            7 -> updateChapterContentsInBook()
            8 -> deleteAChapter()
            9 -> markChapterStatus()
            10 -> searchBooks()
            11 -> searchBooksAuthor()
//            15 -> searchChapters()
            16 -> listActiveBooks()
            17 -> listArchivedBooks()
            0 -> exitApp()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  BOOK KEEPER APP                  |
         > -----------------------------------------------------  
         > | BOOK MENU                                         |
         > |   1) Add a book                                   |
         > |   2) List books                                   |
         > |   3) Update a book                                |
         > |   4) Delete a book                                |
         > |   5) Archive a book                               |
         > -----------------------------------------------------  
         > | CHAPTER MENU                                      | 
         > |   6) Add chapter to a book                        |
         > |   7) Update chapter contents on a book            |
         > |   8) Delete chapter from a book                   |
         > -----------------------------------------------------  
         > | REPORT MENU FOR BOOKS                             | 
         > |   10) Search for all books (by book title)        |
         > |   11) Search for all books (by book Author)       |
         > -----------------------------------------------------  
         > | REPORT MENU FOR CHAPTERS                          |                                
         > |   15) Search for all Chapters                     |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

fun addBook() {
    val bookTitle = readNextLine("Enter a title of the book: ")
    val bookPriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val bookGenre = readNextLine("Enter a genre: ")
    val bookAuthor = readNextLine("Enter an author: ")
    val isAdded = bookApi.add(Book(
        bookTitle = bookTitle, bookPriority = bookPriority.toString(),
        bookGenre = bookGenre, bookAuthor = bookAuthor ))

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
                  > |   1) View ALL notes          |
                  > |   2) View ACTIVE notes       |
                  > |   3) View ARCHIVED notes     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllBooks()
            2 -> listActiveBooks()
            3 -> listArchivedBooks()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
    }
}

fun listAllBooks() = println(bookApi.listAllBooks())
fun listActiveBooks() = println(bookApi.listActiveBooks())
fun listArchivedBooks() = println(bookApi.listArchivedBooks())

fun exitApp() {
    println("Exiting...bye")
    exitProcess(0)
}
fun updateBook() {
    listBooks()
    if (bookApi.numberOfBooks() > 0) {
        // only ask the user to choose the note if notes exist
        val id = readNextInt("Enter the id of the book to update: ")
        if (bookApi.findBook(id) != null) {
            val bookTitle = readNextLine("Enter a title for the note: ")
            val bookAuthor = readNextLine("Enter an author: ")
            val bookPriority = readNextLine("Enter a priority (1-low, 2, 3, 4, 5-high): ")
            val bookGenre = readNextLine("Enter a category for the note: ")

            // pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (bookApi.update(id, Book(0, bookTitle, bookAuthor, bookPriority,bookGenre))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}

fun deleteBook() {
    listBooks()
    if (bookApi.numberOfBooks() > 0) {
        val id = readNextInt("Enter the id of the book to delete: ")
        val bookToDelete = bookApi.delete(id)
        if (bookToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveBook() {
    listActiveBooks()
    if (bookApi.numberOfActiveBooks() > 0) {
        // only ask the user to choose the note to archive if active notes exist
        val id = readNextInt("Enter the id of the book to archive: ")
        // pass the index of the note to NoteAPI for archiving and check for success.
        if (bookApi.archiveBook(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

//-------------------------------------------
//ITEM MENU (only available for active notes)
//-------------------------------------------
private fun addChapterToBook() {
    val book: Book? = askUserToChooseActiveBook()
    if (book != null) {
        if (book.addChapter(Chapter(chapterContents = readNextLine("\t Item Contents: "), chapterNum = readNextInt("\t Chapter Number: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

fun updateChapterContentsInBook() {
    val book: Book? = askUserToChooseActiveBook()
    if (book != null) {
        val chapter: Chapter? = askUserToChooseChapter(book)
        if (chapter != null) {
            val newContents = readNextLine("Enter new contents: ")
            if (book.update(chapter.chapterNum, newChapter = chapter)) {
                println("Item contents updated")
            } else {
                println("Item contents NOT updated")
            }
        } else {
            println("Invalid Item Id")
        }
    }
}

fun deleteAChapter() {
    val book: Book? = askUserToChooseActiveBook()
    if (book != null) {
        val chapter: Chapter? = askUserToChooseChapter(book)
        if (chapter != null) {
            val isDeleted = book.delete(chapter.chapterNum)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

fun markChapterStatus() {
    val book: Book? = askUserToChooseActiveBook()
    if (book != null) {
        val chapter: Chapter? = askUserToChooseChapter(book)
        if (chapter != null) {
            var changeStatus = 'X'
            if (chapter.isChapterComplete) {
                changeStatus = readNextChar("The item is currently complete...do you want to mark it as TODO?")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    chapter.isChapterComplete = false
            }
            else {
                changeStatus = readNextChar("The item is currently TODO...do you want to mark it as Complete?")
                if ((changeStatus == 'Y') ||  (changeStatus == 'y'))
                    chapter.isChapterComplete = true
            }
        }
    }
}

fun searchBooks() {
    val searchTitle = readNextLine("Enter the description to search by: ")
    val searchResults = bookApi.searchBooksByTitle(searchTitle)
    if (searchResults.isEmpty()) {
        println("No boolss found")
    } else {
        println(searchResults)
    }
}

fun searchBooksAuthor() {
    val searchAuthor = readNextLine("Enter the author: ")
    val searchResults = bookApi.searchBooksByAuthor(searchAuthor)
    if (searchResults.isEmpty()) {
        println("No books found")
    } else {
        println(searchResults)
    }
}

//------------------------------------
//ITEM REPORTS MENU
//------------------------------------
//fun searchChapters() {
//    val searchContents = readNextLine("Enter the item contents to search by: ")
//    val searchResults = bookApi.searchChapterByContents(searchContents)
//    if (searchResults.isEmpty()) {
//        println("No chapters found")
//    } else {
//        println(searchResults)
//    }
//}
//
//fun searchChapterByContents(searchString: String): String {
//    return if (numberOfBooks() == 0) "No Books stored"
//    else {
//        var listOfBooks = ""
//        for (book in Book) {
//            for (chapter in book.chapter) {
//                if (chapter.itemContents.contains(searchString, ignoreCase = true)) {
//                    listOfBooks += "${book.bookId}: ${book.bookTitle} \n\t${chapter}\n"
//                }
//            }
//        }
//        if (listOfBooks == "") "No items found for: $searchString"
//        else listOfBooks
//    }
//}
//------------------------------------
// Exit App
//------------------------------------


//------------------------------------
//HELPER FUNCTIONS
//------------------------------------

private fun askUserToChooseActiveBook(): Book? {
    listActiveBooks()
    if (bookApi.numberOfActiveBooks() > 0) {
        val book = bookApi.findBook(readNextInt("\nEnter the id of the book: "))
        if (book != null) {
            if (book.isBookArchived) {
                println("Note is NOT Active, it is Archived")
            } else {
                return book
            }
        } else {
            println("Note id is not valid")
        }
    }
    return null
}

private fun askUserToChooseChapter(book: Book): Chapter? {
    if (book.numberOfChapters() > 0) {
        print(book.listItems())
        return book.findOne(readNextInt("\nEnter the chapter number : "))
    }
    else {
        println("No items for chosen note")
        return null
    }
}






import controllers.BookApi
import models.Book
import models.Chapters
import utils.ScannerInput
import mu.KotlinLogging
import kotlin.math.ln
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

private val logger = KotlinLogging.logger {}
private val book = BookApi()
fun main(){
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        Book APP         |
         > ----------------------------------
         > | Book MENU                      |
         > |   1) Add a book                |
         > |   2) List all books            |
         > |   3) Update a book             |
         > |   4) Delete a book             |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu(){
    do {
        val option = mainMenu()
        when (option) {
            1  -> addBook()
            2  -> listBooks()
            3  -> updateBook()
            4  -> deleteBook()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addBook(){
    return println("Add book")
}

fun listBooks(){
    return println("List books")
}

fun updateBook(){
    return println("Update a book")
}

fun deleteBook(){
    return println("Delete a book")
}

fun exitApp(){
    logger.info { "exitApp() function invoked" }
    System.exit(0)
}

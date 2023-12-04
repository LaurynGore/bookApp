package models

data class Book(var bookId: Int = 0,
                var bookTitle: String,
                var bookAuthor: String,
                var isbn: String,
                var chapters : MutableSet<Chapters> = mutableSetOf()) {

// functions to manage the items set will go in here
}
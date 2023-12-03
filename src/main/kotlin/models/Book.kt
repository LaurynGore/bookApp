package models

import utils.Utilities
class Book(
    private var books: ArrayList<Book> = ArrayList<Book>(),
    var bookTitle: String,
    var isbn: Int,
    var author: String,
    var chapters: MutableSet<Chapters> = mutableSetOf()){
    private var lastItemId = 0
    private fun getItemId() = lastItemId++

    fun add(book: Book): Boolean{
        return books.add(book)
    }
    fun numberOfItems() = chapters.size

    fun findOne(number: Number): Chapters?{
        return chapters.find{ chapter -> chapter.number == number }
    }
    fun update(id: Int, chapters: Chapters): Boolean {
        val foundChapter = findOne(id)

        if (foundChapter != null){
            foundChapter.chapterTitle = chapters.chapterTitle
            foundChapter.number = chapters.number
            foundChapter.numPages = chapters.numPages
            foundChapter.numWords = chapters.numWords
            foundChapter.includesPictures = chapters.includesPictures

            return true
        }

        return false
    }

    fun listChapters() =
        if (chapters.isEmpty())  "\tNO ITEMS ADDED"
        else  Utilities.formatSetString(chapters)
}



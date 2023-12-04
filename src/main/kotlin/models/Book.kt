package models

import utils.Utilities
import controllers.BookApi
data class Book(
    var bookId: Int = 0,
    var bookTitle: String,
    var bookAuthor:String,
    var bookPriority: String,
    var bookGenre: String,
    var isBookArchived: Boolean = false,
    var chapters: MutableSet<Chapter> = mutableSetOf())
{
    private var lastItemId = 0
    private fun getItemId() = lastItemId++

    fun addChapter(chapters: Chapter) : Boolean {
        chapters.chapterNum = getItemId()
        return chapters.add(chapters)
    }

    fun numberOfChapters() = chapters.size

    fun findOne(id: Int): Chapter?{
        return chapters.find{ chapter -> chapter.chapterId == id }
    }

    fun delete(id: Int): Boolean {
        return chapters.removeIf { chapter -> chapter.chapterId == id}
    }

    fun update(id: Int, newChapter: Chapter): Boolean {
        val foundChapter = findOne(id)

        //if the object exists, use the details passed in the newItem parameter to
        //update the found object in the Set
        if (foundChapter != null){
            foundChapter.chapterContents = newChapter.chapterContents
            foundChapter.isChapterComplete = newChapter.isChapterComplete
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    fun checkBookCompletionStatus(): Boolean {
        if (chapters.isNotEmpty()) {
            for (chapter in chapters) {
                if (!chapter.isChapterComplete) {
                    return false
                }
            }
        }
        return true
    }

    fun listItems() =
        if (chapters.isEmpty())  "\tNO CHAPTERS ADDED"
        else  Utilities.formatSetString(chapters)

    override fun toString(): String {
        val archived = if (isBookArchived) 'Y' else 'N'
        return "$bookId: $bookTitle, Priority($bookPriority), Genre($bookGenre), Archived($archived) \n${listItems()}"
    }

}
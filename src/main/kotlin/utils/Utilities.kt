package utils

import models.Chapters
import models.Book

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(notesToFormat: List<Book>): String =
        notesToFormat
            .joinToString(separator = "\n") { book ->  "$book" }

    @JvmStatic
    fun formatSetString(itemsToFormat: Set<Chapters>): String =
        itemsToFormat
            .joinToString(separator = "\n") { chapter ->  "\t$chapter" }

}

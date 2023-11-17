package models

data class Book (   val bookTitle: String,
                    val isbn: Int,
                    val author: String,
                    val chapters: Chapters
    )
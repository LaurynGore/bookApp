package models

data class Chapters (   val number: Int,
                        val chapterTitle: String,
                        val numPages: Int,
                        val numWords: Int,
                        val includesPictures: Boolean
    )
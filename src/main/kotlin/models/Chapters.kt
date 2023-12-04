package models

data class Chapter (var chapterId: Int =0 ,var chapterNum: Int, var chapterContents : String, var isChapterComplete: Boolean = false){

    override fun toString(): String {

        if (isChapterComplete)
            return "$chapterNum: $chapterContents (Complete)"
        else
            return "chapterNum: $chapterContents (TODO)"
    }

}
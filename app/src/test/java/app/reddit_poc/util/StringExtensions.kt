package app.reddit_poc.util

fun String.toJson(): String =
    javaClass.getResource(this)?.readBytes()?.let {
        String(it)
    }.toString()

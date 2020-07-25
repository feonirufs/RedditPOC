package app.reddit_poc.util

fun String.asJson(): String = javaClass.getResource(this)?.readBytes()?.let {
    String(it)
}.toString()

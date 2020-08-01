package app.re_eddit.util

fun String.toJson(): String =
    javaClass.getResource(this)?.readBytes()?.let {
        String(it)
    }.toString()

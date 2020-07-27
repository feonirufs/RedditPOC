package app.reddit_poc

import androidx.test.platform.app.InstrumentationRegistry

fun String.toJson() = InstrumentationRegistry.getInstrumentation().context.assets
    .open(this)
    .readBytes().let {
        String(it)
    }.toString()

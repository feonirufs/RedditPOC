package app.re_eddit.core.ext

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd' at 'HH:mm:ss", Locale.getDefault())
    val date = Date(this * 1000)
    return sdf.format(date)
}

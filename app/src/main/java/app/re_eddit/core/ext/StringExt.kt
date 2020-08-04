@file:Suppress("NOTHING_TO_INLINE")

package app.re_eddit.core.ext

inline fun String.nullIfBlank() = if (isBlank()) null else this


package com.erdemserhat.util

import java.util.regex.Pattern

fun isUUIDFormat(input: String): Boolean {
    val pattern = Pattern.compile(
        "^([0-9a-fA-F]){8}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){12}$"
    )
    return pattern.matcher(input).matches()
}
package com.example.myrecipe.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable fun highlightSearch(fulltext:String,query:String):AnnotatedString {
    val annotated = remember(fulltext, query) {
        buildAnnotatedString {
            val starti = fulltext.indexOf(query, ignoreCase = true)
            if (starti >= 0) {
                append(fulltext.substring(0, starti))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(fulltext.substring(starti, starti + query.length))
                }
                append(fulltext.substring(starti + query.length))
            } else
                append(fulltext)
        }

    }
    return annotated
}


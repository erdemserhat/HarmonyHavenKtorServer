package com.erdemserhat.models

import kotlinx.serialization.Serializable


@Serializable
data class Quote(
    val id:Int,
    val quote:String,
    val writer:String,
    val imageUrl:String,
    val quoteCategory:Int =1
){
    fun convertToQuoteResponse(isLiked:Boolean = false): QuoteResponse{
        return QuoteResponse(
            id = this.id,
            quote = this.quote,
            writer = this.writer,
            imageUrl = this.imageUrl,
            quoteCategory = this.quoteCategory,
            isLiked = isLiked


        )

    }
}

@Serializable
data class QuoteResponse(
    val id:Int,
    val quote:String,
    val writer:String,
    val imageUrl:String,
    val quoteCategory:Int =1,
    val isLiked:Boolean = false
)



package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transaction(
    val transactionId : String? = "-",
    val amount : Float? = 0F,
    val transactionDate : String? = "-",
    val description : String? = "-",
    val transactionType : String? = "-",
    val receipient : Receipient? = null,
    val sender : Sender? = null
)

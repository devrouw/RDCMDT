package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transactions(
    val status : String? = "-",
    val data : List<Transaction>? = null,
)

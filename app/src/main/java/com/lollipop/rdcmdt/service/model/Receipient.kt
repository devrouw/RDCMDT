package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Receipient(
    val accountNo : String? = "-",
    val accountHolder : String? = "-"
)

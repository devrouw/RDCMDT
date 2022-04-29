package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Payee(
    val id : String? = "-",
    val name : String? = "-",
    val accountNo : String? = "-"
)

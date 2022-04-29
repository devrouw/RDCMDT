package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Payees(
    val status : String? = "-",
    val data : List<Payee>? = null
)

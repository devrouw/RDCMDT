package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Balance(
    val status : String? = "-",
    val accountNo : String? = "-",
    val balance : Float? = 0F
)

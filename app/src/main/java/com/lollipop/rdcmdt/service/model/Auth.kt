package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Auth(
    val status : String? = "-",
    val token : String? = "-",
    val username : String? = "-",
    val accountNo: String? = "-"
)
package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transfer(
    val status : String? = "-",
    val transactionId : String? = "-",
    val amount : Float? = 0F,
    val description : String? = "-",
    val recipientAccount : String? = "-"
)

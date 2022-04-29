package com.lollipop.rdcmdt.service.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Transfer(
    val status : String? = "-",
    val transactionId : String? = "-",
    val amount : Long? = 0,
    val description : String? = "-",
    val recipientAccount : String? = "-"
)

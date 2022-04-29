package com.lollipop.rdcmdt.helper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

object DummyTestData {
    fun body(): RequestBody {
        val raw = "{'x':'x'}"
        return raw.toRequestBody("application/json".toMediaTypeOrNull())
    }
}
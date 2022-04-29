package com.lollipop.rdcmdt.helper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

object ConstructRawRequest {
    fun constructRawRequest(jsonObj: JSONObject): RequestBody {
        return jsonObj.toString().toRequestBody("application/json".toMediaTypeOrNull())
    }
}
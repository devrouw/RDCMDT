package com.lollipop.rdcmdt.service.network

import com.lollipop.rdcmdt.helper.Constant
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ResultOfNetwork<T> {
    return try {
        val response = call()
        if (response.isSuccessful) {
            Timber.d("safeapi success")
            val body = response.body()
            if (response.code() == Constant.Network.REQUEST_NO_CONTENT) {
                Timber.d("safeapi empty")
                ResultOfNetwork.Empty("")
            }
            else {
                Timber.d("safeapi not empty")
                ResultOfNetwork.Success(body!!)
            }
        } else {
            Timber.d("safeapi ${response.code()}")
            ResultOfNetwork.ApiFailed(code = response.code(), response.message(), null)
        }
    } catch (throwable: Throwable) {
        when (throwable) {
            is HttpException -> {
                Timber.d("safeapi http exception")
                ResultOfNetwork.ApiFailed(
                    throwable.code(),
                    "[HTTP] error ${throwable.message} please retry",
                    throwable
                )
            }
            else -> {
                Timber.d("safeapi unknown error ${throwable}")
                ResultOfNetwork.UnknownError(
                    throwable
                )
            }
        }
    }
}
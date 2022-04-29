package com.lollipop.rdcmdt.repository.register

import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.network.ApiNetwork
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.service.network.safeApiCall
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegisterRepository @Inject constructor(
    private val service: ApiNetwork
) : IRegisterRepository {
    override suspend fun post(body: RequestBody): ResultOfNetwork<Auth> =
        safeApiCall { service.register(body) }
}
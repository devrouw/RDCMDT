package com.lollipop.rdcmdt.repository.login

import com.lollipop.rdcmdt.helper.ConstructRawRequest
import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.network.ApiNetwork
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.service.network.safeApiCall
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
private val service: ApiNetwork
) : ILoginRepository {
    override suspend fun post(username: String, password: String): ResultOfNetwork<Auth> {
        val bodyRequest = JSONObject().let { obj ->
            obj.put("username", username)
            obj.put("password", password)
            ConstructRawRequest.constructRawRequest(obj)
        }
        return safeApiCall { service.login(bodyRequest) }
    }
}
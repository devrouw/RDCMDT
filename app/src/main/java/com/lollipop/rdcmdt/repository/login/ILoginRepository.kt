package com.lollipop.rdcmdt.repository.login

import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import okhttp3.RequestBody

interface ILoginRepository {
    suspend fun post(username: String, password: String) : ResultOfNetwork<Auth>
}
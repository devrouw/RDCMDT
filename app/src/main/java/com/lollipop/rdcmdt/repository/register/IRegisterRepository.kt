package com.lollipop.rdcmdt.repository.register

import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import okhttp3.RequestBody

interface IRegisterRepository {
    suspend fun post(body: RequestBody) : ResultOfNetwork<Auth>
}
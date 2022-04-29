package com.lollipop.rdcmdt.repository.transfer

import com.lollipop.rdcmdt.service.model.Transfer
import com.lollipop.rdcmdt.service.network.ApiNetwork
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.service.network.safeApiCall
import okhttp3.RequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRepository @Inject constructor(
    private val service: ApiNetwork
) : ITransferRepository {
    override suspend fun post(body: RequestBody, token: String): ResultOfNetwork<Transfer> =
        safeApiCall { service.transfer(token, body) }

}
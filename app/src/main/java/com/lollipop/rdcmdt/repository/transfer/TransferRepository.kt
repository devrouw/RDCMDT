package com.lollipop.rdcmdt.repository.transfer

import com.lollipop.rdcmdt.helper.ConstructRawRequest
import com.lollipop.rdcmdt.service.model.Transfer
import com.lollipop.rdcmdt.service.network.ApiNetwork
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.service.network.safeApiCall
import okhttp3.RequestBody
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransferRepository @Inject constructor(
    private val service: ApiNetwork
) : ITransferRepository {
    override suspend fun post(accountNo: String, amount: Float, description: String, token: String): ResultOfNetwork<Transfer> {
        val bodyRequest = JSONObject().let { obj ->
            obj.put("receipientAccountNo", accountNo)
            obj.put("amount", amount)
            obj.put("description", description)
            ConstructRawRequest.constructRawRequest(obj)
        }
        return safeApiCall { service.transfer(token, bodyRequest) }
    }

}
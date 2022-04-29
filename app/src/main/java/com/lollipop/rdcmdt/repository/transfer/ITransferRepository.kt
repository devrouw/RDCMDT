package com.lollipop.rdcmdt.repository.transfer

import com.lollipop.rdcmdt.service.model.Transactions
import com.lollipop.rdcmdt.service.model.Transfer
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import okhttp3.RequestBody

interface ITransferRepository {
    suspend fun post(accountNo: String, amount: Float, description: String, token: String) : ResultOfNetwork<Transfer>
}
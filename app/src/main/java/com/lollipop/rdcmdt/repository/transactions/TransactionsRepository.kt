package com.lollipop.rdcmdt.repository.transactions

import com.lollipop.rdcmdt.service.model.Transactions
import com.lollipop.rdcmdt.service.network.ApiNetwork
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.service.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionsRepository @Inject constructor(
    private val service: ApiNetwork
) : ITransactionsRepository {
    override suspend fun get(token: String): ResultOfNetwork<Transactions> =
        safeApiCall { service.transactions(token) }

}
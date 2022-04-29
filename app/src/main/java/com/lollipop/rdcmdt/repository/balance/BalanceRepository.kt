package com.lollipop.rdcmdt.repository.balance

import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.network.ApiNetwork
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.service.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BalanceRepository @Inject constructor(
    private val service: ApiNetwork
) : IBalanceRepository {
    override suspend fun get(token: String): ResultOfNetwork<Balance> =
        safeApiCall { service.balance(token) }
}
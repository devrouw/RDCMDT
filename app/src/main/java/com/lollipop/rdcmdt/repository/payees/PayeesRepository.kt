package com.lollipop.rdcmdt.repository.payees

import com.lollipop.rdcmdt.service.model.Payees
import com.lollipop.rdcmdt.service.network.ApiNetwork
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.service.network.safeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PayeesRepository @Inject constructor(
    private val service: ApiNetwork
) : IPayeesRepository {
    override suspend fun get(token: String): ResultOfNetwork<Payees> =
        safeApiCall { service.payees(token) }
}
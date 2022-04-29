package com.lollipop.rdcmdt.repository.balance

import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.network.ResultOfNetwork

interface IBalanceRepository {
    suspend fun get(token: String) : ResultOfNetwork<Balance>
}
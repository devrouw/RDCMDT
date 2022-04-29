package com.lollipop.rdcmdt.repository.payees

import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.model.Payees
import com.lollipop.rdcmdt.service.network.ResultOfNetwork

interface IPayeesRepository {
    suspend fun get(token: String) : ResultOfNetwork<Payees>
}
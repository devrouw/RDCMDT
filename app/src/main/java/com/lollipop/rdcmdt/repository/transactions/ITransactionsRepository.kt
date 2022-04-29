package com.lollipop.rdcmdt.repository.transactions

import com.lollipop.rdcmdt.service.model.Payees
import com.lollipop.rdcmdt.service.model.Transactions
import com.lollipop.rdcmdt.service.network.ResultOfNetwork

interface ITransactionsRepository {
    suspend fun get(token: String) : ResultOfNetwork<Transactions>
}
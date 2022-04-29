package com.lollipop.rdcmdt.repository.datastore

interface IDataStoreRepository {
    suspend fun saveAuth(token: String, username: String, accountNo: String)
    suspend fun resetAuth()
}
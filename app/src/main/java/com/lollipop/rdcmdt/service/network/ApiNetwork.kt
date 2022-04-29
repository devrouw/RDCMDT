package com.lollipop.rdcmdt.service.network

import com.lollipop.rdcmdt.service.model.*
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiNetwork {

    @Headers("Content-Type: application/json")
    @POST("register")
    suspend fun register(
        @Body body: RequestBody
    ) : Response<Auth>

    @Headers("Content-Type: application/json")
    @POST("login")
    suspend fun login(
        @Body body: RequestBody
    ) : Response<Auth>

    @Headers("Content-Type: application/json")
    @POST("transfer")
    suspend fun transfer(
        @Header("Authorization") bearer : String,
        @Body body: RequestBody
    ) : Response<Transfer>

    @GET("balance")
    suspend fun balance(
        @Header("Authorization") bearer : String,
    ) : Response<Balance>

    @GET("payees")
    suspend fun payees(
        @Header("Authorization") bearer : String,
    ) : Response<Payees>

    @GET("transactions")
    suspend fun transactions(
        @Header("Authorization") bearer : String,
    ) : Response<Transactions>
}
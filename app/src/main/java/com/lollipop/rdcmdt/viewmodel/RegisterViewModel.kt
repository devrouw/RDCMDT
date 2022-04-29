package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.rdcmdt.di.abstraction.IoDispatcher
import com.lollipop.rdcmdt.helper.ConstructRawRequest
import com.lollipop.rdcmdt.repository.register.RegisterRepository
import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val registerResponse: LiveData<ResultOfNetwork<Auth>> get() = _registerResponse
    private val _registerResponse = MutableLiveData<ResultOfNetwork<Auth>>()

    fun register(username: String, password: String){
        val bodyRequest = JSONObject().let { obj ->
            obj.put("username", username)
            obj.put("password", password)
            ConstructRawRequest.constructRawRequest(obj)
        }

        viewModelScope.launch(dispatcher) {
            try {
                _registerResponse.postValue(ResultOfNetwork.Loading(true))
                _registerResponse.postValue(repository.post(bodyRequest))
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        _registerResponse.postValue(
                            ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                        throw CancellationException()
                    }
                    else -> _registerResponse.postValue(
                        ResultOfNetwork.UnknownError(
                        throwable
                    ))
                }
            }
        }
    }
}
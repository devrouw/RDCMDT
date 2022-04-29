package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.rdcmdt.di.abstraction.IoDispatcher
import com.lollipop.rdcmdt.helper.ConstructRawRequest.constructRawRequest
import com.lollipop.rdcmdt.repository.login.LoginRepository
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
class LoginViewModel @Inject constructor(
private val repository: LoginRepository,
@IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val loginResponse: LiveData<ResultOfNetwork<Auth>> get() = _loginResponse
    private val _loginResponse = MutableLiveData<ResultOfNetwork<Auth>>()

    fun login(username: String, password: String){

        viewModelScope.launch(dispatcher) {
            try {
                _loginResponse.postValue(ResultOfNetwork.Loading(true))
                _loginResponse.postValue(repository.post(username, password))
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        _loginResponse.postValue(
                            ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                        throw CancellationException()
                    }
                    else -> _loginResponse.postValue(ResultOfNetwork.UnknownError(
                        throwable
                    ))
                }
            }
        }
    }
}
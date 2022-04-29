package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.rdcmdt.di.abstraction.IoDispatcher
import com.lollipop.rdcmdt.repository.balance.BalanceRepository
import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val repository: BalanceRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val balanceResponse: LiveData<ResultOfNetwork<Balance>> get() = _balanceResponse
    private val _balanceResponse = MutableLiveData<ResultOfNetwork<Balance>>()

    fun balance(token: String){
        viewModelScope.launch(dispatcher) {
            try {
                _balanceResponse.postValue(ResultOfNetwork.Loading(true))
                _balanceResponse.postValue(repository.get(token))
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        _balanceResponse.postValue(
                            ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                        throw CancellationException()
                    }
                    else -> _balanceResponse.postValue(
                        ResultOfNetwork.UnknownError(
                            throwable
                        ))
                }
            }
        }
    }
}
package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.rdcmdt.di.abstraction.IoDispatcher
import com.lollipop.rdcmdt.repository.transactions.TransactionsRepository
import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.model.Transactions
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val repository: TransactionsRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val transactionsResponse: LiveData<ResultOfNetwork<Transactions>> get() = _transactionsResponse
    private val _transactionsResponse = MutableLiveData<ResultOfNetwork<Transactions>>()

    fun transactions(token: String){
        viewModelScope.launch(dispatcher) {
            try {
                _transactionsResponse.postValue(ResultOfNetwork.Loading(true))
                _transactionsResponse.postValue(repository.get(token))
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        _transactionsResponse.postValue(
                            ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                        throw CancellationException()
                    }
                    else -> _transactionsResponse.postValue(
                        ResultOfNetwork.UnknownError(
                            throwable
                        ))
                }
            }
        }
    }
}
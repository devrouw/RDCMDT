package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.rdcmdt.di.abstraction.IoDispatcher
import com.lollipop.rdcmdt.helper.ConstructRawRequest
import com.lollipop.rdcmdt.repository.transfer.TransferRepository
import com.lollipop.rdcmdt.service.model.Transactions
import com.lollipop.rdcmdt.service.model.Transfer
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
class TransferViewModel @Inject constructor(
    private val repository: TransferRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {
    val transferResponse: LiveData<ResultOfNetwork<Transfer>> get() = _transferResponse
    private val _transferResponse = MutableLiveData<ResultOfNetwork<Transfer>>()

    fun transfer(accountNo: String, amount: Float, description: String, token: String){
        viewModelScope.launch(dispatcher) {
            try {
                _transferResponse.postValue(ResultOfNetwork.Loading(true))
                _transferResponse.postValue(repository.post(accountNo, amount, description, token))
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        _transferResponse.postValue(
                            ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                        throw CancellationException()
                    }
                    else -> _transferResponse.postValue(
                        ResultOfNetwork.UnknownError(
                            throwable
                        ))
                }
            }
        }
    }
}
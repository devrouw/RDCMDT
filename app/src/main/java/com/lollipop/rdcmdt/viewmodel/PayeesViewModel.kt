package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.rdcmdt.di.abstraction.IoDispatcher
import com.lollipop.rdcmdt.repository.payees.PayeesRepository
import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.model.Payees
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class PayeesViewModel @Inject constructor(
    private val repository: PayeesRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    val payeesRepository: LiveData<ResultOfNetwork<Payees>> get() = _payeesRepository
    private val _payeesRepository = MutableLiveData<ResultOfNetwork<Payees>>()

    fun payees(token: String){
        viewModelScope.launch(dispatcher) {
            try {
                _payeesRepository.postValue(ResultOfNetwork.Loading(true))
                _payeesRepository.postValue(repository.get(token))
            }catch (throwable: Throwable){
                when(throwable){
                    is HttpException -> {
                        _payeesRepository.postValue(
                            ResultOfNetwork.ApiFailed(
                                throwable.code(),
                                "[HTTP] error ${throwable.message} please retry",
                                throwable
                            )
                        )
                        throw CancellationException()
                    }
                    else -> _payeesRepository.postValue(
                        ResultOfNetwork.UnknownError(
                            throwable
                        ))
                }
            }
        }
    }

}
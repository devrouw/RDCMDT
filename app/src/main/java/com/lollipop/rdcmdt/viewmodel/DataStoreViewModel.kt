package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lollipop.rdcmdt.di.abstraction.IoDispatcher
import com.lollipop.rdcmdt.repository.datastore.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val repository: DataStoreRepository
) : ViewModel() {
    /**
     * Store token, refresh token, email in data store
     */
    fun setAuthPref(token: String, username: String, accountNo: String) {
        viewModelScope.launch(dispatcher) {
            repository.saveAuth(token, username, accountNo)
        }
    }

    /**
     * Get stored access token & guid from data store
     */
    fun getAuthPref(): Flow<List<String>> {
        return repository.readAuth
    }

    fun resetAuth(){
        viewModelScope.launch(dispatcher) {
            repository.resetAuth()
        }
    }
}
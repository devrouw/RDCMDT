package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.Observer
import com.lollipop.rdcmdt.helper.DummyTestData
import com.lollipop.rdcmdt.repository.login.LoginRepository
import com.lollipop.rdcmdt.repository.transfer.TransferRepository
import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.model.Transfer
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class TransferViewModelTest {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: TransferViewModel
    private val repository: TransferRepository = mock()

    private val observer: Observer<ResultOfNetwork<Transfer>> = mock()

    private val transfer: Transfer = mock()

    @Before
    fun setUp(){
        viewModel = TransferViewModel(repository, testDispatcher)
    }

    @Test
    fun `Should successfully do transfer`() = runBlockingTest {
        viewModel.transferResponse.observeForever(observer)
        whenever(
            repository.post("x",10F,"x","x")
        ).thenReturn(ResultOfNetwork.Success(transfer))
        viewModel.transfer("x",10F,"x","x")
        TestCase.assertNotNull(viewModel.transferResponse.value)
        TestCase.assertEquals(ResultOfNetwork.Success(transfer), viewModel.transferResponse.value)
    }

    @Test
    fun `Should api failed do transfer`() = runBlockingTest {
        viewModel.transferResponse.observeForever(observer)
        whenever(
            repository.post("x",10F,"x","x")
        ).thenReturn(ResultOfNetwork.ApiFailed(404, "failed", null))
        viewModel.transfer("x",10F,"x","x")
        TestCase.assertNotNull(viewModel.transferResponse.value)
        TestCase.assertEquals(ResultOfNetwork.ApiFailed(404, "failed", null), viewModel.transferResponse.value)
    }

    @Test
    fun `When calling for transfer then return loading`() = runBlockingTest {
        viewModel.transferResponse.observeForever(observer)
        viewModel.transfer("x",10F,"x","x")
        verify(observer).onChanged(ResultOfNetwork.Loading(true))
    }
}
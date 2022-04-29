package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.Observer
import com.lollipop.rdcmdt.helper.BaseUnitTest
import com.lollipop.rdcmdt.repository.balance.BalanceRepository
import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class BalanceViewModelTest : BaseUnitTest() {

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: BalanceViewModel
    private val repository: BalanceRepository = mock()

    private val observer: Observer<ResultOfNetwork<Balance>> = mock()

    private val balance: Balance = mock()

    @Before
    fun setUp(){
        viewModel = BalanceViewModel(repository, testDispatcher)
    }

    @Test
    fun `Should successfully to get list of balance`() = runBlockingTest {
        viewModel.balanceResponse.observeForever(observer)
        whenever(
            repository.get("x")
        ).thenReturn(ResultOfNetwork.Success(balance))
        viewModel.balance("x")
        TestCase.assertNotNull(viewModel.balanceResponse.value)
        assertEquals(ResultOfNetwork.Success(balance), viewModel.balanceResponse.value)
    }

    @Test
    fun `Should api failed get list of balance`() = runBlockingTest {
        viewModel.balanceResponse.observeForever(observer)
        whenever(
            repository.get("x")
        ).thenReturn(ResultOfNetwork.ApiFailed(404, "failed", null))
        viewModel.balance("x")
        TestCase.assertNotNull(viewModel.balanceResponse.value)
        assertEquals(ResultOfNetwork.ApiFailed(404, "failed", null), viewModel.balanceResponse.value)
    }

    @Test
    fun `When calling for list of balance then return loading`() = runBlockingTest {
        viewModel.balanceResponse.observeForever(observer)
        viewModel.balance("x")
        verify(observer).onChanged(ResultOfNetwork.Loading(true))
    }
}
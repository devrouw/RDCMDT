package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.Observer
import com.lollipop.rdcmdt.helper.BaseUnitTest
import com.lollipop.rdcmdt.helper.DummyTestData
import com.lollipop.rdcmdt.repository.login.LoginRepository
import com.lollipop.rdcmdt.repository.transactions.TransactionsRepository
import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.model.Transactions
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
class TransactionsViewModelTest : BaseUnitTest(){
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: TransactionsViewModel
    private val repository: TransactionsRepository = mock()

    private val observer: Observer<ResultOfNetwork<Transactions>> = mock()

    private val transactions: Transactions = mock()

    @Before
    fun setUp(){
        viewModel = TransactionsViewModel(repository, testDispatcher)
    }

    @Test
    fun `Should successfully get list of transactions`() = runBlockingTest {
        viewModel.transactionsResponse.observeForever(observer)
        whenever(
            repository.get("x")
        ).thenReturn(ResultOfNetwork.Success(transactions))
        viewModel.transactions("x")
        TestCase.assertNotNull(viewModel.transactionsResponse.value)
        TestCase.assertEquals(ResultOfNetwork.Success(transactions), viewModel.transactionsResponse.value)
    }

    @Test
    fun `Should api failed get list of transactions`() = runBlockingTest {
        viewModel.transactionsResponse.observeForever(observer)
        whenever(
            repository.get("x")
        ).thenReturn(ResultOfNetwork.ApiFailed(404, "failed", null))
        viewModel.transactions("x")
        TestCase.assertNotNull(viewModel.transactionsResponse.value)
        TestCase.assertEquals(ResultOfNetwork.ApiFailed(404, "failed", null), viewModel.transactionsResponse.value)
    }

    @Test
    fun `When calling for transactions then return loading`() = runBlockingTest {
        viewModel.transactionsResponse.observeForever(observer)
        viewModel.transactions("x")
        verify(observer).onChanged(ResultOfNetwork.Loading(true))
    }
}
package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.Observer
import com.lollipop.rdcmdt.helper.BaseUnitTest
import com.lollipop.rdcmdt.helper.DummyTestData
import com.lollipop.rdcmdt.repository.balance.BalanceRepository
import com.lollipop.rdcmdt.repository.login.LoginRepository
import com.lollipop.rdcmdt.service.model.Auth
import com.lollipop.rdcmdt.service.model.Balance
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
class LoginViewModelTest : BaseUnitTest() {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: LoginViewModel
    private val repository: LoginRepository = mock()

    private val observer: Observer<ResultOfNetwork<Auth>> = mock()

    private val auth: Auth = mock()

    @Before
    fun setUp(){
        viewModel = LoginViewModel(repository, testDispatcher)
    }

    @Test
    fun `Should successfully login`() = runBlockingTest {
        viewModel.loginResponse.observeForever(observer)
        whenever(
            repository.post("x","x")
        ).thenReturn(ResultOfNetwork.Success(auth))
        viewModel.login("x","x")
        TestCase.assertNotNull(viewModel.loginResponse.value)
        TestCase.assertEquals(ResultOfNetwork.Success(auth), viewModel.loginResponse.value)
    }

    @Test
    fun `Should api failed login`() = runBlockingTest {
        viewModel.loginResponse.observeForever(observer)
        whenever(
            repository.post("x","x")
        ).thenReturn(ResultOfNetwork.ApiFailed(401, "failed", null))
        viewModel.login("x","x")
        TestCase.assertNotNull(viewModel.loginResponse.value)
        TestCase.assertEquals(ResultOfNetwork.ApiFailed(401, "failed", null), viewModel.loginResponse.value)
    }

    @Test
    fun `When calling for login then return loading`() = runBlockingTest {
        viewModel.loginResponse.observeForever(observer)
        viewModel.login("x","x")
        verify(observer).onChanged(ResultOfNetwork.Loading(true))
    }
}
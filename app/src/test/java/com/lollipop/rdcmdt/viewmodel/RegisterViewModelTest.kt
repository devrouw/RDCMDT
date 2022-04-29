package com.lollipop.rdcmdt.viewmodel

import androidx.lifecycle.Observer
import com.lollipop.rdcmdt.helper.BaseUnitTest
import com.lollipop.rdcmdt.helper.DummyTestData
import com.lollipop.rdcmdt.repository.login.LoginRepository
import com.lollipop.rdcmdt.repository.register.RegisterRepository
import com.lollipop.rdcmdt.service.model.Auth
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
class RegisterViewModelTest : BaseUnitTest() {
    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: RegisterViewModel
    private val repository: RegisterRepository = mock()

    private val observer: Observer<ResultOfNetwork<Auth>> = mock()

    private val auth: Auth = mock()

    @Before
    fun setUp(){
        viewModel = RegisterViewModel(repository, testDispatcher)
    }

    @Test
    fun `Should successfully register`() = runBlockingTest {
        viewModel.registerResponse.observeForever(observer)
        whenever(
            repository.post("x","x")
        ).thenReturn(ResultOfNetwork.Success(auth))
        viewModel.register("x","x")
        TestCase.assertNotNull(viewModel.registerResponse.value)
        TestCase.assertEquals(ResultOfNetwork.Success(auth), viewModel.registerResponse.value)
    }

    @Test
    fun `Should api failed register`() = runBlockingTest {
        viewModel.registerResponse.observeForever(observer)
        whenever(
            repository.post("x","x")
        ).thenReturn(ResultOfNetwork.ApiFailed(401, "failed", null))
        viewModel.register("x","x")
        TestCase.assertNotNull(viewModel.registerResponse.value)
        TestCase.assertEquals(ResultOfNetwork.ApiFailed(401, "failed", null), viewModel.registerResponse.value)
    }

    @Test
    fun `When calling for register then return loading`() = runBlockingTest {
        viewModel.registerResponse.observeForever(observer)
        viewModel.register("x","x")
        verify(observer).onChanged(ResultOfNetwork.Loading(true))
    }
}
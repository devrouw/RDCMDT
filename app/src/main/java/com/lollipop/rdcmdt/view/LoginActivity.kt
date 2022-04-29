package com.lollipop.rdcmdt.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lollipop.rdcmdt.databinding.ActivityLoginBinding
import com.lollipop.rdcmdt.helper.Constant
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.viewmodel.DataStoreViewModel
import com.lollipop.rdcmdt.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), BaseActivity {

    private lateinit var _binding: ActivityLoginBinding
    private lateinit var _viewModel: LoginViewModel
    private lateinit var _viewModelDataStore: DataStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        initializeViewModel()
        observableLiveData()

        with(_binding){
            emailSignInButton.setOnClickListener {
                val username = _binding.etUsername.text.toString()
                val password = _binding.etPassword.text.toString()
                checkEmptyField(username, password)
            }
            emailSignUpButton.setOnClickListener {
                Timber.d("sign up clicked")
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun checkEmptyField(username: String, password: String) {
        with(_binding){
            if(username.isEmpty()){
                tlUsername.error = "Username cannot be empty"
            }
            if(password.isEmpty()){
                tlPassword.error = "Password cannot be empty"
            }
            if(username.isNotEmpty() && password.isNotEmpty()){
                _viewModel.login(username, password)
            }
        }
    }

    override fun initializeViewModel() {
        _viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
    }

    override fun observableLiveData() {
        _viewModel.loginResponse.observe(this) { loginResponse ->
            when (loginResponse) {
                is ResultOfNetwork.Loading -> setVisibilityProgressBar(true)
                is ResultOfNetwork.Success -> {
                    setVisibilityProgressBar(false)
                    val data = loginResponse.value
                    data.token?.let { data.username?.let { it1 ->
                        data.accountNo?.let { it2 ->
                            _viewModelDataStore.setAuthPref(it,
                                it1, it2
                            )
                        }
                    } }
                    startActivity(Intent(this, BalanceActivity::class.java))
                }
                is ResultOfNetwork.ApiFailed -> {
                    setVisibilityProgressBar(false)
                    when(loginResponse.code){
                        Constant.Network.REQUEST_NOT_FOUND -> {
                            Toast.makeText(this,"User not found",Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else -> {
                    setVisibilityProgressBar(false)
                    Timber.d("Unknown error")
                }
            }
        }
    }

    private fun setVisibilityProgressBar(progressBar: Boolean) {
        if (progressBar) {
            _binding.pbContent.visibility = View.VISIBLE
        } else {
            _binding.pbContent.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}
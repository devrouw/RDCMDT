package com.lollipop.rdcmdt.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lollipop.rdcmdt.databinding.ActivityRegisterBinding
import com.lollipop.rdcmdt.helper.Constant
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.viewmodel.DataStoreViewModel
import com.lollipop.rdcmdt.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity(), BaseActivity {

    private lateinit var _binding: ActivityRegisterBinding
    private lateinit var _viewModel: RegisterViewModel
    private lateinit var _viewModelDataStore: DataStoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        with(_binding){
            emailSignUpButton.setOnClickListener {
                val username = etUsername.text.toString()
                val password = etPassword.text.toString()
                val confirmPassword = etConfirmPassword.text.toString()
                checkEmptyField(username, password, confirmPassword)
            }
        }

        initializeViewModel()
        observableLiveData()
    }

    private fun checkEmptyField(username: String, password: String, confirmPassword: String) {
        with(_binding){
            if(username.isEmpty()){
                tlUsername.error = "Username cannot be empty"
            }
            if(password.isEmpty()){
                tlPassword.error = "Password cannot be empty"
            }
            if(password != confirmPassword){
                tlConfirmPassword.error = "Password doesn't match"
            }

            if(username.isNotEmpty() && password.isNotEmpty()){
                _viewModel.register(username, password)
            }
        }
    }

    override fun initializeViewModel() {
        _viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
    }

    override fun observableLiveData() {
        _viewModel.registerResponse.observe(this){ result ->
            when(result){
                is ResultOfNetwork.Loading -> setVisibilityProgressBar(true)
                is ResultOfNetwork.Success -> {
                    setVisibilityProgressBar(false)
                    Toast.makeText(this,"Register Success",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                is ResultOfNetwork.ApiFailed -> {
                    setVisibilityProgressBar(false)
                    when(result.code){
                        Constant.Network.REQUEST_NOT_FOUND -> {
                            Toast.makeText(this,"Network Error",Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else -> {
                    setVisibilityProgressBar(false)
                    Timber.d("Unknown Error")
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

}
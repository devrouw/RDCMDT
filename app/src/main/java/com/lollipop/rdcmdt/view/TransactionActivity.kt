package com.lollipop.rdcmdt.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lollipop.rdcmdt.R
import com.lollipop.rdcmdt.databinding.ActivityRegisterBinding
import com.lollipop.rdcmdt.databinding.ActivityTransactionBinding
import com.lollipop.rdcmdt.helper.Constant
import com.lollipop.rdcmdt.service.model.Payee
import com.lollipop.rdcmdt.service.model.Payees
import com.lollipop.rdcmdt.service.model.Transfer
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.view.adapter.PayeesAdapter
import com.lollipop.rdcmdt.viewmodel.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class TransactionActivity : AppCompatActivity(), BaseActivity {

    private lateinit var _binding: ActivityTransactionBinding
    private lateinit var _viewModel: TransferViewModel
    private lateinit var _viewModelPayees: PayeesViewModel
    private lateinit var _viewModelDataStore: DataStoreViewModel

    private lateinit var _adapter: PayeesAdapter
    private var _accountNo = ""
    private var _token = "-"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        with(_binding){
            btTransfer.setOnClickListener {
                val amount = etAmount.text.toString()
                val description = etDescription.text.toString()
                checkEmptyField(amount, description)
            }

            spPayees.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    if (p0 != null) {
                        _accountNo = (p0.getItemAtPosition(p2) as Payee).accountNo.toString()
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

            }
        }

        initializeViewModel()
        observableLiveData()
    }

    private fun checkEmptyField(amount: String, description: String) {
        with(_binding){
            if(amount.isEmpty()){
                tlAmount.error = "Amount cannot be empty"
            }else{
                _viewModel.transfer(_accountNo, amount.toFloat(), description, _token)
            }
        }
    }

    override fun initializeViewModel() {
        _viewModel = ViewModelProvider(this).get(TransferViewModel::class.java)
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModelPayees = ViewModelProvider(this).get(PayeesViewModel::class.java)
    }

    override fun observableLiveData() {
        CoroutineScope(Dispatchers.IO).launch {
            _viewModelDataStore.getAuthPref().collect { auth ->
                _token = auth[0]
                if(_token == "-"){
                    startActivity(Intent(this@TransactionActivity, LoginActivity::class.java))
                }else{
                    _viewModelPayees.payees(_token)
                }
            }
        }

        _viewModelPayees.payeesRepository.observe(this){ result ->
            when(result){
                is ResultOfNetwork.Loading -> setVisibilityShimmer(true)
                is ResultOfNetwork.Success -> {
                    setVisibilityShimmer(false)
                    val data = result.value
                    fetchDataPayees(data)
                }
                is ResultOfNetwork.ApiFailed -> {
                    setVisibilityShimmer(false)
                    when(result.code){
                        Constant.Network.REQUEST_NOT_FOUND -> {
                            Toast.makeText(this,"Data not found", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                else -> {
                    setVisibilityShimmer(false)
                    Timber.d("Unknown error")
                }
            }
        }

        _viewModel.transferResponse.observe(this){ result ->
            when(result){
                is ResultOfNetwork.Loading -> setVisibilityProgressBar(true)
                is ResultOfNetwork.Success -> {
                    setVisibilityProgressBar(false)
                    val data = result.value
                    send(data)
                }
                is ResultOfNetwork.ApiFailed -> {
                    setVisibilityProgressBar(false)
                    when(result.code){
                        Constant.Network.REQUEST_NOT_FOUND -> {
                            Toast.makeText(this,"Data not found", Toast.LENGTH_LONG).show()
                        }
                        Constant.Network.BAD_REQUEST -> {
                            Toast.makeText(this,"Insufficient Amount", Toast.LENGTH_LONG).show()
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

    private fun send(data: Transfer) {
        when(data.status){
            "failed" -> {
                Toast.makeText(this, "Insufficient Balance", Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, "Transfer Successful", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun fetchDataPayees(data: Payees) {
        _adapter = data.data?.let { PayeesAdapter(this, it) }!!
        _binding.spPayees.adapter = _adapter
    }

    private fun setVisibilityShimmer(shimmer: Boolean){
        with(_binding){
            if (shimmer) {
                shimmerContainer.visibility = View.VISIBLE
                llContent.visibility = View.GONE
            } else {
                shimmerContainer.visibility = View.GONE
                llContent.visibility = View.VISIBLE
            }
        }
    }

    private fun setVisibilityProgressBar(progress: Boolean){
        with(_binding){
            if (progress) {
                progressbar.visibility = View.VISIBLE
            } else {
                progressbar.visibility = View.GONE
            }
        }
    }
}
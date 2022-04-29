package com.lollipop.rdcmdt.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.lollipop.rdcmdt.databinding.ActivityBalanceBinding
import com.lollipop.rdcmdt.helper.Constant
import com.lollipop.rdcmdt.helper.CurrencyConverter
import com.lollipop.rdcmdt.service.model.Balance
import com.lollipop.rdcmdt.service.network.ResultOfNetwork
import com.lollipop.rdcmdt.view.adapter.TransactionAdapter
import com.lollipop.rdcmdt.viewmodel.BalanceViewModel
import com.lollipop.rdcmdt.viewmodel.DataStoreViewModel
import com.lollipop.rdcmdt.viewmodel.TransactionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class BalanceActivity : AppCompatActivity(), BaseActivity {

    private lateinit var _binding: ActivityBalanceBinding
    private lateinit var _viewModel: BalanceViewModel
    private lateinit var _viewModelTransactions: TransactionsViewModel
    private lateinit var _viewModelDataStore: DataStoreViewModel

    private lateinit var _adapter: TransactionAdapter

    private var _username = ""
    private var _token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBalanceBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        with(_binding){
            btLogout.setOnClickListener {
                _viewModelDataStore.resetAuth()
            }
            btTransfer.setOnClickListener {
                startActivity(Intent(this@BalanceActivity,TransactionActivity::class.java))
            }
        }
        initializeAdapter()
        initializeViewModel()
        observableLiveData()
    }

    private fun initializeAdapter(){
        _adapter = TransactionAdapter()
        val layoutManager = LinearLayoutManager(this)
        _binding.rvTransaction.layoutManager = layoutManager
        _binding.rvTransaction.adapter = _adapter
    }

    override fun initializeViewModel() {
        _viewModel = ViewModelProvider(this).get(BalanceViewModel::class.java)
        _viewModelDataStore = ViewModelProvider(this).get(DataStoreViewModel::class.java)
        _viewModelTransactions = ViewModelProvider(this).get(TransactionsViewModel::class.java)
    }

    override fun observableLiveData() {
        CoroutineScope(Dispatchers.IO).launch {
            _viewModelDataStore.getAuthPref().collect { auth ->
                if(auth[0] == "-"){
                    startActivity(Intent(this@BalanceActivity, LoginActivity::class.java))
                }else{
                    _username = auth[1]
                    _token = auth[0]
                    _viewModel.balance(_token)
                }
            }
        }
        _viewModel.balanceResponse.observe(this){ balanceResponse ->
            when(balanceResponse){
                is ResultOfNetwork.Loading -> setVisibilityShimmer(true)
                is ResultOfNetwork.Success -> {
                    setVisibilityShimmer(false)
                    val data = balanceResponse.value
                    fetchData(data)
                }
                is ResultOfNetwork.ApiFailed -> {
                    setVisibilityShimmer(false)
                    when(balanceResponse.code){
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
        _viewModelTransactions.transactionsResponse.observe(this){ result ->
            when(result){
                is ResultOfNetwork.Loading -> setVisibilityShimmer(true)
                is ResultOfNetwork.Success -> {
                    setVisibilityShimmer(false)
                    val data = result.value
                    data.data?.let { _adapter.setData(it) }
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
    }

    @SuppressLint("SetTextI18n")
    private fun fetchData(data: Balance) {
        with(_binding){
            tvName.text = _username
            tvBalance.text = "SGD ${data.balance?.let { CurrencyConverter.currencyConverter(it) }}"
            tvAccount.text = data.accountNo
        }
        _viewModelTransactions.transactions(_token)
    }

    private fun setVisibilityShimmer(shimmer: Boolean){
        with(_binding){
            if (shimmer) {
                shimmerContainer.visibility = View.VISIBLE
                rlContent.visibility = View.GONE
            } else {
                shimmerContainer.visibility = View.GONE
                rlContent.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
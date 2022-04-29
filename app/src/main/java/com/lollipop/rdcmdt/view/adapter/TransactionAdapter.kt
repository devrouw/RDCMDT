package com.lollipop.rdcmdt.view.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lollipop.rdcmdt.R
import com.lollipop.rdcmdt.databinding.ItemTransactionBinding
import com.lollipop.rdcmdt.helper.CurrencyConverter
import com.lollipop.rdcmdt.helper.DateFormatLocale
import com.lollipop.rdcmdt.service.model.Transaction
import timber.log.Timber

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {
    private var items : MutableList<Transaction> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data : List<Transaction>){
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(binding) {
                when {
                    items.isNotEmpty() -> {
                        Timber.d("cek data $position")
                        var numberState = ""
                        if(items[position].transactionType == "transfer"){
                            imgArrow.rotation = 180F
                            numberState = "-"
                            tvTransaction.setTextColor(Color.RED)
                            tvAccountName.text = items[position].receipient?.accountHolder ?: ""
                        }else{
                            tvAccountName.text = items[position].sender?.accountHolder ?: ""
                        }

                        tvDate.text = items[position].transactionDate?.let {
                            DateFormatLocale.localeDateDayParseHalfMonthSecond(
                                it
                            )
                        }
                        tvTransaction.text = numberState + items[position].amount?.let {
                            CurrencyConverter.currencyConverter(
                                it
                            )
                        }
                    }
                }
            }
        }
    }


    override fun getItemCount() = items.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTransactionBinding.bind(view)
    }
}
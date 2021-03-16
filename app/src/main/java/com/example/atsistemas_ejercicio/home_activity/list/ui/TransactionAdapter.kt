package com.example.atsistemas_ejercicio.home_activity.home.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atsistemas_ejercicio.databinding.ItemTransactionBinding
import com.example.data.models.TransactionDTO

class TransactionAdapter(
    private var mValues: List<TransactionDTO>?,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private lateinit var binding: ItemTransactionBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemTransactionBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = mValues[position]
//        holder.bind(item)

        mValues?.let {
            holder.tvDate.text = it[position].date
            if (it[position].description.isNullOrEmpty())
                holder.tvDescription.text = ""
            else
                holder.tvDescription.text = it[position].description
            holder.tvAmount.text = "${it[position].amount} €"
            if (it[position].fee.isNullOrEmpty()) {
                holder.tvFee.text = ""
                holder.tvFee.visibility = View.GONE
            } else {
                holder.tvFee.text = it[position].fee
                holder.tvFee.visibility = View.VISIBLE
            }
            if (it[position].amount.toDouble() > 0) {
                holder.imgIn.visibility = View.VISIBLE
                holder.imgOut.visibility = View.GONE
            } else {
                holder.imgIn.visibility = View.GONE
                holder.imgOut.visibility = View.VISIBLE
            }
            holder.itemView.setOnClickListener { view ->
                cellClickListener.onCellClickListener(it[position])
            }
        } ?: clearList()
    }

    private fun clearList() {
        mValues = emptyList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mValues?.size ?: 0

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        val tvDate = binding.tvDate
        val tvDescription = binding.tvDescription
        val tvAmount = binding.tvAmount
        val tvFee = binding.tvFee
        val imgIn = binding.imgTransaction
        val imgOut = binding.imgTransactionOut

        override fun toString(): String = super.toString() + " '" + tvDate.text + "'"

//        fun bind(transactionDTO: TransactionDTO) {
//            tvDate.text = transactionDTO.date
//            tvDescription.text =
//                if (transactionDTO.description.isNullOrEmpty()) ""
//                else transactionDTO.description
//            tvAmount.text = transactionDTO.amount
//            tvFee.text = transactionDTO.fee
//
//            binding.root.setOnClickListener {
//                cellClickListener.onCellClickListener(transactionDTO)
//            }
//        }
    }
}

interface CellClickListener {
    fun onCellClickListener(transactionDTO: TransactionDTO)
}

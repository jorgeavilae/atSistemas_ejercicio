package com.example.atsistemas_ejercicio.home_activity.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atsistemas_ejercicio.R
import com.example.atsistemas_ejercicio.commons.BaseFragment
import com.example.atsistemas_ejercicio.commons.Constants
import com.example.atsistemas_ejercicio.commons.uicomponents.ErrorDialog
import com.example.atsistemas_ejercicio.databinding.DdetailFragmentBinding
import com.example.atsistemas_ejercicio.home_activity.detail.vm.DDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DDetailFragment : BaseFragment() {

    private val presenter: DDetailViewModel by viewModel()

    private var _binding: DdetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DdetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val transactionID = arguments?.getString(Constants.BUNDLE_TRANSACTION) ?: ""
        presenter.fetchTransaction(transactionID)

        return view
    }


    override fun loadObservers() {
        presenter.transaction.observe(viewLifecycleOwner) {
            it?.let {
                binding.detailText.text = it.toString()
            }
        }

        presenter.showError.observe(viewLifecycleOwner) {
            errorDialog = activity?.let { activity ->
                ErrorDialog(
                    activity,
                    getString(R.string.alert),
                    it,
                    getString(R.string.close)
                ) {
                    errorDialog?.dismiss()
                }

            }
            errorDialog?.setCancelable(false)
            errorDialog?.show()
        }

    }
}
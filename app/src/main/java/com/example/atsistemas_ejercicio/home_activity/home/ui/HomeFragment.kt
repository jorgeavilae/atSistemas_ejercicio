package com.example.atsistemas_ejercicio.home_activity.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atsistemas_ejercicio.R
import com.example.atsistemas_ejercicio.commons.BaseFragment
import com.example.atsistemas_ejercicio.commons.uicomponents.ErrorDialog
import com.example.atsistemas_ejercicio.commons.uicomponents.SuccessDialog
import com.example.atsistemas_ejercicio.databinding.HomeFragmentBinding
import com.example.atsistemas_ejercicio.home_activity.home.vm.HomeViewModel
import com.example.atsistemas_ejercicio.utils.SharedTransactionVM
import com.example.data.models.TransactionDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : BaseFragment(), CellClickListener {

    private val presenter: HomeViewModel by sharedViewModel()
    private val sharedTransactionVM: SharedTransactionVM by sharedViewModel()

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun loadObservers() {
        presenter.transactionList.observe(viewLifecycleOwner) {
            binding.recyclerView.layoutManager = LinearLayoutManager(activity)
            adapter = TransactionAdapter(it, this)
            binding.recyclerView.adapter = adapter
        }

        presenter.showMessage.observe(viewLifecycleOwner) {
            successDialog = activity?.let { activity ->
                SuccessDialog(activity, it)
            }
            successDialog?.show()
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

        presenter.isLoading.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    binding.progressBar.visibility = View.VISIBLE
                } else  {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

    override fun onCellClickListener(transactionDTO: TransactionDTO) {
//        Toast.makeText(requireActivity(), transactionDTO.description, Toast.LENGTH_SHORT).show()
//        presenter.clearList()

//        val bundle = bundleOf(Constants.BUNDLE_TRANSACTION to transactionDTO.id)
//        findNavController().navigate(R.id.action_homeFragment_to_ddetailFragment, bundle)

        Toast.makeText(activity,"Cell: ${transactionDTO.description}", Toast.LENGTH_SHORT).show()
        sharedTransactionVM.setTransaction(transactionDTO)
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
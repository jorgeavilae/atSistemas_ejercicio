package com.example.atsistemas_ejercicio.home_activity.profile.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.atsistemas_ejercicio.commons.BaseFragment
import com.example.atsistemas_ejercicio.databinding.ProfileFragmentBinding
import com.example.atsistemas_ejercicio.home_activity.profile.vm.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: BaseFragment() {

    private val presenter: ProfileViewModel by viewModel()

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.textInputName.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                presenter.setName(s.toString())
            }

        })

        binding.textInputSurname.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                presenter.setSurname(s.toString())
            }

        })


        return view
    }

    override fun loadObservers() {
        presenter.name.observe(viewLifecycleOwner) {
            it?.let { name ->
                binding.textInputName.editText?.setText(name)
            }
        }

        presenter.surname.observe(viewLifecycleOwner) {
            it?.let { surname ->
                binding.textInputSurname.editText?.setText(surname)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
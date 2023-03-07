package com.example.pruebatecnica.ui.movies.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.pruebatecnica.databinding.FragmentNoDataDialogBinding


class NoDataDialog : DialogFragment() {

    private var _binding: FragmentNoDataDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoDataDialogBinding.inflate(inflater, container, false)
        setupListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
    }

    private fun setupListeners() {
        binding.txtConfirm.setOnClickListener {
            dismiss()
        }
    }

}
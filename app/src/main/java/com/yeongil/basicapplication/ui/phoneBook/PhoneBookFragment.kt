package com.yeongil.basicapplication.ui.phoneBook

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yeongil.basicapplication.databinding.FragmentPhoneBookBinding
import com.yeongil.basicapplication.viewModel.PhoneBookViewModel
import com.yeongil.basicapplication.viewModelFactory.PhoneBookViewModelFactory


class PhoneBookFragment : Fragment() {
    private var _binding: FragmentPhoneBookBinding? = null
    private val binding get() = _binding!!

    private val phoneBookViewModel by activityViewModels<PhoneBookViewModel> {
        PhoneBookViewModelFactory(
            requireActivity().application
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        phoneBookViewModel.refreshPhoneBook()

        _binding = FragmentPhoneBookBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = phoneBookViewModel

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
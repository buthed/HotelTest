package com.tematihonov.hoteltest.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tematihonov.hoteltest.R
import com.tematihonov.hoteltest.databinding.FragmentRoomsBinding
import com.tematihonov.hoteltest.presentation.ui.rcview.roomslist.RoomsListAdapter
import com.tematihonov.hoteltest.presentation.viewmodel.RoomsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment() {

    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!

    private val roomsViewModel by viewModel<RoomsViewModel>()
    private lateinit var adapter: RoomsListAdapter

    private val args: RoomsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appBar()
        roomsListRecycler()
        checkDataAndSetNewValues()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun appBar() = with(binding) {
        toolbarTitle.text = args.hotelname
        appBar.setOnClickListener { findNavController().popBackStack() }
    }

    private fun roomsListRecycler() {
        adapter = RoomsListAdapter()
        adapter.setOnItemClickListener(object : RoomsListAdapter.onItemClickListener {
            override fun onClick(position: Int) {
                findNavController().navigate(R.id.action_roomsFragment_to_bookingFragment2)
            }
        })
        val layoutManager = LinearLayoutManager(this.context)
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
    }

    private fun checkDataAndSetNewValues() {
        roomsViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == false) {
                roomsViewModel.roomsModel.observe(viewLifecycleOwner) { rooms ->
                    adapter.rooms = rooms.rooms
                }
            }
        }
    }
}
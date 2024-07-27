package com.example.myapplication.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.Adapter.ListEditAdapter
import com.example.myapplication.ViewModel.ListEditViewModel
import com.example.myapplication.databinding.FragmentByPixelBinding

class ByPixelFragment : Fragment() {

    private var _binding: FragmentByPixelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListEditViewModel by activityViewModels()
    private lateinit var adapter: ListEditAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentByPixelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListEditAdapter(emptyList())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = adapter

        viewModel.imageData.observe(viewLifecycleOwner) { images ->
            adapter.updateItems(images)
        }
    }

    fun updateData() {
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

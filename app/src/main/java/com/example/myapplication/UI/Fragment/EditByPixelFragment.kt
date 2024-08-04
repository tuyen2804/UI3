package com.example.myapplication.UI.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.EditByPixelAdapter
import com.example.myapplication.Adapter.PopupAdapter
import com.example.myapplication.Adapter.PreAdapter
import com.example.myapplication.Model.PopupModel
import com.example.myapplication.R
import com.example.myapplication.ViewModel.EditViewModel
import com.example.myapplication.databinding.FragmentEditByPixelBinding
import com.example.myapplication.databinding.FragmentPreByPixelBinding

class EditByPixelFragment : Fragment() {

    private var _binding: FragmentEditByPixelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by activityViewModels()
    private lateinit var adapter: EditByPixelAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditByPixelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EditByPixelAdapter(emptyList(), R.layout.layout_edit_bypixel)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = adapter
        viewModel.imageData.observe(viewLifecycleOwner) { images ->
            adapter.updateItems(images)
        }


    }


    fun updateData(){
        adapter.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

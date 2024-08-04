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
import com.example.myapplication.Adapter.EditByPercentageAdapter
import com.example.myapplication.Adapter.PopupAdapter
import com.example.myapplication.Adapter.PreAdapter
import com.example.myapplication.Model.PopupModel
import com.example.myapplication.R
import com.example.myapplication.ViewModel.EditViewModel
import com.example.myapplication.databinding.FragmentEditBypercentageBinding
import com.example.myapplication.databinding.FragmentPreByPixelBinding


class EditBypercentageFragment : Fragment() {

    private var _binding: FragmentEditBypercentageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by activityViewModels()
    private lateinit var adapter: EditByPercentageAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditBypercentageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EditByPercentageAdapter(emptyList(), R.layout.layout_edit_bypercentage)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = adapter
        viewModel.imageData.observe(viewLifecycleOwner) { images ->
            adapter.updateItems(images)
        }


        binding.seekbarEdit.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Cập nhật TextView khi SeekBar thay đổi
                binding.txtSeekbar.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // Thực hiện hành động khi người dùng bắt đầu di chuyển SeekBar (tuỳ chọn)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Thực hiện hành động khi người dùng ngừng di chuyển SeekBar (tuỳ chọn)
            }
        })
    }

    fun updateData(){
        adapter.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

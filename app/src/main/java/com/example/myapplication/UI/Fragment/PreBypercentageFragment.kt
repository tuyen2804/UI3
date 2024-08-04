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
import com.example.myapplication.Adapter.PreAdapter
import com.example.myapplication.Adapter.PopupAdapter
import com.example.myapplication.Model.PopupModel
import com.example.myapplication.R
import com.example.myapplication.ViewModel.EditViewModel
import com.example.myapplication.databinding.FragmentPreBypercentageBinding

class PreBypercentageFragment : Fragment() {

    private var _binding: FragmentPreBypercentageBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EditViewModel by activityViewModels()
    private lateinit var adapter: PreAdapter
    private lateinit var popupWindow: PopupWindow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPreBypercentageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = PreAdapter(emptyList(), R.layout.layout_list_bypixel) { anchor, position ->
            val currentTypeImage = adapter.itemEdits[position].typeImage
            showPopupMenu(anchor, currentTypeImage) { selectedItem ->
                adapter.itemEdits[position].typeImage = selectedItem
                adapter.notifyItemChanged(position)
            }
        }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.recyclerView.adapter = adapter
        viewModel.imageData.observe(viewLifecycleOwner) { images ->
            adapter.updateItems(images)
        }

        binding.btnOriginal.setOnClickListener {
            showPopupMenu(it, null) { selectedItem ->
            }
        }

        binding.seekbarEdit.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtSeekbar.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
        binding.seekbarEdit1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.txtSeekbar1.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    @SuppressLint("MissingInflatedId")
    private fun showPopupMenu(anchor: View, currentTypeImage: String?, onItemClick: (String) -> Unit) {
        val popupItems = listOf(
            PopupModel("png"),
            PopupModel("jpg"),
            PopupModel("heic"),
            PopupModel("webp"),
            PopupModel("jpec")
        ).filter { it.typeImage != currentTypeImage }

        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.popup_menu, null)

        val recyclerViewPopup = view.findViewById<RecyclerView>(R.id.recyclerViewPopup)
        val adapter = PopupAdapter(popupItems) { selectedItem ->
            onItemClick(selectedItem)
            popupWindow.dismiss()
        }
        recyclerViewPopup.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewPopup.adapter = adapter

        popupWindow = PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popupWindow.showAsDropDown(anchor)
    }
    fun updateData(){
        adapter.notifyDataSetChanged()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
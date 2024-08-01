package com.example.myapplication.UI.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ListPreAdapter
import com.example.myapplication.Adapter.PopupAdapter
import com.example.myapplication.Model.ListPopupModel
import com.example.myapplication.R
import com.example.myapplication.ViewModel.ListEditViewModel
import com.example.myapplication.databinding.FragmentByPixelBinding

class ByPixelFragment : Fragment() {

    private var _binding: FragmentByPixelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListEditViewModel by activityViewModels()
    private lateinit var adapter: ListPreAdapter
    private lateinit var popupWindow: PopupWindow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentByPixelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListPreAdapter(emptyList(), R.layout.layout_list_bypixel) { anchor, position ->
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

    @SuppressLint("MissingInflatedId")
    private fun showPopupMenu(anchor: View, currentTypeImage: String?, onItemClick: (String) -> Unit) {
        val popupItems = listOf(
            ListPopupModel("png"),
            ListPopupModel("jpg"),
            ListPopupModel("heic"),
            ListPopupModel("webp"),
            ListPopupModel("jpec")
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

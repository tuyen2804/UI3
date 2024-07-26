package com.example.myapplication.UI.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.PopupMenu
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentByPixelBinding

class ByPixelFragment : Fragment() {

    private var _binding: FragmentByPixelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using ViewBinding
        _binding = FragmentByPixelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the button and set up the click listener
        binding.btnOriginal.setOnClickListener {
            showPopupMenu(it)
        }
    }

    private fun showPopupMenu(view: View) {
        // Create and set up the popup menu
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu_original, popupMenu.menu)

        // Handle menu item clicks
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.jpg -> {
                    Toast.makeText(requireContext(), "JPG selected", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.png -> {
                    Toast.makeText(requireContext(), "PNG selected", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.webp -> {
                    Toast.makeText(requireContext(), "WEBP selected", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.jpeg -> {
                    Toast.makeText(requireContext(), "JPEG selected", Toast.LENGTH_LONG).show()
                    true
                }
                R.id.heic -> {
                    Toast.makeText(requireContext(), "HEIC selected", Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }

        // Show the popup menu
        popupMenu.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

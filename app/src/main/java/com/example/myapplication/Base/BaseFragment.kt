package com.example.myapplication.Base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    protected var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    private lateinit var activity1: BaseActivity<*>

    var onBackInvokedCallback: (() -> Unit)? = null

    abstract fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = getViewBinding(inflater, container)
        activity1 = (requireActivity() as BaseActivity<*>?)!!

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener { v, _ ->
            v.hideKeyboard(requireActivity())
            true
        }

        activity1 = activity as BaseActivity<*>
        init()
        setUpView()
    }

    fun View.hideKeyboard(activity: Activity) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }

    fun openFragment(
        fragmentClazz: Class<*>,
        containerViewId: Int,  // Thêm tham số containerViewId
        args: Bundle? = null,
        addBackStack: Boolean = false
    ) {
        activity?.let {
            if (it is BaseActivity<*>) {
                it.openFragment(fragmentClazz, containerViewId, args, addBackStack)
            }
        }
    }

    fun toast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    abstract fun init()
    abstract fun setUpView()
}

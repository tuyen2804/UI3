package com.example.myapplication.Base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.myapplication.R
import kotlin.jvm.Throws

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity(){
    lateinit var binding: VB
    abstract fun getViewBinding(inflater: LayoutInflater):VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)
        init()
        setUpView()
    }

    abstract fun init()
    abstract fun setUpView()

    @Throws
    open fun openFragment(
        fragmentClass: Class<*>,
        containerViewId: Int,  // Thêm tham số containerViewId
        args: Bundle? = null,
        addBackStack: Boolean = false
    ) {
        val tag = fragmentClass.simpleName
        try {
            val fragment: Fragment
            try {
                fragment = (fragmentClass.asSubclass(Fragment::class.java)).newInstance()
                    .apply { arguments = args }
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                if (addBackStack) {
                    transaction.addToBackStack(tag)
                }
                transaction.replace(containerViewId, fragment, tag) // Sử dụng containerViewId
                transaction.commit()

            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

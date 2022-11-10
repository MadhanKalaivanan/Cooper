package com.cooper.firebasesdk.common

import android.app.Dialog
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.cooper.firebasesdk.R

abstract class BaseActivity<B : ViewDataBinding, V : BaseViewModel> : AppCompatActivity() {

    protected lateinit var dataBinding: B
    protected lateinit var viewModel: V

    private lateinit var dialog: Dialog
    private var dialogShowingCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setupDataBinding()
        setupViewModel()
        setupObservable()

        dialog = object : Dialog(this) {
            override fun onBackPressed() {}
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.setDimAmount(0.1f)
        dialog.window
            ?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

    @LayoutRes
    protected abstract fun getBindingViewId(): Int

    protected abstract fun getViewModelClass(): Class<V>

    private fun setupDataBinding() {
        dataBinding = DataBindingUtil.setContentView(this, getBindingViewId())
        dataBinding.lifecycleOwner = this
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(getViewModelClass())
    }

    protected open fun setupObservable() {
        viewModel.isBusy.observe(this) {
            if (it) showLoader() else hideLoader()
        }

        viewModel.forceStopLoader.observe(this) {
            hideLoader(true)
        }

        viewModel.toastMessageString.observe(this) {
            it?.let {
                Toast.makeText(this, it.getContent(this), it.duration).show()
            }
        }
    }

    open fun showLoader() {
        /**
         * Increase 'dialogShowingCount' before check condition and show dialog
         * to avoid immediate call may be work incorrect.
         */
        dialogShowingCount++
        if (dialogShowingCount == 1 && !dialog.isShowing) {
            dialog.show()
        }
    }

    open fun hideLoader(force: Boolean = false) {

        if (force && dialog.isShowing) {
            dialogShowingCount = 0
            dialog.hide()
        } else {
            /**
             * Decrease 'dialogShowingCount' before check condition and hide dialog
             * to avoid immediate call may be work incorrect.
             */
            if (dialogShowingCount > 0) {
                dialogShowingCount--
            }
            if (dialogShowingCount == 0 && dialog.isShowing) {
                dialog.hide()
            }
        }
    }
}
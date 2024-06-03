package com.census.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.census.utils.NetworkMonitoringHelper

abstract class BaseFragment<VM : BaseViewModel, VB : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    Fragment() {
    var canBeNullBinding: VB? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val bindings get() = canBeNullBinding!!
    protected var baseActivity: BaseActivity<*, *>? = null

    protected abstract fun getBindingVariable(): Int
    abstract fun onNetworkConnected()
    protected abstract val viewModel: VM

    val REQUEST_CODE_GALLERY_PERMISSION = 1
    val REQUEST_CODE_MICROPHONE_PERMISSION = 2
    val REQUEST_CODE_CAMERA_PERMISSION = 3
    val REQUEST_CODE_LOCATION_PERMISSION = 4

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity<*, *>)
            baseActivity = context
    }

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        canBeNullBinding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        bindings.lifecycleOwner = this
        bindings.setVariable(getBindingVariable(), viewModel)
        return bindings.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val networkMonitoringHelper =
            NetworkMonitoringHelper.newInstance(context) { requireActivity().runOnUiThread { onNetworkConnected() } }
        networkMonitoringHelper.setLifecycleOwner(viewLifecycleOwner)
        initUi()
        observeEvents()
    }

    abstract fun initUi()

    private fun observeEvents() {
        viewModel.baseEvent.observe(viewLifecycleOwner) {
            when (it) {
                BaseActivity.ON_BACK_PRESS -> {
                    goBack()
                }
                BaseActivity.EXPIRED_USER_TOKEN -> {
                    onUserSessionExpired()
                }
                BaseActivity.SHOW_LOADER -> {
                    showProgressDialog()
                }
                BaseActivity.HIDE_LOADER -> {
                    hideProgressDialog()
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        canBeNullBinding = null
    }

    private fun onUserSessionExpired() {
        baseActivity?.onUserSessionExpired()
    }

    open fun showErrorMessage(error: String) {
        baseActivity?.showErrorMessage(error)
    }

    open fun goBack() {
        baseActivity?.goBack()
    }

    private fun showProgressDialog() {
        baseActivity?.showLoading()
    }

    private fun hideProgressDialog() {
        baseActivity?.hideLoading()

    }

    open fun unbindDrawables(view: View?) {
        if (view != null) {
            if (view.background != null) {
                view.background.callback = null
            }
            if (view is ViewGroup && view !is AdapterView<*>) {
                for (i in 0 until view.childCount) {
                    unbindDrawables(view.getChildAt(i))
                }
                view.removeAllViews()
            }
            System.gc()
        }
    }
}
package com.census.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.census.R
import com.census.utils.LockableBottomSheetBehavior

abstract class BaseBottomSheetDialog<T : ViewDataBinding, V : BaseViewModel>(@LayoutRes private val layoutResId: Int) :
    BottomSheetDialogFragment() {

    protected abstract val viewModel: V

    private var _binding: T? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    protected val bindings get() = _binding!!


    protected abstract fun getBindingVariable(): Int

    protected var baseActivity: BaseActivity<*, *>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity<*, *>)
            baseActivity = context
    }

    override fun onDetach() {
        super.onDetach()
        baseActivity = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        bindings.lifecycleOwner = this
        bindings.setVariable(getBindingVariable(), viewModel)
        return bindings.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDialog()
        initUi()
        observeEvents()
    }


    fun showToast(msg: String?) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    abstract fun initUi()

    private fun observeEvents() {
        viewModel.baseEvent.observe(viewLifecycleOwner) {
            when (it) {
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
        _binding = null
    }


    private fun setupDialog() {

        val contentView: View = bindings.root

        val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = LockableBottomSheetBehavior<View>()
        (contentView.parent as View).layoutParams = params
        val behavior = params.behavior as BottomSheetBehavior<*>?

        behavior!!.skipCollapsed = true
        if (behavior is LockableBottomSheetBehavior<*>) {
            behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                        dismiss()
                    }
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        dismiss()
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun onUserSessionExpired() {
        baseActivity?.onUserSessionExpired()
    }

    open fun showErrorMessage(error: String) {
        baseActivity?.showErrorMessage(error)
    }

    private fun showProgressDialog() {
        baseActivity?.showLoading()
    }

    private fun hideProgressDialog() {
        baseActivity?.hideLoading()
    }
}
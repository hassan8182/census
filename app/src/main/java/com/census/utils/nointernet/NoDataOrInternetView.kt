package com.census.utils.nointernet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.census.R

class NoDataOrInternetView(context: Context, attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {
    private var retryVisibility: Boolean = true
    private var titleText: String? = null
    private var subtitleText: String? = null
    private var tvTitle: TextView? = null
    private var tvSubtitle: TextView? = null
    private var cvRetryInternet: View? = null
    private val layoutRootView: View
    private var onRetryInternet: OnRetryInternet? = null

    fun setTitle(titleText: String?) {
        this.titleText = titleText
        tvTitle?.text = titleText
    }

    fun setSubTitle(subtitleText: String?) {
        this.subtitleText = subtitleText
        tvSubtitle?.text = subtitleText
    }

    fun setRetryVisibility(retryVisibility: Boolean) {
        this.retryVisibility = retryVisibility
        cvRetryInternet?.visibility = if (retryVisibility) VISIBLE else INVISIBLE
    }

    fun setOnRetryListener(onRetryInternet: OnRetryInternet?) {
        this.onRetryInternet = onRetryInternet
        if (this.onRetryInternet != null) {
            cvRetryInternet?.setOnClickListener {
                this.onRetryInternet?.onRetryInternet()
            }
        }
    }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.noDataOrInternet, 0, 0
        )
        val titleText = typedArray.getString(R.styleable.noDataOrInternet_titleText)
        val subtitleText = typedArray.getString(R.styleable.noDataOrInternet_subtitleText)
        val retryVisibility =
            typedArray.getBoolean(R.styleable.noDataOrInternet_retryVisibility, true)
        typedArray.recycle()
        val inflater = LayoutInflater.from(context)
        layoutRootView = inflater.inflate(R.layout.layout_no_data_or_internet, this, true)
        tvTitle = layoutRootView.findViewById(R.id.tvTitle)
        tvSubtitle = layoutRootView.findViewById(R.id.tvSubtitle)
        cvRetryInternet = layoutRootView.findViewById(R.id.cvRetryInternet)
        if (titleText != null) {
            this.titleText = titleText
        }
        if (subtitleText != null) {
            this.subtitleText = subtitleText
        }
        cvRetryInternet?.visibility = if (retryVisibility) VISIBLE else INVISIBLE
    }
}
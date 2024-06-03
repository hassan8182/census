package com.census.utils

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.jakewharton.rxbinding.view.RxView
import com.census.R
import com.census.utils.typeface.FontsTypes
import timber.log.Timber
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt


open class BindingUtil {


    companion object {
        @JvmStatic
        @BindingAdapter("imageUrl")
        fun setImageUrl(imageView: ImageView, url: String?) {
            val context = imageView.context

            Glide.with(context)
                .asBitmap()
                .load(url)
                .centerCrop()
                .thumbnail(0.05f)
                .into(imageView)
        }


        @JvmStatic
        @BindingAdapter("loadImageWithPlaceHolder", "placeHolder", requireAll = false)
        fun loadImageWithPlaceHolder(imageView: ImageView, url: String?, placeHolder: Int) {

            kotlin.runCatching {

                val context = imageView.context

                Glide.with(context)
                    .asBitmap()
                    .error(placeHolder)
                    .placeholder(placeHolder)
                    .load(url)
                    .fitCenter()
                    .into(imageView)
            }
        }

        @JvmStatic
        @BindingAdapter("loadImageWithError", "errorPlaceHolder", requireAll = false)
        fun loadImageWithError(imageView: ImageView, url: String?, errorPlaceHolder: Int) {

            kotlin.runCatching {

                val context = imageView.context

                if (url.isNullOrEmpty()) {
                    Glide.with(context)
                        .load(errorPlaceHolder)
                        .into(imageView)
                } else {
                    Glide.with(context)
                        .load(url)
                        .error(errorPlaceHolder)
                        .fitCenter().into(imageView)
                }
            }

        }

        @JvmStatic
        @BindingAdapter("imageBackgroundTint")
        fun setImageBackgroundTint(view: View, colorCode: String) {
            if (colorCode.isEmpty()) {
                return
            }
            view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(colorCode))
        }


        @JvmStatic
        @BindingAdapter("rapidOnClick")
        fun rapidOnClick(view: View?, listener: View.OnClickListener) {
            if (view != null) {
                RxView.clicks(view).throttleFirst(700, TimeUnit.MILLISECONDS).subscribe(
                    { listener.onClick(view) }
                ) { error -> Timber.e(error.toString() + "") }
            }
        }

        @JvmStatic
        @BindingAdapter("setBottomMargin")
        fun setBottomMargin(view: View, bottomMargin: Float) {
            val layoutParams: ViewGroup.MarginLayoutParams =
                view.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(
                layoutParams.leftMargin, layoutParams.topMargin,
                layoutParams.rightMargin, bottomMargin.roundToInt()
            )
            view.layoutParams = layoutParams
        }

        @JvmStatic
        @BindingAdapter("setTopMargin")
        fun setTopMargin(view: View, topMargin: Float) {
            val layoutParams: ViewGroup.MarginLayoutParams =
                view.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(
                layoutParams.leftMargin, topMargin.roundToInt(),
                layoutParams.rightMargin, layoutParams.bottomMargin
            )
            view.layoutParams = layoutParams
        }

        @JvmStatic
        @BindingAdapter("loadImage")
        fun loadImage(imageView: ImageView, url: String?) {
            val context = imageView.context


            Glide.with(context)
                .asBitmap()
                .error(ColorDrawable(ContextCompat.getColor(context, R.color.grey_a9)))
                .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.grey_a9)))
                .load(url)
                .fitCenter()
                .into(imageView)


        }

        @JvmStatic
        @BindingAdapter("loadPostDetailImage")
        fun loadPostDetailImage(imageView: ImageView, url: String?) {
            val context = imageView.context
            Glide.with(context)
                .asBitmap()
                .error(ColorDrawable(ContextCompat.getColor(context, R.color.black)))
                .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.black)))
                .load(url)
                .fitCenter()
                .into(imageView)


        }

        @JvmStatic
        @BindingAdapter("setTintImageRes")
        fun setTintImageRes(view: ImageView, tintRes: Int) {
            if (tintRes != 0) {
                view.imageTintList = ColorStateList.valueOf(tintRes)
            }
        }


        @JvmStatic
        @BindingAdapter("loadWebUrl")
        fun loadWebUrl(webView: WebView, url: String?) {
            url?.let { webView.loadUrl(it) }
        }


        @JvmStatic
        @BindingAdapter("setImageTint")
        fun setImageTint(view: ImageView, tintRes: Int) {
            try {
                if (tintRes != 0) {
                    ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(tintRes))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @JvmStatic
        @BindingAdapter("setBackgroundOfView")
        fun setBackgroundOfView(view: View, resDrawable: Drawable) {
            try {
//                if (tintRes != 0) {
//                    ImageViewCompat.setImageTintList(view, ColorStateList.valueOf(tintRes))
//                }
                view.background = resDrawable
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @JvmStatic
        @BindingAdapter("setTextViewColor")
        fun setTextViewColor(view: TextView, colorRes: Int = 0) {
            if (colorRes != 0) {
                view.setTextColor(ContextCompat.getColor(view.context, colorRes))
            }
        }


        @JvmStatic
        @BindingAdapter("font")
        fun TextView.font(type: FontsTypes) {
            try {
                typeface = ResourcesCompat.getFont(context, type.fontRes)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        @JvmStatic
        @BindingAdapter("android:layout_width")
        fun setLayoutWidth(view: View, width: Float) {
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.width = width.toInt()
            view.layoutParams = layoutParams
        }

        @JvmStatic
        @BindingAdapter("android:layout_height")
        fun setLayoutHeight(view: View, height: Float) {
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.height = height.toInt()
            view.layoutParams = layoutParams
        }

        @JvmStatic
        @BindingAdapter("layoutMarginStart")
        fun setLayoutMarginStart(view: View, dimen: Float) {
            val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.marginStart = dimen.toInt()
            view.layoutParams = layoutParams
        }
    }

}

/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */
package com.census.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.telephony.TelephonyManager
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.SparseArray
import android.util.TypedValue
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.census.R
import com.census.utils.typeface.CustomTypefaceSpan
import com.census.utils.typeface.CustomTypefaces
import java.io.File
import java.text.*
import java.util.*
import java.util.regex.Pattern
import kotlin.math.abs
import kotlin.math.roundToInt


object CommonUtils {

    var DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
    private const val thirty = 30
    private const val fourtyFive = 45
    private const val sixety = 60
    private const val ninety = 90
    private const val twentyFour = 24

    fun getFileDuration(videoFile: File?, activity: Activity?): Long {
        var timeInMillis: Long
        try {
            val retriever = MediaMetadataRetriever()
            //use one of overloaded setDataSource() functions to set your data source
            retriever.setDataSource(activity, Uri.fromFile(videoFile))
            val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            timeInMillis = time!!.toLong()
            retriever.release()
        } catch (ignore: java.lang.Exception) {
            timeInMillis = -1
        }
        Log.d("camera_video_duration", "millis= $timeInMillis")
        return timeInMillis
    }

    fun increaseRectArea(view: View) {
        increaseRectArea(view, 10)
    }

    fun increaseRectArea(view: View, rectAreaSize: Int) {
        val delegateArea = Rect()
        view.getHitRect(delegateArea)
        delegateArea.right += rectAreaSize
        delegateArea.bottom += rectAreaSize
        delegateArea.left += rectAreaSize
        delegateArea.top += rectAreaSize
        val touchDelegate = TouchDelegate(
            delegateArea,
            view
        )

        // Sets the TouchDelegate on the parent view, such that touches
        // within the touch delegate bounds are routed to the child.
        if (view.parent is View) {
            (view.parent as View).touchDelegate = touchDelegate
        }
    }

    val EMAIL_ADDRESS = Pattern.compile(
        "^([\\w-]|(?<!\\.)\\.)+[a-zA-Z0-9]@[a-zA-Z0-9]([\\w\\-]+)((\\.([a-zA-Z]){2,9})+)$"
    )


    fun getMimeType(url: String): String? {
        var type: String? = null
        var extension = MimeTypeMap.getFileExtensionFromUrl(url)
        if (extension != null && !extension.isEmpty()) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        } else {
            val i = url.lastIndexOf('.')
            if (i > 0) {
                extension = url.substring(i + 1)
                type = when (extension) {
                    "jpg", "png", "webp", "bmp", "gif" -> "image"
                    "mp4" -> "video"
                    else -> "video"
                }
                type += "/$extension"
            }
        }
        return type
    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width: Int = image.width
        var height: Int = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    @SuppressLint("all")
    fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun isEmailValid(email: String?): Boolean {
        return EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordsMatched(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    fun isPhoneNumberValid(phoneNumber: String): Boolean {
        return phoneNumber.length in 6..12
    }


    fun dpToPx(dp: Int, context: Context): Float =
        TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            context.resources.displayMetrics
        )

    fun formatOneDecimalPoint(number: Double?): String {
        return DecimalFormat("##.#").format(number)
    }

    fun formatTwoDecimalPoint(number: Double?): String? {
        return DecimalFormat("##.##").format(number)
    }

    fun openGoogleMap(context: Context, lat: Double?, lang: Double?, address: String?) {
        var intent: Intent? = null
        val isInvalidLatLng =
            lat == null || lang == null || lat.equals(0.0) || lang.equals(0.0)

        if (!isInvalidLatLng) {
//            val uri = String.format(Locale.ENGLISH, "geo:%f,%f", boatLat, boatLng)
//            intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            val uri = if ((!address.isNullOrEmpty())) {
                "http://maps.google.com/maps?q=loc:$lat,$lang ($address)"
            } else {
                "http://maps.google.com/maps?q=loc:$lat,$lang"
            }
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        } else if (!address.isNullOrEmpty()) {
            intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://maps.google.co.in/maps?q=$address")
        }

        if (intent?.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        }

    }

    fun formatCount(number: Double): String? {
//        if (number < 1000) return
//        return String.format(Locale.getDefault(), "%d", number)
//        val exp = (Math.log(number.toDouble()) / Math.log(1000.0)).toInt()
//        val format = DecimalFormat("0.#")
        return DecimalFormat("##").format(number)
//        val value = format.format(number / Math.pow(1000.0, exp.toDouble()))
//        return String.format("%s%c", value, "kMBTPE"[exp - 1])
    }

    fun convertDp(context: Context, dp: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp,
            context.resources.displayMetrics
        ).toInt()
    }

    fun getDateTimeInFormat(date: Date?, format: String?): String {
        try {
            date?.let {
                val sdf = SimpleDateFormat(format, /*Locale.getDefault()*/Locale.US)
                return sdf.format(date)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun isSupportedMediaType(path: String): Boolean {
        val extension = getFileExtension(path)
        return extension.isNotEmpty() && (extension.equals(".jpg", ignoreCase = true)
                || extension.equals(".png", ignoreCase = true)
                || extension.equals(".jpeg", ignoreCase = true)
                || extension.equals(".tiff", ignoreCase = true)
                || extension.equals(".image", ignoreCase = true)
                || extension.equals(".mp4", ignoreCase = true))
    }

    fun getFileExtension(path: String?): String {
        return path?.substring(path.lastIndexOf('.')) ?: String()
    }

    fun trimExtensionFromFileName(fileName: String?): String? {
        return if (fileName != null && fileName.lastIndexOf('.') > 0) fileName.substring(
            0,
            fileName.lastIndexOf('.')
        ) else null
    }

    fun isMimeTypeOfVideo(mimeType: String?): Boolean {
        return mimeType != null && mimeType.startsWith("video")
    }

    fun <C> asList(sparseArray: SparseArray<C>?): List<C>? {
        if (sparseArray == null) return null
        val arrayList: MutableList<C> = ArrayList(sparseArray.size())
        for (i in 0 until sparseArray.size()) arrayList.add(sparseArray.valueAt(i))
        return arrayList
    }


    fun round(d: Double, decimalPlace: Int): String {
//        var bd = BigDecimal(java.lang.Double.toString(d))
//        bd = if (d % 1 == 0.0) {
//            bd.setScale(0, BigDecimal.ROUND_DOWN)
//        } else {
//            bd.setScale(decimalPlace, BigDecimal.ROUND_DOWN)
//        }

        val locale = Locale("en", "UK")
        val pattern = "##"
        val pattern1 = "##.#"
        val pattern2 = "##.##"

        val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat

        return if (decimalPlace == 1) {
            decimalFormat.applyPattern(pattern1)
            decimalFormat.format(d)
        } else if (decimalPlace == 2) {
            decimalFormat.applyPattern(pattern2)
            decimalFormat.format(d)
        } else {

            decimalFormat.applyPattern(pattern)
            decimalFormat.format(d)
        }
        // esko eng mae conver kro


//        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
//        return bd.toString()
    }


    fun getPricePerHour(isRtl: Boolean, strValue: String): Double? {
        return if (isRtl) {
            val arabicLocale = Locale.getAvailableLocales().first { it.language == "ar" }
            val numberFormat: NumberFormat = NumberFormat.getInstance(arabicLocale)
            try {
                val number: Number? = numberFormat.parse(strValue)

                number.toString().toDouble()
            } catch (e: ParseException) {
                e.printStackTrace()
                0.0
            }
        } else

            strValue.toDouble()
    }

    fun getDetectedCountry(context: Context, defaultCountryIsoCode: String): String {

        detectSIMCountry(context)?.let {
            return it
        }

        detectNetworkCountry(context)?.let {
            return it
        }

        detectLocaleCountry(context)?.let {
            return it
        }

        return defaultCountryIsoCode
    }

    private fun detectSIMCountry(context: Context): String? {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d("arslan_locale", "detectSIMCountry: ${telephonyManager.simCountryIso}")
            return telephonyManager.simCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectNetworkCountry(context: Context): String? {
        try {
            val telephonyManager =
                context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            Log.d("arslan_locale", "detectNetworkCountry: ${telephonyManager.networkCountryIso}")
            return telephonyManager.networkCountryIso
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun detectLocaleCountry(context: Context): String? {
        try {
            var localeCountryISO: String? = ""
            localeCountryISO = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales[0].country
            } else {
                context.resources.configuration.locale.country
            }
            Log.d("arslan_locale", "detectLocaleCountry: $localeCountryISO")
            return localeCountryISO
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun getDayMonthYear(dateString: String?): String? {
        try {
            dateString?.let { ds ->
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val targetFormat: DateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
                val date: Date = originalFormat.parse(ds)
                return targetFormat.format(date)
            }
        } catch (e: Exception) {
            Log.e("getDayMonthYear", e.toString())
        }
        return dateString
    }

    fun getDayPlusDotFromDate(dateString: String?): String? {
        try {
            dateString?.let { ds ->
                val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
                val targetFormat: DateFormat = SimpleDateFormat("E", Locale.ENGLISH)
                val date: Date = originalFormat.parse(ds)
                return targetFormat.format(date) + "."
            }
        } catch (e: Exception) {
        }
        return ""
    }

    fun getTimeAmPm(timeString: String?): String? {
        try {
            timeString?.let { ds ->
                val originalFormat: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)
                val targetFormat: DateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                val date: Date = originalFormat.parse(ds)
                return targetFormat.format(date)
            }
        } catch (e: Exception) {
        }
        return timeString
    }


    fun getFirstLetterCapital(s: String?): String? {
        return s?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }

    fun addMinutesInTime(
        dateStr: String?,
        fromFormat: String?,
        toFormat: String?,
        addMinutes: Int,
    ): String? {
        var date: Date? = null
        val calendar = Calendar.getInstance()
        try {
            date = SimpleDateFormat(fromFormat).parse(dateStr!!)
            calendar.time = date!!
            calendar.add(Calendar.MINUTE, addMinutes)
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }
        val sdf = SimpleDateFormat(toFormat)
        return sdf.format(calendar.time)
    }

    fun formatAndAddMonths(numberOfMonths: Int, dateString: String?): String? {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            if (numberOfMonths < 0) return ""
            val calendar = Calendar.getInstance()
            calendar.time = format.parse(dateString!!)!!
            calendar.add(Calendar.MONTH, numberOfMonths)
            getTimeString(
                calendar.time,
                "dd MMMM yyyy"
            )
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun formatAndAddDays(numberOfDays: Int, dateString: String?): String? {
        return try {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            if (numberOfDays < 0) return ""
            val calendar = Calendar.getInstance()
            calendar.time = format.parse(dateString!!)!!
            calendar.add(Calendar.DATE, numberOfDays)
            getTimeString(
                calendar.time,
                "dd MMMM yyyy"
            )
        } catch (e: ParseException) {
            e.printStackTrace()
            ""
        }
    }

    fun getTimeString(date: Date?, dateFormat: String?): String? {
        val format = SimpleDateFormat(dateFormat)
        return format.format(date)
    }

//    fun shareText(context: Context?, text: String?) {
//        text?.let {
//            val sendIntent: Intent = Intent().apply {
//                action = Intent.ACTION_SEND
//                putExtra(Intent.EXTRA_TEXT, text)
//                type = "text/plain"
//            }
//            val shareIntent = Intent.createChooser(sendIntent, null)
//            context?.startActivity(shareIntent)
//        }
//    }


    fun getFormattedCurrentDate(): String {
        val c = Calendar.getInstance().time
        val df = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return df.format(c)
    }


    fun getSpannableInTwoParts(
        normalText: String,
        phoneNumber: String,
        context: Context
    ): Spannable {
        val spannable: Spannable = SpannableString(normalText + phoneNumber)
        val regular: Typeface = CustomTypefaces.getPoppinsRegularTypeface(context)
        val semi: Typeface = CustomTypefaces.getPoppinsSemiTypeface(context)
        spannable.setSpan(
            CustomTypefaceSpan("", regular),
            0,
            normalText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            CustomTypefaceSpan("", semi),
            normalText.length,
            normalText.length + phoneNumber.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_a9)),
            0,
            normalText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            ForegroundColorSpan(
                ContextCompat.getColor(
                    context,
                    R.color.grey_a9
                )
            ),
            normalText.length,
            normalText.length + phoneNumber.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    fun isAppUpToDate(versionName: String, versionNameServer: String): Boolean {
        var res = 0
        val oldNumbers = versionName.split("\\.".toRegex()).toTypedArray()
        val newNumbers = versionNameServer.split("\\.".toRegex()).toTypedArray()
        // To avoid IndexOutOfBounds
        val maxIndex = Math.min(oldNumbers.size, newNumbers.size)
        for (i in 0 until maxIndex) {
            val oldVersionPart = Integer.valueOf(oldNumbers[i])
            val newVersionPart = Integer.valueOf(newNumbers[i])
            if (oldVersionPart < newVersionPart) {
                res = -1
                break
            } else if (oldVersionPart > newVersionPart) {
                res = 1
                break
            }
        }
        // If versions are the same so far, but they have different length...
//        if (res == 0 && oldNumbers.size != newNumbers.size) {
//            res = if (oldNumbers.size > newNumbers.size) 1 else -1
//        }
        return res >= 0
    }

    fun timeAgo(time: String?): String? {
        val seconds: Double = getSecondsForTime(time!!).toDouble()
        return getTodayText(seconds)
    }

    private fun getSecondsForTime(time: String): Int {
        if (time.isEmpty()) return 0
        val df = SimpleDateFormat(
            DATE_TIME_FORMAT, Locale.getDefault()
        )
        var seconds = 0
        try {
            val date = df.parse(time)
            val diff = Date().time - (date?.time!!)
            seconds = (abs(diff) / 1000).toInt()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return seconds
    }

    private fun getTodayText(seconds: Double): String? {
        val minutes: Double = seconds / sixety
        val words: String = if (seconds < fourtyFive) {
            "just now"
        } else if (seconds < ninety) {
            1.toString() + " min ago"
        } else if (minutes < fourtyFive) {
            minutes.roundToInt().toString() + " mins ago"
        } else if (minutes < ninety) {
            "1 hour ago"
        } else {
            return getTomorrowText(minutes)
        }
        return words
    }

    private fun getTomorrowText(minutes: Double): String {
        val hours: Double = minutes / sixety
        val days: Double = hours / twentyFour
        val words: String = if (hours < twentyFour) {
            hours.roundToInt().toString() + " hours ago"
        } else if (hours < 42) {
            "1 day ago"
        } else if (hours / twentyFour < thirty) {
            days.roundToInt().toString() + " days ago"
        } else if (hours / twentyFour < fourtyFive) {
            "1 month ago"
        } else {
            return getMonthsText(days)
        }
        return words
    }

    private fun getMonthsText(days: Double): String {
        val years = days / 365
        val words: String = if (days < 365) {
            (days / thirty).roundToInt()
                .toString() + " months ago"
        } else if (years < 1.5) {
            "1 year ago"
        } else {
            years.roundToInt().toString() + " years ago"
        }
        return words
    }

    fun getDay(milliSeconds: Long): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd") // HH:mm:ss");
        //start date or current date
        val c = Calendar.getInstance()
        //SimpleDateFormat dateFormat = getDateFormat();
        var currentDate: Date? = null
        val currentDateStr: String
        try {
            currentDateStr = dateFormat.format(c.time)
            currentDate = dateFormat.parse(currentDateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //end date
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        var end_date: Date? = null
        var endDateStr: String? = ""
        try {
            endDateStr = dateFormat.format(calendar.time)
            end_date = dateFormat.parse(endDateStr)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        //difference
        val difference = currentDate!!.time - end_date!!.time
        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24
        return difference / daysInMilli
    }

    public fun getDateInLong(date: String): Long {
        val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return df.parse(date)?.time ?: 0L
    }

    fun getDayMonthYearFromFullDate(dateString: String?): String? {
        try {
            dateString?.let { ds ->
                val originalFormat: DateFormat = SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH)
                val targetFormat: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH)
                val date: Date = originalFormat.parse(ds)
                return targetFormat.format(date)
            }
        } catch (e: Exception) {
            Log.e("getDayMonthYear", e.toString())
        }
        return dateString
    }

    fun roundToTwoDecimal(d: Double): String {
        val locale = Locale("en", "UK")
        val pattern2 = "##.##"
        val decimalFormat = NumberFormat.getNumberInstance(locale) as DecimalFormat
        decimalFormat.applyPattern(pattern2)
        return decimalFormat.format(d)

    }

    fun secondsToString(pTime: Int, format: String?): String? {
        return if (pTime < 0) {
            ""
        } else String.format(Locale.getDefault(), format!!, pTime / 60, pTime % 60)
    }

    fun getDateFromDateString(dateString: String?, dateFormat: String?): Date? {
        try {
            dateString?.let { ds ->
                dateFormat?.let { df ->
                    return SimpleDateFormat(df, Locale.ENGLISH).parse(ds)
                }
            }
        } catch (e: Exception) {
            Log.e("getDateFromDateString", e.toString())
        }
        return null
    }


    fun Double?.formatPrice(): String {
        return this?.let { price ->
            String.format("%.2f", price)
        } ?: "0.00"
    }

    fun String?.formatPrice(): String {

        kotlin.runCatching {
            this?.toDouble()
        }.onSuccess {
            return it.formatPrice()
        }

        return 0.00.formatPrice()
    }

    fun Int.durationToString(): String {
        return if (this <= 0) {
            "00:00"
        } else {
            val duration = (this / 1000f).roundToInt()
            String.format(
                Locale.getDefault(),
                "%02d:%02d",
                duration / 60, duration % 60
            )
        }
    }

    fun String?.formatPriceSymbol(): String {

        kotlin.runCatching {
            this?.toDouble()
        }.onSuccess {
            return it.formatPriceSymbol()
        }

        return 0.00.formatPriceSymbol()
    }

    fun Double?.formatPriceSymbol(): String {
        return this?.let { price ->
            "$${String.format("%.2f", price)}"
        } ?: "$0.00"
    }

    fun View.isKeyboardShown(): Boolean {
        val keyboardHeight = 100
        val rect = Rect()
        this.getWindowVisibleDisplayFrame(rect)
        val metrics = resources.displayMetrics
        val heightDiff: Int = bottom - rect.bottom
        return heightDiff > keyboardHeight * metrics.density
    }

    fun ViewGroup.isKeyboardShown(): Boolean {
        val keyboardHeight = 100
        val rect = Rect()
        this.getWindowVisibleDisplayFrame(rect)
        val metrics = resources.displayMetrics
        val heightDiff: Int = bottom - rect.bottom
        return heightDiff > keyboardHeight * metrics.density
    }

    fun String?.formatPostLikeTimeDate(): String {

        val formattedDateTime = StringBuilder()

        this?.apply {

            val parseFormat = SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss"
            )

            val dateFormat = SimpleDateFormat(
                "dd MMM, yyyy, hh:mm:a"
            )

            kotlin.runCatching {
                parseFormat.parse(this)
            }.onSuccess { parsedDate ->

                parsedDate?.apply {

                    dateFormat.format(this).apply {
                        formattedDateTime.append(this)
                    }

                } ?: formattedDateTime.append(this)

            }.onFailure {
                formattedDateTime.append(this)
            }
        }

        return formattedDateTime.toString()
    }

    fun String.isVideoType(): Boolean {
        return this.startsWith("video")
    }

    fun String.isAudioType(): Boolean {
        return this == "audio/mpeg"
    }

    fun String.isImageType(): Boolean {
        return this.startsWith("image")
    }

    fun String.isDocumentType(): Boolean {
        return this == "application/pdf"
    }

    fun Date.isPastDate(): Boolean {

        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val providedDate = calendar.time

        calendar.time = Date()
        calendar.set(Calendar.HOUR, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val currentDate = calendar.time

        return providedDate.before(currentDate)
    }

    fun Date.isPastDateTime(): Boolean {
        return this.before(Date())
    }

    fun Date.isFutureDateTime(): Boolean {
        return this.after(Date())
    }

    fun String.parseSlotDateTime(): Date? {

        val parsePattern = "yyyy-MM-dd HH:mm:ss"

        val parseFormat = SimpleDateFormat(
            parsePattern, Locale.getDefault()
        )

        kotlin.runCatching {
            parseFormat.parse(this)
        }.onSuccess { parsedDate ->
            return parsedDate
        }
        return null
    }

    fun getCurrentDateTimeForApiRequest(): String {
        val date = Date(System.currentTimeMillis())
        val outputFmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        outputFmt.timeZone = TimeZone.getTimeZone("UTC")
        return outputFmt.format(date)
    }


}

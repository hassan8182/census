package com.census.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKey

class SecureSharedPreference(context: Context) {

    private var sharedPreferences: SharedPreferences? = null

    init {

        kotlin.runCatching {
            val masterKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
            sharedPreferences = EncryptedSharedPreferences.create(
                context, context.packageName,
                masterKey, AES256_SIV, AES256_GCM
            )
        }
    }

    operator fun get(name: String?): String {
        return sharedPreferences?.getString(
            name, String()
        ) ?: String()
    }

    fun put(name: String?, value: String?) {
        val editor = sharedPreferences?.edit()
        editor?.putString(name, value)
        editor?.apply()
    }

    fun clearKeys() {
        kotlin.runCatching {
            put(PREF_GOOGLE_KEY, String())
            put(PREF_ACCESS_KEY, String())
            put(PREF_SECRET_KEY, String())
            put(PREF_BUCKET_NAME, String())
            put(PREF_REGION_NAME, String())
        }
    }

    fun saveKeys(
        googleKey: String = String(),
        accessKey: String = String(),
        secretKey: String = String(),
        regionName: String = String(),
        bucketName: String = String()
    ) {
        kotlin.runCatching {
            put(PREF_BUCKET_NAME, bucketName)
            put(PREF_REGION_NAME, regionName)
            put(PREF_GOOGLE_KEY, googleKey.drop(4).dropLast(4))
            put(PREF_ACCESS_KEY, accessKey.drop(4).dropLast(4))
            put(PREF_SECRET_KEY, secretKey.drop(4).dropLast(4))
        }
    }

    companion object {
        const val TAG = "SecureSharedPreference"
        const val PREF_GOOGLE_KEY = "PREF_GOOGLE_KEY"
        const val PREF_ACCESS_KEY = "PREF_ACCESS_KEY"
        const val PREF_SECRET_KEY = "PREF_SECRET_KEY"
        const val PREF_REGION_NAME = "PREF_REGION_NAME"
        const val PREF_BUCKET_NAME = "PREF_BUCKET_NAME"
    }
}
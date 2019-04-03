package com.andreacioccarelli.cryptoprefs

import android.content.Context
import android.content.SharedPreferences
import com.andreacioccarelli.cryptoprefs.interfaces.Wrapper
import com.andreacioccarelli.cryptoprefs.wrappers.PrefsEncrypter
import com.andreacioccarelli.cryptoprefs.wrappers.PrefsWrapper

/**
 * Created by andrea on 2018/May.
 * Part of the package com.andreacioccarelli.cryptoprefs
 */

public class CryptoWrapper(context: Context, auth: Pair<String, String>, shouldEncrypt: Boolean) {

    private val reader: SharedPreferences = context.getSharedPreferences(auth.first, Context.MODE_PRIVATE)
    private val writer: SharedPreferences.Editor = context.getSharedPreferences(auth.first, Context.MODE_PRIVATE).edit()

    private val crypto: Wrapper = if (shouldEncrypt) PrefsEncrypter(auth) else PrefsWrapper()

    fun getAll(): Map<String, String> {
        val result: MutableMap<String, String> = mutableMapOf()

        reader.all.forEach {
            result[crypto.decrypt(it.key.toString())] = crypto.decrypt(it.value.toString())
        }

        return result.toMap()
    }

    fun get(key: String, default: Any): String {
        val encryptedString = reader.getString(crypto.encrypt(key), crypto.encrypt(default.toString()))
        return crypto.decrypt(encryptedString!!)
    }


    fun put(key: String, value: Any) {
        writer.putString(crypto.encrypt(key), crypto.encrypt(value.toString())).apply()
    }

    fun queue(key: String, value: Any) {
        writer.putString(crypto.encrypt(key), crypto.encrypt(value.toString()))
    }

    fun remove(key: String) {
        writer.remove(crypto.encrypt(key)).apply()
    }

    fun apply() = writer.apply()

    fun erase() = writer.clear().apply()
}
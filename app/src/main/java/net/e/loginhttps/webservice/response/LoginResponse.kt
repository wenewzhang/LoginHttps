package net.e.loginhttps.webservice.response

import android.util.Log
import com.google.gson.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type


data class LoginResponse(val _message:String, val _data: JsonObject? = null){
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: JsonObject? = null

    constructor(): this("", null)

    fun getData(): JsonObject? {
        return data
    }

    fun setData(data: JsonObject) {
        this.data = data
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessage(): String {
        return message!!
    }
}
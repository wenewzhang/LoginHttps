package net.e.loginhttps.webservice.response

import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class ContractAddressResponse {
    private var message: String? = null

    @SerializedName("data")
    @Expose
    private var data: JsonObject? = null


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
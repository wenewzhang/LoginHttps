package net.e.loginhttps.webservice

import net.e.loginhttps.webservice.response.LoginResponse
import com.google.gson.JsonObject
import retrofit2.Call
//import retrofit2.http.Field
import retrofit2.http.*

public interface ApiInterface {

    //@Headers("Accept: application/json", "Content-Type: application/json")

//    @GET("http://cf15ce50.ngrok.io/postewelfare/REST/employee/storePk/LMTCLT70B45E472K/add4285d4de32c6930224fd2a51f8c5773ff93dca71")
//    fun getKeyDetails(): Call<StoreKeyResponse>

    @GET("api/ping")
    fun getPing(): Call<LoginResponse>

    @Headers(
        "Content-type:application/json")
    @POST("api/device/login")
    fun getLogin(@Body data: JsonObject): Call<LoginResponse>

}
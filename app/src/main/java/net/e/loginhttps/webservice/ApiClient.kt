package net.e.loginhttps.webservice

import com.google.gson.GsonBuilder
import net.e.loginhttps.webservice.Url.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    companion object {
        fun getClient(): Retrofit {
            return Retrofit.Builder().baseUrl(Url.BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getKeyAuthenticationDetails(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        fun getPing(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun getLogin(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }


        fun confirmBuyVoucher(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        fun cancelBuyVoucher(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun reedemVoucherInit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun reedemVoucherConfirm(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        fun reedemVoucherRollback(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(HttpClientService.getUnsafeOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

    }

}
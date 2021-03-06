package net.e.loginhttps.ui.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import net.e.loginhttps.R
import net.e.loginhttps.data.LoginRepository
import net.e.loginhttps.webservice.APICallback
import net.e.loginhttps.webservice.ApiClient
import net.e.loginhttps.webservice.ApiInterface
import net.e.loginhttps.webservice.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel(), APICallback {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private var contractAddress: JsonObject? = null

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
        val jsonData = JsonObject()
        jsonData.addProperty("email",username)
        jsonData.addProperty("password",password)
        jsonData.addProperty("device_serial","device_serial")
        getContractAddress(this, jsonData)
    }
    private fun getContractAddress(callBack: APICallback, jsonData: JsonObject) {
        val apiService: ApiInterface =
            ApiClient.getLogin().create(ApiInterface::class.java)
        val call: Call<LoginResponse> = apiService!!.getLogin(jsonData)
        Log.d("sos", "---URL--- contract address: " + call.request().url())
        call.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("sos", "contract address Failure:" + t.message)
                _loginResult.value = LoginResult(error = R.string.login_failed_network)
            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {

                try {

                    if (response.isSuccessful) {
                        var contractAddressResponse: LoginResponse = response.body()!!
                        Log.d("sos", "contract address Success " + response.body())
                        contractAddress = contractAddressResponse.getData()
                        if (contractAddress != null) {
//                            contractAddress = contractAddressResponse.getData()
                            Log.d(
                                "sos",
                                "contract address: get Contract Address:" + contractAddress.toString()
                            )
                            callBack.onSuccess(103, contractAddressResponse, response.code())
                        } else {
                            contractAddressResponse.setMessage("Failed to get the Contract Address")
                            callBack.onFailure(
                                103, contractAddressResponse.getMessage(), response.code()
                            )
                        }

                    } else {
                        Log.d("sos", "login Failure")
                        var contractAddressResponse = LoginResponse();
                        contractAddressResponse.setMessage("Failed to get the Contract Address")
                        callBack.onFailure(
                            103,
                            contractAddressResponse.getMessage(),
                            response.code()
                        )
                    }
                } catch (e: java.lang.Exception) {
                    Log.d("sos", "contract address Failure:" + e.message)

                }


            }

        })
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    override fun onSuccess(requestCode: Int, obj: Any, code: Int) {
        Log.d("sos", "request code $requestCode")
        _loginResult.value = LoginResult(success = LoggedInUserView(displayName = "My Friend!"))
    }

    override fun onFailure(requestCode: Int, obj: Any, code: Int) {
        Log.d("sos", "onFailure request code $requestCode")
        _loginResult.value = LoginResult(error = R.string.login_failed_email)
    }

    override fun onProgress(requestCode: Int, isLoading: Boolean) {
        Log.d("sos", "onProgress  $isLoading")
    }
}
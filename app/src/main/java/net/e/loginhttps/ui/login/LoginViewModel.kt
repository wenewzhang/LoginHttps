package net.e.loginhttps.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.widget.Toast
import net.e.loginhttps.data.LoginRepository
import net.e.loginhttps.data.Result

import net.e.loginhttps.R
import net.e.loginhttps.webservice.APICallback
import net.e.loginhttps.webservice.ApiClient
import net.e.loginhttps.webservice.ApiInterface
import net.e.loginhttps.webservice.response.ContractAddressResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel(), APICallback {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult
    private var contractAddress: String? = null

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
//        val result = loginRepository.login(username, password)
//
//        if (result is Result.Success) {
//            _loginResult.value = LoginResult(success = LoggedInUserView(displayName = result.data.displayName))
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
        getContractAddress(this, username, password)
    }
    private fun getContractAddress(callBack: APICallback, username: String, password: String) {
        val apiService: ApiInterface =
            ApiClient.getLogin().create(ApiInterface::class.java)
        val call: Call<ContractAddressResponse> =
            apiService.getLogin(username, password)
        Log.d("sos", "---URL--- contract address: " + call.request().url())
        call.enqueue(object : Callback<ContractAddressResponse> {
            override fun onFailure(call: Call<ContractAddressResponse>, t: Throwable) {
                Log.d("sos", "contract address Failure:" + t.message)
            }

            override fun onResponse(
                call: Call<ContractAddressResponse>,
                response: Response<ContractAddressResponse>
            ) {

                try {
                    var contractAddressResponse: ContractAddressResponse = response.body()!!
                    Log.d("sos", "contract address Success " + contractAddressResponse.isSuccess())

                    if (contractAddressResponse != null) {
                        if (contractAddressResponse.isSuccess()!!) {
                            contractAddress = contractAddressResponse.getContractAddress()
                            Log.d(
                                "sos",
                                "contract address: get Contract Address:" + contractAddress
                            )
                            callBack.onSuccess(103, contractAddressResponse, response.code())


                        } else {
                            contractAddressResponse.setMessage("Failed to get the Contract Address")
                            callBack.onFailure(
                                103, contractAddressResponse.getMessage(), response.code()
                            )
                        }

                    } else {
                        contractAddressResponse = ContractAddressResponse();
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
    }

    override fun onFailure(requestCode: Int, obj: Any, code: Int) {
        Log.d("sos", "onFailure request code $requestCode")
    }

    override fun onProgress(requestCode: Int, isLoading: Boolean) {
        Log.d("sos", "onProgress  $isLoading")
    }
}
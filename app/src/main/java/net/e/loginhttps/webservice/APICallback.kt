package net.e.loginhttps.webservice

interface APICallback {

     fun onSuccess(requestCode: Int, obj: Any, code: Int)

     fun onFailure(requestCode: Int, obj: Any, code: Int)

     fun onProgress(requestCode: Int, isLoading: Boolean)


}
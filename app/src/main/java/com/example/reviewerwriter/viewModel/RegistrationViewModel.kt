package com.example.reviewerwriter.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reviewerwriter.model.RegistrationRequest
import com.example.reviewerwriter.retrofit.NetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrationViewModel {
    // Состояние для полей кнопок
    val textButtonSignIn = mutableStateOf("SIGN IN")
    val textButtonSignUp = mutableStateOf("SIGN UP")

    // для исключений
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // LiveData для навигации
    private val _navigateToLogin = MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean> get() = _navigateToLogin
    fun onTextButtonSignInClick(){
        _navigateToLogin.value = true
    }
    fun onTextButtonSignUpClick(){
        /*todo:*/
    }
    fun onNavigationDone() {
        _navigateToLogin.value = null
    }
    fun onErrorDone(){
        _error.value = ""
    }
    fun onButtonSignUPClick(usernameTextField: MutableState<String>,
                            passwordTextField: MutableState<String>,
                            confirmPasswordTextField: MutableState<String>){
        // проверка на значения в полях
        if(checkFieldForText(usernameTextField.value) &&
            checkFieldForText(passwordTextField.value) &&
            checkFieldForText(confirmPasswordTextField.value)) {
            if (checkPasswordMatch(passwordTextField.value, confirmPasswordTextField.value)){
                // создание экземпляров NetworkModule и Retrofit перед вызовом provideMainApi
                val networkModule = NetworkModule()
                val client = networkModule.provideOkHttpClient()
                val retrofit = networkModule.provideRetrofit(client)
                val mainApi = networkModule.provideMainApi(retrofit)

                // отправка запроса в многопоточном режиме
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        val statusCode = mainApi.registration(
                            RegistrationRequest (
                                usernameTextField.value,
                                passwordTextField.value
                            )
                        ).code
                    _error.postValue("код статуса = ${statusCode}")
                    }catch (e: Exception){
                        _error.postValue("error")
                        Log.w("error","${e.message}")
                    }
                }
            }
            else{
                /*todo: анимация остутствия инфы в поле*/
                _error.postValue("пароли должны совпадать")
            }
        }
        else{
            /*todo: анимация остутствия инфы в поле*/
            _error.postValue("все поля должны быть заполнены")
        }
    }
    private fun checkFieldForText(field: String) : Boolean{
        return field.trim().isNotEmpty()
    }
    private fun checkPasswordMatch(password1: String, password2: String): Boolean{
        return password1 == password2
    }
}
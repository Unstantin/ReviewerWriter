package com.example.reviewerwriter.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reviewerwriter.model.LoginRequest
import com.example.reviewerwriter.retrofit.NetworkModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel{
    // Состояние для полей кнопок
    val textButtonSignIn = mutableStateOf("SIGN IN")
    val textButtonSignUp = mutableStateOf("SIGN UP")

    // для исключений
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    // LiveData для навигации
    private val _navigateToRegistration = MutableLiveData<Boolean>()
    val navigateToRegistration: LiveData<Boolean> get() = _navigateToRegistration

    fun onTextButtonSignInClick(){
        /*TODO: сделать визуализацию(заглушку) нажатия*/
    }
    fun onTextButtonSignUpClick(){
        /*TODO:  сделать визуализацию нажатия*/
        _navigateToRegistration.value = true
    }
    fun onNavigationDone() {
        _navigateToRegistration.value = null
    }
    fun onErrorDone(){
        _error.value = ""
    }
    fun onButtonSignInClick(usernameTextField: MutableState<String>,
                            passwordTextField: MutableState<String>){
        // проверка на значения в полях
        if(checkFieldForText(usernameTextField.value) && checkFieldForText(passwordTextField.value)){
            // создание экземпляров NetworkModule и Retrofit перед вызовом provideMainApi
            val networkModule = NetworkModule()
            val client = networkModule.provideOkHttpClient()
            val retrofit = networkModule.provideRetrofit(client)
            val mainApi = networkModule.provideMainApi(retrofit)

            // отправка запроса в многопоточном режиме
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val loginResponses = mainApi.authentication(
                        LoginRequest(
                            usernameTextField.value,
                            passwordTextField.value
                        )
                    )
                    /*TODO: сделать проверку на null для errorInfo*/
                    Log.w("myRequest", loginResponses.errorInfo)
                    /*todo: обработка данных с бэка*/
                }
                catch(e: Exception){
                    // вызов тоста в основном потоке
                    withContext(Dispatchers.Main) {
                        _error.postValue("error")                    }
                }
            }
        }
        else{
            Log.w("ЛОГ1", "Не все поля заполнены")
            /*todo: анимация остутствия инфы в поле*/
            _error.postValue("все поля должны быть заполнены")
        }
    }
    private fun checkFieldForText(field: String) : Boolean{
        return field.trim().isNotEmpty()
    }
}

package com.example.reviewerwriter.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.reviewerwriter.model.LoginRequest
import com.example.reviewerwriter.retrofit.NetworkModule
import com.example.reviewerwriter.utils.ObserveNavigationInterface
import com.example.reviewerwriter.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel : showToastMessage , ObserveNavigationInterface{


    // для вывода сообщение
    override val _showToastMessage = MutableLiveData<String>()

    // LiveData для навигации
    override val _navigateTo = MutableLiveData<Boolean>()

    override fun onTextButtonSignInClick(){
        /*TODO: сделать визуализацию(заглушку) нажатия*/
    }
    fun onTextButtonSignUpClick(){
        /*TODO:  сделать визуализацию нажатия*/
        _navigateTo.value = true
    }
    override fun onNavigationDone() {
        _navigateTo.value = null
    }
    override fun onshowToastMessageDone(){
        _showToastMessage.value = ""
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
                        _showToastMessage.postValue("error")
                    }
                }
            }
        }
        else{
            Log.w("ЛОГ1", "Не все поля заполнены")
            /*todo: анимация остутствия инфы в поле*/
            _showToastMessage.postValue("все поля должны быть заполнены")
        }
    }
    private fun checkFieldForText(field: String) : Boolean{
        return field.trim().isNotEmpty()
    }
}

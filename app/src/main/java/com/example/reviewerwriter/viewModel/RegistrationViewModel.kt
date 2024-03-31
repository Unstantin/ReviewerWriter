package com.example.reviewerwriter.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import com.example.reviewerwriter.model.RegistrationRequest
import com.example.reviewerwriter.retrofit.NetworkModule
import com.example.reviewerwriter.utils.Screens
import com.example.reviewerwriter.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class RegistrationViewModel (navController: NavController): showToastMessage {
    val navController = navController
    // для вывода сообщений
    override val _showToastMessage = MutableLiveData<String>()

    fun onTextButtonSignInClick(){
        navController.navigate(Screens.LOGIN_SCREEN)
    }
    fun onTextButtonSignUpClick(){
        /*todo:*/
    }
    override fun onshowToastMessageDone(){
        _showToastMessage.value = ""
    }
    fun onButtonSignUPClick(usernameTextField: MutableState<String>,
                            passwordTextField: MutableState<String>,
                            confirmPasswordTextField: MutableState<String>){
        /*TODO: проверка на отсутствие спец символов*/

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
                        _showToastMessage.postValue("код статуса = ${statusCode}")

                    }catch (e: Exception){
                        _showToastMessage.postValue("error")
                        Log.w("error","${e.message}")
                    }
                }
                // для перехода на экран login после успешной регистрации
                navController.navigate(Screens.LOGIN_SCREEN)
            }
            else{
                /*todo: анимация остутствия инфы в поле*/
                _showToastMessage.postValue("пароли должны совпадать")
            }
        }
        else{
            /*todo: анимация остутствия инфы в поле*/
            _showToastMessage.postValue("все поля должны быть заполнены")
        }
    }
    private fun checkFieldForText(field: String) : Boolean{
        return field.trim().isNotEmpty()
    }
    private fun checkPasswordMatch(password1: String, password2: String): Boolean{
        return password1 == password2
    }
}
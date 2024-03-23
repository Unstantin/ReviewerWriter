package com.example.reviewerwriter.viewModel

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import com.example.reviewerwriter.MainActivity
import com.example.reviewerwriter.model.LoginRequest
import com.example.reviewerwriter.model.LoginResponses
import com.example.reviewerwriter.retrofit.MainApi
import com.example.reviewerwriter.retrofit.NetworkModule
import com.example.reviewerwriter.view.LoginView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginViewModel(private val activity: MainActivity) : ViewModel(){
    // Состояние для полей кнопок
    val textButtonSignIn = mutableStateOf("SIGN IN")
    val textButtonSignUp = mutableStateOf("SIGN UP")

    fun onTextButtonSignInClick(){
        /*TODO: сделать визуализацию(заглушку) нажатия*/
    }
    fun onTextButtonSignUpClick(){
        /*TODO: сделать переход к registrationView*/
    }
    fun onButtonSignInClick(usernameTextField: MutableState<String>, passwordTextField: MutableState<String>){
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
                    val LoginResponses = mainApi.authentication(
                        LoginRequest(
                            usernameTextField.value,
                            passwordTextField.value
                        )
                    )
                    /*TODO: сделать проверку на null для errorInfo*/
                    Log.w("myRequest", LoginResponses.errorInfo)
                    /*todo: обработка данных с бэка*/
                }
                catch(e: Exception){
                    // вызов тоста в основном потоке
                    withContext(Dispatchers.Main) {
                        Toast.makeText(activity,"error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else{
            /*todo: анимация остутствия инфы в поле*/
            Toast.makeText(activity,"все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
        }
    }
    fun checkFieldForText(field: String) : Boolean{
        return field.trim().isNotEmpty()
    }
}

package com.example.reviewerwriter.ui.authScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.domain.authUseCase.AuthRepository
import com.example.reviewerwriter.domain.authUseCase.LogUseCase
import com.example.reviewerwriter.ui.utils.Screens
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val logUseCase: LogUseCase,
    private val authRepository: AuthRepository
) : ViewModel(), showToastMessage {
    // Пустой конструктор

    // для вывода сообщение
    override val _showToastMessage = MutableLiveData<String>()
    val usernameTextField =  mutableStateOf("")
    val passwordTextField = mutableStateOf("")

    fun onTextButtonSignInClick() {
        /*TODO: сделать визуализацию(заглушку) нажатия*/
    }

    fun onTextButtonSignUpClick(navController: NavController) {
        /*TODO:  сделать визуализацию нажатия*/
        navController.navigate(Screens.REGISTRATION_SCREEN)
    }

    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }

    fun onButtonSignInClick(
        usernameTextField: MutableState<String>,
        passwordTextField: MutableState<String>,
        navController: NavController
    ) {
        // проверка на значения в полях
        if (checkFieldForText(usernameTextField.value) && checkFieldForText(passwordTextField.value)) {
            // создание AuthDto для отправки на сервер
            val authDto = AuthDto(usernameTextField.value, passwordTextField.value)

            // отправка запроса в многопоточном режиме
            viewModelScope.launch {
                try {
                    logUseCase.execute(authDto){status ->
                        when(status.statusCode){
                            200 -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    withContext(Dispatchers.Main) {
                                        _showToastMessage.value =
                                            "Вы вошли в аккаунт"
                                        navController.navigate(Screens.MAIN_SCREEN)
                                    }
                                }
                            }
                            else -> {
                                CoroutineScope(Dispatchers.IO).launch {
                                    withContext(Dispatchers.Main) {
                                        // Обработка ошибки
                                        _showToastMessage.value =
                                            "Ошибка: ${status.errors?.message}"
                                        status.errors?.message?.let { Log.w("ошибка else", it) }
                                    }
                                }
                            }
                        }
                    }
                } catch (e: Exception) {
                    // Вызов тоста в основном потоке
                    withContext(Dispatchers.Main) {
                        _showToastMessage.postValue("Ошибка: ${e.message}")
                        e.message?.let { Log.w("ошибка catch", it) }
                    }
                }
            }
        } else {
            // Вывод сообщения о необходимости заполнения всех полей
            _showToastMessage.postValue("Все поля должны быть заполнены")
        }
    }

    private fun checkFieldForText(field: String): Boolean {
        return field.trim().isNotEmpty()
    }
}
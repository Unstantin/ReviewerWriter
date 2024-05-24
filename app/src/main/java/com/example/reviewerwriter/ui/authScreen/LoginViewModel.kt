package com.example.reviewerwriter.ui.authScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.reviewerwriter.data.AuthRepositoryImpl
import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.ui.utils.Screens
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel() : ViewModel(), showToastMessage {

    // для вывода сообщение
    override val _showToastMessage = MutableLiveData<String>()
    val usernameTextField =  mutableStateOf("")
    val passwordTextField = mutableStateOf("")
    private val authRepository = AuthRepositoryImpl()
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
                    val response = authRepository.LoginUser(authDto)
                    // Обработка результата в основном потоке
                    withContext(Dispatchers.Main) {
                        if (response.value != null) {
                            // Успешная авторизация, можно получить токен через response.value.token
                            navController.navigate(Screens.MAIN_SCREEN)
                        } else {
                            // Обработка ошибки
                            val error = response.errors
                            // Вывод сообщения об ошибке
                            _showToastMessage.postValue(error?.message ?: "Ошибка авторизации")
                        }
                    }
                } catch (e: Exception) {
                    // Вызов тоста в основном потоке
                    withContext(Dispatchers.Main) {
                        _showToastMessage.postValue("Ошибка сети")
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
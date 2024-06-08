package com.example.reviewerwriter.ui.authScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.reviewerwriter.data.dto.AuthDto
import com.example.reviewerwriter.domain.authUseCase.AuthRepository
import com.example.reviewerwriter.domain.authUseCase.RegUseCase
import com.example.reviewerwriter.ui.utils.Screens
import com.example.reviewerwriter.ui.utils.showToastMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationViewModel(
    private val authRepository: AuthRepository,
    private val regUseCase: RegUseCase
) : ViewModel(), showToastMessage {
    // для вывода сообщений
    override val _showToastMessage = MutableLiveData<String>()
    val usernameTextField = mutableStateOf("")
    val passwordTextField = mutableStateOf("")
    val confirmPasswordTextField = mutableStateOf("")
    val passwordVisible =  mutableStateOf(false)
    fun onTextButtonSignInClick(navController: NavController) {
        navController.navigate(Screens.LOGIN_SCREEN)
    }

    fun onTextButtonSignUpClick() {
        /*todo:*/
    }

    override fun onshowToastMessageDone() {
        _showToastMessage.value = ""
    }

    fun onButtonSignUPClick(
        usernameTextField: MutableState<String>,
        passwordTextField: MutableState<String>,
        confirmPasswordTextField: MutableState<String>,
        navController: NavController
    ) {
        //TODO: проверка на отсутствие спец символов

        // проверка на значения в полях
        if (checkFieldForText(usernameTextField.value) &&
            checkFieldForText(passwordTextField.value) &&
            checkFieldForText(confirmPasswordTextField.value)
        ) {
            if (checkPasswordMatch(passwordTextField.value, confirmPasswordTextField.value)) {
                val authDto = AuthDto(usernameTextField.value,passwordTextField.value)
                // отправка запроса в многопоточном режиме
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        regUseCase.execute(authDto) { status ->
                            when (status.statusCode) {
                                200 -> {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        withContext(Dispatchers.Main) {
                                            _showToastMessage.value =
                                                "Вы успешно зарегестрировались"
                                            navController.navigate(Screens.LOGIN_SCREEN)
                                        }
                                    }
                                }

                                else -> {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        withContext(Dispatchers.Main) {
                                            // Обработка ошибки
                                            _showToastMessage.value =
                                                "Ошибка: ${status.errors?.message   }"
                                        }
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        _showToastMessage.postValue("Ошибка сети: ${e.message}")
                        Log.w("error", "${e.message}")
                    }
                }
            } else {
                _showToastMessage.postValue("пароли должны совпадать")
            }
        } else {
            _showToastMessage.postValue("все поля должны быть заполнены")
        }
    }

    private fun checkFieldForText(field: String): Boolean {
        return field.trim().isNotEmpty()
    }

    private fun checkPasswordMatch(password1: String, password2: String): Boolean {
        return password1 == password2
    }
}
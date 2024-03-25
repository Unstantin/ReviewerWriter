package com.example.reviewerwriter.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.reviewerwriter.R
import com.example.reviewerwriter.utils.ObserveNavigation
import com.example.reviewerwriter.utils.ObserveToastMessage
import com.example.reviewerwriter.viewModel.RegistrationViewModel
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationView (context: Context, onClick: () -> Unit){
    val registrationViewModel = RegistrationViewModel()

    // Состояние для полей кнопок
    val usernameTextField = remember { mutableStateOf("") }
    val passwordTextField = remember { mutableStateOf("") }
    val confirmPasswordTextField = remember { mutableStateOf("") }
    val textButtonSignIn = mutableStateOf("SIGN IN")
    val textButtonSignUp = mutableStateOf("SIGN UP")
    val mainText = "ReviewerWriterApp"
    val usernameTextFieldPlaceholder = "Username"
    val passwordTextFieldPlaceholder = "Password"
    val confirmPasswordTextFieldPlaceholder = "Confirm Password"
    val mainButtonText = "SIGN UP"

    //отслеживание
    ObserveToastMessage(registrationViewModel, context)
    ObserveNavigation(registrationViewModel, onClick)

    Scaffold{
        // размещение элементов на экране
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = mainText, modifier = Modifier.padding(top = 94.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 130.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                TextButton(onClick = { registrationViewModel.onTextButtonSignInClick() }) {
                    Text(
                        text = textButtonSignIn.value,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                TextButton(onClick = { registrationViewModel.onTextButtonSignUpClick() }) {
                    Text(
                        text = textButtonSignUp.value,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }

            TextField(
                value = usernameTextField.value,
                onValueChange = { usernameTextField.value = it },
                modifier = Modifier.padding(top = 25.dp),
                placeholder = { Text(usernameTextFieldPlaceholder) }
            )

            TextField(value = passwordTextField.value,
                onValueChange = { passwordTextField.value = it},
                modifier = Modifier.padding(top = 25.dp),
                placeholder = { Text(passwordTextFieldPlaceholder) },
                visualTransformation = PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { /*TODO: нажатие на просмотр пароля*/ }) {
                        Icon(
                            /*TODO: изменить иконку для нажатия*/
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Visability Icon")
                    }
                }
            )

            TextField(value = confirmPasswordTextField.value,
                onValueChange = { confirmPasswordTextField.value = it},
                modifier = Modifier.padding(top = 25.dp),
                placeholder = { Text(confirmPasswordTextFieldPlaceholder) },
                visualTransformation = PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { /*TODO: нажатие на просмотр пароля*/ }) {
                        Icon(
                            /*TODO: изменить иконку для нажатия*/
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Visability Icon")
                    }
                }
            )
            Button(
                // передаем значения в полях при нажатии на кнопку
                onClick = {registrationViewModel.onButtonSignUPClick(
                    usernameTextField,
                    passwordTextField,
                    confirmPasswordTextField
                ) },
                modifier = Modifier
                    .padding(60.dp)
                    .size(276.dp, 45.dp)
            ) {
                Text(mainButtonText)
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RegistrationView(NavController).registrationView(RegistrationViewModel())
}*/

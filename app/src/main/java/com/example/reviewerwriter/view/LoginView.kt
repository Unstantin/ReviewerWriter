package com.example.reviewerwriter.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reviewerwriter.R
import com.example.reviewerwriter.viewModel.LoginViewModel


class LoginView {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable

    fun loginView(loginViewModel: LoginViewModel) {
        // размещение элементов на экране
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "ReviewerWriterApp", modifier = Modifier.padding(top = 94.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 130.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                TextButton(onClick = { loginViewModel.onTextButtonSignInClick() }) {
                    Text(
                        text = loginViewModel.textButtonSignIn.value,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                }
                TextButton(onClick = { loginViewModel.onTextButtonSignUpClick() }) {
                    Text(
                        text = loginViewModel.textButtonSignUp.value,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
            val usernameTextField = remember { mutableStateOf("") }
            TextField(
                value = usernameTextField.value,
                onValueChange = { usernameTextField.value = it },
                modifier = Modifier.padding(top = 25.dp),
                placeholder = { Text("Username") }
            )
            val passwordTextField = remember { mutableStateOf("") }
            TextField(value = passwordTextField.value,
                onValueChange = { passwordTextField.value = it},
                modifier = Modifier.padding(top = 25.dp),
                placeholder = { Text("Password") },
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
                onClick = {loginViewModel.onButtonSignInClick(usernameTextField, passwordTextField) },
                modifier = Modifier
                    .padding(60.dp)
                    .size(276.dp, 45.dp)
            ) {
                Text("SIGN IN")
            }
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginView().loginView(LoginViewModel())

}*/

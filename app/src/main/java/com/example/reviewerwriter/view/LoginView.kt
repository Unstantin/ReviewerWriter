package com.example.reviewerwriter.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.reviewerwriter.R
import com.example.reviewerwriter.ui.theme.DarkMuted
import com.example.reviewerwriter.ui.theme.DarkVibrant
import com.example.reviewerwriter.ui.theme.LightMuted
import com.example.reviewerwriter.ui.theme.Muted
import com.example.reviewerwriter.ui.theme.Vibrant
import com.example.reviewerwriter.utils.ObserveNavigation
import com.example.reviewerwriter.utils.ObserveToastMessage
import com.example.reviewerwriter.viewModel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(context: Context, onClick: () -> Unit) {
    val loginViewModel = LoginViewModel()

    // Состояние для полей кнопок
    val usernameTextField = remember { mutableStateOf("") }
    val passwordTextField = remember { mutableStateOf("") }
    val textButtonSignIn = mutableStateOf("SIGN IN")
    val textButtonSignUp = mutableStateOf("SIGN UP")
    val mainText = "ReviewerWriterApp"
    val usernameTextFieldPlaceholder = "Username"
    val passwordTextFieldPlaceholder = "Password"
    val mainButtonText = "SIGN IN"

    //отслеживание
    ObserveToastMessage(loginViewModel, context)
    ObserveNavigation(loginViewModel, onClick)

    Scaffold(

    ) {

        // размещение элементов на экране
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Muted)
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            //verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = mainText,
                style = MaterialTheme.typography.headlineLarge,
                color = Vibrant,
                modifier = Modifier
                .padding(top = 94.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 130.dp, bottom = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                TextButton(onClick = { loginViewModel.onTextButtonSignInClick() }) {
                    Text(
                        text = textButtonSignIn.value,
                        modifier = Modifier.padding(end = 16.dp),
                        color = Vibrant
                    )
                }
                TextButton(onClick = { loginViewModel.onTextButtonSignUpClick() }) {
                    Text(
                        text = textButtonSignUp.value,
                        modifier = Modifier.padding(start = 16.dp),
                        color = Vibrant
                    )
                }
            }

            TextField(
                value = usernameTextField.value,
                onValueChange = { usernameTextField.value = it },
                modifier = Modifier
                    .padding(top = 25.dp)
                    .width(276.dp)
                    .clip(RoundedCornerShape(25.dp)),
                placeholder = { Text(usernameTextFieldPlaceholder) },
                colors = TextFieldDefaults.textFieldColors(placeholderColor = Vibrant,
                    containerColor = LightMuted),
                )

            TextField(value = passwordTextField.value,
                onValueChange = { passwordTextField.value = it },
                modifier = Modifier
                    .padding(top = 25.dp)
                    .clip(RoundedCornerShape(25.dp)),
                placeholder = { Text(passwordTextFieldPlaceholder) },
                visualTransformation = PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { /*TODO: нажатие на просмотр пароля*/ }) {
                        Icon(
                            /*TODO: изменить иконку для нажатия*/
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Visability Icon"
                        )
                    }
                },
                colors = TextFieldDefaults.textFieldColors(placeholderColor = Vibrant,
                    unfocusedTrailingIconColor = DarkVibrant,
                    focusedTrailingIconColor = DarkVibrant,
                    containerColor = LightMuted,
                ),
            )
            Button(
                // передаем значения в полях при нажатии на кнопку
                onClick = {
                    loginViewModel.onButtonSignInClick(
                        usernameTextField,
                        passwordTextField
                    )
                },
                colors = ButtonDefaults.buttonColors(DarkMuted),
                modifier = Modifier
                    .padding(60.dp)
                    .size(276.dp, 45.dp)
            ) {
                Text(mainButtonText, color = Vibrant)

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    LoginView(context = LocalContext.current, onClick = {})

}

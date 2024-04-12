package com.example.reviewerwriter.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.theme.SystemInDarkThemeShadow
import com.example.reviewerwriter.ui.theme.SystemInLightThemeShadow
import com.example.reviewerwriter.utils.ObserveToastMessage
import com.example.reviewerwriter.viewModel.LoginViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState",
    "InvalidColorHexValue"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(context: Context, navController: NavController) {
    val loginViewModel = remember {
        LoginViewModel(navController)
    }

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

    ReviewerWriterTheme {
        Scaffold() {
            // размещение элементов на экране
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = mainText,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier
                        .padding(top = 94.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 130.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    TextButton(
                        onClick = { loginViewModel.onTextButtonSignInClick() },
                        modifier = Modifier
                    ) {
                        Text(
                            text = textButtonSignIn.value,
                            modifier = Modifier
                                .shadow(
                                    clip = true,
                                    elevation = 15.dp,
                                    ambientColor = if (isSystemInDarkTheme()) SystemInDarkThemeShadow
                                    else SystemInLightThemeShadow,
                                    spotColor = if (isSystemInDarkTheme()) SystemInDarkThemeShadow
                                    else SystemInLightThemeShadow
                                )
                                .background(
                                    shape = MaterialTheme.shapes.extraSmall,
                                    color = if (isSystemInDarkTheme()) SystemInDarkThemeShadow
                                    else SystemInLightThemeShadow
                                )
                        )
                    }
                    TextButton(
                        onClick = { loginViewModel.onTextButtonSignUpClick() }
                    ) {
                        Text(
                            text = textButtonSignUp.value,
                            modifier = Modifier
                                .shadow(
                                    clip = true,
                                    elevation = 15.dp,
                                    ambientColor = if (isSystemInDarkTheme()) SystemInDarkThemeShadow
                                    else SystemInLightThemeShadow,
                                    spotColor = if (isSystemInDarkTheme()) SystemInDarkThemeShadow
                                    else SystemInLightThemeShadow
                                )
                                .background(
                                    shape = MaterialTheme.shapes.extraSmall,
                                    color = if (isSystemInDarkTheme()) SystemInDarkThemeShadow
                                    else SystemInLightThemeShadow
                                )
                        )
                    }
                }

                TextField(
                    value = usernameTextField.value,
                    onValueChange = { usernameTextField.value = it },
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .width(276.dp)
                        .clip(MaterialTheme.shapes.extraLarge),
                    placeholder = { Text(usernameTextFieldPlaceholder) },
                    colors = TextFieldDefaults.textFieldColors(
                    ),
                )

                TextField(
                    value = passwordTextField.value,
                    onValueChange = { passwordTextField.value = it },
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .clip(MaterialTheme.shapes.extraLarge),
                    placeholder = { Text(passwordTextFieldPlaceholder) },
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {/*TODO: нажатие на просмотр пароля*/ }) {
                            Icon(
                                /*TODO: изменить иконку для нажатия*/
                                imageVector = Icons.Default.Visibility,
                                contentDescription = "Visability Icon"
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
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
                    modifier = Modifier
                        .padding(60.dp)
                        .size(276.dp, 45.dp)
                ) {
                    Text(
                        mainButtonText,
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    val navController = rememberNavController()
    LoginView(context = LocalContext.current,navController )

}


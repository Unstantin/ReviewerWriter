package com.example.reviewerwriter.ui.authScreen

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.reviewerwriter.ui.theme.ReviewerWriterTheme
import com.example.reviewerwriter.ui.theme.SystemInDarkThemeShadow
import com.example.reviewerwriter.ui.theme.SystemInLightThemeShadow
import com.example.reviewerwriter.ui.utils.ObserveToastMessage
import org.koin.androidx.compose.koinViewModel

@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState",
    "InvalidColorHexValue"
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    loginViewModel: LoginViewModel = koinViewModel(),
    context: Context,
    navController: NavController
) {

    // Состояние для полей кнопок
    val usernameTextField = loginViewModel.usernameTextField
    val passwordTextField = loginViewModel.passwordTextField
    val textButtonSignIn = "SIGN IN"
    val textButtonSignUp = "SIGN UP"
    val mainText = "ReviewerWriterApp"
    val usernameTextFieldPlaceholder = "Username"
    val passwordTextFieldPlaceholder = "Password"
    val mainButtonText = "SIGN IN"
    val passwordVisible =  loginViewModel.passwordVisible

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
                        .background(
                            color =  if (isSystemInDarkTheme()) SystemInDarkThemeShadow.copy(alpha = 0.7f)
                            else SystemInLightThemeShadow.copy(alpha = 0.7f),
                            shape = RoundedCornerShape(40.dp)
                        )
                    ) {
                        Text(
                            text = textButtonSignIn,
                            modifier = Modifier
                        )
                    }
                    TextButton(
                        onClick = { loginViewModel.onTextButtonSignUpClick(navController) }
                    ) {
                        Text(
                            text = textButtonSignUp,
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
                    visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { /* Handle keyboard action */ }),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                            Icon(
                                imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Visibility Icon"
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors()
                )
                Button(
                    // передаем значения в полях при нажатии на кнопку
                    onClick = {
                        loginViewModel.onButtonSignInClick(
                            usernameTextField,
                            passwordTextField,
                            navController
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

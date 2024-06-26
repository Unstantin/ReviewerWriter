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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationView (
    registrationViewModel: RegistrationViewModel = koinViewModel(),
    context: Context,
    navController: NavController
){

    // Состояние для полей кнопок
    val usernameTextField = registrationViewModel.usernameTextField
    val passwordTextField = registrationViewModel.passwordTextField
    val confirmPasswordTextField = registrationViewModel.confirmPasswordTextField
    val textButtonSignIn = "SIGN IN"
    val textButtonSignUp = "SIGN UP"
    val mainText = "ReviewerWriterApp"
    val usernameTextFieldPlaceholder = "Username"
    val passwordTextFieldPlaceholder = "Password"
    val confirmPasswordTextFieldPlaceholder = "Confirm Password"
    val mainButtonText = "SIGN UP"
    val passwordVisible =  registrationViewModel.passwordVisible

    //отслеживание
    ObserveToastMessage(registrationViewModel, context)

    ReviewerWriterTheme {
        Scaffold {
            // размещение элементов на экране
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
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

                    TextButton(onClick = { registrationViewModel.onTextButtonSignInClick(navController) }) {
                        Text(
                            text = textButtonSignIn,
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
                        onClick = { registrationViewModel.onTextButtonSignUpClick() },
                        modifier = Modifier
                            .background(
                                color =  if (isSystemInDarkTheme()) SystemInDarkThemeShadow.copy(alpha = 0.7f)
                                else SystemInLightThemeShadow.copy(alpha = 0.7f),
                                shape = RoundedCornerShape(60.dp)
                            )
                    ) {
                        Text(
                            text = textButtonSignUp,
                            modifier = Modifier
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
                    colors = TextFieldDefaults.textFieldColors(
                    ),
                )

                TextField(
                    value = passwordTextField.value,
                    onValueChange = { passwordTextField.value = it },
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .clip(RoundedCornerShape(25.dp)),
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
                    colors = TextFieldDefaults.textFieldColors(
                    ),
                )

                TextField(
                    value = confirmPasswordTextField.value,
                    onValueChange = { confirmPasswordTextField.value = it },
                    modifier = Modifier
                        .padding(top = 25.dp)
                        .clip(RoundedCornerShape(25.dp)),
                    placeholder = { Text(confirmPasswordTextFieldPlaceholder) },
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
                    colors = TextFieldDefaults.textFieldColors(),
                )
                Button(
                    // передаем значения в полях при нажатии на кнопку
                    onClick = {
                        registrationViewModel.onButtonSignUPClick(
                            usernameTextField,
                            passwordTextField,
                            confirmPasswordTextField,
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

/*
@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
        val navController = rememberNavController()
    RegistrationView(context = LocalContext.current, navController)
}
*/

package com.orb.bmdfoods.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orb.bmdfoods.login.LoginViewModel
import com.orb.bmdfoods.ui.components.MyLoginTextField
import com.orb.bmdfoods.ui.theme.AppTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit
) {
    val loginScreenState = loginViewModel.loginScreenState
    val isError = loginScreenState.loginError != null
    val context = LocalContext.current

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "BMD",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (isError) {
            Text(
                text = loginScreenState.loginError,
                color = Color.Red
            )
        }
        MyLoginTextField(
            textValue = loginScreenState.userName,
            onValueChange = {
                loginViewModel.onUserNameChange(it)
            },
            placeholder = "Email",
//            isError = isError
        )
        MyLoginTextField(
            textValue = loginScreenState.password,
            onValueChange = {
                loginViewModel.onPasswordChange(it)
            },
            placeholder = "Password",
            visualTransformation = PasswordVisualTransformation(),
//            isError = isError
        )
        Button(
            onClick = {
                loginViewModel.loginUser(context)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(text = "Login")
        }
        Spacer(modifier.size(16.dp))
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Don't have an account?")
            Spacer(modifier.size(8.dp))
            Text(
                modifier = modifier
                    .clickable(onClick = onNavToSignUpPage),
                text = "Sign Up",
                color = Color.Green
            )
        }
        if (loginScreenState.isLoading == true) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        LaunchedEffect(loginViewModel.hasUser) {
            if (loginViewModel.hasUser == true) {
                onNavToHomePage()
            }
        }
    }
}


@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit
) {
    val loginScreenState = loginViewModel.loginScreenState
    val isError = loginScreenState.signUpError != null
    val context = LocalContext.current

    Column(
        modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "BMD",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (isError) {
            Text(
                text = loginScreenState.signUpError,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Red
            )
        }
        MyLoginTextField(
            placeholder = "Email",
            textValue = loginScreenState.userNameSignUp,
            onValueChange = {
                loginViewModel.onUserNameSignUpChange(it)
            }
        )
        MyLoginTextField(
            placeholder = "Password",
            textValue = loginScreenState.passwordSignUp,
            onValueChange = {
                loginViewModel.onPasswordSignUpChange(it)
            },
            visualTransformation = PasswordVisualTransformation()
        )
        MyLoginTextField(
            placeholder = "Confirm Password",
            textValue = loginScreenState.confirmPasswordSignUp,
            onValueChange = {
                loginViewModel.onConfirmPasswordSignUpChange(it)
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                loginViewModel.createUser(context)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(text = "Sign Up")
        }
        Spacer(modifier.size(16.dp))
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already have an account?")
            Spacer(modifier.size(8.dp))
            Text(
                modifier = modifier
                    .clickable(onClick = onNavToLoginPage),
                text = "Login",
                color = Color.Green
            )
        }
        if (loginScreenState.isLoading == true) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        LaunchedEffect(loginViewModel.hasUser) {
            if (loginViewModel.hasUser == true) {
                onNavToHomePage.invoke()
            }
        }
    }
}
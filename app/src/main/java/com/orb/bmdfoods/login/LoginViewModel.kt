package com.orb.bmdfoods.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.orb.bmdfoods.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel() {
    val currentUser = repository.currentUser
    private val database = Firebase.database.reference
    val adminControlRef = database.child("admin_control")

    val hasUser: Boolean
        get() = repository.hasUser()

    var loginScreenState by mutableStateOf(LoginScreenState())
        private set

    fun onUserNameChange(userName: String) {
        loginScreenState = loginScreenState.copy(userName = userName)
    }

    fun onPasswordChange(password: String) {
        loginScreenState = loginScreenState.copy(password = password)
    }

    fun onUserNameSignUpChange(userName: String) {
        loginScreenState = loginScreenState.copy(userNameSignUp = userName)
    }

    fun onPasswordSignUpChange(password: String) {
        loginScreenState = loginScreenState.copy(passwordSignUp = password)
    }

    fun onConfirmPasswordSignUpChange(password: String) {
        loginScreenState = loginScreenState.copy(confirmPasswordSignUp = password)
    }

    private fun validateLoginForm() =
        loginScreenState.userName.isNotBlank() &&
                loginScreenState.password.isNotBlank()

    private fun validateSignUpForm() =
        loginScreenState.userNameSignUp.isNotBlank() &&
                loginScreenState.passwordSignUp.isNotBlank() &&
                loginScreenState.confirmPasswordSignUp.isNotBlank()

    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignUpForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginScreenState = loginScreenState.copy(isLoading = true)
            if (loginScreenState.passwordSignUp != loginScreenState.confirmPasswordSignUp) {
                throw IllegalArgumentException("Password do not match")
            }
            loginScreenState = loginScreenState.copy(signUpError = null)
            repository.createUser(
                loginScreenState.userNameSignUp,
                loginScreenState.passwordSignUp
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(context, "successful Login", Toast.LENGTH_SHORT).show()
                    loginScreenState = loginScreenState.copy(isSuccessLogIn = true)
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                    loginScreenState = loginScreenState.copy(isSuccessLogIn = false)
                }
            }
        } catch (e: Exception) {
            loginScreenState = loginScreenState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginScreenState = loginScreenState.copy(isLoading = false)
        }
    }
    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()) {
                throw IllegalArgumentException("email and password can not be empty")
            }
            loginScreenState = loginScreenState.copy(isLoading = true)
            loginScreenState = loginScreenState.copy(loginError = null)
            repository.login(
                loginScreenState.userName,
                loginScreenState.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    Toast.makeText(context, "successful Login", Toast.LENGTH_SHORT).show()
                    loginScreenState = loginScreenState.copy(isSuccessLogIn = true)
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                    loginScreenState = loginScreenState.copy(isSuccessLogIn = false)
                }
            }
        } catch (e: Exception) {
            loginScreenState = loginScreenState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        } finally {
            loginScreenState = loginScreenState.copy(isLoading = false)
        }
    }
}

data class LoginScreenState(
    val userName: String = "",
    val password: String = "",
    val userNameSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogIn: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null
)
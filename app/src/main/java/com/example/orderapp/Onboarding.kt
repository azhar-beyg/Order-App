package com.example.orderapp

import android.preference.PreferenceManager
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.orderapp.ui.theme.LittleLemonColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavController) {

    val context = LocalContext.current
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(130.dp, 90.dp)
                    .padding(top = 5.dp, bottom = 5.dp)
            )
            Text(
                text = "Hi, Welcome to Little Lemon",
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
            OutlinedTextField(value = firstName, onValueChange = { firstName = it },
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp, bottom = 10.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                label = { Text(text = "First Name") })
            OutlinedTextField(value = lastName, onValueChange = { lastName = it },
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp, bottom = 10.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                label = { Text(text = "Last Name") })
            OutlinedTextField(value = email, onValueChange = { email = it },
                modifier = Modifier
                    .fillMaxWidth(.95f)
                    .padding(top = 10.dp, bottom = 10.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                label = { Text(text = "Enter your email") })
            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank()) {
                        Toast.makeText(
                            context,
                            "Registration unsuccessful. Please enter all data.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
                        val editor = preferences.edit()
                        editor.putString("firstName", firstName)
                        editor.putString("lastName", lastName)
                        editor.putString("email", email)
                        editor.apply()
                        navController.navigate(Home.route)
                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT)
                            .show()
                    }
                },
                modifier = Modifier.padding(15.dp),
                colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow)
            ) {
                Text(text = "Register")
            }

        }

    }
}
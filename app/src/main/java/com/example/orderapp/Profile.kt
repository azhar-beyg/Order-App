package com.example.orderapp


import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    val sp = PreferenceManager.getDefaultSharedPreferences(context)
    val firstName = sp.getString("firstName", null) // Second parameter is the default value.
    val lastName = sp.getString("lastName", null) // Second parameter is the default value.
    val email = sp.getString("email", null) // Second parameter is the default value.

    Scaffold(topBar = {
        Surface(shadowElevation = 3.dp) {
            TopAppBar(title = {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                )
            })
        }
    }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),

            ) {
            Text(
                text = "Personal Information", fontSize = 25.sp, textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 50.dp, bottom = 30.dp)
            )
            if (firstName != null) {
                Text(
                    text = "First name: $firstName", fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            if (lastName != null) {
                Text(
                    text = "Last name: $lastName", fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            if (email != null) {
                Text(
                    text = "Email : $email", fontSize = 15.sp,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        PreferenceManager.getDefaultSharedPreferences(context).edit().clear()
                            .apply();
                        navController.navigate(Onboarding.route) {
                            popUpTo(Onboarding.route)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow)
                ) {
                    Text(text = "Log out")
                }
            }
        }
    }
}

package com.example.orderapp

import android.annotation.SuppressLint
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.orderapp.ui.theme.LittleLemonColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {

    val menuItems = DatabaseStorage.databaseInstance?.menuItemDao()?.getAll()?.observeAsState(
        emptyList()
    )?.value
    Log.e("TAG", "menu items : $menuItems")
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(navController)
        UpperPanel()
    }

}

@Composable
fun TopAppBar(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .size(130.dp, 60.dp)
        )
        IconButton(onClick = { navController.navigate(Profile.route) }) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier.size(40.dp)
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun UpperPanel() {
    val keyboardController = LocalSoftwareKeyboardController.current

    var menuItems = DatabaseStorage.databaseInstance?.menuItemDao()?.getAll()?.observeAsState(
        emptyList()
    )?.value
    var selectedCategory by remember { mutableStateOf("") }

    // Get the keyboard controller to hide the keyboard when needed
    Column(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 5.dp, bottom = 15.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = "Little Lemon",
            fontSize = 35.sp,
            fontWeight = FontWeight.Bold,
            color = LittleLemonColor.yellow
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(top = 7.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = "Chicago",
                    fontSize = 24.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Text(
                    text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                    color = Color.Black,
                    modifier = Modifier
                        .padding(bottom = 10.dp, end = 10.dp)
                        .fillMaxWidth(0.7f)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.hero_image),
                contentDescription = "Upper Panel Image",
                modifier = Modifier
                    .size(130.dp, 130.dp)
                    .clip(RoundedCornerShape(15.dp))
            )
        }

        Column {
            var searchPhrase by remember {
                mutableStateOf("")
            }

            OutlinedTextField(
                value = searchPhrase,
                placeholder = { Text(text = "Enter Search Phrase") },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                onValueChange = { newValue ->
                    searchPhrase = newValue
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Black,
                    focusedBorderColor = Color.Black
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(align = Alignment.Center)
                    .background(Color.White),
                singleLine = true,
            )
            // Display filtered menu items
            if (searchPhrase.isNotEmpty()) {
                menuItems =
                    menuItems?.filter { it.title.contains(searchPhrase, ignoreCase = true) }
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
        ) {
            val scrollState = rememberScrollState()

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
                    .horizontalScroll(scrollState)
            ) {
                Button(
                    onClick = {
                        selectedCategory = "starters"
                    },
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.Center)
                        .padding(start = 3.dp, end = 3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0x66C7C6C4)),

                    ) {
                    Text(
                        text = "Starters", color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        selectedCategory = "mains"
                    },
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.Center)
                        .padding(start = 3.dp, end = 3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0x66C7C6C4)),

                    ) {
                    Text(
                        text = "Mains", color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        selectedCategory = "desserts"
                    },
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.Center)
                        .padding(start = 3.dp, end = 3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0x66C7C6C4)),

                    ) {
                    Text(
                        text = "Desserts", color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = {
                        selectedCategory = "drinks"
                    },
                    modifier = Modifier
                        .wrapContentSize(align = Alignment.Center)
                        .padding(start = 3.dp, end = 3.dp),
                    colors = ButtonDefaults.buttonColors(Color(0x66C7C6C4)),

                    ) {
                    Text(
                        text = "Drinks", color = Color.Black,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (selectedCategory.isNotEmpty()) {
                menuItems = menuItems?.filter { it.category.contains(selectedCategory) }
            }
            menuItems?.let { LowerPanel(it) }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun LowerPanel(items: List<MenuItemRoom>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 5.dp)
    ) {

        items(
            items = items,
            itemContent = { menuItem ->

                Card(
                    onClick = {
                        //  Log.d("AAA", "Click ${menu.id}")
                        //  navController?.navigate(DishDetails.route + "/${dish.id}")
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = CardDefaults.cardColors(Color.Transparent)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                    ) {
                        Column {
                            Text(
                                text = menuItem.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                text = menuItem.description,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier
                                    .padding(top = 5.dp, bottom = 5.dp)
                                    .fillMaxWidth(.70f)
                            )
                            Text(
                                text = "$${menuItem.price}",
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }

                        GlideImage(
                            model = menuItem.image, contentDescription = "image",
                            modifier = Modifier
                                .clip(shape = RoundedCornerShape(15.dp))
                                .size(100.dp, 100.dp)
                        )

                    }
                }
                Divider(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp),
                    thickness = 1.dp,
                    color = Color(0x66AAA7A7)
                )
            }
        )
    }

}

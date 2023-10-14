package com.example.orderapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.orderapp.ui.theme.OrderAppTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val client = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType("text","plain"))
        }
    }
    val database by lazy {
        Room.databaseBuilder(applicationContext,AppDatabase::class.java,"database").build()
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OrderAppTheme {
                DatabaseStorage.databaseInstance = database

                val databaseMenuItems by database.menuItemDao().getAll().observeAsState(emptyList())

                //   MenuItemsList(databaseMenuItems)
                lifecycleScope.launch(Dispatchers.IO) {
                    if (database.menuItemDao().isEmpty()){
                        val gettingMenuItems = fetchMenu()
                        saveMenutoDatabase(fetchMenu())
                    }
                }
                NavigationComposable()

            }
        }
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork>{
        return client
            .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
            .body<MenuNetworkdata>().menu
    }
    private fun saveMenutoDatabase(menuItemsNetwork: List<MenuItemNetwork>){
        val menuItemsRoom = menuItemsNetwork.map { it.toMenuItemRoom() }
        database.menuItemDao().insertAll(*menuItemsRoom.toTypedArray())
    }
}

object DatabaseStorage {

    var databaseInstance: AppDatabase? = null
}

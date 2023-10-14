package com.example.orderapp

import android.graphics.Bitmap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.io.File

@Serializable
data class MenuNetworkdata (
    @SerialName("menu")
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String,
    @SerialName("price")
    val price: String,
    @SerialName("image")
    val image: String,
    @SerialName("category")
    val category: String
    ) {
    fun toMenuItemRoom() = MenuItemRoom(
        id,
        title,
        description,
        price,
        image,
        category
    )
}
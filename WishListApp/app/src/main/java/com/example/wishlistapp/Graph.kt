package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository
import kotlin.getValue

object Graph {
    lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(
            wishDao = database.wishDao()
        )
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wish-database.db").build()
    }
}

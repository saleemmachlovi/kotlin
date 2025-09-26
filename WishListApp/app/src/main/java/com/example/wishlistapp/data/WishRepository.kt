package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository (private val wishDao: WishDao){

    suspend fun addWish(wish: Wish){
        wishDao.addWish(wish)
    }

    fun getWish(): Flow<List<Wish>> = wishDao.getAllWishes()

    fun getWishById(id: Long):Flow<Wish>{
        return wishDao.getAWishById(id)
    }

    suspend fun updateWish(wish: Wish){
        wishDao.updateAWish(wish)
    }

    suspend fun deleteWish(wish: Wish){
        wishDao.deleteAWish(wish)
    }

}
package com.justaccounts.mindbrowseandroidtest.dao

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllContact(user: List<PhoneContact>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContact(phoneContact: PhoneContact)

//    @Query("SELECT * FROM contact_table ORDER BY name ASC")
//    fun getAllUser(): List<PhoneContact>
@Query("SELECT * FROM contact_table ORDER BY name ASC")
fun getAllUser(): LiveData<List<PhoneContact>>

    @Query("SELECT COUNT(*) FROM contact_table")
    fun getNoUser(): LiveData<Int>

    @Query("SELECT * FROM contact_table where isFav = 1")
    fun getAllFavUser(): LiveData<List<PhoneContact>>

    @Delete
    suspend fun deleteContact(contact: PhoneContact)

    @Insert
    suspend fun addFavContact(contact: PhoneContact)

    @Update
    suspend fun updateUsers(users: PhoneContact)
}
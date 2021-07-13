package com.justaccounts.mindbrowseandroidtest.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.justaccounts.mindbrowseandroidtest.model.DeletedPhoneContact
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact

@Dao
interface DeletedContactDao {
    @Query("SELECT * FROM deleted_contact_table ORDER BY name ASC")
    fun getAllDeletedContact(): LiveData<List<DeletedPhoneContact>>

    @Delete
    suspend fun deleteContact(contact: DeletedPhoneContact)

    @Insert
    suspend fun addDeletedContact(contact: DeletedPhoneContact)

}
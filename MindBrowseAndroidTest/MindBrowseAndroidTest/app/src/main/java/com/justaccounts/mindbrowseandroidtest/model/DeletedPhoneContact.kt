package com.justaccounts.mindbrowseandroidtest.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "deleted_contact_table")
data class DeletedPhoneContact(
    @PrimaryKey
    val id:String,
    val name: String,
    val mobileNo: String,
    val profile: String?
)
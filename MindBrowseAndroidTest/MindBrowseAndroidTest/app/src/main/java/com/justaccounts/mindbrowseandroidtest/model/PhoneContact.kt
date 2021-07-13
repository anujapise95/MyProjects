package com.justaccounts.mindbrowseandroidtest.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class PhoneContact(
    @PrimaryKey
    val id:String,
    val name: String,
    val mobileNo: String,
    val profile: String?,
    var isFav: Boolean = false
)
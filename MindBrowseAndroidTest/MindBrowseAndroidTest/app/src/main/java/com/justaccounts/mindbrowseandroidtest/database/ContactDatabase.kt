package com.justaccounts.mindbrowseandroidtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.justaccounts.mindbrowseandroidtest.dao.ContactDao
import com.justaccounts.mindbrowseandroidtest.dao.DeletedContactDao
import com.justaccounts.mindbrowseandroidtest.model.DeletedPhoneContact
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact

@Database(entities = [PhoneContact::class,DeletedPhoneContact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
    abstract fun deletedContactDao(): DeletedContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            var tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance;
            }
            //everything within this block will be protected from concurrent execution by multiple thread. here its creating instance to room db initially
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contact_db"
                ).build()
                INSTANCE = instance;
                return instance;

            }
        }
    }
}
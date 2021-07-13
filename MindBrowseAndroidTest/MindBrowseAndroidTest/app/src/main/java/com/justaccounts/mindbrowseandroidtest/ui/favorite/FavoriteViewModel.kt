package com.justaccounts.mindbrowseandroidtest.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.justaccounts.mindbrowseandroidtest.database.ContactDatabase
import com.justaccounts.mindbrowseandroidtest.database.ContactRepository
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository
    public val favContacts:LiveData<List<PhoneContact>>

    init {
        val userDao = ContactDatabase.getDatabase(application).contactDao()
        val deleteDao = ContactDatabase.getDatabase(application).deletedContactDao()
        repository = ContactRepository(userDao,deleteDao)
        favContacts = repository.favContacts
    }
}
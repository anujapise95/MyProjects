package com.justaccounts.mindbrowseandroidtest.ui.deleted

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.justaccounts.mindbrowseandroidtest.database.ContactDatabase
import com.justaccounts.mindbrowseandroidtest.database.ContactRepository
import com.justaccounts.mindbrowseandroidtest.model.DeletedPhoneContact
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeletedViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository
    public val deletedContacts: LiveData<List<DeletedPhoneContact>>

    init {
        val userDao = ContactDatabase.getDatabase(application).contactDao()
        val deleteDao = ContactDatabase.getDatabase(application).deletedContactDao()
        repository = ContactRepository(userDao,deleteDao)
        deletedContacts = repository.deletedContacts
    }

    fun restoreDeletedContact(phoneContact: DeletedPhoneContact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.restoreDeleteUser(phoneContact)
        }
    }
}
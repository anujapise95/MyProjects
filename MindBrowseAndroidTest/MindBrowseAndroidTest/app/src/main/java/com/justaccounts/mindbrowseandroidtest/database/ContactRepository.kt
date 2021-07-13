package com.justaccounts.mindbrowseandroidtest.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justaccounts.mindbrowseandroidtest.dao.ContactDao
import com.justaccounts.mindbrowseandroidtest.dao.DeletedContactDao
import com.justaccounts.mindbrowseandroidtest.model.DeletedPhoneContact
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact


class ContactRepository(
    private val contactDao: ContactDao,
    private val deletedContactDao: DeletedContactDao
) {
    suspend fun addAllUsers(user: List<PhoneContact>) {
        contactDao.insertAllContact(user)
    }

//    suspend fun getAllUsers(): List<PhoneContact> {
//        return contactDao.getAllUser()
//    }

    val noOfUser: LiveData<Int> = contactDao.getNoUser();
    val allUsers: LiveData<List<PhoneContact>> = contactDao.getAllUser()

    suspend fun deleteUser(user: PhoneContact) {
        deletedContactDao.addDeletedContact(
            DeletedPhoneContact(
                user.id,
                user.name,
                user.mobileNo,
                user.profile
            )
        )
        contactDao.deleteContact(user)
    }

    suspend fun updateUser(contact: PhoneContact) {
        contactDao.updateUsers(contact)
    }

    val favContacts: LiveData<List<PhoneContact>> = contactDao.getAllFavUser();

    val deletedContacts: LiveData<List<DeletedPhoneContact>> =
        deletedContactDao.getAllDeletedContact();

    suspend fun restoreDeleteUser(deletedPhoneContact: DeletedPhoneContact) {
        contactDao.insertContact(
            PhoneContact(
                deletedPhoneContact.id,
                deletedPhoneContact.name,
                deletedPhoneContact.mobileNo,
                deletedPhoneContact.profile
            )
        )
        deletedContactDao.deleteContact(deletedPhoneContact)
    }
}
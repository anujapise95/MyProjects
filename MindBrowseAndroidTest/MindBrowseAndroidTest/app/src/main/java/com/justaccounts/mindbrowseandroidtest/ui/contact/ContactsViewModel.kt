package com.justaccounts.mindbrowseandroidtest.ui.contact

import android.app.Application
import android.content.ContentResolver
import android.content.ContentUris
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.justaccounts.mindbrowseandroidtest.database.ContactDatabase
import com.justaccounts.mindbrowseandroidtest.database.ContactRepository
import com.justaccounts.mindbrowseandroidtest.model.PhoneContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "ContactsViewModel"
    private var contacts: LiveData<List<PhoneContact>>
    private val repository: ContactRepository
    public val noOfUser: LiveData<Int>

    init {
        val userDao = ContactDatabase.getDatabase(application).contactDao()
        val deletedContactDao = ContactDatabase.getDatabase(application).deletedContactDao()
        repository = ContactRepository(userDao, deletedContactDao)
        noOfUser = repository.noOfUser
        contacts=repository.allUsers
    }

    fun addAllContacts(contactList: List<PhoneContact>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAllUsers(contactList)
        }
    }

    fun getAllContacts(): LiveData<List<PhoneContact>> {
        return contacts;
    }

    fun getContacts(contentResolver: ContentResolver) {
        Log.i(TAG, "getContacts: " + contacts.value)


        val list: MutableList<PhoneContact> = ArrayList()

        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.CUSTOM_RINGTONE,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor =
            contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection,
                ContactsContract.Contacts.HAS_PHONE_NUMBER + ">0 AND LENGTH(" + ContactsContract.CommonDataKinds.Phone.NUMBER + ")>0",
                null,
                "display_name ASC"
            )

        if (cursor != null && cursor.count > 0) {
            var lastHeader = ""
            while (cursor.moveToNext()) {
                val id =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                val inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                    contentResolver,
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id.toLong())
                )
                val person =
                    ContentUris.withAppendedId(
                        ContactsContract.Contacts.CONTENT_URI,
                        id.toLong()
                    )
                val ring =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CUSTOM_RINGTONE))
                val name =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val mobileNumber =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                var profile:String? = null;
                inputStream?.let {
                    profile= person.toString()
                }
                list.add(
                    PhoneContact(
                        id,
                        name,
                        mobileNumber,
                        profile
                    )
                )


            }
            cursor.close()
        }
        addAllContacts(list)
    }

    fun markFavContact(phoneContact: PhoneContact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(phoneContact)
        }
    }

    fun deleteContact(phoneContact: PhoneContact) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(phoneContact)
        }
    }
}
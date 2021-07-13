package com.justaccounts.mindbrowseandroidtest.interfaces

import com.justaccounts.mindbrowseandroidtest.model.DeletedPhoneContact

interface DeleteContactListener {
    public fun onDeleteContactClick(deletedPhoneContact: DeletedPhoneContact, position: Int)
}
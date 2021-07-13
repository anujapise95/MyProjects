package com.justaccounts.mindbrowseandroidtest.interfaces

import com.justaccounts.mindbrowseandroidtest.model.PhoneContact

interface ContactListener {
   public fun onFavClick(phoneContact: PhoneContact);
   public fun onDeleteClick(phoneContact: PhoneContact, position: Int);
   public fun onRowClick(phoneContact: PhoneContact)
}
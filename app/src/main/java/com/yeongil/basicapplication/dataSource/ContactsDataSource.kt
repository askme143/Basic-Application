package com.yeongil.basicapplication.dataSource

import android.content.ContentResolver
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import com.yeongil.basicapplication.data.Contact

class ContactsDataSource(private val contentResolver: ContentResolver) {
    fun fetch(): List<Contact> {
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            ContactsContract.Contacts.PHOTO_ID,
            ContactsContract.RawContacts.CONTACT_ID
        )
        val sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC"

        val contactCursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        val dupCheckMap = HashMap<String, Boolean>()
        val contactList = ArrayList<Contact>()

        if (contactCursor != null && contactCursor.moveToFirst()) {
            do {
                val name = contactCursor.getString(0)
                val phoneNumber = contactCursor.getString(1)
                val photoId = contactCursor.getLong(2)
                val contactId = contactCursor.getString(3)

                var formattedNumber = phoneNumber
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    formattedNumber = PhoneNumberUtils.formatNumber(phoneNumber, "KOR")
                    if (formattedNumber == null) formattedNumber = phoneNumber
                }

                if (!dupCheckMap.containsKey(contactId)) {
                    dupCheckMap[contactId] = true
                    contactList.add(Contact(formattedNumber, name, photoId))
                }
            } while (contactCursor.moveToNext())
            contactCursor.close()
        }

        return contactList.toList()
    }
}
package nl.csrdelft.plugins.contacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.getcapacitor.PluginCall;

import java.util.List;

public class ContactManager {
    private ContentResolver contentResolver;

    ContactManager(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    long getExistGroup(Contact contactData) {
        long ret = -1;

        String queryColumnArr[] = {ContactsContract.Groups._ID};

        StringBuffer whereClauseBuf = new StringBuffer();
        whereClauseBuf.append(ContactsContract.Groups.TITLE);
        whereClauseBuf.append("='");
        whereClauseBuf.append(contactData.getGroupName());
        whereClauseBuf.append("'");

        Cursor cursor = contentResolver.query(ContactsContract.Groups.CONTENT_URI, queryColumnArr, whereClauseBuf.toString(), null, null);
        if(cursor != null) {
            if(cursor.getCount() > 0) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(ContactsContract.Groups._ID);
                ret = cursor.getLong(columnIndex);
            }
        }
        return ret;
    }

    long insertGroup(Contact contactData) {
        // Insert a group in group table.
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.Groups.TITLE, contactData.getGroupName());
//        contentValues.put(ContactsContract.Groups.NOTES, groupNotes);
        Uri groupUri = contentResolver.insert(ContactsContract.Groups.CONTENT_URI, contentValues);
        // Get the newly created raw contact id.
        long groupId = ContentUris.parseId(groupUri);

        return groupId;
    }

    long insertContact(Contact contactData) {
        // Insert an empty contact in both contacts and raw_contacts table.
        // Return the system generated new contact and raw_contact id.
        // The id in contacts and raw_contacts table has same value.
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, contactData.getGivenName());
        contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_ALTERNATIVE, contactData.getFamilyName());

        Uri rawContactUri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, contentValues);

        // Get the newly created raw contact id.
        long rawContactId = ContentUris.parseId(rawContactUri);
        return rawContactId;
    }

    void insertGroupId(long groupRowId, long rawContactId) {
        ContentValues contentValues = new ContentValues();
        // Set raw contact id. Data table only has raw_contact_id.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);

        // Set mimetype first.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE, ContactsContract.CommonDataKinds.GroupMembership.CONTENT_ITEM_TYPE);
        // Set contact belongs group id.
        contentValues.put(ContactsContract.CommonDataKinds.GroupMembership.GROUP_ROW_ID, groupRowId);

        // Insert to data table.
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
    }

    void insertListData(Uri contentUri, long rawContactId, String mimeType, String dataTypeColumnName, String dataValueColumnName, List<ContactData> dataList) {
        if(dataList != null) {
            ContentValues contentValues = new ContentValues();

            int size = dataList.size();

            for(int i=0; i<size; i++) {

                ContactData contactData = dataList.get(i);

                contentValues.clear();

                // Set raw contact id. Data table only has raw_contact_id.
                contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                // Set data mimetype.
                contentValues.put(ContactsContract.Data.MIMETYPE, mimeType);
                // Set data type.
                contentValues.put(dataTypeColumnName, contactData.getDataType());
                // Set data value.
                contentValues.put(dataValueColumnName, contactData.getDataValue());

                contentResolver.insert(contentUri, contentValues);
            }
        }
    }

    void insertName(Contact contactData) {
        if(contactData != null) {

            ContentValues contentValues = new ContentValues();

            // Set raw contact id. Data table only has raw_contact_id.
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactData.getRawContactId());
            // Set data mimetype.
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            // Set display name.
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactData.getDisplayName());
            // Set given name.
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, contactData.getGivenName());
            // Set family name.
            contentValues.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, contactData.getFamilyName());
            // Insert to data table.
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        }
    }

    void insertNickName(Contact contactData) {
        if(contactData != null) {
            ContentValues contentValues = new ContentValues();

            // Set raw contact id. Data table only has raw_contact_id.
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactData.getRawContactId());
            // Set data mimetype.
            contentValues.put(ContactsContract.CommonDataKinds.Nickname.MIMETYPE, ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE);
            // Set display name.
            contentValues.put(ContactsContract.CommonDataKinds.Nickname.NAME, contactData.getNickname());
            // Insert to data table.
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        }
    }

    void insertNote(Contact contactData) {
        if(contactData != null) {
            ContentValues contentValues = new ContentValues();

            // Set raw contact id. Data table only has raw_contact_id.
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactData.getRawContactId());
            // Set data mimetype.
            contentValues.put(ContactsContract.CommonDataKinds.Note.MIMETYPE, ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE);
            // Set display name.
            contentValues.put(ContactsContract.CommonDataKinds.Note.NOTE, contactData.getNote());
            // Insert to data table.
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        }
    }

    void insertBirthday(Contact contactData) {
        if(contactData != null) {
            ContentValues contentValues = new ContentValues();

            // Set raw contact id. Data table only has raw_contact_id.
            contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactData.getRawContactId());
            // Set data mimetype.
            contentValues.put(ContactsContract.CommonDataKinds.Event.MIMETYPE, ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE);
            // Set event type to birthday.
            contentValues.put(ContactsContract.CommonDataKinds.Event.TYPE, ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY);
            // Set display name.
            contentValues.put(ContactsContract.CommonDataKinds.Event.START_DATE, contactData.getBirthday().toString());
            // Insert to data table.
            contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
        }
    }

    /* Insert contact postal address info in data table. */
    void insertPostalAddress(Contact contactData) {
        ContentValues contentValues = new ContentValues();
        // Set raw contact id. Data table only has raw_contact_id.
        contentValues.put(ContactsContract.Data.RAW_CONTACT_ID, contactData.getRawContactId());
        // Set mimetype first.
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
        // Set country
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY, contactData.getCountry());
        // Set city
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.CITY, contactData.getCity());
        // Set region
//        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.REGION, contact.getRegion());
        // Set street
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.STREET, contactData.getStreet());
        // Set postcode
        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE, contactData.getPostalCode());
        // Set postcode
//        contentValues.put(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, contact.getPostType());

    /* Insert to data table. Do not use uri ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
       Otherwise it will throw error java.lang.UnsupportedOperationException: URI: content://com.android.contacts/data/postals*/
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, contentValues);
    }
}

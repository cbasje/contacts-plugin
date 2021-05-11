package nl.csrdelft.plugins.contacts;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;

@CapacitorPlugin(
    name = "Contacts",
    permissions = { @Permission(alias = "contacts", strings = { Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS }) }
)
public class ContactsPlugin extends Plugin {

    private PluginCall call;
    private ContactManager manager;
    private Contact contactData;

    // From https://devdactic.com/build-capacitor-plugin/ but adapted to Capacitor V3
    @PluginMethod
    public void getContacts(PluginCall call) {
        if (getPermissionState("contacts") != PermissionState.GRANTED) {
            requestPermissionForAlias("contacts", call, "contactsPermsCallback");
        } else {
            // We got the permission!
            loadContacts(call);
        }
    }

    @PermissionCallback
    public void contactsPermsCallback(PluginCall call) {
        if (getPermissionState("contacts") == PermissionState.GRANTED) {
            // We got the permission!
            loadContacts(call);
        } else {
            call.reject("Permission is required to fetch contacts");
        }
    }

    void loadContacts(PluginCall call) {
        ArrayList<Map> contactList = new ArrayList<>();
        ContentResolver cr = this.getContext().getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();

                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                map.put("firstName", name);
                map.put("lastName", "");

                String contactNumber = "";

                if (cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[] { id },
                        null
                    );
                    pCur.moveToFirst();
                    contactNumber = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Log.i("phoneNUmber", "The phone number is " + contactNumber);
                }
                map.put("telephone", contactNumber);
                contactList.add(map);
            }
        }
        if (cur != null) {
            cur.close();
        }

        JSONArray jsonArray = new JSONArray(contactList);
        JSObject ret = new JSObject();
        ret.put("results", jsonArray);
        call.resolve(ret);
    }

    @PluginMethod
    public void addToExisting(PluginCall call) {
        this.call = call;

        // Get contactData from call
        contactData = new Contact(call);

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(call, contactPickerIntent, "processPickedContact");
    }

    @PluginMethod
    public void createNew(PluginCall call) {
        this.call = call;

        // Get contactData from call
        contactData = new Contact(call);

        // Get contentresolver object and create ContactManager
        ContentResolver contentResolver = getContext().getContentResolver();
        manager = new ContactManager(contentResolver);

        // Create a new contact.
        long rawContactId = manager.insertContact(contactData);

        // Contact id and raw contact id has same value.
        contactData.setContactId(rawContactId);
        contactData.setRawContactId(rawContactId);

        saveContact(ContactSaveOptions.CREATE_NEW);
    }

    // Tutorial from https://www.dev2qa.com/how-to-add-contact-in-android-programmatically/
    void saveContact(ContactSaveOptions saveOption) {
        if (saveOption == ContactSaveOptions.CREATE_NEW) {
            // Insert contact display, given and family name.
            manager.insertName(contactData);

            // Insert contact nickname.
            manager.insertNickName(contactData);

            // Insert contact note.
            manager.insertNote(contactData);

            /* Create or add to a group */
            long groupId = manager.getExistGroup(contactData);

            // Create group if it does not exist
            if (groupId == -1) groupId = manager.insertGroup(contactData);

            contactData.setGroupId(groupId);

            // Insert contact group membership data (group id).
            manager.insertGroupId(contactData.getGroupId(), contactData.getRawContactId());
            /* End group logic */

            // Insert contact birthday
            manager.insertBirthday(contactData);
        }

        /* Insert contact email list data, Content uri do not use ContactsContract.CommonDataKinds.Email.CONTENT_URI
         * Otherwise it will throw error java.lang.UnsupportedOperationException: URI: content://com.android.contacts/data/emails */
        manager.insertListData(
            ContactsContract.Data.CONTENT_URI,
            contactData.getRawContactId(),
            ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Email.TYPE,
            ContactsContract.CommonDataKinds.Email.ADDRESS,
            contactData.getEmailList()
        );

        /* Insert contact phone list data, Content uri do not use ContactsContract.CommonDataKinds.Phone.CONTENT_URI
         * Otherwise it will throw error java.lang.UnsupportedOperationException: URI: content://com.android.contacts/data/phones */
        manager.insertListData(
            ContactsContract.Data.CONTENT_URI,
            contactData.getRawContactId(),
            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Phone.TYPE,
            ContactsContract.CommonDataKinds.Phone.NUMBER,
            contactData.getPhoneList()
        );

        // Insert contact website list.
        manager.insertListData(
            ContactsContract.Data.CONTENT_URI,
            contactData.getRawContactId(),
            ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE,
            ContactsContract.CommonDataKinds.Website.TYPE,
            ContactsContract.CommonDataKinds.Website.URL,
            contactData.getWebsiteList()
        );

        // Insert contact post address
        manager.insertPostalAddress(contactData);

        JSObject callData = new JSObject();
        callData.put("savedContact", contactData.getJsonResponse());
        call.resolve(callData);
    }

    @ActivityCallback
    public void processPickedContact(PluginCall call, ActivityResult result) {
        Intent data = result.getData();
        if (data == null) {
            call.reject("No image picked");
            return;
        }

        Uri uri = data.getData();

        // get the contact id from the Uri
        long rawContactId = Long.parseLong(uri.getLastPathSegment());

        // Contact id and raw contact id has same value.
        contactData.setContactId(rawContactId);
        contactData.setRawContactId(rawContactId);

        // First get contentresolver object.
        ContentResolver contentResolver = getContext().getContentResolver();
        manager = new ContactManager(contentResolver);

        saveContact(ContactSaveOptions.ADD_TO_EXISTING);
    }
}

package nl.csrdelft.plugins.contacts;

import android.provider.ContactsContract;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONException;

public class Contact {

    private long contactId;
    private long rawContactId;
    private long groupId;

    private String displayName;
    private String namePrefix;
    private String givenName;
    private String middleName;
    private String familyName;
    private String nameSuffix;
    private String nickname;

    private String note;

    private String groupName;

    private JSArray phoneNumberLabels;
    private JSArray phoneNumbers;

    private JSArray emailLabels;
    private JSArray emails;

    private String addressLabel;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;

    private JSArray urlLabels;
    private JSArray urls;

    private Date birthday;

    // Contact phone list.
    private List<ContactListData> phoneList = new ArrayList<ContactListData>();

    // Contact email list
    private List<ContactListData> emailList = new ArrayList<ContactListData>();

    // Contact address list.
    private List<ContactListData> addressList = new ArrayList<ContactListData>();

    // Contact website list.
    private List<ContactListData> websiteList = new ArrayList<ContactListData>();

    private JSObject jsonResponse;

    Contact(PluginCall call) {
        displayName = call.getString("displayName");
        namePrefix = call.getString("namePrefix");
        givenName = call.getString("givenName");
        middleName = call.getString("middleName");
        familyName = call.getString("familyName");
        // previousFamilyName = call.getString("previousFamilyName");
        nameSuffix = call.getString("nameSuffix");
        nickname = call.getString("nickname");

        note = call.getString("note");

        phoneNumberLabels = call.getArray("phoneNumberLabels");
        phoneNumbers = call.getArray("phoneNumbers");

        // Create mobile phone
        ContactListData mobilePhone = new ContactListData();
        mobilePhone.setDataType(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        try {
            mobilePhone.setDataValue(phoneNumbers.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        phoneList.add(mobilePhone);

        emailLabels = call.getArray("emailLabels");
        emails = call.getArray("emails");

        // Create home email
        ContactListData homeEmailDto = new ContactListData();
        homeEmailDto.setDataType(ContactsContract.CommonDataKinds.Email.TYPE_HOME);
        try {
            homeEmailDto.setDataValue(emails.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        emailList.add(homeEmailDto);

        addressLabel = call.getString("addressLabel");
        street = call.getString("street");
        city = call.getString("city");
        state = call.getString("state");
        postalCode = call.getString("postalCode");
        country = call.getString("country");

        // Create home address
        ContactListData homeAddressDto = new ContactListData();
        homeAddressDto.setDataType(ContactsContract.CommonDataKinds.SipAddress.TYPE_HOME);
        homeAddressDto.setDataValue(street);
        addressList.add(homeAddressDto);

        urlLabels = call.getArray("urlLabels");
        urls = call.getArray("urls");

        // Create profile website
        ContactListData profileWebsiteDto = new ContactListData();
        profileWebsiteDto.setDataType(ContactsContract.CommonDataKinds.Website.TYPE_PROFILE);
        try {
            profileWebsiteDto.setDataValue(urls.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        websiteList.add(profileWebsiteDto);

        birthday = setBirthday(call.getString("birthday"));

        jsonResponse = call.getData();
    }

    private Date setBirthday(String dateString) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getNamePrefix() {
        return namePrefix;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getNameSuffix() {
        return nameSuffix;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNote() {
        return note;
    }

    public JSArray getPhoneNumberLabels() {
        return phoneNumberLabels;
    }

    public JSArray getPhoneNumbers() {
        return phoneNumbers;
    }

    public JSArray getEmailLabels() {
        return emailLabels;
    }

    public JSArray getEmails() {
        return emails;
    }

    public String getAddressLabel() {
        return addressLabel;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public JSArray getUrlLabels() {
        return urlLabels;
    }

    public JSArray getUrls() {
        return urls;
    }

    public Date getBirthday() {
        return birthday;
    }

    public JSObject getJsonResponse() {
        return jsonResponse;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<ContactListData> getPhoneList() {
        return phoneList;
    }

    public List<ContactListData> getEmailList() {
        return emailList;
    }

    public List<ContactListData> getAddressList() {
        return addressList;
    }

    public List<ContactListData> getWebsiteList() {
        return websiteList;
    }

    public long getContactId() {
        return contactId;
    }

    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    public void setRawContactId(long rawContactId) {
        this.rawContactId = rawContactId;
    }

    public long getRawContactId() {
        return rawContactId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }
}

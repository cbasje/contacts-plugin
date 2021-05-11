import Foundation
import Contacts

struct ContactManager {
    var contact: CNMutableContact
    
    init(contact: CNMutableContact) {
        self.contact = contact
    }
    
    func setNameFields(from contactData: Contact) {
        contact.namePrefix = contactData.namePrefix ?? ""
        contact.givenName = contactData.givenName ?? ""
        contact.middleName = contactData.middleName ?? ""
        contact.familyName = contactData.familyName ?? ""
//        contact.previousFamilyName = contactData.previousFamilyName ?? ""
        contact.nameSuffix = contactData.nameSuffix ?? ""
        contact.nickname = contactData.nickname ?? ""
    }
    
    func setNote(from contactData: Contact) {
        contact.note = contactData.note ?? ""
    }
    
    func setBirthday(from contactData: Contact) {
        contact.birthday = contactData.birthdayComps
    }
    
    func addPhoneNumbers(from contactData: Contact) {
        for (phoneNumber, label) in zip(contactData.phoneNumbers, contactData.phoneNumberLabels) {
            let phoneNumberValue = CNPhoneNumber(stringValue: phoneNumber)
//            let phoneNumberField = CNLabeledValue(label: CNLabelPhoneNumberMobile,
//                                                  value: phoneNumberValue)
            let phoneNumberField = CNLabeledValue(label: label,
                                                  value: phoneNumberValue)
            contact.phoneNumbers.append(phoneNumberField)
        }
    }
    
    func addEmailAdresses(from contactData: Contact) {
        for (emailAddress, label) in zip(contactData.emails, contactData.emailLabels) {
//            let emailAddressField = CNLabeledValue(label: CNLabelHome,
//                                                   value: NSString(string: emailAddress))
            let emailAddressField = CNLabeledValue(label: label,
                                                   value: NSString(string: emailAddress))
            contact.emailAddresses.append(emailAddressField)
        }
    }
    
    func addPostalAdresses(from contactData: Contact) {
        let addressValue = CNMutablePostalAddress()
        addressValue.street = contactData.street ?? ""
        addressValue.city = contactData.city ?? ""
        addressValue.state = contactData.state ?? ""
        addressValue.postalCode = contactData.postalCode ?? ""
        addressValue.country = contactData.country ?? ""
        
        let addressField = CNLabeledValue<CNPostalAddress>(label: contactData.addressLabel,
                                                           value: addressValue)
        contact.postalAddresses = [addressField]
    }
    
    func addUrls(from contactData: Contact) {
        for (url, label) in zip(contactData.urls, contactData.urlLabels) {
            let urlField = CNLabeledValue(label: label,
                                          value: NSString(string: url ?? ""))
            contact.urlAddresses.append(urlField)
        }
    }
}

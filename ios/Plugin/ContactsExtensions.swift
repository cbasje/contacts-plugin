import Foundation
import Contacts

extension CNMutableContact {
    func setNameFields(from contact: Contact) {
        self.namePrefix = contact.namePrefix ?? ""
        self.givenName = contact.givenName ?? ""
        self.middleName = contact.middleName ?? ""
        self.familyName = contact.familyName ?? ""
        self.previousFamilyName = contact.previousFamilyName ?? ""
        self.nameSuffix = contact.nameSuffix ?? ""
        self.nickname = contact.nickname ?? ""
    }
    
    func setNote(from contact: Contact) {
        self.note = contact.note ?? ""
    }
    
    func setBirthday(from contact: Contact) {
        self.birthday = contact.birthdayComps
    }
    
    func addPhoneNumbers(from contact: Contact) {
        for (phoneNumber, label) in zip(contact.phoneNumbers, contact.phoneNumberLabels) {
            let phoneNumberValue = CNPhoneNumber(stringValue: phoneNumber)
//            let phoneNumberField = CNLabeledValue(label: CNLabelPhoneNumberMobile,
//                                                  value: phoneNumberValue)
            let phoneNumberField = CNLabeledValue(label: label,
                                                  value: phoneNumberValue)
            self.phoneNumbers.append(phoneNumberField)
        }
    }
    
    func addEmailAdresses(from contact: Contact) {
        for (emailAddress, label) in zip(contact.emails, contact.emailLabels) {
//            let emailAddressField = CNLabeledValue(label: CNLabelHome,
//                                                   value: NSString(string: emailAddress))
            let emailAddressField = CNLabeledValue(label: label,
                                                   value: NSString(string: emailAddress))
            self.emailAddresses.append(emailAddressField)
        }
    }
    
    func addPostalAdresses(from contact: Contact) {
        let addressValue = CNMutablePostalAddress()
        addressValue.street = contact.street ?? ""
        addressValue.city = contact.city ?? ""
        addressValue.state = contact.state ?? ""
        addressValue.postalCode = contact.postalCode ?? ""
        addressValue.country = contact.country ?? ""
        
        let addressField = CNLabeledValue<CNPostalAddress>(label: contact.addressLabel,
                                                           value: addressValue)
        self.postalAddresses = [addressField]
    }
    
    func addUrls(from contact: Contact) {
        for (url, label) in zip(contact.urls, contact.urlLabels) {
            let urlField = CNLabeledValue(label: label,
                                          value: NSString(string: url ?? ""))
            self.urlAddresses.append(urlField)
        }
    }
}

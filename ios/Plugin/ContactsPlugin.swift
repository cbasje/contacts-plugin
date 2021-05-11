import Foundation
import Capacitor
import Contacts
import ContactsUI
import UIKit

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ContactsPlugin)
public class ContactsPlugin: CAPPlugin {
    private var call: CAPPluginCall?
    private var store = CNContactStore()
    
    private var manager: ContactManager?
    private var contactData: Contact?
    
    /*
     * More: https://www.youtube.com/watch?v=5kBtuQAFuGk
     */
    @objc func addToExisting(_ call: CAPPluginCall) {
        self.call = call
        
        DispatchQueue.main.async {
            let pickerVC = CNContactPickerViewController()
            pickerVC.delegate = self
            
            guard let bridge = self.bridge else { return }
            
            bridge.viewController?.present(pickerVC, animated: true, completion: nil)
        }
    }
    
    @objc func createNew(_ call: CAPPluginCall) {
        self.call = call
        
        // Get contactData from call
        contactData = Contact(call: call)

        // Create new contact and ContactManager
        let newContact = CNMutableContact()
        manager = ContactManager(contact: newContact);

        saveContact(saveOption: .createNew)
    }

    /*
     * Save with contactVC: https://stackoverflow.com/questions/58749575/how-to-add-new-contact-into-contacts?noredirect=1&lq=1
     */
    public func saveContact(saveOption: ContactSaveOptions) {

        if saveOption == .createNew {
            manager?.setNameFields(from: contactData!)
            
            manager?.setNote(from: contactData!)

            // TODO: add group
        
            // FIXME
            // newContact.setBirthday(from: contactData)
        }

        // Add all the phone numbers
        manager?.addPhoneNumbers(from: contactData!)
        
        // Add all the email addresses
        manager?.addEmailAdresses(from: contactData!)
        
        // Set addresses
        manager?.addPostalAdresses(from: contactData!)
        
        // Add all the urls
        manager?.addUrls(from: contactData!)

        // Create the VC for adding a contact
        let contactVC: CNContactViewController
        contactVC = CNContactViewController(forNewContact: manager?.contact)
        contactVC.delegate = self
        
        DispatchQueue.main.async {
            guard let bridge = self.bridge else { return }
            
            bridge.viewController?.present(UINavigationController(rootViewController: contactVC), animated: true, completion: nil)
        }
    }
    
    /*
     * From https://devdactic.com/build-capacitor-plugin/ but adapted to Capacitor V3
     */
    @objc func getContacts(_ call: CAPPluginCall) {
        addToExisting(call)
        
        var contacts = [Any]()
        let keys = [
                CNContactFormatter.descriptorForRequiredKeys(for: .fullName),
                    CNContactPhoneNumbersKey,
                    CNContactEmailAddressesKey
                ] as [Any]
        let request = CNContactFetchRequest(keysToFetch: keys as! [CNKeyDescriptor])
        
        store.requestAccess(for: .contacts) { (granted, error) in
            if let error = error {
                print("Failed to request access", error)
                call.reject("Access denied")
                return
            }
            if granted {
                do {
                    try self.store.enumerateContacts(with: request) { (contact, _) in
                        contacts.append([
                            "firstName": contact.givenName,
                            "lastName": contact.familyName,
                            "telephone": contact.phoneNumbers.first?.value.stringValue ?? ""
                        ])
                    }
                    print(contacts)
                    call.resolve([
                        "results": contacts
                    ])
                } catch {
                    print("Unable to fetch contacts")
                    call.reject("Unable to fetch contacts")
                }
            } else {
                print("Access denied")
                call.reject("Access denied")
            }
        }
    }
    
    /**
     * Save without confirmation
     * Tutorial: https://www.raywenderlich.com/2547730-contacts-framework-tutorial-for-ios
     */
    @objc func addContact(_ call: CAPPluginCall) {
//        let value = call.getString("value") ?? ""
//
//        guard
//          let friend = friend,
//          let phoneNumberText = phoneTextField.text
//          else { return }
//
//        let store = CNContactStore()
//
//        let phoneNumberValue = CNPhoneNumber(stringValue: phoneNumberText)
//        let saveRequest = CNSaveRequest()
//
//        if let storedContact = friend.storedContact,
//           let phoneNumberToEdit = storedContact.phoneNumbers.first(where: { $0 == friend.phoneNumberField }),
//           let index = storedContact.phoneNumbers.firstIndex(of: phoneNumberToEdit) {
//
//            let newPhoneNumberField = phoneNumberToEdit.settingValue(phoneNumberValue)
//            storedContact.phoneNumbers.remove(at: index)
//            storedContact.phoneNumbers.insert(newPhoneNumberField, at: index)
//            friend.phoneNumberField = newPhoneNumberField
//
//            saveRequest.update(storedContact)
//            friend.storedContact = nil
//
//        } else if let unsavedContact = friend.contactValue.mutableCopy() as? CNMutableContact {
//
//            let phoneNumberField = CNLabeledValue(label: CNLabelPhoneNumberMain,
//                                                  value: phoneNumberValue)
//            unsavedContact.phoneNumbers = [phoneNumberField]
//            friend.phoneNumberField = phoneNumberField
//
//            saveRequest.add(unsavedContact, toContainerWithIdentifier: nil)
//        }
//
//        do {
//            try store.execute(saveRequest)
//            resolve
//        } catch {
//            print(error)
//            failure
//        }
    }
    
    public override func checkPermissions(_ call: CAPPluginCall) {
        let contactsState: String
        
        switch CNContactStore.authorizationStatus(for: .contacts) {
        case .notDetermined:
            contactsState = "prompt"
        case .restricted, .denied:
            contactsState = "denied"
        case .authorized:
            contactsState = "granted"
        @unknown default:
            contactsState = "prompt"
        }
        
        call.resolve(["contacts": contactsState])
    }
    
    public override func requestPermissions(_ call: CAPPluginCall) {
        CNContactStore().requestAccess(for: .contacts) { [weak self] _, _ in
            self?.checkPermissions(call)
        }
    }
}

extension ContactsPlugin: CNContactViewControllerDelegate {
    
    // Dismiss the popover when adding the contact is done
    public func contactViewController(_ viewController: CNContactViewController, didCompleteWith contact: CNContact?) {
        guard let bridge = self.bridge else { return }

        bridge.viewController?.dismiss(animated: true, completion: nil)

        if contact != nil {
            self.call?.resolve([
                "savedContact": contactData!.jsonResponse
            ])
        } else {
            self.call?.reject("Cancelled adding contact")
        }
    }
}

extension ContactsPlugin: CNContactPickerDelegate {

    // The popover to pick a contact
    public func contactPicker(_ picker: CNContactPickerViewController, didSelect contact: CNContact) {
        contactData = Contact(call: call!)
        guard let existingContact = contact.mutableCopy() as? CNMutableContact else { return }
        
        // Create new ContactManager
        manager = ContactManager(contact: existingContact);
        
        saveContact(saveOption: .addToExisting)
    }
    
    // Dismiss the popover when choosing the contact is canceled
    public func contactPickerDidCancel(_ picker: CNContactPickerViewController) {
        guard let bridge = self.bridge else { return }

        bridge.viewController?.dismiss(animated: true, completion: nil)
        
        // TODO: Add call.resolve
        self.call?.reject("Cancelled contact picker")
    }
}

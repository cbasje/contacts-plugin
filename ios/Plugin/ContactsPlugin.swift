import Foundation
import Capacitor
import Contacts
import ContactsUI

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ContactsPlugin)
public class ContactsPlugin: CAPPlugin {
    private let implementation = Contacts()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }

    /**
     * Tutorial: https://www.raywenderlich.com/2547730-contacts-framework-tutorial-for-ios
     */
    @objc func addContact(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        
        guard
          let friend = friend,
          let phoneNumberText = phoneTextField.text
          else { return }
        
        let store = CNContactStore()
        
        let phoneNumberValue = CNPhoneNumber(stringValue: phoneNumberText)
        let saveRequest = CNSaveRequest()

        if let storedContact = friend.storedContact,
           let phoneNumberToEdit = storedContact.phoneNumbers.first(where: { $0 == friend.phoneNumberField }),
           let index = storedContact.phoneNumbers.firstIndex(of: phoneNumberToEdit) {
            
            let newPhoneNumberField = phoneNumberToEdit.settingValue(phoneNumberValue)
            storedContact.phoneNumbers.remove(at: index)
            storedContact.phoneNumbers.insert(newPhoneNumberField, at: index)
            friend.phoneNumberField = newPhoneNumberField

            saveRequest.update(storedContact)
            friend.storedContact = nil
            
        } else if let unsavedContact = friend.contactValue.mutableCopy() as? CNMutableContact {
            
            let phoneNumberField = CNLabeledValue(label: CNLabelPhoneNumberMain,
                                                  value: phoneNumberValue)
            unsavedContact.phoneNumbers = [phoneNumberField]
            friend.phoneNumberField = phoneNumberField
            
            saveRequest.add(unsavedContact, toContainerWithIdentifier: nil)
        }
        
        do {
            try store.execute(saveRequest)
            resolve
        } catch {
            print(error)
            failure
        }
    }
    
    /*
     * More: https://www.youtube.com/watch?v=5kBtuQAFuGk
     */
    @objc func addToExisting() {
        let value = call.getString("value") ?? ""

        let newContact = CNContact()
        let vc = CNContactViewController(forNewContact: newContact)
        vc.delegate = self // this delegate CNContactViewControllerDelegate
        // self.navigationController?.pushViewController(vc, animated: true)
        self.present(UINavigationController(rootViewController: vc), animated:true)
    }

    @objc func contactViewController(_ viewController: CNContactViewController, didCompleteWith contact: CNContact?) {
        self.dismiss(animated: true, completion: nil)
    }
    
    @objc func getContacts(_ call: CAPPluginCall) {
        let contactStore = CNContactStore()
        var contacts = [Any]()
        let keys = [
                CNContactFormatter.descriptorForRequiredKeys(for: .fullName),
                    CNContactPhoneNumbersKey,
                    CNContactEmailAddressesKey
                ] as [Any]
        let request = CNContactFetchRequest(keysToFetch: keys as! [CNKeyDescriptor])
        
        contactStore.requestAccess(for: .contacts) { (granted, error) in
            if let error = error {
                print("Failed to request access", error)
                call.reject("Access denied")
                return
            }
            if granted {
                do {
                    try contactStore.enumerateContacts(with: request) { (contact, _) in
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
}

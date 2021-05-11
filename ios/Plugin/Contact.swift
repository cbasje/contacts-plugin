import Foundation
import Capacitor
import Contacts

struct Contact {
//    var name: ContactName
//    var note: String?
//    var phoneNumbers: [String]
//    var emails: [String]
//    var addresses: [ContactAddress]
//    var urls: [String?]
//    var birthday: Date
    
    var namePrefix: String?
    var givenName: String?
    var middleName: String?
    var familyName: String?
    var nameSuffix: String?
    var nickname: String?

    var note: String?

    var groupName: String?

    var phoneNumberLabels: [String]
    var phoneNumbers: [String]

    var emailLabels: [String]
    var emails: [String]

    var addressLabel: String?
    var street: String?
    var city: String?
    var state: String?
    var postalCode: String?
    var country: String?

    var urlLabels: [String?]
    var urls: [String?]

    var birthday: Date
    
    var jsonResponse: JSObject
    
    var birthdayComps: DateComponents {
        let dc = Calendar.current.dateComponents(
            [.day, .month, .year],
            from: birthday
        )
        return DateComponents(
            calendar: .current,
            timeZone: .current,
            year: dc.year!,
            month: dc.month!,
            day: dc.day!
        )
    }
    
    init(call: CAPPluginCall) {
        namePrefix = call.getString("namePrefix")
        givenName = call.getString("givenName")
        middleName = call.getString("middleName")
        familyName = call.getString("familyName")
        // previousFamilyName = call.getString("previousFamilyName")
        nameSuffix = call.getString("nameSuffix")
        nickname = call.getString("nickname")
        
        note = call.getString("note")

        groupName = call.getString("groupName")
        
        phoneNumberLabels = call.getArray("phoneNumberLabels", String.self) ?? []
        phoneNumbers = call.getArray("phoneNumbers", String.self) ?? []
        
        emailLabels = call.getArray("emailLabels", String.self) ?? []
        emails = call.getArray("emails", String.self) ?? []
        
        addressLabel = call.getString("addressLabel")
        street = call.getString("street")
        city = call.getString("city")
        state = call.getString("state")
        postalCode = call.getString("postalCode")
        country = call.getString("country")
        
        urlLabels = call.getArray("urlLabels", String.self) ?? []
        urls = call.getArray("urls", String.self) ?? []
        
        birthday = call.getDate("birthday") ?? Date()
        
        jsonResponse = call.jsObjectRepresentation
    }
}
//extension Contact: Codable {
//    init(dictionary: [String: JSValue]) throws {
//        self = try JSONDecoder().decode(Contact.self, from: JSONSerialization.data(withJSONObject: dictionary))
//    }
//    private enum CodingKeys: String, CodingKey {
//        case number = "jobNumber", name = "jobName", client
//    }
//}

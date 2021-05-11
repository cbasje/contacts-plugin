# contacts-plugin

A capacitor plugin that allows the app to add contacts to iOS or Android device

## Install

```bash
npm install contacts-plugin
npx cap sync
```

## API

<docgen-index>

* [`createNew(...)`](#createnew)
* [`addToExisting(...)`](#addtoexisting)
* [`getContacts(...)`](#getcontacts)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### createNew(...)

```typescript
createNew(contact: Contact) => Promise<{ savedContact: Contact; }>
```

Creates a new contact and saves it, returning the saved contact's data.

| Param         | Type                                        |
| ------------- | ------------------------------------------- |
| **`contact`** | <code><a href="#contact">Contact</a></code> |

**Returns:** <code>Promise&lt;{ savedContact: <a href="#contact">Contact</a>; }&gt;</code>

--------------------


### addToExisting(...)

```typescript
addToExisting(contact: Contact) => Promise<{ savedContact: Contact; }>
```

Allows the user to pick a contact and adds the data to it, returning the saved contact's data.

| Param         | Type                                        |
| ------------- | ------------------------------------------- |
| **`contact`** | <code><a href="#contact">Contact</a></code> |

**Returns:** <code>Promise&lt;{ savedContact: <a href="#contact">Contact</a>; }&gt;</code>

--------------------


### getContacts(...)

```typescript
getContacts(filter: string) => Promise<{ results: any[]; }>
```

Gets contacts and returns the results. From https://devdactic.com/build-capacitor-plugin/ but adapted to Capacitor V3

| Param        | Type                |
| ------------ | ------------------- |
| **`filter`** | <code>string</code> |

**Returns:** <code>Promise&lt;{ results: any[]; }&gt;</code>

**Since:** 1.1.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Implements the Capacitor Permissions API.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 1.1.0

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

--------------------


### Interfaces


#### Contact

| Prop                    | Type                                  | Description                                   |
| ----------------------- | ------------------------------------- | --------------------------------------------- |
| **`displayName`**       | <code>string \| null</code>           | The full name of the contact                  |
| **`namePrefix`**        | <code>string \| null</code>           | The prefix for the name of the contact        |
| **`givenName`**         | <code>string \| null</code>           | The given name of the contact                 |
| **`middleName`**        | <code>string \| null</code>           | The middle name of the contact                |
| **`familyName`**        | <code>string \| null</code>           | The family name of the contact                |
| **`nameSuffix`**        | <code>string \| null</code>           | The suffix for the name of the contact        |
| **`nickname`**          | <code>string \| null</code>           | The nickname of the contact                   |
| **`note`**              | <code>string \| null</code>           | A note for the contact                        |
| **`groupName`**         | <code>string \| null</code>           | The name of the group the contact is added to |
| **`phoneNumberLabels`** | <code>[string]</code>                 | The labels of the phone numbers               |
| **`phoneNumbers`**      | <code>[string]</code>                 | The phone numbers                             |
| **`emailLabels`**       | <code>[string]</code>                 | The labels of the email addresses             |
| **`emails`**            | <code>[string]</code>                 | The email addresses                           |
| **`addressLabel`**      | <code>string \| null</code>           | The label of the postal address               |
| **`street`**            | <code>string \| null</code>           | The street of the postal address              |
| **`city`**              | <code>string \| null</code>           | The city of the postal address                |
| **`state`**             | <code>string \| null</code>           | The state of the postal address               |
| **`postalCode`**        | <code>string \| null</code>           | The postal code of the postal address         |
| **`country`**           | <code>string \| null</code>           | The country of the postal address             |
| **`urlLabels`**         | <code>[] \| [string]</code>           | The labels of the URLs                        |
| **`urls`**              | <code>[] \| [string]</code>           | The URLs                                      |
| **`birthday`**          | <code><a href="#date">Date</a></code> | The birthday of the contact                   |


#### Date

Enables basic storage and retrieval of dates and times.

| Method                 | Signature                                                                                                    | Description                                                                                                                             |
| ---------------------- | ------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------- |
| **toString**           | () =&gt; string                                                                                              | Returns a string representation of a date. The format of the string depends on the locale.                                              |
| **toDateString**       | () =&gt; string                                                                                              | Returns a date as a string value.                                                                                                       |
| **toTimeString**       | () =&gt; string                                                                                              | Returns a time as a string value.                                                                                                       |
| **toLocaleString**     | () =&gt; string                                                                                              | Returns a value as a string value appropriate to the host environment's current locale.                                                 |
| **toLocaleDateString** | () =&gt; string                                                                                              | Returns a date as a string value appropriate to the host environment's current locale.                                                  |
| **toLocaleTimeString** | () =&gt; string                                                                                              | Returns a time as a string value appropriate to the host environment's current locale.                                                  |
| **valueOf**            | () =&gt; number                                                                                              | Returns the stored time value in milliseconds since midnight, January 1, 1970 UTC.                                                      |
| **getTime**            | () =&gt; number                                                                                              | Gets the time value in milliseconds.                                                                                                    |
| **getFullYear**        | () =&gt; number                                                                                              | Gets the year, using local time.                                                                                                        |
| **getUTCFullYear**     | () =&gt; number                                                                                              | Gets the year using Universal Coordinated Time (UTC).                                                                                   |
| **getMonth**           | () =&gt; number                                                                                              | Gets the month, using local time.                                                                                                       |
| **getUTCMonth**        | () =&gt; number                                                                                              | Gets the month of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                             |
| **getDate**            | () =&gt; number                                                                                              | Gets the day-of-the-month, using local time.                                                                                            |
| **getUTCDate**         | () =&gt; number                                                                                              | Gets the day-of-the-month, using Universal Coordinated Time (UTC).                                                                      |
| **getDay**             | () =&gt; number                                                                                              | Gets the day of the week, using local time.                                                                                             |
| **getUTCDay**          | () =&gt; number                                                                                              | Gets the day of the week using Universal Coordinated Time (UTC).                                                                        |
| **getHours**           | () =&gt; number                                                                                              | Gets the hours in a date, using local time.                                                                                             |
| **getUTCHours**        | () =&gt; number                                                                                              | Gets the hours value in a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                       |
| **getMinutes**         | () =&gt; number                                                                                              | Gets the minutes of a <a href="#date">Date</a> object, using local time.                                                                |
| **getUTCMinutes**      | () =&gt; number                                                                                              | Gets the minutes of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                           |
| **getSeconds**         | () =&gt; number                                                                                              | Gets the seconds of a <a href="#date">Date</a> object, using local time.                                                                |
| **getUTCSeconds**      | () =&gt; number                                                                                              | Gets the seconds of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                           |
| **getMilliseconds**    | () =&gt; number                                                                                              | Gets the milliseconds of a <a href="#date">Date</a>, using local time.                                                                  |
| **getUTCMilliseconds** | () =&gt; number                                                                                              | Gets the milliseconds of a <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                      |
| **getTimezoneOffset**  | () =&gt; number                                                                                              | Gets the difference in minutes between the time on the local computer and Universal Coordinated Time (UTC).                             |
| **setTime**            | (time: number) =&gt; number                                                                                  | Sets the date and time value in the <a href="#date">Date</a> object.                                                                    |
| **setMilliseconds**    | (ms: number) =&gt; number                                                                                    | Sets the milliseconds value in the <a href="#date">Date</a> object using local time.                                                    |
| **setUTCMilliseconds** | (ms: number) =&gt; number                                                                                    | Sets the milliseconds value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                              |
| **setSeconds**         | (sec: number, ms?: number \| undefined) =&gt; number                                                         | Sets the seconds value in the <a href="#date">Date</a> object using local time.                                                         |
| **setUTCSeconds**      | (sec: number, ms?: number \| undefined) =&gt; number                                                         | Sets the seconds value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                   |
| **setMinutes**         | (min: number, sec?: number \| undefined, ms?: number \| undefined) =&gt; number                              | Sets the minutes value in the <a href="#date">Date</a> object using local time.                                                         |
| **setUTCMinutes**      | (min: number, sec?: number \| undefined, ms?: number \| undefined) =&gt; number                              | Sets the minutes value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                   |
| **setHours**           | (hours: number, min?: number \| undefined, sec?: number \| undefined, ms?: number \| undefined) =&gt; number | Sets the hour value in the <a href="#date">Date</a> object using local time.                                                            |
| **setUTCHours**        | (hours: number, min?: number \| undefined, sec?: number \| undefined, ms?: number \| undefined) =&gt; number | Sets the hours value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                     |
| **setDate**            | (date: number) =&gt; number                                                                                  | Sets the numeric day-of-the-month value of the <a href="#date">Date</a> object using local time.                                        |
| **setUTCDate**         | (date: number) =&gt; number                                                                                  | Sets the numeric day of the month in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                        |
| **setMonth**           | (month: number, date?: number \| undefined) =&gt; number                                                     | Sets the month value in the <a href="#date">Date</a> object using local time.                                                           |
| **setUTCMonth**        | (month: number, date?: number \| undefined) =&gt; number                                                     | Sets the month value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                     |
| **setFullYear**        | (year: number, month?: number \| undefined, date?: number \| undefined) =&gt; number                         | Sets the year of the <a href="#date">Date</a> object using local time.                                                                  |
| **setUTCFullYear**     | (year: number, month?: number \| undefined, date?: number \| undefined) =&gt; number                         | Sets the year value in the <a href="#date">Date</a> object using Universal Coordinated Time (UTC).                                      |
| **toUTCString**        | () =&gt; string                                                                                              | Returns a date converted to a string using Universal Coordinated Time (UTC).                                                            |
| **toISOString**        | () =&gt; string                                                                                              | Returns a date as a string value in ISO format.                                                                                         |
| **toJSON**             | (key?: any) =&gt; string                                                                                     | Used by the JSON.stringify method to enable the transformation of an object's data for JavaScript Object Notation (JSON) serialization. |


#### PermissionStatus

| Prop           | Type                                                                      |
| -------------- | ------------------------------------------------------------------------- |
| **`contacts`** | <code>"prompt" \| "prompt-with-rationale" \| "granted" \| "denied"</code> |

</docgen-api>

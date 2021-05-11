import type { PermissionState } from '@capacitor/core';

export interface PermissionStatus {
  contacts: PermissionState;
}

export interface ContactsPlugin {
  createNew(contact: Contact): Promise<{ success: string }>;
  addToExisting(contact: Contact): Promise<{ success: string }>;

  /**
   * Gets contacts and returns the results.
   *
   * @since 1.1.0
   */
  getContacts(filter: string): Promise<{ results: any[] }>;

  checkPermissions(): Promise<PermissionStatus>;
  requestPermissions(): Promise<PermissionStatus>;
}

export interface Contact {
  /**
   * The name at which to open the map.
   */
  displayName: string | null;
  namePrefix: string | null;
  givenName: string | null;
  middleName: string | null;
  familyName: string | null;
  previousFamilyName: string | null;
  nameSuffix: string | null;
  nickname: string | null;
  
  note: string | null;

  groupName: string | null;

  phoneNumberLabels: [string];
  phoneNumbers: [string];
  
  emailLabels: [string];
  emails: [string];

  addressLabel: string | null;
  street: string | null;
  city: string | null;
  state: string | null;
  postalCode: string | null;
  country: string | null;

  urlLabels: [string] | [];
  urls: [string] | [];

  birthday: Date;
}
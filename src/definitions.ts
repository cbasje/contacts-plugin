import type { PermissionState } from '@capacitor/core';

export interface PermissionStatus {
  contacts: PermissionState;
}

export interface ContactsPlugin {
  /**
   * Creates a new contact and saves it, returning the saved contact's data.
   * 
   * @param contact
   */
  createNew(contact: Contact): Promise<{ savedContact: Contact }>;
  /**
   * Allows the user to pick a contact and adds the data to it, returning the saved contact's data.
   * 
   * @param contact
   */
  addToExisting(contact: Contact): Promise<{ savedContact: Contact }>;

  /**
   * Gets contacts and returns the results. From https://devdactic.com/build-capacitor-plugin/ but adapted to Capacitor V3
   *
   * @since 1.1.0
   */
  getContacts(filter: string): Promise<{ results: any[] }>;

  /**
   * Implements the Capacitor Permissions API.
   *
   * @since 1.1.0
   */
  checkPermissions(): Promise<PermissionStatus>;
  requestPermissions(): Promise<PermissionStatus>;
}

export interface Contact {
  /**
   * The full name of the contact
   */
  displayName: string | null;
  /**
   * The prefix for the name of the contact
   */
  namePrefix: string | null;
  /**
   * The given name of the contact
   */
  givenName: string | null;
  /**
   * The middle name of the contact
   */
  middleName: string | null;
  /**
   * The family name of the contact
   */
  familyName: string | null;
  /**
   * The suffix for the name of the contact
   */
  nameSuffix: string | null;
  /**
   * The nickname of the contact
   */
  nickname: string | null;
  
  /**
   * A note for the contact
   */
  note: string | null;

  /**
   * The name of the group the contact is added to
   */
  groupName: string | null;

  /**
   * The labels of the phone numbers
   * 
   * @version iOS
   */
  phoneNumberLabels: [string];
  /**
   * The phone numbers
   */
  phoneNumbers: [string];
  
  /**
   * The labels of the email addresses
   * 
   * @version iOS
   */
  emailLabels: [string];
  /**
   * The email addresses
   */
  emails: [string];

  /**
   * The label of the postal address
   * 
   * @version iOS
   */
  addressLabel: string | null;
  /**
   * The street of the postal address
   */
  street: string | null;
  /**
   * The city of the postal address
   */
  city: string | null;
  /**
   * The state of the postal address
   */
  state: string | null;
  /**
   * The postal code of the postal address
   */
  postalCode: string | null;
  /**
   * The country of the postal address
   */
  country: string | null;

  /**
   * The labels of the URLs
   * 
   * @version iOS
   */
  urlLabels: [string] | [];
  /**
   * The URLs
   */
  urls: [string] | [];

  /**
   * The birthday of the contact
   */
  birthday: Date;
}
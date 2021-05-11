import { WebPlugin } from '@capacitor/core';

import { Contact, ContactsPlugin, PermissionStatus } from './definitions';

export class ContactsWeb extends WebPlugin implements ContactsPlugin {
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  createNew(contact: Contact): Promise<{ savedContact: Contact }> {
    throw this.unimplemented('Not implemented on web.');
  }
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  addToExisting(contact: Contact): Promise<{ savedContact: Contact }> {
    throw this.unimplemented('Not implemented on web.');
  }

  async getContacts(filter: string): Promise<{ results: any[] }> {
    console.log('filter: ', filter);
    return {
      results: [{
        firstName: 'Dummy',
        lastName: 'Entry',
        telephone: '123456'
      }]
    };
  }

  checkPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }
  requestPermissions(): Promise<PermissionStatus> {
    throw this.unimplemented('Not implemented on web.');
  }
}

import { WebPlugin } from '@capacitor/core';

import { Contact, ContactsPlugin, PermissionStatus } from './definitions';

export class ContactsWeb extends WebPlugin implements ContactsPlugin {
  createNew(contact: Contact): Promise<{ success: string }> {
    // FIXME
    throw new Error('Method not implemented.');
  }
  addToExisting(contact: Contact): Promise<{ success: string }> {
    // FIXME
    throw new Error('Method not implemented.');
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

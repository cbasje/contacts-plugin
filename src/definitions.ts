export interface ContactsPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  /**
   * Gets contacts and returns the results.
   *
   * @since 1.1.0
   */
  getContacts(filter: string): Promise<{ results: any[] }>;
}

export interface OpenMapOptions {
  /**
   * The latitude at which to open the map.
   */
  latitude: number;

  /**
   * The longitude at which to open the map.
   */
  longitude: number;
}

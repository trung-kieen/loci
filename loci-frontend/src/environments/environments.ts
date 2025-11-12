export const environment = {
  production: false,
  apiUrl: '/api/v1',
  keycloak: {
    // Keycloak url
    issuer: 'http://localhost:9090',
    // Realm
    realm: 'loci-realm',
    clientId: 'angular',
  },
};

export const environment = {
  production: false,
  apiUrl: '/api',
  keycloak: {
    // Keycloak url
    issuer: 'http://localhost:9090',
    // Realm
    realm: 'loci-realm',
    clientId: 'angular',
  },
};

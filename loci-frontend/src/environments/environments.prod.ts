import { IEnv } from './env.model';

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const w = (window as any).__env || {};

export const environment: IEnv = {
  production: w.production ?? true,

  socketEndpoint: w.socketEndpoint || 'wss://your-prod-domain/api/v1/ws',
  apiUrl:         w.apiUrl         || '//your-prod-domain/api/v1',

  keycloak: {
    issuer:   w.keycloakIssuer    || 'https://your-prod-keycloak',
    realm:    w.keycloakRealm     || 'loci-realm',
    clientId: w.keycloakClientId  || 'angular',
  },
};

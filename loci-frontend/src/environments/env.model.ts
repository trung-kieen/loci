export interface IEnv {
  production: boolean;
  socketEndpoint: string;
  apiUrl: string;
  keycloak: {
    issuer: string;
    realm: string;
    clientId: string;
  };
}

// file is auto-generated at Docker runtime via docker-entrypoint.sh
// Values here are only used in local dev (non-Docker) as fallback
(function (window) {
  window.__env = window.__env || {};

  // Overridden by Docker environment variables at runtime
  window.__env.production      = false;
  window.__env.socketEndpoint  = 'ws://localhost:8080/api/v1/ws';
  window.__env.apiUrl          = '//localhost:8080/api/v1';
  window.__env.keycloakIssuer  = 'http://localhost:9090';
  window.__env.keycloakRealm   = 'loci-realm';
  window.__env.keycloakClientId = 'angular';
}(this));

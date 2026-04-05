// file is auto-generated at Docker runtime via docker-entrypoint.sh
(function (window) {
  window.__env = window.__env || {};

  window.__env.production     = false;
  window.__env.socketEndpoint = 'ws://localhost:8080/api/v1/ws';
  window.__env.apiUrl         = '//localhost:8080/api/v1';

  window.__env.keycloak = {
    issuer:   'http://localhost:9090',
    realm:    'loci-realm',
    clientId: 'angular'
  };

})(window);

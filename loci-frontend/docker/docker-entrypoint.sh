#!/bin/sh
set -e

ENV_JS_PATH="/app/dist/loci-frontend/assets/env.js"

echo "Generating runtime environment config..."
mkdir -p "$(dirname "$ENV_JS_PATH")"

cat > "$ENV_JS_PATH" <<EOF
(function (window) {
  window.__env = window.__env || {};
  window.__env.production = ${PRODUCTION:-false};
  window.__env.socketEndpoint = '${SOCKET_ENDPOINT:-ws://localhost:8080/api/v1/ws}';
  window.__env.apiUrl = '${API_URL:-//localhost:8080/api/v1}';
  window.__env.keycloakIssuer = '${KEYCLOAK_ISSUER:-http://localhost:9090}';
  window.__env.keycloakRealm = '${KEYCLOAK_REALM:-loci-realm}';
  window.__env.keycloakClientId = '${KEYCLOAK_CLIENT_ID:-angular}';
}(this));
EOF

echo "env.js written at $ENV_JS_PATH"
cat "$ENV_JS_PATH"

exec "$@"

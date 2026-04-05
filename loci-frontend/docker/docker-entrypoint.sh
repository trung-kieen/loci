#!/bin/sh
set -e

ENV_JS_PATH="/usr/share/nginx/html/assets/env.js"

echo "Generating runtime environment config..."
mkdir -p "$(dirname "$ENV_JS_PATH")"

# prevent shell injection ──
PRODUCTION_SAFE=$(echo "${PRODUCTION:-false}" | tr -d "'\"\`")
API_URL_SAFE=$(echo "${API_URL:-//localhost:8080/api/v1}" | tr -d "'\"\`")
SOCKET_SAFE=$(echo "${SOCKET_ENDPOINT:-ws://localhost:8080/api/v1/ws}" | tr -d "'\"\`")
KEYCLOAK_ISSUER_SAFE=$(echo "${KEYCLOAK_ISSUER:-http://localhost:9090}" | tr -d "'\"\`")
KEYCLOAK_REALM_SAFE=$(echo "${KEYCLOAK_REALM:-loci-realm}" | tr -d "'\"\`")
KEYCLOAK_CLIENT_ID_SAFE=$(echo "${KEYCLOAK_CLIENT_ID:-angular}" | tr -d "'\"\`")

cat > "$ENV_JS_PATH" <<EOF
// Auto-generated at container startup — do not edit manually
(function (window) {
  window.__env = window.__env || {};
  window.__env.production     = ${PRODUCTION_SAFE};
  window.__env.apiUrl         = '${API_URL_SAFE}';
  window.__env.socketEndpoint = '${SOCKET_SAFE}';
  window.__env.keycloak = {
    issuer:   '${KEYCLOAK_ISSUER_SAFE}',
    realm:    '${KEYCLOAK_REALM_SAFE}',
    clientId: '${KEYCLOAK_CLIENT_ID_SAFE}'
  };
})(window);
EOF

echo "env.js written at $ENV_JS_PATH"
cat "$ENV_JS_PATH"

exec "$@"

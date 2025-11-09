#!/bin/bash
set -e

# Only backup if database already exists
if [ -d "/var/lib/postgresql/data/pgdata/base" ]; then
  # BACKUP_FILE="/backups/startup_$(date +%Y%m%d_%H%M%S).sql"
  BACKUP_FILE="/backups/backup.sql"
  echo "Creating backup to $BACKUP_FILE..."

  # Wait for PostgreSQL to be ready
  until pg_isready -U postgres; do sleep 1; done

  # Run backup in background (don't block startup)
  pg_dump -U keycloak keycloak > "$BACKUP_FILE" &
  echo "Backup started in background"
else
  echo "First run detected - skipping backup"
fi

# --- START POSTGRESQL ---
exec /usr/local/bin/docker-entrypoint.sh "$@"

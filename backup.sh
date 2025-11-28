#!/bin/bash
backup_file=./backups/backup.sql
docker exec -i loci-db pg_dump -U admin locidb > $backup_file

echo "Export postgresql database migration script to $backup_file"



docker exec loci-keycloak  \
  /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/export --realm loci-realm && echo "Export keycloak realm to  ./keycloak-data/export/"


echo "NOTE: consider use ./keycloak-data/import.sh" to make the export data being use in the import process

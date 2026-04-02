#!/bin/bash



backup_file=./backups/backup.sql
backup_file_old="$backup_file.old"
mv $backup_file $backup_file_old

docker exec -i loci-db pg_dump -U admin locidb > $backup_file

echo "Export postgresql database migration script to $backup_file"


# # include schema + data (full backup)
# docker exec -i <container_name> pg_dump \
#   -U postgres \
#   --column-inserts \
#   --no-owner \
#   --no-acl \
#   chatdb > chatdb_full.sql


# docker exec -i <container_name> pg_dump \
#   -U postgres \
#   --column-inserts \
#   --data-only \
#   --no-owner \
#   --no-acl \
#   chatdb > chatdb_seed_data.sql

# # Specific table
# docker exec -i <container_name> pg_dump \
#   -U postgres \
#   --column-inserts \
#   --data-only \
#   --table=user_ \
#   --table=authority \
#   --table=user_authority \
#   --table=user_setting \
#   --table=contact \
#   --table=conversation \
#   --table=conversation_participant \
#   --table=message \
#   --no-owner \
#   chatdb > chatdb_seed_data.sql




docker exec loci-auth \
  /opt/keycloak/bin/kc.sh export --dir /opt/keycloak/data/export --realm loci-realm && echo "Export keycloak realm to  ./keycloak-data/export/"


echo "NOTE: consider use ./keycloak-data/migrate-import-data.sh" to make the export data being use in the import process

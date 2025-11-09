#!/bin/bash
docker exec -i loci-db pg_dump -U admin locidb > ./backups/backup.sql

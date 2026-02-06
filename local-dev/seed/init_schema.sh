#!/bin/bash
cat 0.schema.sql  | docker  exec -i loci-db psql -U admin -d locidb

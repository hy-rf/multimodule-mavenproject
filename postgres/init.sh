#!/bin/bash

psql -U mmdbuser -d mmdb -c "ALTER USER mmdbuser WITH PASSWORD '00000000';"

exec docker-entrypoint.sh postgres

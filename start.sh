
#!/bin/bash

set -e

AMBIENTE=$1

docker compose --env-file envs/.env.$AMBIENTE up
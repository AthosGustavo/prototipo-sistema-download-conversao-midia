#!/bin/bash

# Para execução se houver erro
set -e

docker compose -f docker-compose-jenkins.yml --env-file infra/envs/.env.jenkins up --build -d 
#!/bin/bash

# Para execução se houver erro
set -e

echo "Gerando JARS dos serviços..."

AMBIENTE=$1
CAMINHO_ENV=envs/.env.$AMBIENTE

cd services

# Array com os serviços
services=(
    "eureka-server"
    "gateway"
    "orquestrador"
    "midia-extrator"
)


for service in "${services[@]}"; do
    
    # Verifica se diretório existe
    if [ ! -d "$service" ]; then
        echo "ERRO: Diretório da serviço '${service}' não encontrado"
        exit 1  
    fi
    

    cd "$service"
    
    if ! mvn clean package -DskipTests; then
        echo "ERRO: Falha ao gerar o JAR do serviço '${service}'"
        exit 1
    fi

    echo -e "======== JAR do serviço ${service} gerado. ========"
    
    # Volta para o diretório raiz
    cd ..
    
done

cd ..

docker compose --env-file $CAMINHO_ENV up --build -d


echo -e "Container iniciado"


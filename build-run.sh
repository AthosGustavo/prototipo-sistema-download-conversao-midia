#!/bin/bash

# Para execução se houver erro
set -e

echo "Gerando JARS dos serviços..."

# Array com os serviços
services=(
    "server"
    "gateway"
    "orquestrador"
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

    echo -e "JAR do serviço $[service] gerado"
    
    # Volta para o diretório raiz
    cd ..
    
done

cd docker
docker-compose build
docker-compose up
cd ..

echo -e "Container iniciado"


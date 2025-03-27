#!/bin/bash
set -e

echo "Gerando imagem docker..."
mvn clean package dockerfile:build -DskipTests

echo "Listando ultimas imagens..."
docker images | head -2
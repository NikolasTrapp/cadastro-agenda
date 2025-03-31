# Guia de Execução do docker-compose

Este documento fornece instruções para rodar o ambiente utilizando **Docker Compose**, contendo os serviços necessários para a aplicação **Cadastro Agenda**.

## Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- **Docker**: [Instalar Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Instalar Docker Compose](https://docs.docker.com/compose/install/)

## Estrutura do docker-compose.yml

O arquivo `docker-compose.yml` contém os seguintes serviços:

1. **Portainer** - Interface de gerenciamento para Docker.
2. **PostgreSQL** - Banco de dados PostgreSQL.
3. **Backend** - API do sistema Cadastro Agenda.
4. **Frontend** - Interface do usuário da aplicação Cadastro Agenda.

## Como executar

### 1. Subir os containers
Para iniciar os serviços, execute o seguinte comando na raiz do projeto:

```sh
docker-compose up -d
```

Isso iniciará todos os containers em segundo plano (`-d` = detached mode).

### 2. Verificar os containers em execução
Use o comando abaixo para listar os containers ativos:

```sh
docker ps
```

### 3. Acessar os serviços

- **Portainer**: [http://localhost:9000](http://localhost:9000)
- **Backend**: [http://localhost:8080](http://localhost:8080)
- **Frontend**: [http://localhost:4200](http://localhost:4200)
- **Banco de Dados (PostgreSQL)**: `localhost:5432`, usuário: `postgres`, senha: `postgres`

### 4. Parar e remover os containers
Para parar os serviços e remover os containers, execute:

```sh
docker-compose down
```

Para remover também os volumes persistentes:

```sh
docker-compose down -v
```

## Observações

- O serviço **Portainer** pode ser utilizado para monitorar os containers e volumes.
- O backend depende do banco de dados, e há uma verificação de saúde configurada para garantir que ele aguarde a inicialização do PostgreSQL antes de iniciar.
- O frontend está configurado para rodar na porta `4200`, enquanto o backend na porta `8080`.

Se houver dúvidas ou problemas, consulte a documentação do Docker ou verifique os logs dos serviços com:

```sh
docker logs -f nome_do_container
```


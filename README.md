# Guia de Execução do docker-compose

Este documento fornece instruções para rodar o ambiente utilizando **Docker Compose**, contendo os serviços necessários para a aplicação **Cadastro Agenda**.

## Pré-requisitos

Antes de iniciar, certifique-se de ter instalado:

- **Docker**: [Instalar Docker](https://docs.docker.com/get-docker/)
- **Docker Compose**: [Instalar Docker Compose](https://docs.docker.com/compose/install/)

## Estrutura do docker-compose.yml

O arquivo `compose.yml` contém os seguintes serviços:

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
- **Banco de Dados (PostgreSQL)**: `localhost:5432`, usuário: `postgres`, senha: `postgres`, banco de dados: `cadastro_agenda`

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

## Quais eram os proximos passos?

##### Devido ao tempo que tive em meio a compromissos pessoais e prazo estabelecido, no meu ponto de vista, a aplicação poderia ter ou ter melhor os seguintes pontos (de inicio):
- Adicionado `HATEOAS`, `SpringDoc` e `Swagger` na aplicacao;
- Feito um sistema de login e roles, teria feito um contexto estatico com `ThreadLocals` para setar usuario e adicionado campos de auditoria `created_by` e `last_modified_by` nas tabelas e configurado o `Slf4j` para logar este usuario em todos os logs;
- Melhorado o sistema de validacoes e exception handling com uma excecao de negocio;
- Feito um "aplicador de filtros" mais abrangente e mais inteligente para aplicar os filtros que vem do frontend nas queries do `QueryDSL`;
- Mais testes, garantindo maior cobertura e testando as constraints que as entidades possuem a nivel de controlador e banco de dados, alem de testes de integracao;
- Adicionado uma entidade de propriedades, para possibilitar a configuracao dinamica dos precos e parametros das rotinas;
- Uma tabela e sistema de `lock semantico`, junto a uma validacao para nao permitir cadastrar reservas dentro de um periodo ja ocupado para o mesmo quarto.
- Balanceador de carga / api-gateway, para permitir multiplas instancias e entrega homogenea de requisicoes.
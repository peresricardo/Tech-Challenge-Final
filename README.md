# Tech-Challenge-Fase5
<hr>
Todos os nossos micro-serviços foram desenvolvidos utlizando a versão <b>21 do Java - AmazonCorretto.</b>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-blue?style=for-the-badge&logo=postgresql&logoColor=white)

## Serviços em nossa aplicação
- [x] Service-Discovery
- [x] Service-Gateway
- [x] Service-Cliente

### Service-Discovery
Serviço em Spring Boot 3 utilizando Eureka Server para que seja possível registrar os micros serviços da aplicação.


### Service-Gateway
Serviço em Spring Boot 3 utilizando Gateway para que seja possível centralizar todos os micros serviços da aplicação
em um único endereço, facilitando a utilização de todos os serviços com chamada única


### Service-Cliente
Serviço em Spring Boot 3 utilizando:
Jpa - Actuator - Discovery Client e OpenFeign<br>
OpenFeign é utilizado para efetuar busca de endereço por Cep, utilizando o serviço ViaCep

<br>

## 📑 Swagger Documentação
- Microserviço de Clientes - http://localhost:8080/clientes/swagger-ui.html
<br>
<br>

Para acessar os serviços registrados no Eureka acesse:
http://localhost:8761
<br>
<br>

## 🔧 Instalação

```shell
git clone https://github.com/peresricardo/Tech-Challenge-Final
```
### Docker
Criar a rede para utilização dos serviços
```sh
docker network create fase5network
```

### Limpar, compilar e gerar imagem para o docker

- SrvDiscovery - Serviço de registro dos micros serviços
  cd srvDiscovery
```sh
mvn clean install
docker image build -t srv-discovery .
```

- SrvGateway - Serviço de centralização do endereço único dos micros serviços
  cd srvGateway
```sh
mvn clean install
docker image build -t srv-gateway .
```

- srvCliente - Micro serviço de clientes
  cd srvCliente
```sh
mvn clean install
docker image build -t srv-cliente .
```

- É possível executar todos os serviços de uma única vez utilizado o arquivo docker-compose que se encontra na raiz da pasta principal do projeto.
<br>Executar o comando abaixo para iniciar os serviços
```sh
docker-compose up -d
```
Executar o comando abaixo para parar os serviços
```sh
docker-compose stop
```
## 💻 Configuração Pgadmin
- pgadmin: http://localhost:15432/
<br>Host name/address: postgres-db
<br>Username: postgres
<br>Password: Postgres2023!
![img.png](img.png)



## 🛠️ Execução de testes no projeto
<hr>

- Para executar os testes unitários:

```sh
mvn test
mvn clean jacoco:prepare-agent install jacoco:report
```

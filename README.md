# Tech-Challenge-Fase5
<hr>
Todos os nossos micro-serviços foram desenvolvidos utlizando a versão <b>21 do Java - AmazonCorretto.</b>

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

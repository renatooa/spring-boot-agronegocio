# Web API Agronegocio

Web Api para exposição de recursos De Fazenda e seus animais 

## Pilha tecnológica
Projeto elaborado utilizando Spring Boot versão 2.3.3, utilizando a pilha tecnológica apresentada a baixo:
- __Spring Data__ – Persistência de dados
- __Spring Cache__ - Cache de recursos (Não utilizando no projeto, pois haveria muitas ocorrencias para limpar o Cache)
- __Spring Actuator__ - Fornecimento de dados sobre a saúde da Web Api
- __Boot Admin__ - Envio de dados obtidos do Actuator para o Spring Boot Adimin, que por sua vez fornece  a interface gráfica. Mais informações [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)
- __H2__ –-    Banco de Dados
- __Spring Fox__ - Documentação da API para Swagger 2
- __Javax Validation__ –    Validação de Beans

### Observações
Consta no projeto o arquivo data.sql, este arquivo é responsável pela criação de dados no arranque da Web API. Caso o banco tenha sido criado anteriormente, será exibido um erro em console que pode ser desprezado, pois refere-se a nova tentativa de criar os dados.
Talvez seja necessário reconhecer o Certificado Digital como seguro. Pode ser feito pelo Browser acessando a URL de documentação da API.

### Teste da WebApi
O teste da API pode ser feito pela interface do Swagger Disponível pela URL  [http://localhost:8080/agronegocio/v1/swagger-ui/](http://localhost:8080/agronegocio/v1/swagger-ui/)

### Release
O release está disponível pela URL  [v1](https://github.com/renatooa/spring-boot-agronegocio/releases/tag/v1), siga as orientações de instalação e testes



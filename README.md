# Equipment Maintenance Service - REST API

![license](https://img.shields.io/github/license/leonardovcl/sweet-control)
![status](https://img.shields.io/static/v1?label=status&message=Developing&color=red)

[Overview](#features)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Licença](#licença)
:small_blue_diamond:
[Autores](#autores)

:arrows_counterclockwise: API REST desenvolvida para auxiliar no controle de todas as etapas de execução de um serviço de manutenção de equipamentos.

## :clipboard: Testes

    ./mvnw test

## :gear: Como executar

### :computer: Localmente 

    ./mvnw package
    java -jar target/EquipmentMaintenanceService-0.0.1-SNAPSHOT.jar

#### :whale: Docker

    docker pull ghcr.io/leonardovcl/ems-api:latest
    docker run -p 8080:8080 ghcr.io/leonardovcl/ems-api:latest

### :satellite: Remoto

Há uma live version da API que pode ser acessada pela url:

    https://ems-rest-api.fly.dev/<endpoint>

## :hammer: Funcionalidades

- Cadastro, gerenciamento e consulta de Clientes (url: /customers);
- Cadastro e gerenciamento de Funcionários de Manutenção (url: /employees);
- Cadastro, gerenciamento (registro das etapas envolvidos no serviço) e consulta de Ordens de Serviço (url: /serviceOrders).

### :keyboard: Usos

###### Alguns dados são carregados no banco de dados in memory no start up da aplicação para fins de demonstração.

- Obter lista de Clientes cadastrados:

      curl localhost:8080/customers
      
##### Modelo de Resposta:
      
```json
[{
    "id": 1,
    "name": "Adriana V.",
    "email": "adrianav@gmail.com",
    "phoneNumber": "+5541999999901",
    "address": "R. 01, n. 277"
}, {
    "id": 2,
    "name": "Ricardo L.",
    "email": "ricardol@gmail.com",
    "phoneNumber": "+5541999999901",
    "address": "R. 02, n. 100"
}]
```

- Obter Cliente por Id:

      curl localhost:8080/customers/<id>
      
##### Modelo de Resposta (``<id>`` = 1):
      
```json
{
    "id": 1,
    "name": "Adriana V.",
    "email": "adrianav@gmail.com",
    "phoneNumber": "+5541999999901",
    "address": "R. 01, n. 277"
}
```

- Obter Cliente por nome:

      curl localhost:8080/customers/byName/<string>
      
##### Modelo de Resposta (``<string>`` = ric):
      
```json
{
    "id": 2,
    "name": "Ricardo L.",
    "email": "ricardol@gmail.com",
    "phoneNumber": "+5541999999901",
    "address": "R. 02, n. 100"
}
```

- Registrar Cliente:

      curl -X POST localhost:8080/customers/ -H 'Content-Type: application/json' -d '<new_customer>'
      
##### Modelo de Resposta (retorna objeto criado ``<new_customer>``):
      
```json
{
    "id": 3,
    "name": "Teste01",
    "email": "teste01@gmail.com",
    "phoneNumber": "+5541999999900",
    "address": "R. 00, n. 000"
}
```

## Tech Stack

[⬆ Topo](#)
:small_blue_diamond:

Equipment Maintenance Service API foi construído como API REST em Java com o framework Spring (Spring Boot & Spring Data).

![Java](https://res.cloudinary.com/practicaldev/image/fetch/s--KR6jSVNe--/c_limit%2Cf_auto%2Cfl_progressive%2Cq_auto%2Cw_880/https://img.shields.io/badge/Java-ED8B00%3Fstyle%3Dfor-the-badge%26logo%3Djava%26logoColor%3Dwhite)
![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![SpringBoot](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)

Os dados são armazenados em um banco de dados in memory H2.

Persistência e a validação são realizadas com Hibernate (através do Spring Data).

![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)

## Histórico

[⬆ Topo](#)
:small_blue_diamond:

* 0.1.0
    * Primeira versão estável.

## Roadmap

[⬆ Topo](#)
:small_blue_diamond:

O projeto ainda está em desenvolvimento e as próximas atualizações serão focadas nas seguintes tarefas:

- [ ] Finalizar documentação do código (Javadoc);
- [ ] Implementar usuários para controle de acesso e pemissão de uso de métodos;
- [ ] Implementar autenticação via JWT;
- [ ] Substituir banco de dados H2 in memory por outro banco de dados relacional;

Features:

- [ ] Busca de clientes por outros atributos (e-mail e telefone);
- [ ] Busca de clientes utilizando diferentes atributos simultaneamente;
- [ ] Atualizações do andamento em tempo real enviadas para e-mails cadastrados;
- [ ] Geração de relatórios.

## Licença

[⬆ Topo](#)
:small_blue_diamond:

Licenciado sob os termos da licença MIT.
Veja [LICENSE](https://github.com/leonardovcl/EquipmentMaintenanceService/blob/main/LICENSE) para mais informações.

## Autores

[⬆ Topo](#)
:small_blue_diamond:

:heavy_check_mark: 
**Leonardo Viana**

[![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/leonardovcl/ "leonardovcl")
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/leonardovcl/ "LinkedIn")
[![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:leonardovc.lima@gmail.com "leonardovc.lima@gmail.com")

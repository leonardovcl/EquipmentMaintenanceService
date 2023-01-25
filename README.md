# Equipment Maintenance Service - REST API

![license](https://img.shields.io/github/license/leonardovcl/sweet-control)
![status](https://img.shields.io/static/v1?label=status&message=Developing&color=red)

[Testes](#testes)
:small_blue_diamond:
[Executar](#como-executar)
:small_blue_diamond:
[Modelo Entidades](#modelos-das-entidades)
:small_blue_diamond:
[Como Usar](#como-usar)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Histórico](#histórico)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Licença](#licença)
:small_blue_diamond:
[Autores](#autores)

:arrows_counterclockwise: API REST desenvolvida para auxiliar no controle de todas as etapas de execução de um serviço de manutenção de equipamentos.

## Testes

    ./mvnw test

## Como executar

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

[⬆ Topo](#)
:small_blue_diamond:
[Testes](#testes)
:small_blue_diamond:
[Executar](#como-executar)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Histórico](#histórico)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Licença](#licença)
:small_blue_diamond:
[Autores](#autores)

- Cadastro, gerenciamento e consulta de Clientes (url: /customers);
- Cadastro e gerenciamento de Funcionários de Manutenção (url: /employees);
- Cadastro, gerenciamento (registro das etapas envolvidos no serviço) e consulta de Ordens de Serviço (url: /serviceOrders).

### Modelos das Entidades

<details>
	<summary>Clientes (Costumers)</summary>
	
```json
{
    "id": <Long>, #Não utilizar no Cadstramento!
    "name": <String>,
    "email": <String>,
    "phoneNumber": <String>,
    "address": <String>
}
```
</details>
	
<details>
	<summary>Funcionários (MaintenanceEmployee)</summary>
	
```json
{
    "id": <Long>, #Não utilizar no Cadstramento!
    "name": <String>,
    "position": <Position Enum> #LEADER, PRINCIPAL, ASSISTANT
}
```
</details>
	
<details>
	<summary>Equipamentos (Equipments)</summary>
	
```json
{
    "id": <Long>, #Não utilizar no Cadstramento!
    "type": <String>,
    "brand": <String>,
    "observations": <String>
}
```
</details>
	
<details>
	<summary>Status (Status)</summary>
	
```json
{
    "id": <Long>, #Não utilizar no Cadstramento!
    "employee": <MaintenanceEmployee>,
    "stageDateTime": <TimeStamp>,
    "stage": <Stage Enum> #RECEIVED, INITIATED, ONHOLD, RESUMED, FINISHED,
    "description": <String>
}
```
</details>
	
<details>
	<summary>Ordem de Serviço (ServiceOrder)</summary>
	
```json
{
    "id": <Long>, #Não utilizar no Cadstramento!,
    "customer": <Customer>,
    "equipment": <Equipment>,
    "statusLog": [
        <Status>,
	<Status>,
	<Status>
    ],
    "problemDescription": <String>
}
```
</details>

### Como Usar

###### Alguns dados são carregados no banco de dados in memory no start up da aplicação para fins de demonstração.

<details>
	<summary>Clientes Endpoint</summary>
    
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

      curl localhost:8080/customers/<customerId>
      
##### Modelo de Resposta (``<customerId>`` = 1):
      
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
      
##### Retorna objeto criado ``<new_customer>``
    
- Atualizar Cliente:

      curl -X PUT localhost:8080/customers/<customerId> -H 'Content-Type: application/json' -d '<updated_customer>'
      
##### Retorna Cliente atualizado ``<updated_customer>``
    
- Deletar Cliente:

      curl -X DELETE localhost:8080/customers/<customerId>
</details>
	
<details>
	<summary>Funcionários de Manutenção Endpoint</summary>
    
- Obter lista de Funcionários cadastrados:

      curl localhost:8080/employees
      
##### Modelo de Resposta:
      
```json
[{
    "id": 1,
    "name": "Paulo H.",
    "position": "LEADER"
}, {
    "id": 2,
    "name": "Gabiel H.",
    "position": "ASSISTANT"
}]
```

- Obter Funcionário por Id:

      curl localhost:8080/employees/<employeeId>
      
##### Modelo de Resposta (``<employeeId>`` = 1):
      
```json
{
    "id": 1,
    "name": "Paulo H.",
    "position": "LEADER"
}
```

- Registrar Funcionário:

      curl -X POST localhost:8080/employees/ -H 'Content-Type: application/json' -d '<new_employee>'
      
##### Retorna objeto criado ``<new_employee>``
    
- Atualizar Funcionário:

      curl -X PUT localhost:8080/employees/<employeeId> -H 'Content-Type: application/json' -d '<updated_employee>'
      
##### Retorna Funcionário atualizado ``<updated_employee>``
    
- Deletar Funcionário:

      curl -X DELETE localhost:8080/employees/<employeeId>
</details>
	
<details>
	<summary>Ordens de Serviço Endpoint</summary>
    
- Obter lista de Ordens de Serviço cadastradas:

      curl localhost:8080/serviceOrders
      
##### Modelo de Resposta:
      
```json
[{
    "id": 1,
    "customer": {
        "id": 1,
        "name": "Adriana V.",
        "email": "adrianav@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 01, n. 277"
    },
    "equipment": {
        "id": 1,
        "type": "Compressor de Ar",
        "brand": "Vonder",
        "observations": null
    },
    "statusLog": [{
        "id": 1,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 2,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Testes de vazamentos falharam, iniciando reparos na lataria"
    }, {
        "id": 3,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "FINISHED",
        "description": "Reparo finalizado, Equipamento em funcionamento normal"
    }],
    "problemDescription": "Equipamento nao esta conseguindo realizar a compressao"
}, {
    "id": 2,
    "customer": {
        "id": 2,
        "name": "Ricardo L.",
        "email": "ricardol@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 02, n. 100"
    },
    "equipment": {
        "id": 2,
        "type": "Esmerilhadeira Angular",
        "brand": "Bosh",
        "observations": null
    },
    "statusLog": [{
        "id": 4,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 5,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Testes de circuitos eletronicos falharam"
    }, {
        "id": 6,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "ONHOLD",
        "description": "Aguardando chegada de capacitor para substituicao, estimativa: 3 dias"
    }],
    "problemDescription": "Equipamento nao liga"
}, {
    "id": 3,
    "customer": {
        "id": 2,
        "name": "Ricardo L.",
        "email": "ricardol@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 02, n. 100"
    },
    "equipment": {
        "id": 3,
        "type": "Furadeira",
        "brand": "Black&Decker",
        "observations": null
    },
    "statusLog": [{
        "id": 7,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 8,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Danos irreparáveis na estrutura plastica"
    }, {
        "id": 9,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "ONHOLD",
        "description": "Aguardando chegada de nova carcaca, estimativa: 1 dia"
    }, {
        "id": 10,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RESUMED",
        "description": "Realizando substituicao da carcaca plastica"
    }],
    "problemDescription": "Equipamento com avarias na carcaca"
}]
```

- Obter Ordens de Serviço por Id:

      curl localhost:8080/serviceOrders/<serviceOrderId>
      
##### Modelo de Resposta (``<serviceOrderId>`` = 1):
      
```json
{
    "id": 1,
    "customer": {
        "id": 1,
        "name": "Adriana V.",
        "email": "adrianav@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 01, n. 277"
    },
    "equipment": {
        "id": 1,
        "type": "Compressor de Ar",
        "brand": "Vonder",
        "observations": null
    },
    "statusLog": [{
        "id": 1,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 2,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Testes de vazamentos falharam, iniciando reparos na lataria"
    }, {
        "id": 3,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "FINISHED",
        "description": "Reparo finalizado, Equipamento em funcionamento normal"
    }],
    "problemDescription": "Equipamento nao esta conseguindo realizar a compressao"
}
```

- Obter Ordens de Serviço por Id do Cliente:

      curl localhost:8080/serviceOrders/customer/<customerId>
      
##### Modelo de Resposta (``<customerId>`` = 2):
      
```json
[{
    "id": 2,
    "customer": {
        "id": 2,
        "name": "Ricardo L.",
        "email": "ricardol@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 02, n. 100"
    },
    "equipment": {
        "id": 2,
        "type": "Esmerilhadeira Angular",
        "brand": "Bosh",
        "observations": null
    },
    "statusLog": [{
        "id": 4,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 5,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Testes de circuitos eletronicos falharam"
    }, {
        "id": 6,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "ONHOLD",
        "description": "Aguardando chegada de capacitor para substituicao, estimativa: 3 dias"
    }],
    "problemDescription": "Equipamento nao liga"
}, {
    "id": 3,
    "customer": {
        "id": 2,
        "name": "Ricardo L.",
        "email": "ricardol@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 02, n. 100"
    },
    "equipment": {
        "id": 3,
        "type": "Furadeira",
        "brand": "Black&Decker",
        "observations": null
    },
    "statusLog": [{
        "id": 7,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 8,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Danos irreparáveis na estrutura plastica"
    }, {
        "id": 9,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "ONHOLD",
        "description": "Aguardando chegada de nova carcaca, estimativa: 1 dia"
    }, {
        "id": 10,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RESUMED",
        "description": "Realizando substituicao da carcaca plastica"
    }],
    "problemDescription": "Equipamento com avarias na carcaca"
}]
```

- Obter Ordens de Serviço por estágio do serviço:

      curl localhost:8080/serviceOrders/stage?stageName=<Stage Enum>
      
##### Modelo de Resposta (``<Stage Enum>`` = onhold):
      
```json
[{
    "id": 2,
    "customer": {
        "id": 2,
        "name": "Ricardo L.",
        "email": "ricardol@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 02, n. 100"
    },
    "equipment": {
        "id": 2,
        "type": "Esmerilhadeira Angular",
        "brand": "Bosh",
        "observations": null
    },
    "statusLog": [{
        "id": 4,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 5,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Testes de circuitos eletronicos falharam"
    }, {
        "id": 6,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "ONHOLD",
        "description": "Aguardando chegada de capacitor para substituicao, estimativa: 3 dias"
    }],
    "problemDescription": "Equipamento nao liga"
}]
```

- Obter Ordens de Serviço pendentes:

      curl localhost:8080/serviceOrders/pending
      
##### Modelo de Resposta:
      
```json
[{
    "id": 2,
    "customer": {
        "id": 2,
        "name": "Ricardo L.",
        "email": "ricardol@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 02, n. 100"
    },
    "equipment": {
        "id": 2,
        "type": "Esmerilhadeira Angular",
        "brand": "Bosh",
        "observations": null
    },
    "statusLog": [{
        "id": 4,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 5,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Testes de circuitos eletronicos falharam"
    }, {
        "id": 6,
        "employee": {
            "id": 2,
            "name": "Paulo H. J.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "ONHOLD",
        "description": "Aguardando chegada de capacitor para substituicao, estimativa: 3 dias"
    }],
    "problemDescription": "Equipamento nao liga"
}, {
    "id": 3,
    "customer": {
        "id": 2,
        "name": "Ricardo L.",
        "email": "ricardol@gmail.com",
        "phoneNumber": "+5541999999901",
        "address": "R. 02, n. 100"
    },
    "equipment": {
        "id": 3,
        "type": "Furadeira",
        "brand": "Black&Decker",
        "observations": null
    },
    "statusLog": [{
        "id": 7,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RECEIVED",
        "description": "Equipamento aguardando diagnostico"
    }, {
        "id": 8,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "INITIATED",
        "description": "Danos irreparáveis na estrutura plastica"
    }, {
        "id": 9,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "ONHOLD",
        "description": "Aguardando chegada de nova carcaca, estimativa: 1 dia"
    }, {
        "id": 10,
        "employee": {
            "id": 1,
            "name": "Paulo H.",
            "position": "LEADER"
        },
        "stageDateTime": "2023-01-25T16:29:54.714+00:00",
        "stage": "RESUMED",
        "description": "Realizando substituicao da carcaca plastica"
    }],
    "problemDescription": "Equipamento com avarias na carcaca"
}]
```
	
- Registrar Ordem de Serviço:

      curl -X POST localhost:8080/serviceOrder -H 'Content-Type: application/json' -d '<new_serviceOrder>'
      
##### Retorna objeto criado ``<new_serviceOrder>``
    
- Atualizar Ordens de Serviço:

      curl -X PUT localhost:8080/serviceOrders/<serviceOrderId> -H 'Content-Type: application/json' -d '<updated_serviceOrder>'
      
##### Retorna Ordem de Serviço atualizada ``<updated_serviceOrder>``
	
- Atualizar Status da Ordem de Serviço:

      curl -X PATCH localhost:8080/serviceOrders/<serviceOrderId> -H 'Content-Type: application/json' -d '<Status>'
      
##### Retorna Ordem de Serviço (id = <serviceOrderId>) com statusLog atualizado.
    
- Deletar Orden de Serviço:

      curl -X DELETE localhost:8080/serviceOrders/<id>
</details>

## Tech Stack

[⬆ Topo](#)
:small_blue_diamond:
[Testes](#testes)
:small_blue_diamond:
[Executar](#como-executar)
:small_blue_diamond:
[Modelo Entidades](#modelos-das-entidades)
:small_blue_diamond:
[Como Usar](#como-usar)
:small_blue_diamond:
[Histórico](#histórico)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Licença](#licença)
:small_blue_diamond:
[Autores](#autores)

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
[Testes](#testes)
:small_blue_diamond:
[Executar](#como-executar)
:small_blue_diamond:
[Modelo Entidades](#modelos-das-entidades)
:small_blue_diamond:
[Como Usar](#como-usar)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Licença](#licença)
:small_blue_diamond:
[Autores](#autores)

* 0.1.0
    * Primeira versão estável.

## Roadmap

[⬆ Topo](#)
:small_blue_diamond:
[Testes](#testes)
:small_blue_diamond:
[Executar](#como-executar)
:small_blue_diamond:
[Modelo Entidades](#modelos-das-entidades)
:small_blue_diamond:
[Como Usar](#como-usar)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Histórico](#histórico)
:small_blue_diamond:
[Licença](#licença)
:small_blue_diamond:
[Autores](#autores)

O projeto ainda está em desenvolvimento e as próximas atualizações serão focadas nas seguintes tarefas:

- [ ] Finalizar documentação do código (Javadoc);
- [ ] Implementar usuários para controle de acesso e pemissão de uso de métodos;
- [ ] Implementar autenticação via JWT;
- [ ] Substituir banco de dados H2 in memory por outro banco de dados relacional;
- [ ] Tratamento de exceções nas chamadas aos repositories.

Features:

- [ ] Busca de clientes por outros atributos (e-mail e telefone);
- [ ] Busca de clientes utilizando diferentes atributos simultaneamente;
- [ ] Atualizações do andamento em tempo real enviadas para e-mails cadastrados;
- [ ] Geração de relatórios.

## Licença

[⬆ Topo](#)
:small_blue_diamond:
	[Testes](#testes)
:small_blue_diamond:
[Executar](#como-executar)
:small_blue_diamond:
[Modelo Entidades](#modelos-das-entidades)
:small_blue_diamond:
[Como Usar](#como-usar)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Histórico](#histórico)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Autores](#autores)

Licenciado sob os termos da licença MIT.
Veja [LICENSE](https://github.com/leonardovcl/EquipmentMaintenanceService/blob/main/LICENSE) para mais informações.

## Autores

[⬆ Topo](#)
:small_blue_diamond:
[Testes](#testes)
:small_blue_diamond:
[Executar](#como-executar)
:small_blue_diamond:
[Modelo Entidades](#modelos-das-entidades)
:small_blue_diamond:
[Como Usar](#como-usar)
:small_blue_diamond:
[Tech Stack](#tech-stack)
:small_blue_diamond:
[Histórico](#histórico)
:small_blue_diamond:
[Roadmap](#roadmap)
:small_blue_diamond:
[Licença](#licença)

:heavy_check_mark: 
**Leonardo Viana**

[![Github](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/leonardovcl/ "leonardovcl")
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/leonardovcl/ "LinkedIn")
[![Gmail](https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:leonardovc.lima@gmail.com "leonardovc.lima@gmail.com")

# CrieEvent App

# Descrição

Crie Event, é uma aplicação web que permite a criação e gerenciamento básico de participantes. Com ele, é possível:

- Criar eventos;
- Cadastrar participantes;
- Inserir um número limite de participantes;
- Gerar um slug de acesso ao evento;
- Permitir o Checkin;
- Gerenciar data e hora de cadastro do participante ao evento;
- Gerenciar horário do checkin realizado.

---
# Ambiente

### Requisitos do Sistema
- **Sistema operacional**: Windows 10 ou superior, Linux ou MacOs.
- **Versão do JDK**: 17
- **Versão do NodeJs**: 14 ou superior
- **Versão do npm**: 6 ou superior
- **Versão do Banco de Dados**: PostgreSQL 16

### Dependências

Necessário possuir as seguintes dependências importadas e/ou instaladas:

- `FlyWay`: para executar as Migrations do Banco de Dados.
- `Spring Starter Data JPA`: Para interagir com o banco de dados e criar a persistência.
- `PostgreSQL`: Drive de conexão do banco de dados.
- `Lombok`: Para utilizar anotations.
- `SpringBoot Starter WEB`: Para combinação das dependênicas no Maven
- `SpringBoot DevTools`: Para auxiliar na otimzação e melhoria de performance.

### Gerenciador de dependências: `Maven`

---
# Entidades e relacionamento

## Visão geral

Foram utilizadas três entidades, sendo duas com chaves UUID's, e uma com chave BigInt, visando performance quando necessário, porém, segurança em armazenar as informações.


Ferramentas utilizadas:

<img height="65em" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/postgresql/postgresql-plain-wordmark.svg"/>

<img height="65em" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/insomnia/insomnia-original.svg" />

<img height="65em" src="https://cdn.jsdelivr.net/gh/devicons/devicon@latest/icons/json/json-original.svg" />


### Diagrama:

<div>
    <img src="https://cdn.discordapp.com/attachments/1298064704958107701/1298117312842567722/entitymodel-crieEvent.png?ex=67186575&is=671713f5&hm=78b883f1cc2f0b1c6a9984e167f0aca8335aba6a62dfed01280ae460f6955681&"/>
</div>

#### Indexes: 
    - Só pode existir um Slug por evento e o mesmo não pode repetir-se em outros eventos;
    - O participante só pode se registrar uma vez em cada evento;
    - Cada pariticipante, pode fazer apenas uma checkin.

---

# Documentação da API

## Visão Geral

A API foi criada utilizando o Spring Boot, e permite que o usuário se cadastre, realize o checkIn, além de permitir que o usuário tenha acesso ao evento.

## EdnPoints

### 1. Criar evento:
- **Método**: `POST`
- **Endpoint**: `/evento`
- **Descrição**: Realiza a criação de um novo evento.

### Parâmetros
| Parâmetro | Tipo | Requerido | Descrição                     |
|-----------|------|-----------|-------------------------------|
|nome       |string| sim       | nome do evento                |
|detalhes   |string| sim       | detalhes do evento            |
|slug       |string| sim       | chave gerada automaticamente  |
|qtd maxima |string| sim       | limite de participantes       |

#### Exemplo de requisição
```http
post | http://localhost:8080/evento
```
_**obs**: exemplo utilizando tomcat em localhost_

#### Exemplo em JSON
``` json

    {
	"nome": "Aprenda Lógica na prática",
	"detalhes": "aprenda lógica de programação na prática com C++ e com Kotilin",
	"qtdMaxima": 300
}
```
#### Resposta
``` json
"idEvento": "dfbe5d9b-afd2-4152-8439-edad7575a1cc"
```
### Exibir detalhes do evento:
- **Método**: `GET`
- **Endpoint**: `/evento`
- **Descrição**: Retorna os detalhes do evento

#### Exemplo de requisição
```http - localhost no tomcat
get | http://localhost:8080/evento/dfbe5d9b-afd2-4152-8439-edad7575a1cc
```
_**obs**: exemplo utilizando tomcat em localhost_

#### Exemplo de resposta
``` Json
{
	"evento": {
		"id": "dfbe5d9b-afd2-4152-8439-edad7575a1cc",
		"nome": "Aprenda Lógica na prática",
		"detalhes": "aprenda lógica de programação na prática com C++ e com Kotilin",
		"slug": "aprenda-logica-na-pratica",
		"qtdMaxima": 300,
		"somaParticipantes": 1
	}
}
```

### Consultando participantes por evento:
- **Método**: `GET`
- **Endpoint**: `/evento/participante/id_do_evento`
- **Descrição**: Retorna os participantes do evento

#### Exemplo de requisição
```http
get | http://localhost:8080/evento/participante/dfbe5d9b-afd2-4152-8439-edad7575a1cc
```
#### Exemplo de resposta

``` Json
    {
	"participantes": [
		{
			"id": "fb84053oi9e-edjenman-etysoens",
			"nome": "José Fernando dos Santos",
			"email": "josefds@gmail.com",
			"criadoEm": "2024-10-17T18:28:53.536208",
			"checkedEm": null
		}
	]
}
```

### Cadastrar Participantes:
- **Método**: `POST`
- **Endpoint**: `/evento/evento_id/participante`
- **Descrição**: Realiza o cadastro do participante para o evento.

### Parâmetros
| Parâmetro  | Tipo | Requerido | Descrição                     |
|------------|------|-----------|-------------------------------|
| nome       |string| sim       | nome do evento                |
| email      |string| sim       | detalhes do evento            |

#### Exemplo de requisição
```http
post | http://localhost:8080/evento/{Evento_id}/participante
```
_**obs**: exemplo utilizando tomcat em localhost_

```Json
{
  "nome": "segunto teste",
  "email": "teste88@gmail.com"
}
```

#### Exemplo de resposta

```Json
{
    "participanteid": "8b850e0c-f6ab-4393-9d8f-db6390eab08d"
}
```

### Eximbindo Crachá dos participantes (dados):
- **Método**: `GET`
- **Endpoint**: `participante/id_do_participante`
- **Descrição**: Retorna os dados/crachá do participante

#### Exemplo de requisição
```http
get | http://localhost:8080/participante/ee413dda-3594-467c-8255-2ffa3580645e/cracha
```
_**obs**: exemplo utilizando tomcat em localhost_

#### Exemplo de resposta

```Json
{
	"cracha": {
		"nome": "Teste teste dos teste",
		"email": "teste58@gmail.com",
		"checkInUrl": "http://localhost:8080/participante/ee413dda-3594-467c-8255-2ffa3580645e/check-in",
		"eventoId": "d2e14f35-78c1-4ebe-b7c2-5dfcacb03833"
	}
}
```

### Fazendo CheckIn:
- **Método**: `POST`
- **Endpoint**: `\participante\participante_id\check-in`
- **Descrição**: Checkin do evento em que o cliente pode realizar no evendo

#### Exemplo de requisição
```
post | http://localhost:8080/participante/{participante_id/check-in
```
#### Obtendo a resposta:
    Para essa requisição, o retorno é visíel, refazendo a requisição de consulta dos participantes por evendo:

#### Exemplo de resposta:
``` Json
{
	"participantes": [
		{
			"id": "ee413dda-3594-467c-8255-2ffa3580645e",
			"nome": "Teste teste dos teste",
			"email": "teste58@gmail.com",
			"criadoEm": "2024-10-19T21:12:46.397444",
			"checkedEm": "2024-10-19T21:14:11.989463"
		},
		{
			"id": "8b850e0c-f6ab-4393-9d8f-db6390eab08d",
			"nome": "segunto teste",
			"email": "teste88@gmail.com",
			"criadoEm": "2024-10-19T21:13:06.856962",
			"checkedEm": null
		}
	]
}
```
# Contribuindo para CrieEvent

Obrigado por considerar contribuir para o nosso projeto! Aqui estão algumas diretrizes para garantir uma contribuição suave.

## Como Contribuir

1. **Fork o Repositório**  
   Clique no botão "Fork" no canto superior direito do repositório para criar uma cópia pessoal.

2. **Clone seu Fork**  
   Use o comando abaixo para clonar seu fork para sua máquina local:
   ```bash
   git clone https://github.com/seu-usuario/crie-event.git

3. **Crie uma Nova Branch**
   
    Lembre-se de sempre criar uma nova branch para alteração
    ```bash
   git checkout -b nome-da-sua-branch

4. **Commit suas Alterações**
   
    Faça commit das suas alterações:
    ```bash
   git add .
    git commit -m "Descrição das suas alterações"
   ```
5. **Envie suas alterações para o Fork**
    
    Envie suas alterações para o seu fork
    ```bash
   git push origin nome-da-sua-branch
    ```

## Diretrizes 

- **Comente o código:** Adicione comentários explicando o que foi alterado e partes complexas.
- **Siga o estilo do código**: Mantenha o mesmo padrão e boas práticas de desenvolvimento em Java.
- **Teste**: Não dê o deploy sem testar previamente.
- **Documente**: Caso vá realizar alguma alteração na API/Endpoints ou Entidades, lembre-se de alterar na documentação acima.
# Meteo Solution API

API REST em Java e Spring Boot para registrar consultas de risco de alagamento por latitude e longitude.

O Meteo Solution é uma API REST desenvolvida em Java com Spring Boot que tem como objetivo centralizar, validar e gerenciar informações geográficas e ambientais que poderão ser utilizadas por sistemas inteligentes de análise e tomada de decisão.

A proposta do projeto é fornecer uma base de dados estruturada e confiável, permitindo o cadastro e gerenciamento de países, estados, cidades, bairros, regiões monitoradas e consultas realizadas. Dessa forma, todas as informações passam por processos de validação e persistência antes de serem disponibilizadas para consumo por outros sistemas.

Como o objetivo da solução é apoiar aplicações baseadas em inteligência artificial, a qualidade dos dados armazenados é um fator essencial. Por isso, a API foi desenvolvida para garantir consistência, organização e integridade das informações, funcionando como uma camada intermediária entre o banco de dados e os serviços que utilizarão esses dados para análises futuras.

Para atender aos requisitos da disciplina, a aplicação foi construída seguindo boas práticas de desenvolvimento, arquitetura em camadas e princípios REST, utilizando recursos como Spring Data JPA, DTOs, Java Records, Spring Validation, HATEOAS, documentação com Swagger/OpenAPI e modelagem avançada com herança, chave composta e objetos embarcados.

## Tecnologias

- Java 17
- Spring Boot 3.5.14
- Spring Web
- Spring Data JPA
- Bean Validation
- Oracle Driver
- SpringDoc OpenAPI / Swagger
- HATEOAS

## Fluxo principal

1. Usuario envia prompt, latitude e longitude.
2. API valida prompt vazio, tamanho maximo, campos nulos e coordenadas invalidas.
3. API busca uma regiao monitorada proxima das coordenadas.
4. API gera uma resposta mockada da IA.
5. API salva o historico em `CONSULTA_RISCO`.
6. API devolve a consulta criada com links HATEOAS.

## Endpoints

- `GET /usuarios` lista usuarios com paginacao.
- `GET /usuarios/{id}` busca usuario por id.
- `POST /usuarios` cria usuario.
- `PUT /usuarios/{id}` atualiza usuario.
- `DELETE /usuarios/{id}` remove usuario.
- `GET /regioes` lista regioes monitoradas.
- `GET /regioes/{id}` busca regiao monitorada.
- `POST /consultas` cria consulta de risco.
- `GET /consultas/{usuarioId}/{protocolo}` busca uma consulta.
- `GET /consultas/usuarios/{usuarioId}/historico` lista historico por usuario.
- `GET /consultas/regioes/{regiaoId}/historico` lista historico por regiao.

## Exemplo de consulta

```json
{
  "usuarioId": 1,
  "prompt": "Existe risco de alagamento nessa regiao?",
  "latitude": -23.55052,
  "longitude": -46.633308
}
```

## Swagger

Com a aplicacao rodando localmente:

`http://localhost:8080/swagger-ui.html`


swagger no render:

`https://meteo-final-1.onrender.com/swagger-ui/index.html`

## Render 
link do deploy

`https://meteo-final-1.onrender.com`

alguns exemplos:

`https://meteo-final-1.onrender.com/usuarios`

`https://meteo-final-1.onrender.com/regioes`

## link video de apresentação 

`https://youtu.be/Swnc4SbstLE`

## Pitch



## Banco de dados

O projeto usa Oracle. Configure as variaveis:

- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`

## RMS E NOMES

Ana Carolina Pereira Fontes | 562145

João Victor Nascimento Adão | 563409

Johnny Dias Mathias Junior | 566516

Luisa Ganasevici de Abreu | 563403

Matheus Moya de Oliveira | 562822

# Bitzen Tecnologia

Este projeto foi desenvolvido em virtude de um processo seletivo, em que foi construído uma API REST para um gerenciamento de cadastro de músicas.

## Pré-requisitos
Antes de executar a aplicação, certifique-se de ter instalado em seu ambiente:

- Java na versão 17 ou superior
- Maven

## Instruções de execução da aplicação:
1. Baixe o projeto: 'git clone https://github.com/ryanfelipe87/App-Musica.git'
2. Abra o terminal e navegue até a pasta do projeto.
3. Execute o seguinte comando no terminal: 'mvn clean package'
4. Inicialize a aplicação com o comando: 'java -jar target\app-musica-0.0.1-SNAPSHOT.jar
   '
5. Abra o Swagger no navegador através do link: 'http://localhost:8080/api/swagger-ui/index.html#/'

Observações:
- A porta em que a aplicação está rodando é a '8080'. Caso necessário, ajuste a porta ao utilizar o Swagger no navegador.
- Este projeto pode ser executado em qualquer IDE de preferência para desenvolvimento de API com Spring.

## Tecnologias utilizadas
- Java 17
- Spring Boot na versão 3.2.2
- JUnit 5 e Mockito para testes unitários
- Insomnia para testar a API REST
- Git para versionamento de código e GitHub para hospedagem do projeto
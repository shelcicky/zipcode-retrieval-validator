# Zipcode Retrieval Validator

Este projeto tem como objetivo ser um objeto de estudo/exemplo no que diz respeito à testes unitários e/ou integrados.
O mesmo consome a API do site [ViaCEP](https://viacep.com.br/).

### Cenário de funcionamento previsto:

De forma resumida, ao ser imputado um cep no controller, a app validará se o mesmo é válido;  
&nbsp;&nbsp;&nbsp;&nbsp;Caso não seja válido, retornará o status code "400" no controller;  
&nbsp;&nbsp;&nbsp;&nbsp;Caso seja válido, ocorrerá uma busca no banco de dados;  

Se existir um registro no banco de dados para o cep em questão, será retornado um json para o controller;

Em caso negativo, será realizado uma busca na api do **ViaCEP**.  
&nbsp;&nbsp;&nbsp;&nbsp;Se o cep for encontrado, o dado será persistido no banco e, posteriormente, retornado para o controller;  
&nbsp;&nbsp;&nbsp;&nbsp;Se o cep não for encontrado, será retornado o status code "404" no controller;

## 🚀 Get Started

### 📋 Pré-requisitos

-Java 21+;  
-MySQL 9.0.0;

Para validar se o Java está instalado e devidamente configurado no ambiente, execute o seguinte comando: 
```sh
java -version
```

O resultado deve ser algo semelhante com o texto abaixo:
```sh
openjdk version "21.0.2" 2024-01-16
OpenJDK Runtime Environment (build 21.0.2+13-58)
OpenJDK 64-Bit Server VM (build 21.0.2+13-58, mixed mode, sharing)
``` 

### 🔧 Instalação
Faça o clone do repositório
```sh
git clone git@github.com:shelcicky/zipcode-retrieval-validator.git
```

Acesse o diretório do projeto recém clonado: 
```sh
cd zipcode-retrieval-validator
```

Instale as dependências do projeto:
```sh
./gradlew clean build
```

Para ambientes __Windows__, pode ser necessário remover o `./` da instrução acima.

## ⚙️ Executando o projeto
### - Execute o seguinte comando no diretório raiz do projeto, substituindo o valor de `$(port)` pela porta http que deseja "subir" a aplicação.   
Exemplo:
```sh
./gradlew build bootRun -Dserver.port=$(port)
./gradlew build bootRun -Dserver.port=8080
```
É necessário que uma instância do MySQL esteva respondendo em `localhost:3306`, com as seguintes credenciais:  
`username: root`  
`password: root`

Caso não seja possível, atualize as informações no arquivo: `application.yml` presente no seguinte path: `src/main/resources/`;


## 🛠️ Construído com
* [Java](https://www.oracle.com/br/java/technologies/downloads/) - Linguagem de programação;
* [Spring](https://spring.io/) - Framework que simplifica o desenvolvimento de aplicações Java;
* [MySQL](https://www.mysql.com/) - Sistema de gerenciamento de banco de dados relacional de código aberto.

## ✒️ Autor
* **Shelcicky Wilkerson** - [LinkedIn](https://www.linkedin.com/in/shelcicky/) / [Github](https://github.com/shelcicky)
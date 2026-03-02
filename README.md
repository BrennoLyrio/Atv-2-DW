# AutoManager — Atividade II: RESTful + HATEOAS

Evolução do sistema AutoManager com implementação do **Richardson Maturity Model nível 3 (HATEOAS)**, adicionando links de navegação hipermídia em todas as respostas da API.

## 📋 Sobre o Projeto

Segunda atividade da série AutoManager (disciplina de Desenvolvimento Web — FATEC São José dos Campos). Com base na Atividade I, foi implementado o padrão **HATEOAS (Hypermedia as the Engine of Application State)**, tornando a API auto-descritiva através de links embutidos nas respostas JSON.

## 🛠 Tecnologias

- Java 17+
- Spring Boot
- Spring HATEOAS
- Spring Data JPA
- Spring Web (REST)
- Maven
- MySQL / H2

## 📦 Estrutura do Projeto

```
src/main/java/com/autobots/automanager/
├── controles/
│   ├── ClienteControle.java
│   ├── DocumentoControle.java
│   ├── EnderecoControle.java
│   └── TelefoneControle.java
├── entidades/
├── modelo/
│   ├── AdicionadorLink.java          # Interface base para HATEOAS
│   ├── AdicionadorLinkCliente.java
│   ├── AdicionadorLinkDocumento.java
│   ├── AdicionadorLinkEndereco.java
│   ├── AdicionadorLinkTelefone.java
│   ├── ClienteAtualizador.java
│   ├── ClienteSelecionador.java
│   ├── DocumentoAtualizador.java
│   ├── EnderecoAtualizador.java
│   ├── TelefoneAtualizador.java
│   └── StringVerificadorNulo.java
└── repositorios/
```

## 🎯 Richardson Maturity Model

| Nível | Descrição | Implementado |
|-------|-----------|-------------|
| 0 | POX — Plain Old XML/JSON | ✅ |
| 1 | Recursos individuais por URI | ✅ |
| 2 | Verbos HTTP corretos (GET, POST, PUT, DELETE) | ✅ |
| **3** | **HATEOAS — links hipermídia nas respostas** | ✅ |

## 🔗 Exemplo de Resposta com HATEOAS

Cada entidade retorna links de navegação na resposta JSON:

```json
{
  "id": 1,
  "nome": "João Silva",
  "_links": {
    "self": { "href": "http://localhost:8080/clientes/1" },
    "clientes": { "href": "http://localhost:8080/clientes" },
    "documentos": { "href": "http://localhost:8080/documentos/cliente/1" },
    "telefones": { "href": "http://localhost:8080/telefones/cliente/1" },
    "endereco": { "href": "http://localhost:8080/enderecos/cliente/1" }
  }
}
```

## 🔗 Endpoints

### Cliente
| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/clientes` | Lista clientes com links HATEOAS |
| GET | `/clientes/{id}` | Busca cliente com links |
| POST | `/clientes` | Cadastra cliente |
| PUT | `/clientes/{id}` | Atualiza cliente |
| DELETE | `/clientes/{id}` | Remove cliente |

> O mesmo padrão se aplica para `Documento`, `Endereço` e `Telefone`.

## ▶️ Como Executar

```bash
git clone https://github.com/BrennoLyrio/Atv-2-DW.git
cd Atv-2-DW/automanager
./mvnw spring-boot:run
```

API disponível em `http://localhost:8080`

## 👤 Autor

**Brenno Rosa Lyrio de Oliveira**  
[GitHub](https://github.com/BrennoLyrio) · [LinkedIn](https://linkedin.com/in/brennolyrio)  
FATEC — Desenvolvimento de Software Multiplataforma · 2024–2026

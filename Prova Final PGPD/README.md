## Jantar dos Filósofos Distribuído

Esta implementação do Jantar dos Filósofos utiliza um modelo distribuído, onde:

- Os Filósofos são implementados como clientes independentes.
- Um servidor centralizado gerencia os recursos compartilhados (garfos).
- A comunicação segue um protocolo customizado definido no arquivo `PROTOCOL.md`.

---

## Como executar o projeto

### Requisitos

- Java JDK 8 ou superior
- Ambiente configurado com variáveis de ambiente para o Java (JAVA\_HOME).

### Estrutura do diretório

- `src/`: Contém os arquivos de código-fonte:
  - `ResourceServer.java`: Implementação do servidor.
  - `PhilosopherClient.java`: Implementação do cliente.
- `PROTOCOL.md`: Documento descrevendo o protocolo de comunicação.
- `ARCHITECTURE.drawio`: Diagrama de arquitetura.
- `README.md`: Este documento.

### Passos para execução

1. Compile os arquivos.

```bash
javac src/*.java
```

2. Inicie o servidor.

```bash
java src.ResourceServer
```

3. Em outra janela/terminal, inicie os clientes (filósofos). Cada cliente é executado separadamente.

```bash
java src.PhilosopherClient
```

Repita o passo 3 para criar até 5 clientes.

---

## Funcionalidades

### Cliente (Filósofo)

- Solicita garfos ao servidor para comer.
- Notifica o servidor quando está pensando ou comendo.
- Libera os garfos após terminar de comer.
- Tenta reconectar automaticamente ao perder a conexão.

### Servidor

- Gerencia até 5 filósofos simultaneamente.
- Aloca e libera garfos de forma segura.
- Registra atividades de cada filósofo, incluindo:
  - Quantidade de vezes que pensou.
  - Quantidade de refeições realizadas.

---

## Arquitetura

O sistema é composto por dois componentes principais:

1. **Servidor Centralizado de Recursos**

   - Utiliza `ServerSocket` para aceitar conexões de clientes.
   - Controla o acesso aos recursos compartilhados (garfos).
   - Mantém o estado de cada filósofo conectado.

2. **Clientes Filósofos**

   - Cada cliente é uma aplicação independente que se conecta ao servidor via `Socket`.
   - Implementa a lógica do comportamento dos filósofos (pensar, solicitar garfos, comer e liberar garfos).

---

## Fluxo de Execução

1. O cliente se conecta ao servidor e envia um comando `HELLO`.
2. O servidor atribui um ID único ao cliente e responde com `HI <ID>`.
3. O cliente informa seu nome e inicia seu ciclo de:
   - Pensar (tempo aleatório seguindo distribuição normal).
   - Solicitar garfos.
   - Comer por um tempo fixo.
   - Liberar os garfos.
4. O servidor gerencia os recursos e registra as atividades.

---

## Autor

- Este projeto foi desenvolvido para ilustrar uma solução distribuída para o Problema do Jantar dos Filósofos.
- Qualquer dúvida ou sugestão pode ser encaminhada ao responsável pela manutenção.

---

## Licença

Este projeto é de uso acadêmico e não possui licença para utilização comercial.


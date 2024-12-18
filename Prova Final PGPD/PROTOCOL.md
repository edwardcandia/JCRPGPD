# PROTOCOL.md

## Protocolo de Comunicação

O protocolo utilizado entre os Filósofos (Clientes) e o Servidor de Recursos segue os seguintes comandos e respostas:

### Conexão Inicial
- **Cliente envia:**
  - `HELLO`
- **Servidor responde:**
  - `HI <ID>` - Atribui um ID único ao filósofo.

### Identificação do Filósofo
- **Cliente envia:**
  - `LOGIN: <NOME>` - Envia o nome do filósofo para o servidor.
- **Servidor responde:**
  - `ACK` - Confirma o recebimento do nome.

### Ciclo de Pensar e Comer

#### Pensar
- **Cliente envia:**
  - `THINK` - Notifica o servidor que o filósofo está pensando.
- **Servidor responde:**
  - `ACK` - Confirma o registro do pensamento.

#### Solicitação de Garfos
- **Cliente envia:**
  - `REQUEST_FORKS` - Solicita os garfos ao servidor.
- **Servidor responde:**
  - `FORKS_GRANTED` - Concede os garfos ao filósofo.
  - `FORKS_DENIED` - Informa que os garfos não estão disponíveis.

#### Comer
- **Cliente envia:**
  - `EAT` - Notifica o servidor que o filósofo está comendo.
- **Servidor responde:**
  - `ACK` - Confirma o registro da refeição.

#### Liberação de Garfos
- **Cliente envia:**
  - `RELEASE_FORKS` - Libera os garfos após terminar de comer.
- **Servidor responde:**
  - `ACK` - Confirma a liberação dos garfos.

### Erros e Comandos Inválidos
- **Servidor responde:**
  - `UNKNOWN_COMMAND` - Indica que o comando enviado não é reconhecido.

### Desconexão
- Caso a conexão seja perdida, o filósofo pode reconectar enviando novamente `HELLO` e o servidor irá reatribuir o mesmo ID, caso o filósofo se identifique corretamente.


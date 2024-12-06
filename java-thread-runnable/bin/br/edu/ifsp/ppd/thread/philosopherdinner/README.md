
## Estrutura do Código

Explicações da classes abaixo:

### 1. `Fork`
- Representa um garfo na mesa.
- Atributo: `id` (identificador do garfo).
- Método: `getName()` para facilitar a identificação do garfo.

### 2. `Philosopher`
- Representa um filósofo, executado em uma thread separada.
- Atributos:
  - `id`: identificador único.
  - `leftFork` e `rightFork`: garfos à esquerda e direita do filósofo.
  - `numberOfThoughts` e `numberOfMeals`: contadores para monitorar as ações do filósofo.
  - `state`: estado atual do filósofo ("Thinking", "Eating", "DONE", etc.).
- Métodos principais:
  - `think()`: simula o filósofo pensando.
  - `eat()`: simula o filósofo comendo.
  - Estratégia de sincronização para evitar **deadlocks**: os filósofos pares pegam os garfos na ordem inversa (primeiro o direito, depois o esquerdo).

### 3. `Main`
- Classe principal que inicia a aplicação.
- Configura os filósofos e garfos e monitora os estados das threads.

## Estratégia de Prevenção de Deadlocks

Para evitar deadlocks (situação onde todos os filósofos ficam esperando indefinidamente por garfos):
- Os filósofos pares começam pegando o garfo da direita, e os ímpares, o garfo da esquerda. Isso quebra a simetria no momento de adquirir os garfos.


## Comportamento Esperado

- Cada filósofo alternará entre pensar e comer, enquanto os estados são monitorados no terminal.
- O programa termina quando todos os filósofos completam suas tarefas (10 pensamentos).
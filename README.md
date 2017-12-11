# OS-Simulator

Descrição do problema [aqui](https://github.com/ingoguilherme/OS-Simulator/blob/master/example/TP2-SimulaSO.pdf).

## Ambiente usado para implementação e testes

* Sistema Operacional Windows 10
* Eclipse IDE Oxygen Release (4.7.0)
* Eclipse IDE Neon.1a Release (4.6.1)
* Java SE Development Kit 8 Update 101 (64-bit) (8.0.1010.13)
* Java 8 Update 131 (64-bit) (8.0.1310.11)

## Como executar o programa

Abra o Eclipse e adicione o projeto.

1. New
2. Java Project
3. Desmaque o checkbox `Use default location`
4. Browse
5. Selecione a pasta do projeto
6. Finish

Abra o arquivo `Main.java` e execute-o.

## Componentes Implementados

1. Processador (Processor)
2. Disco (Disk)
2. Teclado (Keyboard)

### Processador (Processor)

O `Processador (Processor)` sempre que lê/executa uma instrução, ele mostra a mensagem `Executei uma instrução` e em seguida mostra a instrução lida na próxima linha (linha de baixo). Ele possui 3 tipos de instruções (até o momento):

1. Instrução de componente (Component Instruction)
2. Valor inteiro (Integer)
3. Vetor de instruções (ArrayList)

> #### Component Instruction
> Ao executar uma instrução `Component Instruction`, o processador pega e identifica para qual componente deve mandar e envia para o mesmo. Ele possui apenas uma instrução (até o momento):
> 1. Disco (Disk)

> #### Integer
> Ao executar uma instrução `Integer` apenas mostra a instrução junto ao seu valor na tela.

> #### ArrayList
> Ao executar uma instrução `ArrayList` ele entra diretamente no vetor e ja executa a primeira instrução do mesmo e as demais sequencialmente. Após terminar as instruções do vetor, volta ao fluxo normal.

### Disco (Disk)

Como o disco possui tempo de execução maior que o do processador, quando ele é chamado pelo processador, o mesmo da um `up()` no semáforo do disco para manter esse controle, assim o disco executa todas as instruções passadas pelo processador no tempo dele, cada vez que executa uma, dando um `down()`.

### Teclado (Keyboard)

Como o teclado é praticamente independente, executa e mostra sempre que o usuário inserir algum comando, ele não é executado pelo `Processador (Processor)`, apenas mostra na tela os dados inseridos pelo processador.

## Exemplo de uso

Para desligar o `OS-Simulator` depois de ler todas as intruções do processaor, troque a constante `turnOff` para `true`, ou caso não deseja encerar as threads ao acabar as instruções e sim parar manualmente a execução, deixe em `false`.

```java
public static final boolean turnOff = true;
```

> Observação: O teclado sempre espera alguma leitura, já que quando inicia ele já fica esperando essa leitura. caso o `turnOff` seja `true`, é necessário inserir alguma entrada no teclado após o processador ter encerrado.

```java
//Sempre inicializa para salvar o tempo inicial da execução do programar (as mensagens mostradas no console utilizam funções estáticas dessa classe, e requerem a inicialização para mostrar os dados corretos)
new Time();
		
//Gera instruções aleatórias na memória (nessa exemplo está gerando entre 1 e 5 instruções aleatórias)
LinkedList<Data> memory = Random.generateRandomDataList(Random.generateRandomInt(1, 5));

//Cria semáforo do disco (disk)
Semaphore diskSemaphore = new Semaphore(0);

/*
* Parâmetros do construtor do disco (disck):
* (tempo que disco leva para executar uma instrução, semáforo do disco)
*/
d = new Disk(2000, diskSemaphore);

/*
* Parâmetros do construtor do processador (processor):
* (tempo que processador leva para executar uma instrução, vetor com as instruções, semáforo do disco)
*/
p = new Processor(1000, memory, diskSemaphore);

/*
* Construtor do teclado (keyboard):
*/
k = new Keyboard();


//Inicia todos os componentes criados anteriormente
p.start();
d.start();
k.start();
```

## Exemplo do formato de saída:

A saída é composta por 3 colunas cada linha:

Os sleeps dentro de cada componente são executadas por primeiro para simular que a tarefa está sendo realizada, e logo após
imprime a mensagem no console.

1. A primeira coluna é o tempo em que foi executado em milisegundos, começando em 0 desde a execução do programa.
2. A segunda coluna é o componente responsavel por essa mostrada na linha.
3. E a terceira é a mensagem ou dado mostrado/executado pelo componente.

### Exemplo de saída no console

```javascript
7 | PROCESSOR | Inicia Processador!
7 | KEYBOARD  | Inicia Teclado!
7 | DISK      | Inicia Disco!

1008 | PROCESSOR | Executei uma instrução.
1008 | PROCESSOR | Data [Type: Integer, Value: 19]

2008 | PROCESSOR | Executei uma instrução.
2008 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

3008 | PROCESSOR | Executei uma instrução.
3008 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

4008 | DISK      | Dei uma volta.

4008 | PROCESSOR | Executei uma instrução.
4008 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

5009 | PROCESSOR | Executei uma instrução.
5009 | PROCESSOR | Data [Type: Integer, Value: 13]

6008 | DISK      | Dei uma volta.

6009 | PROCESSOR | Executei uma instrução.
6009 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

7009 | PROCESSOR | Executei uma instrução.
7009 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

8008 | DISK      | Dei uma volta.

8009 | PROCESSOR | Executei uma instrução.
8009 | PROCESSOR | Data [Type: Integer, Value: 7]

9010 | PROCESSOR | Executei uma instrução.
9010 | PROCESSOR | Data [Type: Integer, Value: 4]

10008 | DISK      | Dei uma volta.

10010 | PROCESSOR | Executei uma instrução.
10010 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

11010 | PROCESSOR | Executei uma instrução.
11010 | PROCESSOR | Data [Type: Integer, Value: 15]

12009 | DISK      | Dei uma volta.

12010 | PROCESSOR | Executei uma instrução.
12010 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

13010 | PROCESSOR | Executei uma instrução.
13010 | PROCESSOR | Data [Type: Integer, Value: 8]

14009 | DISK      | Dei uma volta.

14011 | PROCESSOR | Executei uma instrução.
14011 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

15011 | PROCESSOR | Executei uma instrução.
15011 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

16009 | DISK      | Dei uma volta.

16011 | PROCESSOR | Executei uma instrução.
16011 | PROCESSOR | Data [Type: Integer, Value: 20]

17011 | PROCESSOR | Executei uma instrução.
17011 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

18010 | DISK      | Dei uma volta.

18011 | PROCESSOR | Executei uma instrução.
18011 | PROCESSOR | Data [Type: Component Instruction, Value: Disk]

19011 | PROCESSOR | Executei uma instrução.
19011 | PROCESSOR | Data [Type: Integer, Value: 14]

20010 | DISK      | Dei uma volta.

20012 | PROCESSOR | Não há mais instruções

22010 | DISK      | Dei uma volta.

32997 | KEYBOARD  | Usuário digitou algo: 'kk'.

```

# ⚔️ Vale-Cinzento: A Jornada do Herói

> **RPG de Texto em Java** — Uma aventura épica desenvolvida com Programação Orientada a Objetos

---

## 📖 A Lenda de Vale-Cinzento

Bem-vindo a **Vale-Cinzento**, viajante. Estas terras outrora prósperas agora respiram sob a sombra de **Umbreterna**, a praga que se alastra desde o **Castelo do Véu Quebrado**. Dizem que **Balduran, o Imperador Esqueleto**, governa os mortos-vivos que guardam o portão, impedindo qualquer esperança de libertação.

Você é um herói chamado pelo destino para cruzar as fronteiras do desconhecido. Armado apenas com coragem e escolhas difíceis, sua jornada atravessará:

- **🌙 A Encruzilhada do Suspiro** — onde a névoa esconde perigos e a lua minguante observa seus primeiros passos
- **🔥 O Campo de Magma Adormecido** — um mar de brasas e fendas incandescentes vigiado pelo temível Brukkar
- **🏰 O Portão do Véu Quebrado** — a fortaleza final onde runas antigas decidem quem merece passar

Mas há sussurros de um **caminho secreto**... para aqueles que dominam as artes arcanas e nunca recuaram diante do perigo.

---

## 🎮 Como Jogar

### **Requisitos**
- **JDK 11+** instalado e configurado (`JAVA_HOME`)
- **Apache Maven** instalado (`mvn` disponível no PATH)
- `pom.xml` presente na raiz do projeto

### **Execução Rápida**

**1. Compilar o projeto:**
```bash
mvn clean compile
```

**2. Executar via Maven:**
```bash
mvn exec:java
```

**3. Ou empacotar e executar o JAR:**
```bash
mvn package
java -jar target/projetorpg-1.0-SNAPSHOT.jar
```

### **Durante o Jogo**

O jogo é totalmente interativo via console. A cada etapa você terá:

**Escolhas narrativas** → Cada decisão afeta sua jornada (dano, recompensas, encontros)  
**Combates táticos** → Ataque, use itens ou tente fugir (50% de chance)  
**Gerenciamento de inventário** → Colete troféus e use elixires estrategicamente  
**Sistema de dados** → Ataques são (Ataque + d6) vs Defesa do oponente

---

## 🗡️ Classes de Personagem

### **Guerreiro** — *Aço e Disciplina*
- **HP:** 150 | **Ataque:** 20 | **Defesa:** 15
- **Item inicial:** Elixir de Vigor (+10 Defesa)
- **Estilo:** Tanque resistente, ideal para combate direto

### **Arcanista** — *Segredos Antigos*
- **HP:** 100 | **Ataque:** 25 | **Defesa:** 10
- **Item inicial:** Elixir do Sábio (+8 Ataque)
- **Estilo:** Alto dano mágico, acesso ao **final secreto**
- ⚠️ **Condição especial:** Se nunca lutar até a etapa final, desbloqueia **Mórvek, o Arquiteto do Vazio**

### **Arqueiro** — *Olhos Atentos, Passos Leves*
- **HP:** 120 | **Ataque:** 22 | **Defesa:** 12
- **Item inicial:** Elixir Olho de Águia (+7 Ataque)
- **Estilo:** Equilíbrio entre dano e sobrevivência

---

## 🌍 As Três Etapas da Jornada

### **ETAPA 1: A Encruzilhada do Suspiro**

*"A lua minguante repousa sobre o Caminho do Suspiro, onde a estrada se divide..."*

**Escolhas:**
- **Vereda da Névoa** → Mais segura, mas galhos traem passos (50% de 10 dano)
  - **Inimigo:** Sapo Gigante (HP: 50, Atk: 10, Def: 5)
  - **Drop:** Língua de Sapo Gigante
  
- **Senda das Ruínas** → Arriscada, símbolos antigos (50% de perder 1 item)
  - **Inimigo:** Pantera de Duas Cabeças (HP: 70, Atk: 12, Def: 3)
  - **Drop:** Presa Gêmea Obsidiana

---

### **ETAPA 2: O Campo de Magma Adormecido**

*"O chão treme — um mar rubro de rochas incandescentes onde o fogo ainda respira..."*

**Escolhas:**
- **Saltar entre rochas** → Ousado! (50% de +7 dano no próximo combate OU 15 de dano)
  
- **Contornar pelas fendas** → Cauteloso
  - **Recompensa:** Erva de Cinza (cura 25 HP)

**Inimigo garantido:**  
**Brukkar, o Portador da Corrente** (HP: 80, Atk: 15, Def: 10)  
*"Pedágio: sangue ou ossos."*  
**Drop:** Corrente Chamuscada

---

### **ETAPA FINAL: O Portão do Véu Quebrado**

*"As torres perfuram a neblina. Runas observam. O destino aguarda..."*

**Escolhas:**
- **Arrombar o portão** → Repulso arcano (–20 HP)
- **Esgotos secretos** → Acha um Frasco de Fôlego (+5 Ataque)

**Desfecho A — Rota Comum:**  
**Balduran, o Imperador Esqueleto** (HP: 150, Atk: 25, Def: 15)  
*"Nenhum passo profanará meu domínio."*  
**Drop:** Coroa Estilhaçada  
**Vitória:** *"Vale-Cinzento é libertada do jugo de Umbreterna. Seu nome ecoará nas canções."*

**Desfecho B — Rota Secreta (Arcanista + 0 fugas):**  
**Mórvek, o Arquiteto do Vazio** (HP: 200, Atk: 22, Def: 12)  
*"Discípulo da centelha, prove que merece atravessar."*  
**Drop:** Fragmento de Obsidiana  
**Vitória:** *"A máscara racha. O caminho adiante se revela."*

---

## 🎲 Sistema de Combate

### **Mecânicas de Turno**

```
Força de Ataque = Ataque Base + 1d6 (dado de 6 lados)
Dano = Força de Ataque - Defesa do Oponente
```

**Ações disponíveis:**
1. **Atacar** → Engaja em combate corpo-a-corpo
2. **Usar Item** → Consome turno, mas pode curar ou aumentar atributos
3. **Fugir** → 50% de chance (impossível contra chefes)

### **Regras Especiais**
- Fugir com **sucesso** mantém você elegível ao final secreto (Arcanista)
- Fugir **falhando** conta como engajamento (inimigo ataca)
- Chefes (Balduran e Mórvek) **não permitem fuga**
- Vencer combates concede **level up** (HP +20, Atk +5, Def +3)

---

## � Arquitetura do Código (POO)

### **Hierarquia de Classes**

```
Personagem (abstrata, Cloneable)
├── Guerreiro
├── Mago (Arcanista)
├── Arqueiro
└── Inimigo
```

### **Componentes Principais**

**`Personagem.java`**
- Atributos: `nome`, `pontosVida`, `ataque`, `defesa`, `nivel`, `Inventario`
- Métodos: `estaVivo()`, `subirNivel()`, `clone()`, `equals()`, `hashCode()`

**`Item.java`**
- Tipos de efeito: `CURA`, `AUMENTO_ATAQUE`, `AUMENTO_DEFESA`
- Implementa `Comparable<Item>` para ordenação
- Stackable: itens iguais somam quantidade

**`Inventario.java`**
- Gerencia coleção de `Item`
- Métodos: `adicionarItem()`, `removerItemAleatorio()`, `usarItem()`, `clone()`
- Deep copy para evitar referências compartilhadas

**`Jogo.java`**
- Motor principal do jogo
- Gerencia fluxo narrativo, combates e sistema de save points
- Controla condições para final secreto

---

## 🏆 Requisitos Técnicos Implementados

- ✅ **toString(), equals(), hashCode()** em todas as classes principais
- ✅ **clone()** e construtor de cópia para deep cloning
- ✅ **Comparable<Item>** para ordenação de inventário
- ✅ **Encapsulamento** com getters/setters apropriados
- ✅ **Polimorfismo** através de herança de Personagem
- ✅ **Tratamento de exceções** em operações críticas

---

## 🧪 Testando o Projeto

### **Cenários de Teste Recomendados**

1. **Combate Básico**
   - Criar Guerreiro, enfrentar Sapo Gigante
   - Verificar cálculo de dano e level up

2. **Sistema de Inventário**
   - Usar Elixir de Cura (verificar HP)
   - Adicionar item duplicado (verificar stack)
   - Remover item aleatório

3. **Final Secreto**
   - Criar Arcanista
   - Fugir com sucesso de TODOS os combates
   - Verificar se Mórvek aparece no final

4. **Clonagem Profunda**
   - Clonar Personagem com inventário
   - Modificar clone e verificar independência

---

## 🛠️ Extensões VS Code Recomendadas

- **Extension Pack for Java** (Microsoft)
- **Maven for Java** (Microsoft)
- **Language Support for Java(TM)** (Red Hat)
- **Debugger for Java**
- **Java Test Runner**

---

## 📝 Checklist de Entrega

- [x] `pom.xml` configurado com Maven
- [x] Classe principal `Jogo` com método `main()`
- [x] Hierarquia de classes implementada
- [x] Sistema de combate funcional
- [x] Inventário com clonagem profunda
- [x] Três etapas narrativas completas
- [x] Final secreto condicional
- [x] toString/equals/hashCode implementados
- [x] Sistema de save points entre etapas

---

## 🎭 Créditos

**Desenvolvido como projeto acadêmico de Programação Orientada a Objetos**

- **Engine:** Java 11
- **Build:** Apache Maven
- **Paradigma:** POO (Herança, Polimorfismo, Encapsulamento)

---

*"Que sua lâmina seja afiada, sua magia precisa, e suas flechas certeiras. Vale-Cinzento aguarda seu herói."* ⚔️🔮🏹

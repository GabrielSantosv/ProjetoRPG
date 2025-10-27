# 🎲 RPG de Texto em Java (POO)

Uma versão limpa e interativa do README para facilitar leitura e uso.

## 📌 Descrição
Jogo de RPG em texto desenvolvido em Java para aplicar conceitos de Programação Orientada a Objetos. Mecânicas principais: combates por rolagem de dados, uso de itens em inventário e navegação em uma história simples.

## 🚀 Como executar
Opções rápidas (Windows):
- Importar o projeto em uma IDE Java (Eclipse / IntelliJ) e executar a classe `Jogo`.
- Via terminal (ex.: JDK instalado):
  1. Abrir PowerShell ou cmd.
  2. cd para a pasta do projeto:
     - cd "c:\Users\Darkz\Desktop\Projetos\ProjetoRPG"
  3. Compilar / rodar conforme sua estrutura (ou execute pelo IDE).

## ▶️ Como jogar (comandos comuns)
- Explorar — procurar locais, inimigos e itens.
- Inventário — listar itens ordenados.
- Usar [id/nome] — aplicar efeito do item (reduz quantidade).
- Fugir — rolar dado para tentativa de fuga.
- Status — ver atributos do personagem.
- Sair — encerrar o jogo.

Exemplo de interação (console):  
> 1. Explorar  
> 2. Usar Poção 1  
> 3. Fugir

## 🧩 Estrutura principal (resumo)
- Personagem (abstrata)
  - Atributos: nome, pontosVida, ataque, defesa, nivel, Inventario inventario
  - Subclasses: Guerreiro, Mago, Arqueiro
  - Inimigo deriva de Personagem
  - Requer: construtor padrão e construtor de cópia
- Item
  - Atributos: nome, descricao, efeito, quantidade
  - Usar decrementa quantidade
  - equals() para detectar igualdade (somar quantidade)
  - compareTo() para ordenação
- Inventario
  - Adicionar item (incrementa quantidade se existir)
  - Remover item (reduz quantidade)
  - Listar itens ordenados
  - Implementar clone() / construtor de cópia para deep copy

## ⚔️ Sistema de combate
Método: batalhar(Inimigo inimigo)  
Mecânica por turno:
1. Jogador e inimigo rolam dados.
2. Soma do dado + atributo ataque.
3. Ataque efetivo se (ataque + dado) > defesa do adversário.
4. Combate encerra quando pontosVida <= 0 de qualquer lado.

## ✅ Requisitos obrigatórios (a implementar)
- toString(), equals(), hashCode()
- clone() e/ou construtor de cópia
- compareTo() onde aplicável

## 🧪 Testes recomendados
- Cenários de combate (vários níveis/armas/defesas).
- Uso de itens e decremento correto de quantidade.
- Clonagem de inventário sem referências compartilhadas.
- Ordenação e mesclagem de itens iguais (equals).

## 📝 Critérios de avaliação
- Uso correto de POO: 7,0 pts  
- Qualidade do código: 1,5 pts  
- Criatividade e história/navegação: 1,5 pts

## 💡 Dicas rápidas
- Priorize código limpo e nomes claros.
- Documente métodos públicos com javadoc.
- Escreva testes unitários para batalhas e inventário.

--- 
Pequena checklist antes da entrega:
- [ ] Construtores e clones implementados
- [ ] toString/equals/hashCode testados
- [ ] Inventário com deep copy
- [ ] Cenários de combate testados
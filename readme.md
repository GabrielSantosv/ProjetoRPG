# RPG de Texto em Java (POO)

Descrição
- Jogo de RPG em texto desenvolvido em Java com foco na aplicação dos conceitos de Programação Orientada a Objetos.
- Combates por rolagem de dados, uso de itens em inventário e navegação por uma história simples.
- Trabalho em dupla. Entrega e demonstração: 28 de outubro de 2025.

Estrutura principal (resumo)
- Personagem (abstrata)
  - Atributos: nome, pontosVida, ataque, defesa, nivel, Inventario inventario
  - Subclasses: Guerreiro, Mago, Arqueiro
  - Inimigo também deriva de Personagem
  - Requer: construtor padrão e construtor de cópia

- Item
  - Atributos: nome, descricao, efeito, quantidade
  - Quantidade decrementa ao usar
  - equals para detectar itens iguais (somar quantidade)
  - compareTo para ordenação (por nome ou poder)

- Inventario
  - Adicionar item (aumenta quantidade se já existe)
  - Remover item (reduz quantidade)
  - Listar itens ordenados
  - Implementar clone() para copiar itens independentemente

Sistema de combate
- Método: batalhar(Inimigo inimigo)
- Mecânica:
  1. Jogador e inimigo rolam dados a cada turno.
  2. Valor do dado somado ao atributo ataque.
  3. Ataque é efetivo se (ataque + dado) > defesa do adversário.
  4. Combate termina quando pontosVida <= 0 de um dos lados.

Navegação e jogabilidade
- Classe Jogo contém loop principal.
- Ações do jogador incluem:
  - Explorar (encontrar locais, inimigos, armadilhas)
  - Usar itens do inventário
  - Fugir (com chance de falha baseada em rolagem de dados)
  - Tomar decisões que afetam progressão (caminhos, portas, pegar itens)

Requisitos obrigatórios (quando aplicáveis)
- Implementar, conforme apropriado nas classes:
  - toString(), equals(), hashCode()
  - clone(), construtor de cópia
  - compareTo()

Critérios de avaliação
- Uso correto de POO: 7,0 pts
- Qualidade do código: 1,5 pts
- Criatividade e qualidade da história/navegação: 1,5 pts

Observações
- Priorizar código limpo e documentação dos métodos.
- Testar cenários de combate, uso de itens
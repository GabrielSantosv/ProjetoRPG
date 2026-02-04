# Mapeamento de Telas → Código (para apresentação)

Este arquivo lista onde cada tela / botão da interface foi implementado, com os métodos e trechos que você deve abrir durante a apresentação.

Formato: Tela (exemplo nas capturas) → Arquivo Java responsável → Métodos / componentes chave → O que mostrar na apresentação

---

## 1) Tela de Seleção do Personagem (Welcome / Create Hero)
- Arquivo: `src/main/java/TelaSelecaoPersonagem.java`
- Componentes / métodos chave:
  - `criarPainelFormulario()` — onde o `campoNome` e o `comboClasses` (JComboBox) são criados.
  - `comboClasses.addActionListener(...)` — listener que troca a imagem de seleção e chama `carregarImagemParaLabelSelecao(...)`.
  - `criarPainelBotoes()` — cria `botaoIniciar` (JButton) com estilo e efeitos de hover (MouseListener) e registra o `ActionListener`.
  - `iniciarJogo()` — valida o nome, chama `jogoLogica.criarPersonagem(nome, tipoClasse)` e troca para a tela do jogo (`((JogoGUI) frame).mostrarTelaJogo()`).
  - `carregarImagemParaLabelSelecao(...)` — método utilitário de carregamento de imagens para o painel direito.

- O que abrir e destacar na apresentação:
  - Abrir `TelaSelecaoPersonagem.java` no VS Code, navegar até `criarPainelBotoes()` e mostrar o `addActionListener` do `botaoIniciar`.
  - Mostrar `iniciarJogo()` (explicar validações e chamada a `JogoLogica`).
  - Mostrar `comboClasses` e o listener que atualiza `labelImagemSelecao`.

---

## 2) Tela Principal do Jogo (Narrativa + Escolhas)
- Arquivo: `src/main/java/TelaJogo.java`
- Componentes / métodos chave:
  - `criarPainelPersonagem()` — labels de HUD (`labelNomePersonagem`, `labelVida`, `labelNivel`).
  - `criarPainelCentral()` — `labelImagem` (cenário) e `areaTexto` (narrativa).
  - `criarPainelAcoes()` — criação de botões: `botoesAcao[0]`, `botoesAcao[1]`, `botaoInventario`, `botaoVoltar`.
  - `criarBotaoAcao(String texto)` — utilitário de estilização dos botões.
  - Listeners:
    - `botoesAcao[0].addActionListener(...)` e `botoesAcao[1].addActionListener(...)` → chamam `processarEscolha(1/2)`.
    - `botaoInventario.addActionListener(...)` → chama `mostrarInventario()`.
    - `botaoVoltar.addActionListener(...)` → chama `voltarAoMenu()`.
  - `atualizar()` — método que puxa o estado de `JogoLogica`/`GerenciadorHistoria` e atualiza texto, imagem e texto dos botões (importante para mostrar a conexão GUI → backend).
  - `processarEscolha(int escolha)` — envia a escolha para o `GerenciadorHistoria` (por exemplo `processarEscolhaEtapa1/2` ou ações de batalha) e chama `atualizar()`.

- O que abrir e destacar na apresentação:
  - Abrir `TelaJogo.java`, mostrar a criação dos botões em `criarPainelAcoes()` e destacar os `addActionListener` que chamam `processarEscolha(...)`.
  - Abrir `atualizar()` e mostrar como os textos dos botões são preenchidos com `gerenciador.getOpcoesAtuais()` — ótima oportunidade para explicar como a lógica antiga fornece o conteúdo.
  - Demonstrar no jogo: clicar em uma opção na GUI e mostrar o console/log para evidenciar que `processarEscolha` foi executado.

---

## 3) Tela de Batalha (Herói x Inimigo)
- Arquivo: `src/main/java/TelaBatalha.java`
- Componentes / métodos chave:
  - Construção da UI (no construtor): `labelHero`, `labelInimigo`, `heroHpBar`, `inimigoHpBar`, `areaLog`.
  - Botões principais:
    - `botaoAtacar` — criado via `criarBotaoAcao(...)` e com `botaoAtacar.addActionListener(...)` que chama `gerenciador.processarAcaoBatalha(1); atualizar(); checarFimDeBatalha();`.
    - `botaoFugir` — `addActionListener` chamando `gerenciador.processarAcaoBatalha(2); atualizar(); checarFimDeBatalha();`.
    - `botaoInventario` — `addActionListener` chamando `mostrarInventarioBatalha()`.
  - `mostrarInventarioBatalha()` — diálogo para escolher item durante batalha e chamada a `gerenciador.usarItemBatalha(nomeItem)`.
  - `atualizar()` — atualiza barras de vida, sprites e log; conecta ao `SistemaBatalha`/`GerenciadorHistoria`.

- O que abrir e destacar na apresentação:
  - Abrir `TelaBatalha.java` e localizar os `addActionListener` dos três botões (ATACAR / FUGIR / INVENTÁRIO).
  - Mostrar `mostrarInventarioBatalha()` e `checarFimDeBatalha()` para explicar como a GUI reage ao fim da batalha e volta para `TelaJogo`.
  - Durante demo, clicar em `ATACAR` e mostrar no painel de log (`areaLog`) e no console que as funções do backend foram executadas.

---

## 4) Arquivo que organiza as telas (card switching)
- Arquivo: `src/main/java/JogoGUI.java`
- Componentes / métodos chave:
  - `CardLayout cardLayout` + `cardPanel` — adiciona as telas com `cardPanel.add(telaSelecao, "SELECAO")`, `cardPanel.add(telaJogo, "JOGO")`, `cardPanel.add(telaBatalha, "BATALHA")`.
  - Métodos públicos: `mostrarTelaSelecao()`, `mostrarTelaJogo()`, `mostrarTelaBatalha()` — usados pela lógica para trocar views.
  - `main()` — inicia a GUI com `SwingUtilities.invokeLater`.

- O que abrir e destacar:
  - Abrir `JogoGUI.java` e mostrar onde as telas são instanciadas e adicionadas ao `CardLayout`.
  - Mostrar `mostrarTelaBatalha()` para explicar como a lógica chama `gui.mostrarTelaBatalha()` quando um combate começa.

---

## 5) Código do backend que a GUI chama (para referência rápida)
- `src/main/java/JogoLogica.java` — métodos públicos que a GUI chama, por exemplo `criarPersonagem(...)`, `getJogador()`, `getGerenciadorHistoria()`, `getGui()`.
- `src/main/java/GerenciadorHistoria.java` — contém `iniciarEtapa1()`, `getNarrativaAtual()`, `getOpcoesAtuais()`, `processarEscolhaEtapa1/2`, `processarAcaoBatalha(...)`, `getEstadoAtual()` etc.

---

## 6) Sugestão prática para a apresentação (passo-a-passo curto)
1. Abra `JogoGUI.java` (mostre `CardLayout`) — 20s
2. Abra `TelaSelecaoPersonagem.java` — destaque `botaoIniciar` e `iniciarJogo()` — 50s
3. Execute o jogo e mostre a tela de seleção (crie personagem) — 40s
4. Abra `TelaJogo.java` — destaque `criarPainelAcoes()` e `atualizar()` — 50s
5. No jogo, clique em uma opção e mostre o console/log que chama `processarEscolha(...)` — 40s
6. Abra `TelaBatalha.java` — destaque `botaoAtacar` / `botaoFugir` / `botaoInventario` e `mostrarInventarioBatalha()` — 40s
7. Faça uma batalha rápida no jogo e mostre `areaLog` e as mudanças nas barras de vida — 40s

---

## 7) Notas finais / arquivos para abrir rapidamente
- Arquivos úteis para abrir antes da apresentação:
  - `src/main/java/JogoGUI.java`
  - `src/main/java/TelaSelecaoPersonagem.java`
  - `src/main/java/TelaJogo.java`
  - `src/main/java/TelaBatalha.java`
  - `src/main/java/JogoLogica.java`
  - `src/main/java/GerenciadorHistoria.java`

Salve este arquivo como referência rápida ao apresentar — abra os arquivos listados e use o terminal integrado para rodar `java -cp target/classes JogoGUI`.

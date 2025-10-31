// ...existing code...

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Jogo {

    private Personagem jogador;
    private Scanner scanner = new Scanner(System.in, "UTF-8");
    private Random random = new Random();

    // Rastreador do Easter Egg / Margit
    private String easterEggTracker = "";
    private boolean jogadorEhMago = false;
    private boolean fugiuDeTodasBatalhas = true;
    private boolean encontrouMargit = false;

    public void iniciar() {
        criarPersonagem();
        System.out.println("\nPersonagem criado com seus itens iniciais: ");
        System.out.println(jogador);
        jogador.getInventario().listarItens();

        Personagem savePoint = (Personagem) jogador.clone();

        System.out.println("\n>>> Ponto de sauvegarde inicial criado! <<<");

        if (etapa1()) {
            savePoint = (Personagem) jogador.clone();
            System.out.println("\n>>> Jogo salvo! Progresso da Etapa 1 registrado. <<<");

            if (etapa2()) {
                savePoint = (Personagem) jogador.clone();
                System.out.println("\n>>> Jogo salvo! Progresso da Etapa 2 registrado. <<<");

                if (etapaFinal()) {
                    System.out.println("\n----------------------------------------");
                    System.out.println("PARABÉNS! Você derrotou o chefe final e concluiu sua jornada!");
                    System.out.println("FIM DE JOGO.");
                }
            }
        }
    }

    private void criarPersonagem() {
        System.out.println("Bem-vindo à Jornada do Herói!");
        System.out.print("Digite o nome do seu personagem: ");
        String nome = scanner.nextLine();
        System.out.println("Escolha sua classe:\n1. Guerreiro\n2. Mago\n3. Arqueiro");
        int escolha = lerEscolha();
        switch (escolha) {
            case 1:
                jogador = new Guerreiro(nome);
                jogadorEhMago = false;
                break;
            case 2:
                jogador = new Mago(nome);
                jogadorEhMago = true;
                fugiuDeTodasBatalhas = true; // elegível ao início
                break;
            case 3:
                jogador = new Arqueiro(nome);
                jogadorEhMago = false;
                break;
            default:
                System.out.println("Escolha inválida. Você será um Guerreiro.");
                jogador = new Guerreiro(nome);
                jogadorEhMago = false;
                break;
        }
    }

    private boolean etapa1() {
        System.out.println("\n--- ETAPA 1: A FLORESTA SOMBRIA ---");
        System.out.println("Você entra na floresta e se depara com uma bifurcação. Um caminho vai para a esquerda, outro para a direita.");
        System.out.println("1. Virar à esquerda, por um caminho lamacento.");
        System.out.println("2. Virar à direita, por um atalho entre espinhos.");

        int escolha = lerEscolha();

        if (escolha == 6) {
            this.easterEggTracker = "6";
            escolha = 1;
        } else {
            this.easterEggTracker = "";
        }

        if (escolha == 1) {
            System.out.println("Você escorrega na lama e torce o tornozelo! Você sofre 10 de dano.");
            jogador.setPontosVida(jogador.getPontosVida() - 10);
        } else {
            System.out.println("Seus pertences se enroscam nos espinhos! Você perde um item aleatório.");
            jogador.getInventario().removerItemAleatorio();
        }
        System.out.println("\nIndependente do caminho, um Goblin Batedor salta em sua direção!");
        Inimigo goblin = new Inimigo("Goblin Batedor", 50, 10, 5, 1);
        goblin.getInventario().adicionarItem(new Item("Adaga Enferrujada de Goblin", "Troféu", 0, 1, null));

        return batalhar(goblin);
    }

    private boolean etapa2() {
        System.out.println("\n--- ETAPA 2: A PONTE QUEBRADA ---");
        System.out.println("Após a floresta, você chega a uma ponte antiga sobre um abismo.");
        System.out.println("1. Tentar saltar a parte quebrada.");
        System.out.println("2. Escalar pela lateral da ponte.");

        int escolha = lerEscolha();

        if (escolha == 6) {
            if (this.easterEggTracker.equals("6")) {
                this.easterEggTracker = "66";
            } else {
                this.easterEggTracker = "6";
            }
            escolha = 1;
        } else {
            this.easterEggTracker = "";
        }

        if (escolha == 1) {
            System.out.println("Você salta, mas cai de mal jeito, sofrendo 15 de dano.");
            jogador.setPontosVida(jogador.getPontosVida() - 15);
        } else {
            System.out.println("Durante a escalada, você encontra uma erva rara! Você ganhou uma Poção de Cura.");
            jogador.getInventario().adicionarItem(new Item("Elixir de Cura", "Restaura 25 HP.", 25, 1, TipoEfeito.CURA));
        }
        System.out.println("\nDo outro lado da ponte, um Orc Guerreiro bloqueia o caminho!");
        Inimigo orc = new Inimigo("Orc Guerreiro", 80, 15, 10, 3);
        orc.getInventario().adicionarItem(new Item("Machado Rustico de Orc", "Troféu", 0, 1, null));

        return batalhar(orc);
    }

    private boolean etapaFinal() {
        System.out.println("\n--- ETAPA FINAL: O PORTÃO DO CASTELO ---");
        System.out.println("Finalmente, você alcança o portão do castelo amaldiçoado.");
        System.out.println("1. Tentar arrombar o portão principal com força bruta.");
        System.out.println("2. Procurar uma entrada secreta pelos esgotos.");

        int escolha = lerEscolha();

        if (escolha == 1) {
            System.out.println("O portão está magicamente selado e repele sua força, causando 20 de dano.");
            jogador.setPontosVida(jogador.getPontosVida() - 20);
        } else {
            System.out.println("Nos esgotos, você encontra um Elixir de Força esquecido por outro aventureiro!");
            jogador.getInventario().adicionarItem(new Item("Elixir de Força", "Aumenta o ataque.", 5, 1, TipoEfeito.AUMENTO_ATAQUE));
        }

        // Verifica condição secreta (Margit: jogador deve ser Mago e ter fugido de todas as batalhas)
        if (verificouFugasCompletas()) {
            System.out.println("\nUma trilha oculta se revela por sua astúcia... algo pressagia um confronto único.");
            encounterMargit();
            // encounterMargit pode encerrar o jogo via System.exit(0)
            return true;
        } else {
            System.out.println("\nO barulho atrai o guardião! Um Dragão Chefe emerge das sombras!");
            Inimigo chefe = new Inimigo("Dragão Chefe", 150, 25, 15, 5);
            chefe.getInventario().adicionarItem(new Item("Escama de Dragao", "Troféu", 0, 1, null));
            return batalhar(chefe);
        }
    }

    private boolean batalhar(Inimigo inimigo) {
        System.out.println("\n--- COMBATE INICIADO: " + jogador.getNome() + " vs " + inimigo.getNome() + " ---");
        // marca inicial: assumimos que, até prova em contrário, este encontro não foi fugido
        boolean battleWasFled = false;

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n" + jogador);
            System.out.println(inimigo);
            System.out.println("\nEscolha sua ação:\n1. Atacar\n2. Usar Item\n3. Fugir");
            int acao = lerEscolha();
            switch (acao) {
                case 1:
                    // jogador engajou em combate => quebra possibilidade do easter egg
                    battleWasFled = false;
                    this.fugiuDeTodasBatalhas = false;
                    turnoDeCombate(jogador, inimigo);
                    if (inimigo.estaVivo()) {
                        turnoDeCombate(inimigo, jogador);
                    }
                    break;
                case 2:
                    // usando item considera-se engajado (não é fuga)
                    battleWasFled = false;
                    this.fugiuDeTodasBatalhas = false;
                    jogador.getInventario().listarItens();
                    System.out.print("Digite o nome do item que deseja usar (ou 'cancelar'): ");
                    String nomeItem = scanner.nextLine();
                    if (!nomeItem.equalsIgnoreCase("cancelar")) {
                        boolean itemUsadoComSucesso = jogador.getInventario().usarItem(nomeItem, jogador);
                        if (itemUsadoComSucesso) {
                            if (inimigo.estaVivo()) {
                                System.out.println("\nO inimigo aproveita a brecha e ataca!");
                                turnoDeCombate(inimigo, jogador);
                            }
                        } else {
                            System.out.println("Tente outra ação.");
                        }
                    }
                    break;
                case 3:
                    // Verificação para não fugir do chefe
                    if (inimigo.getNome().equals("Dragão Chefe") || inimigo.getNome().equals("O Maliguininho") || inimigo.getNome().equals("Margit, o desalmado")) {
                        System.out.println("Você não pode fugir de uma batalha de chefe!");
                        // tentar fugir de chefe conta como engajamento
                        battleWasFled = false;
                        this.fugiuDeTodasBatalhas = false;
                        turnoDeCombate(inimigo, jogador);
                    } else {
                        // tentativa de fuga
                        // 50% de chance de fuga
                        boolean sucessoFuga = random.nextBoolean();
                        if (sucessoFuga) {
                            System.out.println("Você conseguiu fugir!");
                            battleWasFled = true;
                            // manter fugiuDeTodasBatalhas true (se já true)
                            return true; // saiu vivo do combate (considerado sucesso da etapa)
                        } else {
                            System.out.println("A fuga falhou! O inimigo ataca.");
                            // falha ao fugir => engajamento definitivo e quebra da condição
                            battleWasFled = false;
                            this.fugiuDeTodasBatalhas = false;
                            turnoDeCombate(inimigo, jogador);
                        }
                    }
                    break;
                default:
                    System.out.println("Ação inválida. Você perdeu o turno.");
                    battleWasFled = false;
                    this.fugiuDeTodasBatalhas = false;
                    turnoDeCombate(inimigo, jogador);
                    break;
            }
        }

        // Se o loop terminou sem que o jogador tivesse sido derrotado e battleWasFled == true,
        // significa que o jogador fugiu e a batalha não foi realmente considerada vencida.
        if (!jogador.estaVivo()) {
            System.out.println("\nVocê foi derrotado! Fim de jogo.");
            return false; // Jogador perdeu
        }

        // Se o jogador venceu o inimigo (inimigo morreu)
        if (inimigo.estaVivo() == false) {
            System.out.println("\nVocê venceu o combate!");
            jogador.subirNivel();
            Inventario saque = inimigo.getInventario().clone();
            System.out.println("Você saqueou os itens do inimigo:");
            saque.listarItens();
            jogador.getInventario().adicionarInventario(saque);
            return true; // Jogador venceu
        }

        // Caso o jogador tenha fugido (já retornamos true), mas se chegou aqui por segurança:
        return true;
    }

    /**
     * Verifica se o jogador é Mago e fugiu com sucesso de todas as batalhas até
     * agora.
     */
    private boolean verificouFugasCompletas() {
        return jogadorEhMago && fugiuDeTodasBatalhas;
    }

    /**
     * Encontra Margit, o desalmado — só chamado se verificouFugasCompletas()
     * for true. Se o jogador derrotar Margit, exibe final secreto.
     */
    private void encounterMargit() {
        if (encontrouMargit) {
            return;
        }
        encontrouMargit = true;
        System.out.println("\nVocê sentiu um chamado sombrio... a trilha secreta se abre.");
        // criar Inimigo Margit (valores ajustáveis)
        Inimigo margit = new Inimigo("Margit, o desalmado", 200, 22, 12, 12);
        margit.getInventario().adicionarItem(new Item("Coroa Corrompida", "Troféu do final secreto", 0, 1, null));
        System.out.println("Margit aparece diante de você. Este é o verdadeiro responsável pela praga.");
        exibirAsciiArtMargit();
        boolean venceu = batalhar(margit);
        if (venceu) {
            System.out.println("\nVocê derrotou Margit, o desalmado!");
            System.out.println("A praga recua. Um final secreto se revela. Parabéns — jogo zerado (Easter Egg).");
            System.exit(0);
        } else {
            System.out.println("\nVocê não conseguiu derrotar Margit. A praga persiste...");
        }
    }

    private int lerEscolha() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 1; // Retorna 1 se a entrada for inválida
        }
    }

    private void turnoDeCombate(Personagem atacante, Personagem defensor) {
        if (!atacante.estaVivo()) {
            return;
        }
        int rolagemDado = random.nextInt(6) + 1;
        int forcaAtaque = atacante.getAtaque() + rolagemDado;
        System.out.println("\n" + atacante.getNome() + " ataca (Força: " + atacante.getAtaque() + " + Dado: " + rolagemDado + " = " + forcaAtaque + ")");
        int dano = forcaAtaque - defensor.getDefesa();
        if (dano > 0) {
            defensor.setPontosVida(defensor.getPontosVida() - dano);
            System.out.println(atacante.getNome() + " causou " + dano + " de dano em " + defensor.getNome() + "!");
        } else {
            System.out.println(defensor.getNome() + " defendeu completamente o ataque!");
        }
    }

    /**
     * Exibe a arte ASCII do Margit lendo o arquivo Margit.txt (resources raiz
     * ou pasta do projeto).
     */
    private void exibirAsciiArtMargit() {
        System.out.println("\n\n...O ar escurece... Uma presença terrível se aproxima...\n\n");
        // Tenta ler o arquivo TXT
        try (Scanner leitorArquivo = new Scanner(new File("Margit.txt"), "UTF-8")) {
            while (leitorArquivo.hasNextLine()) {
                System.out.println(leitorArquivo.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("!!! ARTE DE MARGIT NÃO ENCONTRADA: Margit.txt não disponível. !!!");
            System.err.println("!!! Coloque Margit.txt na pasta raiz do projeto para exibir a arte. !!!");
        }
        System.out.println("\nVOZ: 'Chegou a hora... você finalmente me encontrou.'\n");
    }

    public static void main(String[] args) {
        Jogo meuJogo = new Jogo();
        meuJogo.iniciar();
    }
}
// ...existing code...

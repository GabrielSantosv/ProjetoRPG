// ...existing code...

import java.util.Random;
import java.util.Scanner;

public class Jogo {

    private Personagem jogador;
    private Scanner scanner = new Scanner(System.in, "UTF-8");
    private Random random = new Random();

    private boolean jogadorEhMago = false;
    private boolean fugiuDeTodasBatalhas = true;
    private boolean encontrouMargit = false;
    private int bonusDanoProximoInimigo = 0;

    public void iniciar() {
        criarPersonagem();
        System.out.println("\nPersonagem criado com seus itens iniciais: ");
        System.out.println(jogador);
        jogador.getInventario().listarItens();

    System.out.println("\n>>> Ponto de sauvegarde inicial criado! <<<");

        if (etapa1()) {
            System.out.println("\n>>> Jogo salvo! Progresso da Etapa 1 registrado. <<<");

            if (etapa2()) {
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
        System.out.println("Bem-vindo a Vale-Cinzento, viajante. Antes de cruzar as fronteiras do desconhecido, diga seu nome e escolha seu caminho.");
        System.out.print("Digite o nome do seu herói: ");
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
        System.out.println("\n--- ETAPA 1: A Encruzilhada do Suspiro ---");
        System.out.println("A lua minguante repousa sobre o Caminho do Suspiro, onde a estrada se divide em duas trilhas. O vento sopra dos dois lados — frio e indeciso.");
        System.out.println("1. Vereda da Névoa (mais segura, porém desgastante)");
        System.out.println("2. Senda das Ruínas (mais arriscada, porém promissora)");

        int escolha = lerEscolha();

        if (escolha == 1) {
            System.out.println("A névoa lhe abraça com dedos frios. Galhos arranham sua pele, pedras traem seus passos.");
            if (random.nextBoolean()) { // 50% de chance de os galhos machucarem
                System.out.println("Você sofre 10 de dano.");
                jogador.setPontosVida(jogador.getPontosVida() - 10);
            } else {
                System.out.println("Você avança com cautela e evita os galhos cortantes.");
            }
            System.out.println("\nUm Sapo Gigante acorda com o barulho e salta até você enfurecido!");
            Inimigo sapo = new Inimigo("Sapo Gigante", 50, 10, 5, 1);
            sapo.getInventario().adicionarItem(new Item("Língua de Sapo Gigante", "Troféu", 0, 1, null));
            return batalhar(sapo);
        } else if (escolha == 2) {
            System.out.println("Símbolos antigos se acendem ao seu passo. Um puxão invisível remexe seus pertences… e algo some.");
            if (random.nextBoolean()) { // 50% de chance de perder um item
                System.out.println("Você perde um item aleatório.");
                jogador.getInventario().removerItemAleatorio();
            } else {
                System.out.println("Você protege seus pertences a tempo e não perde nada.");
            }
            System.out.println("\nDas sombras, uma Pantera de Duas Cabeças salta para atacar!");
            Inimigo pantera = new Inimigo("Pantera de Duas Cabeças", 70, 12, 3, 1);
            pantera.getInventario().adicionarItem(new Item("Presa Gêmea Obsidiana", "Troféu", 0, 1, null));
            return batalhar(pantera);
        } else {
            // Entrada inválida — segue pela Vereda da Névoa como padrão
            System.out.println("Você segue pela Vereda da Névoa, pressionado a decidir logo.");
            System.out.println("A névoa lhe abraça com dedos frios. Galhos arranham sua pele, pedras traem seus passos.");
            if (random.nextBoolean()) { // 50% de chance de os galhos machucarem
                System.out.println("Você sofre 10 de dano.");
                jogador.setPontosVida(jogador.getPontosVida() - 10);
            } else {
                System.out.println("Você avança com cautela e evita os galhos cortantes.");
            }
            System.out.println("\nUm Sapo Gigante acorda com o barulho e corre até você enfurecido!");
            Inimigo sapo = new Inimigo("Sapo Gigante", 50, 10, 5, 1);
            sapo.getInventario().adicionarItem(new Item("Língua de Sapo Gigante", "Troféu", 0, 1, null));
            return batalhar(sapo);
        }
        
    }

    private boolean etapa2() {
        System.out.println("\n--- ETAPA 2: O Campo de Magma Adormecido ---");
        System.out.println("Após deixar a floresta úmida para trás, o calor começa a crescer. O chão treme sob passos incertos — diante de você, estende-se o Campo de Magma Adormecido, um mar rubro de rochas incandescentes e fendas por onde o fogo respira. O ar vibra com o cheiro de enxofre e cinza.");
        System.out.println("1. Atravessar rapidamente saltando entre as rochas incandescentes.");
        System.out.println("2. Descer por um desnível e contornar as fendas.");

        int escolha = lerEscolha();

        if (escolha == 1) {
            boolean saltoBemSucedido = random.nextBoolean(); // 50% de chance
            if (saltoBemSucedido) {
                System.out.println("Você toma distância, corre e salta de pedra em pedra. O calor lambe seus pés, e o suor arde nos olhos. Você se sente motivado!");
                System.out.println("Você ganhou um bônus de +7 de dano contra o próximo inimigo!");
                bonusDanoProximoInimigo = 7;
            } else {
                System.out.println("Você toma distância, corre e salta de pedra em pedra. O calor lambe seus pés, e o suor arde nos olhos. Um deslize, um estalo, e a dor o alcança. Você sofre 15 de dano.");
                jogador.setPontosVida(jogador.getPontosVida() - 15);
            }
        } else if (escolha == 2) {
            System.out.println("Com cautela, você se arrasta entre rochas escuras e fumaça. O ar é pesado, mas, entre fendas resfriadas, encontra um brilho esverdeado — uma erva rara nascida do fogo.");
            jogador.getInventario().adicionarItem(new Item("Erva de Cinza", "Restaura 25 HP.", 25, 1, TipoEfeito.CURA));
        } else {
            boolean saltoBemSucedido = random.nextBoolean(); // 50% de chance
            if (saltoBemSucedido) {
                System.out.println("Você não pensa duas vezes, corre e salta de pedra em pedra. O calor lambe seus pés, e o suor arde nos olhos. Você se sente motivado!");
                System.out.println("Você ganhou um bônus de +7 de dano contra o próximo inimigo!");
                bonusDanoProximoInimigo = 7;
            } else {
                System.out.println("Você não pensa duas vezes, corre e salta de pedra em pedra. O calor lambe seus pés, e o suor arde nos olhos. Um deslize, um estalo, e a dor o alcança. Você sofre 15 de dano.");
                jogador.setPontosVida(jogador.getPontosVida() - 15);
            }
        }
        System.out.println("\nNo caminho oposto, emergindo das chamas, surge Brukkar, o Portador da Corrente — a armadura marcada por fuligem, o mangual incandescente gotejando faíscas. Sua presença faz o calor parecer um sussurro.");
        System.out.println("Brukkar bate a corrente em brasa no chão. 'Pedágio: sangue ou ossos.'");
        Inimigo brukkar = new Inimigo("Brukkar, o Portador da Corrente", 80, 15, 10, 3);
        brukkar.getInventario().adicionarItem(new Item("Corrente Chamuscada", "Troféu", 0, 1, null));

        return batalhar(brukkar);
    }

    private boolean etapaFinal() {
        System.out.println("\n--- ETAPA FINAL: O Portão do Véu Quebrado ---");
        System.out.println("As torres do Castelo do Véu Quebrado perfuram a neblina. Um portão colossal se ergue, coberto de runas que parecem observar.");
        System.out.println("1. Tentar arrombar o portão principal com força bruta.");
        System.out.println("2. Procurar uma entrada secreta pelos esgotos.");

        int escolha = lerEscolha();

        if (escolha == 1) {
            System.out.println("Você investe. As runas piscam e um repuxo invisível o lança para trás. Você sofre 20 de dano.");
            jogador.setPontosVida(jogador.getPontosVida() - 20);
        } else if (escolha == 2) {
            System.out.println("Por baixo da muralha, uma grade enferrujada cede. O cheiro é terrível, mas você encontra algo numa bolsa encharcada.");
            jogador.getInventario().adicionarItem(new Item("Frasco de Fôlego", "Aumenta o ataque.", 5, 1, TipoEfeito.AUMENTO_ATAQUE));
        } else {
            System.out.println("Escolha inválida. Por precaução, você investiga os esgotos e acaba encontrando algo útil em uma bolsa encharcada.");
            jogador.getInventario().adicionarItem(new Item("Frasco de Fôlego", "Aumenta o ataque.", 5, 1, TipoEfeito.AUMENTO_ATAQUE));
        }

        // Verifica condição secreta (Encontro secreto: Arcanista + zero fugas)
        if (verificouFugasCompletas()) {
            boolean venceuMorvek = encounterMorvek();
            return venceuMorvek;
        } else {
            System.out.println("\nDo além-túmulo, ergue-se Balduran, o Imperador Esqueleto — manto puído sobre ossos entalhados, olhos vazios que ardem em brasa pálida.");
            System.out.println("Os grilhões no portão se soltam com um lamento antigo. A coroa quebrada tilinta enquanto ele aponta a espada real. 'Nenhum passo profanará meu domínio.'");
            Inimigo chefe = new Inimigo("Balduran, o Imperador Esqueleto", 150, 25, 15, 5);
            chefe.getInventario().adicionarItem(new Item("Coroa Estilhaçada", "Troféu", 0, 1, null));
            return batalhar(chefe);
        }
    }

    private boolean batalhar(Inimigo inimigo) {
        System.out.println("\n--- COMBATE INICIADO: " + jogador.getNome() + " vs " + inimigo.getNome() + " ---");

        while (jogador.estaVivo() && inimigo.estaVivo()) {
            System.out.println("\n" + jogador);
            System.out.println(inimigo);
            System.out.println("\nEscolha sua ação:\n1. Atacar\n2. Usar Item\n3. Fugir");
            int acao = lerEscolha();
            switch (acao) {
                case 1:
                    // jogador engajou em combate — marca como combate ativo
                    this.fugiuDeTodasBatalhas = false;
                    turnoDeCombate(jogador, inimigo);
                    if (inimigo.estaVivo()) {
                        turnoDeCombate(inimigo, jogador);
                    }
                    break;
                case 2:
                    // usando item considera-se engajado (não é fuga)
                    this.fugiuDeTodasBatalhas = false;
                    jogador.getInventario().listarItensUsaveis();
                    System.out.print("Digite o ID ou nome do item que deseja usar (ou 'cancelar'): ");
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
                    // Verificação para não fugir dos chefes (Balduran e Mórvek)
                    if (inimigo.getNome().equals("Balduran, o Imperador Esqueleto") ||
                        inimigo.getNome().equals("Mórvek, o Arquiteto do Vazio")) {
                        System.out.println("Você não pode fugir de uma batalha de chefe!");
                        // tentar fugir de chefe conta como engajamento
                        this.fugiuDeTodasBatalhas = false;
                        turnoDeCombate(inimigo, jogador);
                    } else {
                        // tentativa de fuga
                        // 50% de chance de fuga
                        boolean sucessoFuga = random.nextBoolean();
                        if (sucessoFuga) {
                            System.out.println("Você conseguiu fugir!");
                            // manter fugiuDeTodasBatalhas true (se já true)
                            if (inimigo.getNome().equals("Brukkar, o Portador da Corrente")) {
                                bonusDanoProximoInimigo = 0;
                            }
                            return true; // saiu vivo do combate (considerado sucesso da etapa)
                        } else {
                            System.out.println("A fuga falhou! O inimigo ataca.");
                            // falha ao fugir não impede final secreto
                            turnoDeCombate(inimigo, jogador);
                        }
                    }
                    break;
                default:
                    System.out.println("Ação inválida. Você perdeu o turno.");
                    // ação inválida — considerado engajamento
                    this.fugiuDeTodasBatalhas = false;
                    turnoDeCombate(inimigo, jogador);
                    break;
            }
        }

    // Quando este loop termina, tratamos os resultados abaixo (derrota, vitória ou fuga).
        if (!jogador.estaVivo()) {
            System.out.println("\nVocê foi derrotado! Fim de jogo.");
            if (inimigo.getNome().equals("Balduran, o Imperador Esqueleto")) {
                System.out.println("As correntes antigas se apertam, e a sombra de Umbreterna cobre de vez estas terras.");
                System.out.println("Sem sua luz, Vale-Cinzento sucumbe ao lamento do Monarca. Você não conseguiu salvar o território.");
            }
            if (inimigo.getNome().equals("Brukkar, o Portador da Corrente")) {
                bonusDanoProximoInimigo = 0;
            }
            return false; // Jogador perdeu
        }

        // Se o jogador venceu o inimigo (inimigo morreu)
        if (inimigo.estaVivo() == false) {
            System.out.println("\nVocê venceu o combate!");
            // Mensagens específicas de vitória por inimigo
            if ("Sapo Gigante".equals(inimigo.getNome())) {
                System.out.println("O Sapo Gigante solta um último coaxar e tomba pesadamente, deixando a trilha livre.");
            } else if ("Pantera de Duas Cabeças".equals(inimigo.getNome())) {
                System.out.println("As duas cabeças da Pantera uivam em uníssono antes de se desfazerem nas sombras, deixando apenas suas presas obsidianas.");
            } else if ("Brukkar, o Portador da Corrente".equals(inimigo.getNome())) {
                System.out.println("A corrente flamejante se apaga num estalo. Brukkar encara o céu rubro pela última vez antes de tombar entre brasas que se apagam lentamente.");
            } else if ("Balduran, o Imperador Esqueleto".equals(inimigo.getNome())) {
                System.out.println("A coroa partida cai e ecoa no vazio. Balduran desmorona em pó de eras, e o portão solta um último suspiro.");
                System.out.println();
                System.out.println("As runas ao redor apagam-se como brasas ao amanhecer. A escuridão que enforcava o reino começa a se desfazer.");
                System.out.println("Do alto das torres, um vento claro varre a névoa — Vale-Cinzento é libertada do jugo de Umbreterna.");
                System.out.println("Seu nome ecoará nas encruzilhadas e nas canções: aquele que quebrou a noite.");
            } else if ("Mórvek, o Arquiteto do Vazio".equals(inimigo.getNome())) {
                System.out.println("A máscara racha. Um olho antigo observa você com respeito e se desfaz em poeira luminosa. O caminho adiante se revela.");
            }
            jogador.subirNivel();
            Inventario saque = inimigo.getInventario().clone();
            System.out.println("Você saqueou os itens do inimigo:");
            saque.listarItens();
            jogador.getInventario().adicionarInventario(saque);
            if (inimigo.getNome().equals("Brukkar, o Portador da Corrente")) {
                bonusDanoProximoInimigo = 0;
            }
            return true; // Jogador venceu
        }

        // Caso o jogador tenha fugido (já retornamos true), mas se chegou aqui por segurança:
        if (inimigo.getNome().equals("Brukkar, o Portador da Corrente")) {
            bonusDanoProximoInimigo = 0;
        }
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
    private boolean encounterMorvek() {
        if (encontrouMargit) {
            return false;
        }
        encontrouMargit = true;
        System.out.println();
        System.out.println("────────────────────────────────────────────────────");
        System.out.println("As runas o reconhecem. O portão se parte em silêncio.");
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
        System.out.println("Surge Mórvek, o Arquiteto do Vazio — manto rasgado, máscara de obsidiana, mãos que deixam rastro de geada no ar.");
        try { Thread.sleep(700); } catch (InterruptedException ignored) {}

        Inimigo morvek = new Inimigo("Mórvek, o Arquiteto do Vazio", 200, 22, 12, 12);
        morvek.getInventario().adicionarItem(new Item("Fragmento de Obsidiana", "Troféu do final secreto", 0, 1, null));

        System.out.println();
        System.out.println("Mórvek desliza do escuro: 'Discípulo da centelha, prove que merece atravessar.'");
        System.out.println("────────────────────────────────────────────────────\n");

        exibirCenaMorvek();
        batalhar(morvek);
        if (!morvek.estaVivo()) {
            System.out.println("\nVocê derrotou Mórvek.");
            System.out.println("A máscara racha. Um olho antigo observa você com respeito e se desfaz em poeira luminosa. O caminho adiante se revela.");
            return true;
        }

        // Se chegou aqui e o jogador ainda vive, ou ele fugiu ou perdeu
        if (jogador.estaVivo()) {
            System.out.println("\nVocê fugiu de Mórvek. O Véu permanece fechado...");
        } else {
            System.out.println("\nVocê não conseguiu derrotar Mórvek. O Véu permanece fechado...");
        }
        return false;
    }

    /**
     * Exibe uma cena atmosférica para o encontro com Margit sem depender de
     * arquivos externos. Mantém a voz e pequenas pausas.
     */
    private void exibirCenaMorvek() {
        System.out.println("\n\n...O ar esfria... Um silêncio denso toma o corredor...\n\n");
        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
        System.out.println("O gelo racha ao toque do vazio; as runas cintilam e se calam.");
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        System.out.println("Sombras se dobram ao redor de uma figura — a máscara de obsidiana observa, imóvel.");
        try { Thread.sleep(500); } catch (InterruptedException ignored) {}
        System.out.println("\nUMA VOZ VELADA: 'Além do véu, apenas os dignos.'\n");
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
        if (atacante == jogador && "Brukkar, o Portador da Corrente".equals(defensor.getNome()) && bonusDanoProximoInimigo > 0) {
            dano += bonusDanoProximoInimigo;
            System.out.println("(Bônus de +" + bonusDanoProximoInimigo + " aplicado contra a Armadura)");
        }
        if (dano > 0) {
            defensor.setPontosVida(defensor.getPontosVida() - dano);
            System.out.println(atacante.getNome() + " causou " + dano + " de dano em " + defensor.getNome() + "!");
        } else {
            System.out.println(defensor.getNome() + " defendeu completamente o ataque!");
        }
    }
    
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.iniciar();
    }
}

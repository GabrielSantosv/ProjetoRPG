import java.util.Random;

/**
 * Gerencia a progressão da história do RPG
 * Controla etapas, narrativas e escolhas do jogador
 */
public class GerenciadorHistoria {
    
    public static final int ESTADO_ESCOLHA = 0;
    public static final int ESTADO_BATALHA = 1;
    public static final int ESTADO_VITORIA = 2;
    public static final int ESTADO_DERROTA = 3;
    public static final int ESTADO_FIM_JOGO = 4;
    
    private int etapaAtual = 0; // 0 = inicial, 1 = etapa1, 2 = etapa2, 3 = etapa final
    private int estadoAtual = ESTADO_ESCOLHA;
    private Personagem jogador;
    private Random random;
    private int bonusDanoProximoInimigo = 0;
    
    private String narrativaAtual = "";
    private String[] opcoesAtuais = new String[0];
    private Inimigo inimigoAtual = null;
    private SistemaBatalha sistemaBatalha = null;
    
    public GerenciadorHistoria(Personagem jogador) {
        this.jogador = jogador;
        this.random = new Random();
    }
    
    /**
     * Inicia a etapa 1
     */
    public void iniciarEtapa1() {
        etapaAtual = 1;
        estadoAtual = ESTADO_ESCOLHA;
        narrativaAtual = "--- ETAPA 1: A Encruzilhada do Suspiro ---\n\n" +
                "A lua minguante repousa sobre o Caminho do Suspiro, onde a estrada se divide em duas trilhas.\n" +
                "O vento sopra dos dois lados — frio e indeciso.\n\n" +
                "Qual caminho você escolhe?";
        
        opcoesAtuais = new String[]{
            "1. Vereda da Névoa (mais segura, porém desgastante)",
            "2. Senda das Ruínas (mais arriscada, porém promissora)"
        };
    }
    
    /**
     * Processa a escolha do jogador na etapa 1
     */
    public void processarEscolhaEtapa1(int escolha) {
        if (escolha == 1) {
            narrativaAtual = "A névoa lhe abraça com dedos frios. Galhos arranham sua pele, pedras traem seus passos.\n";
            
            if (random.nextBoolean()) {
                narrativaAtual += "\nVocê sofre 10 de dano.\n";
                jogador.setPontosVida(jogador.getPontosVida() - 10);
            } else {
                narrativaAtual += "\nVocê avança com cautela e evita os galhos cortantes.\n";
            }
            
            narrativaAtual += "\nUm Sapo Gigante acorda com o barulho e salta até você enfurecido!";
            inimigoAtual = new Inimigo("Sapo Gigante", 50, 10, 5, 1);
            inimigoAtual.getInventario().adicionarItem(new Item("Língua de Sapo Gigante", "Troféu", 0, 1, null));
            
        } else if (escolha == 2) {
            narrativaAtual = "Símbolos antigos se acendem ao seu passo. Um puxão invisível remexe seus pertences…\n";
            
            if (random.nextBoolean()) {
                narrativaAtual += "\nVocê perde um item aleatório.\n";
                jogador.getInventario().removerItemAleatorio();
            } else {
                narrativaAtual += "\nVocê protege seus pertences a tempo e não perde nada.\n";
            }
            
            narrativaAtual += "\nDas sombras, uma Pantera de Duas Cabeças salta para atacar!";
            inimigoAtual = new Inimigo("Pantera de Duas Cabeças", 70, 12, 3, 1);
            inimigoAtual.getInventario().adicionarItem(new Item("Presa Gêmea Obsidiana", "Troféu", 0, 1, null));
        }
        
        iniciarBatalha();
    }
    
    /**
     * Inicia a etapa 2
     */
    public void iniciarEtapa2() {
        etapaAtual = 2;
        estadoAtual = ESTADO_ESCOLHA;
        narrativaAtual = "--- ETAPA 2: O Campo de Magma Adormecido ---\n\n" +
                "Após deixar a floresta úmida para trás, o calor começa a crescer.\n" +
                "O chão treme sob passos incertos — diante de você, estende-se o Campo de Magma Adormecido.\n\n" +
                "Qual caminho você escolhe?";
        
        opcoesAtuais = new String[]{
            "1. Atravessar rapidamente saltando entre as rochas incandescentes",
            "2. Descer por um desnível e contornar as fendas"
        };
    }
    
    /**
     * Processa a escolha do jogador na etapa 2
     */
    public void processarEscolhaEtapa2(int escolha) {
        if (escolha == 1) {
            boolean saltoBemSucedido = random.nextBoolean();
            if (saltoBemSucedido) {
                narrativaAtual = "Você corre e salta de pedra em pedra. O calor lambe seus pés.\n" +
                        "Você se sente motivado! Ganhando +7 de dano contra o próximo inimigo!";
                bonusDanoProximoInimigo = 7;
            } else {
                narrativaAtual = "Você corre e salta de pedra em pedra. Um deslize, um estalo, e a dor o alcança.\n" +
                        "Você sofre 15 de dano.";
                jogador.setPontosVida(jogador.getPontosVida() - 15);
            }
        } else if (escolha == 2) {
            narrativaAtual = "Com cautela, você se arrasta entre rochas escuras e fumaça.\n" +
                    "Entre fendas resfriadas, encontra um brilho esverdeado — uma erva rara nascida do fogo.\n\n" +
                    "Você ganhou: Erva de Cinza!";
            jogador.getInventario().adicionarItem(new Item("Erva de Cinza", "Restaura 25 HP.", 25, 1, TipoEfeito.CURA));
        }
        
        narrativaAtual += "\n\nNo caminho oposto, emergindo das chamas, surge Brukkar!\n" +
                "A armadura marcada por fuligem, o mangual incandescente gotejando faíscas.";
        
        inimigoAtual = new Inimigo("Brukkar, o Portador da Corrente", 80, 15, 10, 3);
        inimigoAtual.getInventario().adicionarItem(new Item("Corrente Chamuscada", "Troféu", 0, 1, null));
        
        iniciarBatalha();
    }
    
    /**
     * Inicia uma batalha
     */
    private void iniciarBatalha() {
        if (inimigoAtual != null) {
            sistemaBatalha = new SistemaBatalha(jogador, inimigoAtual);
            estadoAtual = ESTADO_BATALHA;
            narrativaAtual += "\n\n" + sistemaBatalha.getLogBatalha();
            opcoesAtuais = new String[]{"ATACAR", "FUGIR"};
        }
    }
    
    /**
     * Processa ação durante a batalha
     */
    public void processarAcaoBatalha(int acao) {
        if (sistemaBatalha == null || sistemaBatalha.isBatalhaAcabou()) return;
        
        if (acao == 1) {
            // Atacar
            sistemaBatalha.atacarComJogador();
        } else if (acao == 2) {
            // Fugir
            sistemaBatalha.tentarFugir();
        }
        
        narrativaAtual = sistemaBatalha.getLogBatalha();
        
        // Verifica se a batalha acabou
        if (sistemaBatalha.isBatalhaAcabou()) {
            if (sistemaBatalha.isJogadorVenceu()) {
                estadoAtual = ESTADO_VITORIA;
                opcoesAtuais = new String[]{"CONTINUAR", ""};
                
                // Jogador sobe de nível ao vencer
                jogador.subirNivel();
                
                // Se foi a batalha da etapa 1, vai para etapa 2
                if (etapaAtual == 1) {
                    narrativaAtual += "\n\nVocê venceu a primeira etapa!";
                } else if (etapaAtual == 2) {
                    narrativaAtual += "\n\nVocê avança para a etapa final!";
                }
            } else if (sistemaBatalha.isJogadorFugiu()) {
                // Jogador fugiu com sucesso - avança para a próxima etapa
                estadoAtual = ESTADO_VITORIA;
                opcoesAtuais = new String[]{"CONTINUAR", ""};
                narrativaAtual += "\n\nVocê conseguiu escapar! Agora deve avançar...";
            } else {
                estadoAtual = ESTADO_DERROTA;
                opcoesAtuais = new String[]{"VOLTAR AO MENU", ""};
            }
        } else {
            opcoesAtuais = new String[]{"ATACAR", "FUGIR"};
        }
    }
    
    /**
     * Usa um item durante a batalha
     */
    public boolean usarItemBatalha(String nomeItem) {
        if (sistemaBatalha == null || sistemaBatalha.isBatalhaAcabou()) return false;
        
        boolean usou = sistemaBatalha.usarItem(nomeItem);
        
        if (usou) {
            narrativaAtual = sistemaBatalha.getLogBatalha();
            
            // Verifica se a batalha acabou após usar item
            if (sistemaBatalha.isBatalhaAcabou()) {
                if (sistemaBatalha.isJogadorVenceu()) {
                    // Jogador sobe de nível ao vencer
                    jogador.subirNivel();
                    estadoAtual = ESTADO_VITORIA;
                    opcoesAtuais = new String[]{"CONTINUAR", ""};
                    
                } else {
                    estadoAtual = ESTADO_DERROTA;
                    opcoesAtuais = new String[]{"VOLTAR AO MENU", ""};
                }
            } else {
                opcoesAtuais = new String[]{"ATACAR", "FUGIR"};
            }
        }
        
        return usou;
    }
    
    /**
     * Inicia a etapa final
     */
    public void iniciarEtapaFinal() {
        etapaAtual = 3;
        estadoAtual = ESTADO_ESCOLHA;
        narrativaAtual = "--- ETAPA FINAL: O Portão do Véu Quebrado ---\n\n" +
                "As torres do Castelo do Véu Quebrado perfuram a neblina.\n" +
                "Um portão colossal se ergue, coberto de runas que parecem observar.\n\n" +
                "Como você deseja entrar no castelo?";
        
        opcoesAtuais = new String[]{
            "1. Arrombar o portão principal com força bruta",
            "2. Procurar uma entrada secreta pelos esgotos"
        };
    }
    
    /**
     * Processa a escolha do jogador na etapa final
     */
    public void processarEscolhaEtapaFinal(int escolha) {
        if (escolha == 1) {
            narrativaAtual = "Você investe contra o portão. As runas piscam e um repuxo invisível o lança para trás.\n" +
                    "Você sofre 20 de dano.\n\n";
            jogador.setPontosVida(jogador.getPontosVida() - 20);
        } else if (escolha == 2) {
            narrativaAtual = "Por baixo da muralha, uma grade enferrujada cede. O cheiro é terrível, mas você encontra algo útil.\n" +
                    "Você ganhou: Frasco de Fôlego! (Aumenta Ataque +5)\n\n";
            jogador.getInventario().adicionarItem(new Item("Frasco de Fôlego", "Aumenta o ataque.", 5, 1, TipoEfeito.AUMENTO_ATAQUE));
        }
        
        narrativaAtual += "Do além-túmulo, ergue-se Balduran, o Imperador Esqueleto — manto puído sobre ossos entalhados, " +
                "olhos vazios que ardem em brasa pálida.\n" +
                "Os grilhões no portão se soltam com um lamento antigo. A coroa quebrada tilinta enquanto ele aponta a espada real.\n\n" +
                "'Nenhum passo profanará meu domínio.'\n\n" +
                "Prepare-se para a batalha final!";
        
        inimigoAtual = new Inimigo("Balduran, o Imperador Esqueleto", 150, 25, 15, 5);
        inimigoAtual.getInventario().adicionarItem(new Item("Coroa Estilhaçada", "Troféu", 0, 1, null));
        
        iniciarBatalha();
    }
    
    /**
     * Continua para a próxima etapa após vitória
     */
    public void continuarProximaEtapa() {
        if (etapaAtual == 1) {
            iniciarEtapa2();
        } else if (etapaAtual == 2) {
            iniciarEtapaFinal();
        } else if (etapaAtual == 3) {
            narrativaAtual = "--- FIM DE JOGO ---\n\n" +
                    "PARABÉNS! Você derrotou o Rei das Ruínas e concluiu sua jornada!\n" +
                    "Vale-Cinzento foi salvo por seu heroísmo.\n\n" +
                    "FIM.";
            estadoAtual = ESTADO_FIM_JOGO;
            opcoesAtuais = new String[]{"VOLTAR AO MENU", ""};
        }
    }
    
    // Getters
    public String getNarrativaAtual() {
        return narrativaAtual;
    }
    
    public String[] getOpcoesAtuais() {
        return opcoesAtuais;
    }
    
    public int getEtapaAtual() {
        return etapaAtual;
    }
    
    public int getEstadoAtual() {
        return estadoAtual;
    }
    
    public Inimigo getInimigoAtual() {
        return inimigoAtual;
    }
    
    public SistemaBatalha getSistemaBatalha() {
        return sistemaBatalha;
    }
    
    public void setNarrativaAtual(String narrativa) {
        this.narrativaAtual = narrativa;
    }
    
    public void setOpcoesAtuais(String[] opcoes) {
        this.opcoesAtuais = opcoes;
    }
}


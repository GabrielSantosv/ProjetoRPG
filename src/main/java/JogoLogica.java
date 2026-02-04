import java.util.Random;

/**
 * Classe que gerencia a lógica do jogo RPG
 * Faz a ponte entre a GUI e as classes do jogo
 */
public class JogoLogica {
    
    private Personagem jogador;
    private Random random;
    private JogoGUI gui;
    private GerenciadorHistoria gerenciadorHistoria;
    
    private boolean jogadorEhMago = false;
    
    public JogoLogica(JogoGUI gui) {
        this.gui = gui;
        this.random = new Random();
    }

    public JogoGUI getGui() {
        return gui;
    }
    
    /**
     * Cria um personagem baseado no tipo escolhido
     */
    public void criarPersonagem(String nome, int tipoClasse) {
        switch (tipoClasse) {
            case 1:
                jogador = new Guerreiro(nome);
                jogadorEhMago = false;
                break;
            case 2:
                jogador = new Mago(nome);
                jogadorEhMago = true;
                break;
            case 3:
                jogador = new Arqueiro(nome);
                jogadorEhMago = false;
                break;
            default:
                jogador = new Guerreiro(nome);
                jogadorEhMago = false;
                break;
        }
        
        // Cria o gerenciador de história
        gerenciadorHistoria = new GerenciadorHistoria(jogador);
    }
    
    // Getters
    public Personagem getJogador() {
        return jogador;
    }
    
    public GerenciadorHistoria getGerenciadorHistoria() {
        return gerenciadorHistoria;
    }
    
    public boolean isJogadorEhMago() {
        return jogadorEhMago;
    }
    
    public Random getRandom() {
        return random;
    }
}

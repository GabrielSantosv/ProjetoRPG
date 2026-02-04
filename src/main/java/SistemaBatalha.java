import java.util.Random;

/**
 * Sistema de combate do RPG
 * Gerencia batalhas entre jogador e inimigos
 */
public class SistemaBatalha {
    
    private Personagem jogador;
    private Inimigo inimigo;
    private Random random;
    private String logBatalha = "";
    private boolean batalhaAcabou = false;
    private boolean jogadorVenceu = false;
    private boolean jogadorFugiu = false;
    private int inimigoVidaMax;
    
    public SistemaBatalha(Personagem jogador, Inimigo inimigo) {
        this.jogador = jogador;
        this.inimigo = inimigo;
        this.random = new Random();
        this.inimigoVidaMax = inimigo.getPontosVida();
        this.logBatalha = ">>> INICIANDO BATALHA <<<\n";
        this.logBatalha += "Seu oponente: " + inimigo.getNome() + "\n";
        this.logBatalha += "Vida do inimigo: " + inimigo.getPontosVida() + "\n\n";
    }
    
    /**
     * Simula um turno de ataque do jogador
     */
    public void atacarComJogador() {
        if (batalhaAcabou) return;
        
        // Calcula dano
        int dano = jogador.getAtaque() + random.nextInt(10) - 5;
        dano = Math.max(1, dano); // Mínimo 1 de dano
        
        int defesaInimigo = inimigo.getDefesa();
        int danoFinal = Math.max(1, dano - defesaInimigo / 2);
        
        inimigo.setPontosVida(inimigo.getPontosVida() - danoFinal);
        
        logBatalha += ">>> Sua vez!\n";
        logBatalha += "Você causou " + danoFinal + " de dano!\n";
        logBatalha += inimigo.getNome() + " tem " + Math.max(0, inimigo.getPontosVida()) + " de vida.\n\n";
        
        // Verifica se inimigo morreu
        if (inimigo.getPontosVida() <= 0) {
            finalizarBatalha(true);
            return;
        }
        
        // Inimigo contra-ataca
        turnoInimigo();
    }
    
    /**
     * Turno de ataque do inimigo
     */
    private void turnoInimigo() {
        if (inimigo.getPontosVida() <= 0) return;
        
        int dano = inimigo.getAtaque() + random.nextInt(10) - 5;
        dano = Math.max(1, dano);
        
        int defesaJogador = jogador.getDefesa();
        int danoFinal = Math.max(1, dano - defesaJogador / 2);
        
        jogador.setPontosVida(jogador.getPontosVida() - danoFinal);
        
        logBatalha += ">>> Turno do inimigo!\n";
        logBatalha += inimigo.getNome() + " causou " + danoFinal + " de dano!\n";
        logBatalha += "Sua vida agora é " + Math.max(0, jogador.getPontosVida()) + "\n\n";
        
        // Verifica se jogador morreu
        if (jogador.getPontosVida() <= 0) {
            finalizarBatalha(false);
        }
    }
    
    /**
     * Usa um item durante a batalha
     */
    public boolean usarItem(String nomeItem) {
        if (batalhaAcabou) return false;
        
        // Tenta usar o item
        boolean usouComSucesso = jogador.getInventario().usarItem(nomeItem, jogador);
        
        if (usouComSucesso) {
            logBatalha += ">>> Sua vez!\n";
            logBatalha += "Você usou " + nomeItem + "!\n";
            logBatalha += "Sua vida agora é " + jogador.getPontosVida() + "\n\n";
            
            // Inimigo contra-ataca
            turnoInimigo();
            return true;
        } else {
            logBatalha += "Não conseguiu usar " + nomeItem + "!\n\n";
            return false;
        }
    }
    
    /**
     * Tenta fugir da batalha (50% de chance)
     */
    public boolean tentarFugir() {
        if (batalhaAcabou) return false;
        
        boolean conseguiuFugir = random.nextDouble() < 0.5;
        
        if (conseguiuFugir) {
            logBatalha += ">>> Você conseguiu fugir da batalha!\n";
            finalizarBatalha(false, true); // Não é vitória, mas é fuga
            return true;
        } else {
            logBatalha += ">>> Você não conseguiu fugir!\n";
            turnoInimigo();
            return false;
        }
    }
    
    /**
     * Finaliza a batalha
     */
    private void finalizarBatalha(boolean vitoria, boolean fuga) {
        batalhaAcabou = true;
        jogadorVenceu = vitoria;
        jogadorFugiu = fuga;
        
        if (vitoria) {
            logBatalha += "\n>>> VITÓRIA! <<<\n";
            logBatalha += "Você derrotou " + inimigo.getNome() + "!\n";
            
            // Adiciona itens do inimigo ao inventário do jogador
            jogador.getInventario().adicionarInventario(inimigo.getInventario());
            
            logBatalha += "Você ganhou os troféus de " + inimigo.getNome() + "!\n";
        } else if (fuga) {
            logBatalha += "\nVocê fugiu com sucesso!\n";
        } else {
            logBatalha += "\n>>> DERROTA! <<<\n";
            logBatalha += "Você foi derrotado por " + inimigo.getNome() + "!\n";
        }
    }
    
    /**
     * Versão anterior do finalizarBatalha para compatibilidade
     */
    private void finalizarBatalha(boolean vitoria) {
        finalizarBatalha(vitoria, false);
    }
    
    // Getters
    public String getLogBatalha() {
        return logBatalha;
    }
    
    public boolean isBatalhaAcabou() {
        return batalhaAcabou;
    }
    
    public boolean isJogadorVenceu() {
        return jogadorVenceu;
    }
    
    public boolean isJogadorFugiu() {
        return jogadorFugiu;
    }
    
    public Personagem getJogador() {
        return jogador;
    }
    
    public Inimigo getInimigo() {
        return inimigo;
    }

    public int getInimigoVidaMax() {
        return inimigoVidaMax;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Janela principal do jogo RPG com interface gráfica
 * Herda de JFrame para criar a janela principal
 */
public class JogoGUI extends JFrame {
    
    private JogoLogica jogoLogica;
    private TelaSelecaoPersonagem telaSelecao;
    private TelaJogo telaJogo;
    private TelaBatalha telaBatalha;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    
    public JogoGUI() {
        // Configurações da janela principal
        setTitle("Jogo RPG - Vale-Cinzento");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Tela cheia
        setLocationRelativeTo(null); // Centraliza a janela
        setResizable(true);
        setBackground(new Color(0, 0, 0)); // Preto puro
        
        // Inicializa o CardLayout para trocar de telas
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        // Cria a lógica do jogo
        jogoLogica = new JogoLogica(this);
        
        // Cria as telas
        telaSelecao = new TelaSelecaoPersonagem(jogoLogica);
        telaJogo = new TelaJogo(jogoLogica);
        telaBatalha = new TelaBatalha(jogoLogica);
        
        // Adiciona as telas ao painel com CardLayout
        cardPanel.add(telaSelecao, "SELECAO");
        cardPanel.add(telaJogo, "JOGO");
        cardPanel.add(telaBatalha, "BATALHA");
        
        // Adiciona o painel à janela
        add(cardPanel);
        
        // Mostra a tela inicial
        mostrarTelaSelecao();
        
        setVisible(true);
    }
    
    public void mostrarTelaSelecao() {
        cardLayout.show(cardPanel, "SELECAO");
    }
    
    public void mostrarTelaJogo() {
        telaJogo.atualizar();
        cardLayout.show(cardPanel, "JOGO");
    }

    public void mostrarTelaBatalha() {
        // Atualiza a tela de batalha antes de exibir
        if (telaBatalha != null) {
            telaBatalha.atualizar();
        }
        cardLayout.show(cardPanel, "BATALHA");
    }
    
    public static void main(String[] args) {
        // Usa SwingUtilities para garantir que a GUI é criada na Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                new JogoGUI();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Erro ao iniciar o jogo: " + e.getMessage(), 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

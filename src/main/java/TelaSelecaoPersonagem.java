import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Tela de seleção de personagem
 * Utiliza JPanel, JLabel, JTextField, JComboBox e JButton
 */
public class TelaSelecaoPersonagem extends JPanel {
    
    private JogoLogica jogoLogica;
    private JTextField campoNome;
    private JComboBox<String> comboClasses;
    private JButton botaoIniciar;
    private JLabel labelImagemSelecao;
    private JLabel labelDescricaoClasse;
    
    public TelaSelecaoPersonagem(JogoLogica jogoLogica) {
        this.jogoLogica = jogoLogica;
        
        // Define o layout principal como BorderLayout
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(20, 20, 25)); // Azul-cinza escuro
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Painel superior com título
        JPanel painelTitulo = criarPainelTitulo();
        add(painelTitulo, BorderLayout.NORTH);
        
        // Painel central com layout lado-a-lado (texto esquerda, imagem direita)
        JPanel painelCentral = criarPainelCentral();
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel inferior com botão
        JPanel painelBotoes = criarPainelBotoes();
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelTitulo() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(20, 20, 25)); // Azul-cinza escuro
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 200, 120, 100)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel titulo = new JLabel("⚔️ BEM-VINDO A VALE-CINZENTO ⚔️");
        titulo.setFont(new Font("Serif", Font.BOLD, 38));
        titulo.setForeground(new Color(255, 215, 100)); // Dourado brilhante
        
        JLabel subtitulo = new JLabel("✨ Crie seu personagem e escolha seu destino ✨");
        subtitulo.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitulo.setForeground(new Color(200, 200, 220)); // Branco-azulado
        
        JLabel lore = new JLabel("<html><center>Uma terra sob a maldição de Umbreterna aguarda por um herói corajoso</center></html>");
        lore.setFont(new Font("Arial", Font.PLAIN, 12));
        lore.setForeground(new Color(150, 150, 170)); // Cinza suave
        
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lore.setAlignmentX(Component.CENTER_ALIGNMENT);
        painel.add(titulo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(subtitulo);
        painel.add(Box.createVerticalStrut(8));
        painel.add(lore);
        
        return painel;
    }
    
    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(20, 20, 25));
        painel.setLayout(new BorderLayout(20, 20));
        
        // Painel ESQUERDO com formulário (texto)
        JPanel painelFormulario = criarPainelFormulario();
        painelFormulario.setPreferredSize(new Dimension(400, 0));
        painel.add(painelFormulario, BorderLayout.WEST);
        
        // Painel DIREITO com imagem
        JPanel painelImagem = new JPanel(new BorderLayout());
        painelImagem.setBackground(new Color(40, 35, 50)); // Roxo escuro
        painelImagem.setBorder(BorderFactory.createLineBorder(new Color(150, 140, 160), 2));
        labelImagemSelecao = new JLabel("[Personagem]");
        labelImagemSelecao.setForeground(new Color(180, 170, 190));
        labelImagemSelecao.setFont(new Font("Arial", Font.ITALIC, 16));
        labelImagemSelecao.setHorizontalAlignment(SwingConstants.CENTER);
        labelImagemSelecao.setVerticalAlignment(SwingConstants.CENTER);
        painelImagem.add(labelImagemSelecao, BorderLayout.CENTER);
        painel.add(painelImagem, BorderLayout.CENTER);
        
        // Agora que o labelImagemSelecao e comboClasses existem, registra listener no combo e inicializa a imagem
        comboClasses.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sel = ((String) comboClasses.getSelectedItem());
                // Remove emojis e espaços extras para pegar apenas o nome da classe
                String nomeClasse = sel.replaceAll("[^a-zA-ZÀ-ÿ]", "").toLowerCase();
                carregarImagemParaLabelSelecao(nomeClasse + ".png", labelImagemSelecao);
            }
        });
        // inicializa com a classe atual
        String classeAtual = ((String)comboClasses.getSelectedItem());
        String nomeClasseAtual = classeAtual.replaceAll("[^a-zA-ZÀ-ÿ]", "").toLowerCase();
        carregarImagemParaLabelSelecao(nomeClasseAtual + ".png", labelImagemSelecao);

        return painel;
    }
    
    private JPanel criarPainelFormulario() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(25, 25, 35)); // Azul-cinza mais escuro
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 160, 110), 3),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        // Título da seção
        JLabel tituloSecao = new JLabel("📜 CRIAÇÃO DO HERÓI");
        tituloSecao.setFont(new Font("Arial", Font.BOLD, 16));
        tituloSecao.setForeground(new Color(255, 215, 100));
        tituloSecao.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Linha separadora
        JSeparator separador1 = new JSeparator();
        separador1.setForeground(new Color(180, 160, 110));
        separador1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separador1.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label e campo de nome com ícone
        JLabel labelNome = new JLabel("🗡️ Nome do Herói:");
        labelNome.setFont(new Font("Arial", Font.BOLD, 14));
        labelNome.setForeground(new Color(220, 200, 120)); // Dourado suave
        labelNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        campoNome = new JTextField();
        campoNome.setFont(new Font("Arial", Font.PLAIN, 14));
        campoNome.setBackground(new Color(15, 15, 25)); // Mais escuro
        campoNome.setForeground(new Color(240, 240, 250));
        campoNome.setCaretColor(new Color(255, 215, 100)); // Dourado cursor
        campoNome.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 160, 110), 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campoNome.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoNome.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Adiciona placeholder text
        campoNome.setToolTipText("Digite o nome do seu herói");
        
        // Linha separadora
        JSeparator separador2 = new JSeparator();
        separador2.setForeground(new Color(180, 160, 110));
        separador2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separador2.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Label e combobox de classe com ícone
        JLabel labelClasse = new JLabel("⚔️ Escolha sua Classe:");
        labelClasse.setFont(new Font("Arial", Font.BOLD, 14));
        labelClasse.setForeground(new Color(220, 200, 120)); // Dourado suave
        labelClasse.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        String[] classes = {"⚔️ Guerreiro", "🔮 Mago", "🏹 Arqueiro"};
        comboClasses = new JComboBox<>(classes);
        comboClasses.setFont(new Font("Arial", Font.BOLD, 13));
        comboClasses.setBackground(new Color(15, 15, 25));
        comboClasses.setForeground(new Color(240, 240, 250));
        comboClasses.setBorder(BorderFactory.createLineBorder(new Color(180, 160, 110), 2));
        comboClasses.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        comboClasses.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        // Descrição detalhada da classe selecionada
        labelDescricaoClasse = new JLabel();
        labelDescricaoClasse.setFont(new Font("Arial", Font.PLAIN, 12));
        labelDescricaoClasse.setForeground(new Color(220, 220, 230));
        labelDescricaoClasse.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelDescricaoClasse.setVerticalAlignment(SwingConstants.TOP);
        atualizarDescricaoClasse(0); // Inicializa com Guerreiro
        
        // Listener para atualizar descrição quando mudar a classe
        comboClasses.addActionListener(e -> atualizarDescricaoClasse(comboClasses.getSelectedIndex()));
        
        painel.add(tituloSecao);
        painel.add(Box.createVerticalStrut(10));
        painel.add(separador1);
        painel.add(Box.createVerticalStrut(15));
        painel.add(labelNome);
        painel.add(Box.createVerticalStrut(8));
        painel.add(campoNome);
        painel.add(Box.createVerticalStrut(20));
        painel.add(separador2);
        painel.add(Box.createVerticalStrut(15));
        painel.add(labelClasse);
        painel.add(Box.createVerticalStrut(8));
        painel.add(comboClasses);
        painel.add(Box.createVerticalStrut(20));
        painel.add(labelDescricaoClasse);
        
        return painel;
    }
    
    private void atualizarDescricaoClasse(int indice) {
        String descricao = "";
        
        switch(indice) {
            case 0: // Guerreiro
                descricao = "<html><div style='width: 350px; padding: 15px; background-color: rgba(40, 30, 30, 0.6); border-left: 4px solid #C9A55A;'>" +
                    "<b style='color:#FFD764; font-size: 15px;'>⚔️ GUERREIRO</b><br>" +
                    "<span style='color:#B8A070; font-size: 11px; font-style: italic;'>Aço e Disciplina</span><br><br>" +
                    "<div style='background-color: rgba(20, 20, 30, 0.8); padding: 10px; border-radius: 5px;'>" +
                    "<b style='color:#90EE90; font-size: 12px;'>❤️ VIDA:</b> <span style='color:#FFF; font-size: 13px;'>150</span> &nbsp;&nbsp; " +
                    "<b style='color:#FFB6C1; font-size: 12px;'>⚔️ ATK:</b> <span style='color:#FFF; font-size: 13px;'>20</span> &nbsp;&nbsp; " +
                    "<b style='color:#87CEEB; font-size: 12px;'>🛡️ DEF:</b> <span style='color:#FFF; font-size: 13px;'>15</span>" +
                    "</div><br>" +
                    "<b style='color:#DCC878; font-size: 12px;'>🎒 EQUIPAMENTO INICIAL:</b><br>" +
                    "<span style='color:#DCDCE6; font-size: 11px;'>" +
                    "  ⚗️ Poção de Cura <span style='color:#90EE90;'>(+30 HP)</span><br>" +
                    "  🍷 Elixir de Vigor <span style='color:#87CEEB;'>(+10 DEF)</span><br><br>" +
                    "<span style='color:#AAA; font-size: 11px; font-style: italic;'>» Tanque resistente, excelente para combate direto e sobrevivência prolongada</span>" +
                    "</span></div></html>";
                break;
                
            case 1: // Mago
                descricao = "<html><div style='width: 350px; padding: 15px; background-color: rgba(30, 30, 50, 0.6); border-left: 4px solid #9B7EDE;'>" +
                    "<b style='color:#C89FFF; font-size: 15px;'>🔮 MAGO</b><br>" +
                    "<span style='color:#9B7EDE; font-size: 11px; font-style: italic;'>Mestre dos Segredos Antigos</span><br><br>" +
                    "<div style='background-color: rgba(20, 20, 30, 0.8); padding: 10px; border-radius: 5px;'>" +
                    "<b style='color:#90EE90; font-size: 12px;'>❤️ VIDA:</b> <span style='color:#FFF; font-size: 13px;'>100</span> &nbsp;&nbsp; " +
                    "<b style='color:#FFB6C1; font-size: 12px;'>⚔️ ATK:</b> <span style='color:#FFF; font-size: 13px;'>25</span> &nbsp;&nbsp; " +
                    "<b style='color:#87CEEB; font-size: 12px;'>🛡️ DEF:</b> <span style='color:#FFF; font-size: 13px;'>10</span>" +
                    "</div><br>" +
                    "<b style='color:#DCC878; font-size: 12px;'>🎒 EQUIPAMENTO INICIAL:</b><br>" +
                    "<span style='color:#DCDCE6; font-size: 11px;'>" +
                    "  ⚗️ Poção de Cura <span style='color:#90EE90;'>(+30 HP)</span><br>" +
                    "  🧪 Elixir do Sábio <span style='color:#FFB6C1;'>(+8 ATK)</span><br><br>" +
                    "<span style='color:#C89FFF; font-size: 11px;'>⭐ ESPECIAL:</span> <span style='color:#AAA; font-size: 11px; font-style: italic;'>Acesso ao final secreto!</span><br>" +
                    "<span style='color:#AAA; font-size: 11px; font-style: italic;'>» Alto dano mágico, glass cannon devastador</span>" +
                    "</span></div></html>";
                break;
                
            case 2: // Arqueiro
                descricao = "<html><div style='width: 350px; padding: 15px; background-color: rgba(30, 50, 30, 0.6); border-left: 4px solid #7EC97E;'>" +
                    "<b style='color:#9FFF9F; font-size: 15px;'>🏹 ARQUEIRO</b><br>" +
                    "<span style='color:#7EC97E; font-size: 11px; font-style: italic;'>Olhos Atentos, Passos Leves</span><br><br>" +
                    "<div style='background-color: rgba(20, 20, 30, 0.8); padding: 10px; border-radius: 5px;'>" +
                    "<b style='color:#90EE90; font-size: 12px;'>❤️ VIDA:</b> <span style='color:#FFF; font-size: 13px;'>120</span> &nbsp;&nbsp; " +
                    "<b style='color:#FFB6C1; font-size: 12px;'>⚔️ ATK:</b> <span style='color:#FFF; font-size: 13px;'>22</span> &nbsp;&nbsp; " +
                    "<b style='color:#87CEEB; font-size: 12px;'>🛡️ DEF:</b> <span style='color:#FFF; font-size: 13px;'>12</span>" +
                    "</div><br>" +
                    "<b style='color:#DCC878; font-size: 12px;'>🎒 EQUIPAMENTO INICIAL:</b><br>" +
                    "<span style='color:#DCDCE6; font-size: 11px;'>" +
                    "  ⚗️ Poção de Cura <span style='color:#90EE90;'>(+30 HP)</span><br>" +
                    "  🦅 Elixir Olho de Águia <span style='color:#FFB6C1;'>(+7 ATK)</span><br><br>" +
                    "<span style='color:#AAA; font-size: 11px; font-style: italic;'>» Equilíbrio perfeito entre dano e sobrevivência</span>" +
                    "</span></div></html>";
                break;
        }
        
        labelDescricaoClasse.setText(descricao);
    }

    // Carrega imagem para o label de seleção (procura em /images/ no classpath ou em src/main/resources/images)
    private void carregarImagemParaLabelSelecao(String relativePath, JLabel label) {
        if (relativePath == null || relativePath.isEmpty()) {
            label.setIcon(null);
            label.setText("");
            return;
        }
        try {
            BufferedImage img = null;
            
            // Tenta diferentes variações do nome do arquivo
            String[] variacoes = {
                relativePath.replace(".png", "1.png"), // guerreiro.png -> guerreiro1.png
                relativePath, // guerreiro.png
                relativePath.replace(".png", ".jpg"), // guerreiro.jpg
                relativePath.replace(".png", "1.jpg") // guerreiro1.jpg
            };
            
            // Primeiro tenta pelo classpath (quando executado via JAR)
            for (String variacao : variacoes) {
                java.net.URL res = getClass().getResource("/images/" + variacao);
                if (res != null) {
                    img = ImageIO.read(res);
                    if (img != null) break;
                }
            }
            
            // Se não encontrou pelo classpath, tenta pelo sistema de arquivos
            if (img == null) {
                for (String variacao : variacoes) {
                    File f = new File("src/main/resources/images/" + variacao);
                    if (!f.exists()) {
                        f = new File("resources/images/" + variacao);
                    }
                    if (f.exists()) {
                        img = ImageIO.read(f);
                        if (img != null) break;
                    }
                }
            }
            
            if (img != null) {
                // Tamanho padronizado para todas as imagens
                int w = 500;
                int h = 400;
                Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
                label.setText("");
            } else {
                label.setIcon(null);
                label.setText("[Imagem não encontrada]");
            }
        } catch (IOException e) {
            label.setIcon(null);
            label.setText("[Erro ao carregar imagem]");
            e.printStackTrace(); // Para debug
        }
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(20, 20, 25)); // Azul-cinza escuro
        painel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        painel.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, new Color(220, 200, 120, 100)));
        
        botaoIniciar = new JButton("⚔️ INICIAR JORNADA ⚔️");
        botaoIniciar.setFont(new Font("Arial", Font.BOLD, 20));
        botaoIniciar.setBackground(new Color(200, 170, 100)); // Dourado vibrante
        botaoIniciar.setForeground(new Color(20, 20, 30)); // Texto escuro
        botaoIniciar.setFocusPainted(false);
        botaoIniciar.setPreferredSize(new Dimension(320, 70));
        botaoIniciar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botaoIniciar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 100), 3),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Efeito hover
        botaoIniciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                botaoIniciar.setBackground(new Color(255, 215, 100));
                botaoIniciar.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 235, 150), 4),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                botaoIniciar.setBackground(new Color(200, 170, 100));
                botaoIniciar.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(255, 215, 100), 3),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
                ));
            }
        });
        
        // Adiciona listener ao botão
        botaoIniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });
        
        painel.add(botaoIniciar);
        
        return painel;
    }
    
    private void iniciarJogo() {
        String nome = campoNome.getText().trim();
        
        // Validação do nome
        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "⚠️ Por favor, digite um nome para seu herói!\n\nTodo grande aventureiro precisa de um nome!", 
                    "⚔️ Nome Necessário", 
                    JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocus();
            return;
        }
        
        if (nome.length() < 3) {
            JOptionPane.showMessageDialog(this, 
                    "⚠️ O nome deve ter pelo menos 3 caracteres!\n\nEscolha um nome digno de um herói.", 
                    "⚔️ Nome Muito Curto", 
                    JOptionPane.WARNING_MESSAGE);
            campoNome.requestFocus();
            return;
        }
        
        int tipoClasse = comboClasses.getSelectedIndex() + 1;
        
        // Cria o personagem na lógica do jogo
        jogoLogica.criarPersonagem(nome, tipoClasse);
        
        // Mensagem de boas-vindas
        String classeNome = comboClasses.getSelectedItem().toString();
        JOptionPane.showMessageDialog(this, 
                "✨ Bem-vindo, " + nome + "!\n\n" +
                "Sua jornada como " + classeNome + " está prestes a começar.\n" +
                "Que os deuses da sorte estejam ao seu lado!", 
                "🎭 Que comece a aventura!", 
                JOptionPane.INFORMATION_MESSAGE);
        
        // Muda para a tela do jogo
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame instanceof JogoGUI) {
            ((JogoGUI) frame).mostrarTelaJogo();
        }
    }
}

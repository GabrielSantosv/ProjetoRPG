import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Tela de batalha que exibe o herói à esquerda e o inimigo à direita
 */
public class TelaBatalha extends JPanel {

    private JogoLogica jogoLogica;
    private GerenciadorHistoria gerenciador;

    private JLabel labelHero;
    private JLabel labelInimigo;
    private JTextPane areaLog;
    private StyledDocument logDoc;
    private Style normalStyle;
    private Style dmgStyle;
    private Style healStyle;
    private JProgressBar heroHpBar;
    private JProgressBar inimigoHpBar;
    private JLabel labelHeroInfo;
    private JLabel labelInimigoInfo;

    private JButton botaoAtacar;
    private JButton botaoFugir;
    private JButton botaoInventario;

    public TelaBatalha(JogoLogica jogoLogica) {
        this.jogoLogica = jogoLogica;
        setLayout(new BorderLayout(10,10));
        setBackground(new Color(18,18,22));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // (removido HUD superior) as barras de vida e informações serão mostradas diretamente
        // acima dos sprites para um visual integrado à arena.

        // Painel central: centraliza ambos os sprites horizontalmente
        JPanel painelCentro = new JPanel(new BorderLayout());
        painelCentro.setBackground(new Color(18,18,22));

        // Área central que contém os sprites alinhados no centro
        JPanel centerSprites = new JPanel();
        centerSprites.setBackground(new Color(18,18,22));
        centerSprites.setLayout(new BoxLayout(centerSprites, BoxLayout.X_AXIS));

        centerSprites.add(Box.createHorizontalGlue());

        // Herói (ligeiramente à esquerda) com HUD acima
        JPanel heroWrap = new JPanel();
        heroWrap.setOpaque(false);
        heroWrap.setLayout(new BoxLayout(heroWrap, BoxLayout.Y_AXIS));

        // small hero info above sprite
        labelHeroInfo = new JLabel(" ");
        labelHeroInfo.setForeground(new Color(220,220,230));
        labelHeroInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        heroHpBar = new JProgressBar();
        heroHpBar.setStringPainted(true);
        heroHpBar.setPreferredSize(new Dimension(160, 14));
        heroHpBar.setMaximum(100);
        heroHpBar.setValue(100);
        heroHpBar.setBackground(new Color(25,25,35));
        heroHpBar.setForeground(new Color(120,200,120));
        heroHpBar.setBorder(BorderFactory.createLineBorder(new Color(120,120,120), 1));
        heroHpBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelHero = new JLabel();
        labelHero.setHorizontalAlignment(SwingConstants.CENTER);
        labelHero.setVerticalAlignment(SwingConstants.CENTER);
        labelHero.setPreferredSize(new Dimension(240,160));
        labelHero.setAlignmentX(Component.CENTER_ALIGNMENT);

        heroWrap.add(Box.createVerticalStrut(8));
        heroWrap.add(labelHeroInfo);
        heroWrap.add(Box.createVerticalStrut(6));
        heroWrap.add(heroHpBar);
        heroWrap.add(Box.createVerticalStrut(8));
        heroWrap.add(labelHero);
        heroWrap.setBorder(BorderFactory.createEmptyBorder(40, 10, 10, 10));
        centerSprites.add(heroWrap);

        centerSprites.add(Box.createRigidArea(new Dimension(10,0)));

        // Inimigo (ligeiramente à direita) com HUD acima
        JPanel enemyWrap = new JPanel();
        enemyWrap.setOpaque(false);
        enemyWrap.setLayout(new BoxLayout(enemyWrap, BoxLayout.Y_AXIS));

        labelInimigoInfo = new JLabel(" ");
        labelInimigoInfo.setForeground(new Color(220,220,230));
        labelInimigoInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        inimigoHpBar = new JProgressBar();
        inimigoHpBar.setStringPainted(true);
        inimigoHpBar.setPreferredSize(new Dimension(160, 14));
        inimigoHpBar.setMaximum(100);
        inimigoHpBar.setValue(100);
        inimigoHpBar.setBackground(new Color(25,25,35));
        inimigoHpBar.setForeground(new Color(200,120,120));
        inimigoHpBar.setBorder(BorderFactory.createLineBorder(new Color(120,120,120), 1));
        inimigoHpBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        labelInimigo = new JLabel();
        labelInimigo.setHorizontalAlignment(SwingConstants.CENTER);
        labelInimigo.setVerticalAlignment(SwingConstants.CENTER);
        labelInimigo.setPreferredSize(new Dimension(240,160));
        labelInimigo.setAlignmentX(Component.CENTER_ALIGNMENT);

        enemyWrap.add(Box.createVerticalStrut(8));
        enemyWrap.add(labelInimigoInfo);
        enemyWrap.add(Box.createVerticalStrut(6));
        enemyWrap.add(inimigoHpBar);
        enemyWrap.add(Box.createVerticalStrut(8));
        enemyWrap.add(labelInimigo);
        enemyWrap.setBorder(BorderFactory.createEmptyBorder(20, 10, 40, 10));
        centerSprites.add(enemyWrap);

        centerSprites.add(Box.createHorizontalGlue());

        // Log (registro) no lado direito — mantém largura fixa e estende verticalmente
        areaLog = new JTextPane();
        areaLog.setEditable(false);
        areaLog.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaLog.setBackground(new Color(25,25,35));
        areaLog.setForeground(new Color(220,220,230));
        areaLog.setMargin(new Insets(8,8,8,8));
        logDoc = areaLog.getStyledDocument();

        // styles
        StyleContext sc = new StyleContext();
        normalStyle = sc.addStyle("normal", null);
        StyleConstants.setForeground(normalStyle, new Color(220,220,230));

        dmgStyle = sc.addStyle("dmg", null);
        StyleConstants.setForeground(dmgStyle, new Color(240,120,120));
        StyleConstants.setBold(dmgStyle, true);

        healStyle = sc.addStyle("heal", null);
        StyleConstants.setForeground(healStyle, new Color(120,200,120));
        StyleConstants.setBold(healStyle, true);

        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setPreferredSize(new Dimension(300, 400));

        painelCentro.add(centerSprites, BorderLayout.CENTER);
        painelCentro.add(scroll, BorderLayout.EAST);

        add(painelCentro, BorderLayout.CENTER);

        // Painel inferior com caixa de ações ao estilo Pokémon
        JPanel caixaAcoes = new JPanel(new BorderLayout());
        caixaAcoes.setBackground(new Color(30,30,40));
        caixaAcoes.setBorder(BorderFactory.createLineBorder(new Color(140,130,90), 2));
        caixaAcoes.setPreferredSize(new Dimension(0, 180));

        // Painel esquerdo dentro da caixa para botões (grid 2x2) — ocupa toda a caixa para ficar grande
        JPanel botoesPanel = new JPanel(new GridLayout(0,2,18,18));
        botoesPanel.setBackground(new Color(30,30,40));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        botaoAtacar = criarBotaoAcao("ATACAR");
        botaoFugir = criarBotaoAcao("FUGIR");
        botaoInventario = criarBotaoAcao("INVENTÁRIO");
        // Adiciona os botões; GridLayout com 0 linhas criará linhas conforme necessário
        botoesPanel.add(botaoAtacar);
        botoesPanel.add(botaoFugir);
        botoesPanel.add(botaoInventario);

        // Remove ou esconde quaisquer botões sem texto para evitar componentes vazios
        esconderBotoesSemTexto(botoesPanel);

        // Ao clicar, encaminhar para as funções já existentes
        botaoAtacar.addActionListener(e -> { if (gerenciador != null) { gerenciador.processarAcaoBatalha(1); atualizar(); checarFimDeBatalha(); } });
        botaoFugir.addActionListener(e -> { if (gerenciador != null) { gerenciador.processarAcaoBatalha(2); atualizar(); checarFimDeBatalha(); } });
        botaoInventario.addActionListener(e -> mostrarInventarioBatalha());

        // Coloca apenas o painel de botões na caixa de ações para que ocupem toda a largura
        caixaAcoes.add(botoesPanel, BorderLayout.CENTER);

        add(caixaAcoes, BorderLayout.SOUTH);
    }

    // Oculta componentes JButton sem texto dentro do container para evitar botões vazios
    private void esconderBotoesSemTexto(Container container) {
        if (container == null) return;
        Component[] comps = container.getComponents();
        for (Component c : comps) {
            if (c instanceof JButton) {
                JButton b = (JButton) c;
                String t = b.getText();
                if (t == null || t.trim().isEmpty()) {
                    b.setVisible(false);
                }
            }
        }
        container.revalidate();
        container.repaint();
    }

    public void atualizar() {
        gerenciador = jogoLogica.getGerenciadorHistoria();
        if (gerenciador == null) return;

        // Atualiza o log de batalha preferencialmente através do sistema de batalha
        if (gerenciador.getSistemaBatalha() != null) {
            String narrative = gerenciador.getNarrativaAtual();
            String sbLog = gerenciador.getSistemaBatalha().getLogBatalha();

            // Se a narrativa já contém o cabeçalho da batalha (iniciado em GerenciadorHistoria),
            // então mostra apenas a narrativa (evita duplicar o log do sistema).
            if (narrative != null && narrative.contains(">>> INICIANDO BATALHA <<<")) {
                appendLogStyled(narrative);
            } else {
                String combined;
                if (narrative == null || narrative.trim().isEmpty()) combined = sbLog;
                else combined = narrative + "\n\n" + sbLog;
                appendLogStyled(combined);
            }
        } else {
            String narrative = gerenciador.getNarrativaAtual();
            appendLogStyled(narrative);
        }

        // Atualiza HUD superior (hero e inimigo)
        Personagem jogador = jogoLogica.getJogador();
        SistemaBatalha sb = gerenciador.getSistemaBatalha();
        Inimigo inimigo = gerenciador.getInimigoAtual();

        if (jogador != null) {
            int vidaMax = jogador.getClass().getSimpleName().equals("Guerreiro") ? 150 : 
                         (jogador.getClass().getSimpleName().equals("Mago") ? 100 : 120);
            vidaMax += (jogador.getNivel() - 1) * 20;
            heroHpBar.setMaximum(vidaMax);
            heroHpBar.setValue(jogador.getPontosVida());
            heroHpBar.setString(jogador.getPontosVida() + " / " + vidaMax);
            labelHeroInfo.setText(jogador.getNome() + " - Nível " + jogador.getNivel() + " | Ataque: " + jogador.getAtaque() + " | Defesa: " + jogador.getDefesa());
        } else {
            heroHpBar.setMaximum(100);
            heroHpBar.setValue(0);
            labelHeroInfo.setText(" ");
        }

        if (inimigo != null) {
            int inimigoMax = (sb != null) ? sb.getInimigoVidaMax() : inimigo.getPontosVida();
            inimigoHpBar.setMaximum(Math.max(1, inimigoMax));
            inimigoHpBar.setValue(inimigo.getPontosVida());
            inimigoHpBar.setString(inimigo.getPontosVida() + " / " + inimigoMax);
            labelInimigoInfo.setText(inimigo.getNome());
        } else {
            inimigoHpBar.setMaximum(100);
            inimigoHpBar.setValue(0);
            labelInimigoInfo.setText(" ");
        }

        // Atualiza sprites (usa as mesmas variáveis já obtidas acima)
        if (jogador != null) carregarSpriteHeroi(jogador, labelHero);
        else labelHero.setIcon(null);

        if (inimigo != null) carregarSpriteInimigo(inimigo, labelInimigo);
        else labelInimigo.setIcon(null);

        // Habilita/desabilita botões dependendo do estado
        int estado = gerenciador.getEstadoAtual();
        boolean emBatalha = estado == GerenciadorHistoria.ESTADO_BATALHA;
        botaoAtacar.setEnabled(emBatalha);
        botaoFugir.setEnabled(emBatalha);
        botaoInventario.setEnabled(true);
    }

    private void appendLogStyled(String text) {
        if (logDoc == null) return;
        try {
            // clear existing
            logDoc.remove(0, logDoc.getLength());
        } catch (BadLocationException e) {
            // ignore
        }

        if (text == null || text.isEmpty()) return;

        String[] lines = text.split("\\r?\\n");
        for (String line : lines) {
            String lower = line.toLowerCase();
            Style style = normalStyle;

            // damage-related keywords -> red
            if (lower.matches(".*\\b(dano|sofreu|sofre|causou|perdeu|morreu|derrotad)\\b.*")) {
                style = dmgStyle;
            }
            // healing-related keywords -> green
            else if (lower.matches(".*\\b(restaura|restaurou|restaur|cura|curou|recuper|ganhou|resta)\\b.*")) {
                style = healStyle;
            }

            try {
                logDoc.insertString(logDoc.getLength(), line + "\n", style);
            } catch (BadLocationException e) {
                // ignore
            }
        }

        // auto-scroll to bottom
        SwingUtilities.invokeLater(() -> {
            areaLog.setCaretPosition(areaLog.getDocument().getLength());
        });
    }

    private void checarFimDeBatalha() {
        if (gerenciador == null) return;
        if (gerenciador.getEstadoAtual() != GerenciadorHistoria.ESTADO_BATALHA) {
            // Volta para a tela principal do jogo
            JogoGUI gui = jogoLogica.getGui();
            if (gui != null) {
                gui.mostrarTelaJogo();
                gui.mostrarTelaJogo(); // garante atualização
            }
        }
    }

    private void mostrarInventarioBatalha() {
        Personagem jogador = jogoLogica.getJogador();
        if (jogador == null) return;

        Inventario inv = jogador.getInventario();
        java.util.List<Item> todosItens = inv.getItens();
        java.util.List<String> itensUsaveis = new java.util.ArrayList<>();
        for (Item it : todosItens) {
            if (it.getTipoEfeito() != null) itensUsaveis.add(it.getNome());
        }

        if (itensUsaveis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Você não possui itens para usar!", "Inventário Vazio", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        itensUsaveis.add("Cancelar");
        String[] opcoes = itensUsaveis.toArray(new String[0]);
        int escolha = JOptionPane.showOptionDialog(this, "Qual item deseja usar?", "Inventário - Batalha",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcoes, opcoes[0]);

        if (escolha >= 0 && escolha < opcoes.length - 1) {
            String nomeItem = opcoes[escolha];
            boolean usou = gerenciador.usarItemBatalha(nomeItem);
            if (usou) {
                atualizar();
                checarFimDeBatalha();
                JOptionPane.showMessageDialog(this, "Você usou: " + nomeItem, "Item Usado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Não conseguiu usar o item!", "Falha", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private void carregarSpriteHeroi(Personagem jogador, JLabel label) {
        String classe = jogador.getClass().getSimpleName().toLowerCase();
        String arquivo = "";
        // Usa a versão de costas para o herói
        if (classe.contains("guerreiro")) arquivo = "guerreirocostas.png";
        else if (classe.contains("mago")) arquivo = "magocostas.png";
        else if (classe.contains("arqueiro")) arquivo = "arqueirocostas.png";
        else arquivo = classe + "costas.png";

        carregarImagemParaLabel(arquivo, label);
    }

    private JButton criarBotaoAcao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 15));
        botao.setBackground(new Color(180, 160, 110));
        botao.setForeground(new Color(40, 40, 50));
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(220, 90));
        botao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createLineBorder(new Color(140, 130, 90), 2));
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        botao.setVerticalAlignment(SwingConstants.CENTER);
        botao.setVerticalTextPosition(SwingConstants.CENTER);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);
        return botao;
    }

    private void carregarSpriteInimigo(Inimigo inimigo, JLabel label) {
        String nome = inimigo.getNome().toLowerCase();
        String arquivo = "";
        if (nome.contains("sapo")) arquivo = "sapo.png";
        else if (nome.contains("pantera")) arquivo = "pantera.png";
        else if (nome.contains("brukkar")) arquivo = "brukkar.png";
        else if (nome.contains("balduran") || nome.contains("esqueleto") || nome.contains("rei")) arquivo = "rei_esqueleto.png";
        else arquivo = "";

        if (!arquivo.isEmpty()) carregarImagemParaLabel(arquivo, label);
        else label.setIcon(null);
    }

    // Carrega imagem do mesmo modo que TelaJogo
    private void carregarImagemParaLabel(String relativePath, JLabel label) {
        if (relativePath == null || relativePath.isEmpty()) {
            label.setIcon(null);
            label.setText("");
            return;
        }
        try {
            BufferedImage img = null;
            java.net.URL res = getClass().getResource("/images/" + relativePath);
            if (res != null) {
                img = ImageIO.read(res);
            } else {
                File f = new File("src/main/resources/images/" + relativePath);
                if (!f.exists()) {
                    f = new File("resources/images/" + relativePath);
                }
                if (f.exists()) {
                    img = ImageIO.read(f);
                }
            }
            if (img != null) {
                int imgW = img.getWidth();
                int imgH = img.getHeight();
                int maxW = Math.min(imgW, 300);
                int maxH = Math.min(imgH, 300);
                double scale = Math.min(1.0, Math.min((double) maxW / imgW, (double) maxH / imgH));
                int scaledW = (int) Math.round(imgW * scale);
                int scaledH = (int) Math.round(imgH * scale);
                Image scaled = img.getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(scaled));
                label.setText("");
                label.setPreferredSize(new Dimension(scaledW, scaledH));
                label.revalidate();
            } else {
                label.setIcon(null);
                label.setText("");
            }
        } catch (IOException e) {
            label.setIcon(null);
            label.setText("");
        }
    }
}

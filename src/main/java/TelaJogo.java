import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Tela principal do jogo com narrativa interativa
 * Mostra informações do personagem, narrativa e opções de escolha
 */
public class TelaJogo extends JPanel {
    
    private JogoLogica jogoLogica;
    private JTextArea areaTexto;
    private JPanel painelPersonagem;
    private JPanel painelAcoes;
    private JLabel labelVida;
    private JLabel labelNivel;
    private JLabel labelNomePersonagem;
    private JLabel labelImagem; // placeholder for scenario/character image
    
    private JButton[] botoesAcao = new JButton[2];
    private JButton botaoInventario;
    private JButton botaoVoltar;
    private GerenciadorHistoria gerenciador;
    
    public TelaJogo(JogoLogica jogoLogica) {
        this.jogoLogica = jogoLogica;
        
        // Layout principal com BorderLayout
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(20, 20, 25)); // Azul-cinza escuro
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel superior com informações do personagem
        painelPersonagem = criarPainelPersonagem();
        add(painelPersonagem, BorderLayout.NORTH);
        
        // Painel central com área de texto
        JPanel painelCentral = criarPainelCentral();
        add(painelCentral, BorderLayout.CENTER);
        
        // Painel inferior com botões de ação
        painelAcoes = criarPainelAcoes();
        add(painelAcoes, BorderLayout.SOUTH);
    }
    
    private JPanel criarPainelPersonagem() {
        JPanel painel = new JPanel();
        painel.setBackground(new Color(30, 30, 40)); // Azul-cinza suave
        painel.setLayout(new GridLayout(1, 3, 15, 10));
        painel.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 120), 2)); // Borda dourada suave
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Nome do personagem
        labelNomePersonagem = new JLabel("Personagem");
        labelNomePersonagem.setFont(new Font("Arial", Font.BOLD, 18));
        labelNomePersonagem.setForeground(new Color(220, 200, 120)); // Dourado suave
        
        // Informações do personagem
        labelVida = new JLabel("Vida: 0/0");
        labelVida.setFont(new Font("Arial", Font.BOLD, 16));
        labelVida.setForeground(new Color(220, 120, 120)); // Vermelho suave
        
        labelNivel = new JLabel("Nível: 1 | Ataque: 0 | Defesa: 0");
        labelNivel.setFont(new Font("Arial", Font.BOLD, 16));
        labelNivel.setForeground(new Color(220, 200, 120)); // Dourado suave
        
        painel.add(labelNomePersonagem);
        painel.add(labelVida);
        painel.add(labelNivel);
        
        return painel;
    }
    
    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout(10, 10));
        painel.setBackground(new Color(20, 20, 25)); // Fundo escuro neutro
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Painel ESQUERDO com imagem do cenário (grande)
        // Usa FlowLayout para que o label reserve seu tamanho natural (não seja esticado pelo BorderLayout)
        JPanel painelImagem = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        painelImagem.setBackground(new Color(40, 35, 50)); // Roxo escuro para a imagem
        painelImagem.setBorder(BorderFactory.createLineBorder(new Color(150, 140, 160), 2)); // Borda cinza suave
        labelImagem = new JLabel("[Cenário]");
        labelImagem.setForeground(new Color(180, 170, 190));
        labelImagem.setFont(new Font("Arial", Font.ITALIC, 16));
        labelImagem.setHorizontalAlignment(SwingConstants.CENTER);
        labelImagem.setVerticalAlignment(SwingConstants.CENTER);
        painelImagem.add(labelImagem);
        
        // Área de texto com scroll (direita, pequena)
        areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Courier New", Font.PLAIN, 13));
        areaTexto.setBackground(new Color(25, 25, 35)); // Azul-cinza escuro suave
        areaTexto.setForeground(new Color(220, 220, 230)); // Branco suave
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setMargin(new Insets(10, 10, 10, 10));
        areaTexto.setText("Bem-vindo a Vale-Cinzento, viajante...\n\n");
        
        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setBackground(new Color(20, 20, 25));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(150, 140, 160), 2)); // Borda cinza suave
        scrollPane.setPreferredSize(new Dimension(300, 0)); // Texto fica menor na direita
        
        painel.add(painelImagem, BorderLayout.CENTER);
        painel.add(scrollPane, BorderLayout.EAST);
        
        return painel;
    }
    
    private JPanel criarPainelAcoes() {
        painelAcoes = new JPanel();
        painelAcoes.setBackground(new Color(30, 30, 40)); // Azul-cinza suave
        painelAcoes.setLayout(new GridLayout(2, 2, 20, 20));
        painelAcoes.setBorder(BorderFactory.createLineBorder(new Color(150, 140, 160), 2)); // Borda cinza suave
        painelAcoes.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Cria 2 botões para escolhas principais
        botoesAcao[0] = criarBotaoAcao("Opção 1");
        botoesAcao[1] = criarBotaoAcao("Opção 2");
        
        botoesAcao[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarEscolha(1);
            }
        });
        
        botoesAcao[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarEscolha(2);
            }
        });
        
        // Botão de inventário
        botaoInventario = criarBotaoAcao("Inventário");
        botaoInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarInventario();
            }
        });
        
        // Botão de voltar
        botaoVoltar = criarBotaoAcao("Voltar");
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                voltarAoMenu();
            }
        });
        
        painelAcoes.add(botoesAcao[0]);
        painelAcoes.add(botoesAcao[1]);
        painelAcoes.add(botaoInventario);
        painelAcoes.add(botaoVoltar);
        
        return painelAcoes;
    }
    
    private JButton criarBotaoAcao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 15));
        botao.setBackground(new Color(180, 160, 110)); // Dourado suave
        botao.setForeground(new Color(40, 40, 50)); // Texto cinza escuro
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(220, 90));
        botao.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createLineBorder(new Color(140, 130, 90), 2)); // Borda dourada mais escura
        botao.setHorizontalAlignment(SwingConstants.CENTER);
        botao.setVerticalAlignment(SwingConstants.CENTER);
        botao.setVerticalTextPosition(SwingConstants.CENTER);
        botao.setHorizontalTextPosition(SwingConstants.CENTER);
        return botao;
    }

    // Oculta botões sem texto dentro do painel de ações para evitar espaços/botões vazios
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
        if (jogoLogica.getJogador() != null) {
            Personagem jogador = jogoLogica.getJogador();
            
            // Atualiza informações do personagem
            labelNomePersonagem.setText(jogador.getNome() + " - " + jogador.getClass().getSimpleName());
            int vidaMax = jogador.getClass().getSimpleName().equals("Guerreiro") ? 150 : 
                         (jogador.getClass().getSimpleName().equals("Mago") ? 100 : 120);
            // Ajusta vidaMax baseado no nível (cada nível adiciona 20 HP ao máximo)
            vidaMax += (jogador.getNivel() - 1) * 20;
            labelVida.setText("Vida: " + jogador.getPontosVida() + "/" + vidaMax);
            labelNivel.setText("Nível: " + jogador.getNivel() + " | Ataque: " + jogador.getAtaque() + " | Defesa: " + jogador.getDefesa());
            
            // Inicializa o gerenciador de história
            gerenciador = jogoLogica.getGerenciadorHistoria();

            // Somente inicia a etapa 1 se ainda não houver etapa definida (valor 0)
            if (gerenciador.getEtapaAtual() == 0) {
                gerenciador.iniciarEtapa1();
            }
            atualizarNarrativa();
        }
    }

    
    
    private void atualizarNarrativa() {
        // Se estamos em batalha, delega para a TelaBatalha
        if (gerenciador.getEstadoAtual() == GerenciadorHistoria.ESTADO_BATALHA) {
            JogoGUI gui = jogoLogica.getGui();
            if (gui != null) {
                gui.mostrarTelaBatalha();
                return;
            }
        }

        areaTexto.setText(gerenciador.getNarrativaAtual());
        
        // Atualiza todas as informações do jogador
        if (jogoLogica.getJogador() != null) {
            Personagem jogador = jogoLogica.getJogador();
            int vidaMax = jogador.getClass().getSimpleName().equals("Guerreiro") ? 150 : 
                         (jogador.getClass().getSimpleName().equals("Mago") ? 100 : 120);
            labelVida.setText("Vida: " + jogador.getPontosVida() + "/" + vidaMax);
            labelNivel.setText("Nível: " + jogador.getNivel() + " | Ataque: " + jogador.getAtaque() + " | Defesa: " + jogador.getDefesa());
                // Se estamos em um estado especial (fim de jogo ou derrota), mostre a imagem correspondente
                try {
                    int estado = gerenciador.getEstadoAtual();
                    if (estado == GerenciadorHistoria.ESTADO_FIM_JOGO) {
                        // tenta alguns nomes comuns para o arquivo de imagem de fim feliz
                        carregarImagemParaLabel("fim_de_jogo_feliz.png", labelImagem);
                        carregarImagemParaLabel("FIMDEJOGOFELIZ.png", labelImagem);
                        carregarImagemParaLabel("fimdejogofeliz.png", labelImagem);
                    } else if (estado == GerenciadorHistoria.ESTADO_DERROTA) {
                        carregarImagemParaLabel("fim_de_jogo_triste.png", labelImagem);
                        carregarImagemParaLabel("FIMDEJOGOTRISTE.png", labelImagem);
                        carregarImagemParaLabel("fimdejogotriste.png", labelImagem);
                    } else {
                        // Se estamos nas etapas iniciais exiba o cenário correspondente
                        int etapa = gerenciador.getEtapaAtual();
                        if (etapa == 1) {
                            carregarImagemParaLabel("etapa1.png", labelImagem);
                        } else if (etapa == 2) {
                            carregarImagemParaLabel("etapa2.png", labelImagem);
                        } else if (etapa == 3) {
                            carregarImagemParaLabel("etapa3.png", labelImagem);
                        } else {
                            // Atualiza imagem do personagem baseado na classe (procura images/<classe>.png)
                            String classe = jogador.getClass().getSimpleName().toLowerCase();
                            carregarImagemParaLabel(classe + ".png", labelImagem);
                        }
                    }
                } catch (Exception e) {
                    // ignore image loading errors
                }
        }
        
        String[] opcoes = gerenciador.getOpcoesAtuais();
        
        // Atualiza os textos dos botões de escolha
        for (int i = 0; i < botoesAcao.length; i++) {
            if (i < opcoes.length) {
                botoesAcao[i].setText(opcoes[i]);
                botoesAcao[i].setEnabled(true);
                botoesAcao[i].setVisible(true); // Mostra o botão
            } else {
                botoesAcao[i].setEnabled(false); // Desabilita para não permitir clicks
                botoesAcao[i].setVisible(false); // Esconde o botão quando não há opção
            }
        }

        // Se algum dos botões de ação já fornece "Voltar ao menu" não precisamos mostrar
        // o botão de volta separado para evitar duplicatas. Detecta palavras-chave "voltar"/"voltar ao menu".
        boolean possuiVoltar = false;
        if (opcoes != null) {
            for (String o : opcoes) {
                if (o != null && o.toLowerCase().contains("voltar")) {
                    possuiVoltar = true;
                    break;
                }
            }
        }
        botaoVoltar.setVisible(!possuiVoltar);

        // Também esconde botões sem texto no painel de ações
        esconderBotoesSemTexto(painelAcoes);
    }

    // Carrega uma imagem do diretório resources/images ou classpath /images
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
                // Redimensiona preservando proporção sem esticar: mostra a imagem no seu tamanho
                // natural ou reduzida apenas quando maior que o espaço disponível (contain, sem upscaling).
                int imgW = img.getWidth();
                int imgH = img.getHeight();

                // Determina limite máximo baseado no pai (se disponível) para evitar imagens maiores que o painel
                int maxW = imgW;
                int maxH = imgH;
                java.awt.Container p = label.getParent();
                if (p != null && p.getWidth() > 0 && p.getHeight() > 0) {
                    // deixa um pequeno padding interno
                    maxW = p.getWidth() - 10;
                    maxH = p.getHeight() - 10;
                }

                double scale = Math.min(1.0, Math.min((double) maxW / imgW, (double) maxH / imgH));
                int scaledW = (int) Math.round(imgW * scale);
                int scaledH = (int) Math.round(imgH * scale);

                Image scaled = img.getScaledInstance(scaledW, scaledH, Image.SCALE_SMOOTH);

                label.setIcon(new ImageIcon(scaled));
                label.setText("");
                // Ajusta o tamanho preferencial do label para que o painel mantenha o espaço correto
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
    
    private void processarEscolha(int escolha) {
        int estado = gerenciador.getEstadoAtual();
        
        // Se está em batalha
        if (estado == GerenciadorHistoria.ESTADO_BATALHA) {
            gerenciador.processarAcaoBatalha(escolha);
        }
        // Se está em vitória
        else if (estado == GerenciadorHistoria.ESTADO_VITORIA) {
            gerenciador.continuarProximaEtapa();
        }
        // Se está em derrota
        else if (estado == GerenciadorHistoria.ESTADO_DERROTA) {
            voltarAoMenu();
            return;
        }
        // Se está em fim de jogo
        else if (estado == GerenciadorHistoria.ESTADO_FIM_JOGO) {
            voltarAoMenu();
            return;
        }
        // Se está em escolha (etapa)
        else if (estado == GerenciadorHistoria.ESTADO_ESCOLHA) {
            int etapa = gerenciador.getEtapaAtual();
            
            switch (etapa) {
                case 1:
                    gerenciador.processarEscolhaEtapa1(escolha);
                    break;
                case 2:
                    gerenciador.processarEscolhaEtapa2(escolha);
                    break;
                case 3:
                    gerenciador.processarEscolhaEtapaFinal(escolha);
                    break;
            }
        }
        
        atualizarNarrativa();
    }
    
    private void mostrarInventario() {
        if (jogoLogica.getJogador() != null) {
            int estado = gerenciador.getEstadoAtual();
            Personagem jogador = jogoLogica.getJogador();
            Inventario inv = jogador.getInventario();
            
            // Se está em batalha, mostra diálogo interativo para usar item
            if (estado == GerenciadorHistoria.ESTADO_BATALHA) {
                // Coleta os itens com efeito
                java.util.List<String> itensUsaveis = new java.util.ArrayList<>();
                java.util.List<Item> todosItens = inv.getItens();
                
                for (Item item : todosItens) {
                    if (item.getTipoEfeito() != null) {
                        itensUsaveis.add(item.getNome());
                    }
                }
                
                if (itensUsaveis.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                            "Você não possui itens para usar!",
                            "Inventário Vazio",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                
                itensUsaveis.add("Cancelar");
                String[] opcoes = itensUsaveis.toArray(new String[0]);
                
                int escolha = JOptionPane.showOptionDialog(this,
                        "Qual item deseja usar?",
                        "Inventário - Batalha",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]);
                
                if (escolha >= 0 && escolha < opcoes.length - 1) {
                    String nomeItem = opcoes[escolha];
                    boolean usou = gerenciador.usarItemBatalha(nomeItem);
                    if (usou) {
                        atualizarNarrativa();
                        JOptionPane.showMessageDialog(this,
                                "Você usou: " + nomeItem,
                                "Item Usado",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this,
                                "Não conseguiu usar o item!",
                                "Falha",
                                JOptionPane.WARNING_MESSAGE);
                    }
                }
            } else {
                // Fora da batalha, mostra inventário simples
                StringBuilder inventario = new StringBuilder("=== INVENTÁRIO ===\n\n");
                
                java.util.List<Item> todosItens = inv.getItens();
                if (todosItens.isEmpty()) {
                    inventario.append("Inventário vazio.\n");
                } else {
                    for (Item item : todosItens) {
                        String quantidade = item.getQuantidade() > 1 ? " x" + item.getQuantidade() : "";
                        inventario.append("• ").append(item.getNome()).append(quantidade).append("\n");
                        inventario.append("  ").append(item.getDescricao()).append("\n\n");
                    }
                }
                
                inventario.append("\n(Mais itens aparecerão conforme você avança na jornada)");
                
                JOptionPane.showMessageDialog(this, inventario.toString(), "Inventário", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void voltarAoMenu() {
        int opcao = JOptionPane.showConfirmDialog(this,
                "Você tem certeza que deseja voltar ao menu?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (frame instanceof JogoGUI) {
                ((JogoGUI) frame).mostrarTelaSelecao();
            }
        }
    }
}

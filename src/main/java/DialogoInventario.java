import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Tela de visualização e uso de itens
 */
public class DialogoInventario extends JDialog {
    
    private Personagem jogador;
    private JList<String> listaItens;
    private DefaultListModel<String> modeloLista;
    private JLabel labelDescricao;
    private JButton botaoUsar;
    private JButton botaoCancelar;
    private boolean itemUsado = false;
    
    public DialogoInventario(Window parent, Personagem jogador) {
        super((Frame) parent, "Inventário", ModalityType.APPLICATION_MODAL);
        this.jogador = jogador;
        
        inicializarComponentes();
        atualizarLista();
        
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    private void inicializarComponentes() {
        JPanel painel = new JPanel();
        painel.setLayout(new BorderLayout(10, 10));
        painel.setBackground(new Color(0, 0, 0));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Título
        JLabel titulo = new JLabel("=== INVENTÁRIO ===");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(255, 255, 0));
        painel.add(titulo, BorderLayout.NORTH);
        
        // Lista de itens
        modeloLista = new DefaultListModel<>();
        listaItens = new JList<>(modeloLista);
        listaItens.setBackground(new Color(10, 10, 30));
        listaItens.setForeground(new Color(255, 255, 255));
        listaItens.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(listaItens);
        scrollPane.setBackground(new Color(0, 0, 0));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 255), 2));
        painel.add(scrollPane, BorderLayout.CENTER);
        
        // Descrição
        labelDescricao = new JLabel("Selecione um item para ver detalhes");
        labelDescricao.setFont(new Font("Arial", Font.PLAIN, 12));
        labelDescricao.setForeground(new Color(0, 255, 255));
        painel.add(labelDescricao, BorderLayout.SOUTH);
        
        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(0, 0, 0));
        painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        botaoUsar = new JButton("USAR");
        botaoUsar.setBackground(new Color(255, 0, 255));
        botaoUsar.setForeground(new Color(0, 0, 0));
        botaoUsar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoUsar.addActionListener(e -> usarItem());
        
        botaoCancelar = new JButton("FECHAR");
        botaoCancelar.setBackground(new Color(100, 0, 150));
        botaoCancelar.setForeground(new Color(255, 255, 255));
        botaoCancelar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoCancelar.addActionListener(e -> dispose());
        
        painelBotoes.add(botaoUsar);
        painelBotoes.add(botaoCancelar);
        
        add(painel, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void atualizarLista() {
        modeloLista.clear();
        
        // Obtém os itens do inventário do jogador
        java.util.List<Item> itens = jogador.getInventario().getItens();
        
        if (itens.isEmpty()) {
            modeloLista.addElement("(Inventário vazio)");
            botaoUsar.setEnabled(false);
        } else {
            for (Item item : itens) {
                if (item.getTipoEfeito() != null && item.getQuantidade() > 0) {
                    String linha = "• " + item.getNome() + " x" + item.getQuantidade();
                    modeloLista.addElement(linha);
                } else {
                    String linha = "• " + item.getNome() + " x" + item.getQuantidade() + " (troféu)";
                    modeloLista.addElement(linha);
                }
            }
            botaoUsar.setEnabled(true);
        }
        
        // Listener para mostrar descrição quando selecionar item
        listaItens.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int indice = listaItens.getSelectedIndex();
                if (indice >= 0 && indice < itens.size()) {
                    Item item = itens.get(indice);
                    String desc = item.getDescricao();
                    if (item.getTipoEfeito() != null) {
                        switch (item.getTipoEfeito()) {
                            case CURA:
                                desc = "Restaura " + item.getEfeito() + " HP.";
                                break;
                            case AUMENTO_ATAQUE:
                                desc = "Aumenta o ataque em " + item.getEfeito() + ".";
                                break;
                            case AUMENTO_DEFESA:
                                desc = "Aumenta a defesa em " + item.getEfeito() + ".";
                                break;
                        }
                    }
                    labelDescricao.setText(desc);
                }
            }
        });
    }
    
    private void usarItem() {
        int indice = listaItens.getSelectedIndex();
        java.util.List<Item> itens = jogador.getInventario().getItens();
        
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item para usar!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (itens.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Inventário vazio!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (indice >= itens.size()) {
            JOptionPane.showMessageDialog(this, "Item inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Item itemSelecionado = itens.get(indice);
        
        // Verifica se o item é usável
        if (itemSelecionado.getTipoEfeito() == null) {
            JOptionPane.showMessageDialog(this, 
                "Este item é um troféu e não pode ser usado!", 
                "Item não usável", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Usa o item através do inventário
        boolean sucesso = jogador.getInventario().usarItem(itemSelecionado.getId(), jogador);
        
        if (sucesso) {
            itemUsado = true;
            
            // Mensagem de sucesso baseada no tipo de efeito
            String mensagem = "";
            switch (itemSelecionado.getTipoEfeito()) {
                case CURA:
                    mensagem = "Você usou " + itemSelecionado.getNome() + " e recuperou " + 
                              itemSelecionado.getEfeito() + " HP!\n\nHP atual: " + jogador.getPontosVida();
                    break;
                case AUMENTO_ATAQUE:
                    mensagem = "Você usou " + itemSelecionado.getNome() + " e seu ataque aumentou!\n\n" +
                              "Ataque atual: " + jogador.getAtaque();
                    break;
                case AUMENTO_DEFESA:
                    mensagem = "Você usou " + itemSelecionado.getNome() + " e sua defesa aumentou!\n\n" +
                              "Defesa atual: " + jogador.getDefesa();
                    break;
            }
            
            JOptionPane.showMessageDialog(this, mensagem, "Item usado!", JOptionPane.INFORMATION_MESSAGE);
            
            // Atualiza a lista e fecha o diálogo
            atualizarLista();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Não foi possível usar o item!", 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public boolean foiItemUsado() {
        return itemUsado;
    }
}

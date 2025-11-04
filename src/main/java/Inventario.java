import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Inventario implements Cloneable {
    private List<Item> itens;

    public Inventario() {
        this.itens = new ArrayList<>();
    }

    public Inventario(Inventario c) throws Exception {
        if (c == null) throw new Exception("Modelo ausente");
        this.itens = new ArrayList<>();
        for (Item item : c.itens) {
            this.itens.add(item.clone());
        }
    }

    public void adicionarItem(Item novoItem) {
        for (Item itemExistente : itens) {
            if (itemExistente.getNome().equalsIgnoreCase(novoItem.getNome())) {
                itemExistente.setQuantidade(itemExistente.getQuantidade() + novoItem.getQuantidade());
                return;
            }
        }
        itens.add(novoItem.clone());
    }


    public boolean usarItem(String nomeOuId, Personagem alvo) {
        Item itemParaUsar = null;
        
        // Tenta encontrar por ID ou nome normalizado
        for (Item item : this.itens) {
            if (item.getId().equals(nomeOuId) || 
                item.getId().equals(StringNormalizer.gerarId(nomeOuId)) ||
                StringNormalizer.equalsNormalizado(item.getNome(), nomeOuId)) {
                itemParaUsar = item;
                break;
            }
        }

        if (itemParaUsar == null) {
            System.out.println("Item '" + nomeOuId + "' não encontrado.");
            return false; 
        }

        if (itemParaUsar.getTipoEfeito() == null) {
            System.out.println("O item '" + itemParaUsar.getNome() + "' não tem um efeito utilizável.");
            return false; 
        }

        switch (itemParaUsar.getTipoEfeito()) {
            case CURA:
                alvo.setPontosVida(alvo.getPontosVida() + itemParaUsar.getEfeito());
                System.out.println(alvo.getNome() + " usou " + itemParaUsar.getNome() + " e recuperou " + itemParaUsar.getEfeito() + " de vida.");
                break;
            case AUMENTO_ATAQUE:
                alvo.setAtaque(alvo.getAtaque() + itemParaUsar.getEfeito());
                System.out.println(alvo.getNome() + " usou " + itemParaUsar.getNome() + " e seu ataque aumentou para " + alvo.getAtaque() + "!");
                break;
            case AUMENTO_DEFESA:
                alvo.setDefesa(alvo.getDefesa() + itemParaUsar.getEfeito());
                System.out.println(alvo.getNome() + " usou " + itemParaUsar.getNome() + " e sua defesa aumentou para " + alvo.getDefesa() + "!");
                break;
            default:
                System.out.println("Este item não tem um efeito conhecido.");
                return false; // FALHA: Efeito desconhecido
        }

        itemParaUsar.setQuantidade(itemParaUsar.getQuantidade() - 1);
        if (itemParaUsar.getQuantidade() <= 0) {
            this.itens.remove(itemParaUsar);
        }
        
        return true; // SUCESSO: O item foi usado
    }

    public void adicionarInventario(Inventario outroInventario) {
        for (Item item : outroInventario.itens) {
            this.adicionarItem(item);
        }
    }

    public void removerItemAleatorio() {
        if (itens.isEmpty()) {
            System.out.println("O inventário está vazio, nada a perder.");
            return;
        }
        Random random = new Random();
        Item itemARemover = itens.get(random.nextInt(itens.size()));
        System.out.println("Você perdeu x1 " + itemARemover.getNome() + "!");
        itemARemover.setQuantidade(itemARemover.getQuantidade() - 1);
        if (itemARemover.getQuantidade() <= 0) {
            this.itens.remove(itemARemover);
        }
    }

    public void listarItens() {
        if (itens.isEmpty()) {
            System.out.println("O inventário está vazio.");
            return;
        }
        Collections.sort(this.itens);
        System.out.println("--- Inventário ---");
        for (Item item : itens) {
            System.out.println(item);
        }
        System.out.println("------------------");
    }
    
    public void listarItensUsaveis() {
        List<Item> usaveis = new ArrayList<>();
        for (Item item : itens) {
            if (item.getTipoEfeito() != null && item.getQuantidade() > 0) {
                usaveis.add(item);
            }
        }
        
        if (usaveis.isEmpty()) {
            System.out.println("Nenhum item usável disponível.");
            return;
        }
        
        Collections.sort(usaveis);
        System.out.println("--- Itens Usáveis ---");
        for (Item item : usaveis) {
            System.out.println(item.toStringComId());
        }
        System.out.println("---------------------");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Inventario c = (Inventario) obj;
        return this.itens.equals(c.itens);
    }

    @Override
    public int hashCode() {
        int ret = 666;
        ret = ret * 13 + this.itens.hashCode();
        if (ret < 0) ret = -ret;
        return ret;
    }

    @Override
    public Inventario clone() {
        Inventario retorno = null;
        try {
            retorno = new Inventario(this);
        } catch (Exception e) {}
        return retorno;
    }
}
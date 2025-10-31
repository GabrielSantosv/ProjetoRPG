
public class Item implements Comparable<Item>, Cloneable {

    private String nome;
    private String descricao;
    private int efeito; // valor numérico do efeito (ex: cura 25, ataque +5)
    private int quantidade;
    private TipoEfeito tipoEfeito;

    public Item(String nome, String descricao, int efeito, int quantidade, TipoEfeito tipoEfeito) {
        this.nome = nome == null ? "" : nome;
        this.descricao = descricao == null ? "" : descricao;
        this.efeito = efeito;
        this.quantidade = Math.max(0, quantidade);
        this.tipoEfeito = tipoEfeito;
    }

    public Item(Item outro) {
        if (outro == null) {
            this.nome = "";
            this.descricao = "";
            this.efeito = 0;
            this.quantidade = 0;
            this.tipoEfeito = null;
        } else {
            this.nome = outro.nome;
            this.descricao = outro.descricao;
            this.efeito = outro.efeito;
            this.quantidade = outro.quantidade;
            this.tipoEfeito = outro.tipoEfeito;
        }
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getEfeito() {
        return efeito;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public TipoEfeito getTipoEfeito() {
        return tipoEfeito;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = Math.max(0, quantidade);
    }

    @Override
    public String toString() {
        String q = quantidade > 1 ? " x" + quantidade : "";
        String tipo = tipoEfeito == null ? "" : " [" + tipoEfeito.name() + "]";
        return nome + q + tipo + " - " + descricao;
    }

    @Override
    public int compareTo(Item o) {
        if (o == null) {
            return 1;
        }
        return this.nome.compareToIgnoreCase(o.nome);
    }

    @Override
    public Item clone() {
        return new Item(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item it = (Item) o;
        return this.nome.equalsIgnoreCase(it.nome)
                && this.tipoEfeito == it.tipoEfeito;
    }

    @Override
    public int hashCode() {
        int r = 1;
        r = r * 31 + nome.toLowerCase().hashCode();
        r = r * 31 + (tipoEfeito == null ? 0 : tipoEfeito.hashCode());
        return r;
    }
}

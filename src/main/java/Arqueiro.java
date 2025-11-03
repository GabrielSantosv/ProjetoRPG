public class Arqueiro extends Personagem {
    public Arqueiro(String nome) {
        super(nome, 120, 22, 12, 1);
        this.inventario.adicionarItem(new Item("Elixir Olho de Águia", "Aumenta o ataque em 7.", 7, 1, TipoEfeito.AUMENTO_ATAQUE));
    }

    public Arqueiro(Arqueiro c) throws Exception {
        super(c);
    }

    @Override
    public Arqueiro clone() {
        Arqueiro retorno = null;
        try {
            retorno = new Arqueiro(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao clonar Arqueiro", e);
        }
        return retorno;
    }
}
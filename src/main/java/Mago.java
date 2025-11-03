public class Mago extends Personagem {
    public Mago(String nome) {
        super(nome, 100, 25, 10, 1);
        this.inventario.adicionarItem(new Item("Elixir do Sábio", "Aumenta o ataque em 8.", 8, 1, TipoEfeito.AUMENTO_ATAQUE));
    }

    public Mago(Mago c) throws Exception {
        super(c);
    }

    @Override
    public Mago clone() {
        Mago retorno = null;
        try {
            retorno = new Mago(this);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao clonar Mago", e);
        }
        return retorno;
    }
}
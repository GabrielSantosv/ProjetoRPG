public class Guerreiro extends Personagem {
    public Guerreiro(String nome) {
        super(nome, 150, 20, 15, 1);
        this.inventario.adicionarItem(new Item("Elixir de Vigor", "Aumenta a defesa em 10.", 10, 1, TipoEfeito.AUMENTO_DEFESA));
    }

    public Guerreiro(Guerreiro c) throws Exception {
        super(c);
    }

    @Override
    public Guerreiro clone() {
        Guerreiro retorno = null;
        try {
            retorno = new Guerreiro(this);
        } catch (Exception e) {}
        return retorno;
    }
}
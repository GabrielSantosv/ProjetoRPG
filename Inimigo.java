public class Inimigo extends Personagem {
    public Inimigo(String nome, int pontosVida, int ataque, int defesa, int nivel) {
        super(nome, pontosVida, ataque, defesa, nivel);
    }

    public Inimigo(Inimigo c) throws Exception {
        super(c);
    }

    @Override
    public Inimigo clone() {
        Inimigo retorno = null;
        try {
            retorno = new Inimigo(this);
        } catch (Exception e) {}
        return retorno;
    }
}
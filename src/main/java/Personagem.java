public abstract class Personagem implements Cloneable {
    protected String nome;
    protected int pontosVida;
    protected int ataque;
    protected int defesa;
    protected int nivel;
    protected Inventario inventario;

    public Personagem(String nome, int pontosVida, int ataque, int defesa, int nivel) {
        this.nome = nome;
        this.pontosVida = pontosVida;
        this.ataque = ataque;
        this.defesa = defesa;
        this.nivel = nivel;
        this.inventario = new Inventario();
        this.inventario.adicionarItem(new Item("Poção de Cura", "Restaura 25 HP.", 25, 1, TipoEfeito.CURA));
    }

    public Personagem(Personagem c) throws Exception {
        if (c == null) throw new Exception("Modelo ausente");
        this.nome = c.nome;
        this.pontosVida = c.pontosVida;
        this.ataque = c.ataque;
        this.defesa = c.defesa;
        this.nivel = c.nivel;
        this.inventario = (Inventario) c.inventario.clone();
    }

    public boolean estaVivo() { return this.pontosVida > 0; }

    // Getters e Setters
    public String getNome() { return nome; }
    public int getPontosVida() { return pontosVida; }
    public int getAtaque() { return ataque; }
    public int getDefesa() { return defesa; }
    public int getNivel() { return nivel; }
    public Inventario getInventario() { return inventario; }
    public void setPontosVida(int pontosVida) { this.pontosVida = pontosVida; }
    public void setAtaque(int ataque) { this.ataque = ataque; }
    public void setDefesa(int defesa) { this.defesa = defesa; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public void subirNivel() {
        this.nivel++;
        System.out.println("----------------------------------------");
        System.out.println(this.nome + " subiu para o Nível " + this.nivel + "!");
        this.pontosVida += 20;
        this.ataque += 5;
        this.defesa += 3;
        System.out.println("HP aumentado para " + this.pontosVida);
        System.out.println("Ataque aumentado para " + this.ataque);
        System.out.println("Defesa aumentada para " + this.defesa);
        System.out.println("----------------------------------------");
    }
    
    @Override
    public String toString() {
        return String.format("%s (Nível %d) - HP: %d, Ataque: %d, Defesa: %d",
                nome, nivel, pontosVida, ataque, defesa);
    }

    @Override
    public abstract Object clone();

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;
        if (obj.getClass() != this.getClass()) return false;
        Personagem c = (Personagem) obj;
        if (!this.nome.equals(c.nome)) return false;
        if (this.pontosVida != c.pontosVida) return false;
        if (this.ataque != c.ataque) return false;
        if (this.defesa != c.defesa) return false;
        if (this.nivel != c.nivel) return false;
        return this.inventario.equals(c.inventario);
    }

    @Override
    public int hashCode() {
        int ret = 666;
        ret = ret * 13 + this.nome.hashCode();
        ret = ret * 13 + Integer.hashCode(this.pontosVida);
        ret = ret * 13 + Integer.hashCode(this.ataque);
        ret = ret * 13 + Integer.hashCode(this.defesa);
        ret = ret * 13 + Integer.hashCode(this.nivel);
        ret = ret * 13 + this.inventario.hashCode();
        if (ret < 0) ret = -ret;
        return ret;
    }
}
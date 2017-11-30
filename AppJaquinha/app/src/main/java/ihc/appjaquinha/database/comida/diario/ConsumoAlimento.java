package ihc.appjaquinha.database.comida.diario;

import ihc.appjaquinha.database.comida.Alimento;

public class ConsumoAlimento {
    //private HashMap<Alimento, Float> alimentoList; //alimento, quantidade (g)
    //nao era funcional no Firebase, entao precisei fazer uma classe
    private Alimento alimento;
    //para ser melhor no armazenamento, pode guardar a chave do alimento que esta na geladeira
    //(mas que nao podera ser deletado -> bool)
    private Float quantidade;

    public ConsumoAlimento() {
    }

    public ConsumoAlimento(Alimento alimento, Float quantidade) {
        this.alimento = alimento;
        this.quantidade = quantidade;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }
}

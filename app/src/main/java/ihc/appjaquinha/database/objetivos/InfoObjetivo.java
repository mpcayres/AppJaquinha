package ihc.appjaquinha.database.objetivos;

public class InfoObjetivo {
    private Integer tempo; //dias
    private Float quantidade; //g

    public InfoObjetivo() {
    }

    public InfoObjetivo(Integer tempo, Float quantidade) {
        this.tempo = tempo;
        this.quantidade = quantidade;
    }

    public Integer getTempo() {
        return tempo;
    }

    public void setTempo(Integer tempo) {
        this.tempo = tempo;
    }

    public Float getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Float quantidade) {
        this.quantidade = quantidade;
    }
}

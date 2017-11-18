package ihc.appjaquinha.database.objetivos;

import java.util.HashMap;

public class Objetivos {
    private InfoObjetivo valorEnergetico; //kcal
    private InfoObjetivo carboidratos; //g
    private InfoObjetivo proteinas; //g
    private InfoObjetivo gordurasTotais; //g
    private InfoObjetivo gordurasSaturadas; //g
    private InfoObjetivo gordurasTrans; //g
    private InfoObjetivo fibraAlimentar; //g
    private InfoObjetivo sodio; //mg
    private InfoObjetivo acucares; //mg
    private InfoObjetivo colesterol; //mg
    private InfoObjetivo calcio; //mg
    private InfoObjetivo ferro; //mg

    public Objetivos() {
        valorEnergetico = new InfoObjetivo();
        carboidratos = new InfoObjetivo();
        proteinas = new InfoObjetivo();
        gordurasTotais = new InfoObjetivo();
        gordurasSaturadas = new InfoObjetivo();
        gordurasTrans = new InfoObjetivo();
        fibraAlimentar = new InfoObjetivo();
        sodio = new InfoObjetivo();
        acucares = new InfoObjetivo();
        colesterol = new InfoObjetivo();
        calcio = new InfoObjetivo();
        ferro = new InfoObjetivo();
    }

    public InfoObjetivo getValorEnergetico() {
        return valorEnergetico;
    }

    public void setValorEnergetico(InfoObjetivo valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public InfoObjetivo getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(InfoObjetivo carboidratos) {
        this.carboidratos = carboidratos;
    }

    public InfoObjetivo getProteinas() {
        return proteinas;
    }

    public void setProteinas(InfoObjetivo proteinas) {
        this.proteinas = proteinas;
    }

    public InfoObjetivo getGordurasTotais() {
        return gordurasTotais;
    }

    public void setGordurasTotais(InfoObjetivo gordurasTotais) {
        this.gordurasTotais = gordurasTotais;
    }

    public InfoObjetivo getGordurasSaturadas() {
        return gordurasSaturadas;
    }

    public void setGordurasSaturadas(InfoObjetivo gordurasSaturadas) {
        this.gordurasSaturadas = gordurasSaturadas;
    }

    public InfoObjetivo getGordurasTrans() {
        return gordurasTrans;
    }

    public void setGordurasTrans(InfoObjetivo gordurasTrans) {
        this.gordurasTrans = gordurasTrans;
    }

    public InfoObjetivo getFibraAlimentar() {
        return fibraAlimentar;
    }

    public void setFibraAlimentar(InfoObjetivo fibraAlimentar) {
        this.fibraAlimentar = fibraAlimentar;
    }

    public InfoObjetivo getSodio() {
        return sodio;
    }

    public void setSodio(InfoObjetivo sodio) {
        this.sodio = sodio;
    }

    public InfoObjetivo getAcucares() {
        return acucares;
    }

    public void setAcucares(InfoObjetivo acucares) {
        this.acucares = acucares;
    }

    public InfoObjetivo getColesterol() {
        return colesterol;
    }

    public void setColesterol(InfoObjetivo colesterol) {
        this.colesterol = colesterol;
    }

    public InfoObjetivo getCalcio() {
        return calcio;
    }

    public void setCalcio(InfoObjetivo calcio) {
        this.calcio = calcio;
    }

    public InfoObjetivo getFerro() {
        return ferro;
    }

    public void setFerro(InfoObjetivo ferro) {
        this.ferro = ferro;
    }
}

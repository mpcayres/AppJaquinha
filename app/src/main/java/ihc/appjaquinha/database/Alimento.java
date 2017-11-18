package ihc.appjaquinha.database;

import java.util.HashMap;

public class Alimento {
    private String nome;
    //obrigatorios
    private int porcao; //g
    private int valorEnergetico; //kcal
    private int carboidratos; //g
    private int proteinas; //g
    private int gordurasTotais; //g
    private int gordurasSaturadas; //g
    private int gordurasTrans; //g
    private int fibraAlimentar; //g
    private int sodio; //mg
    //optativas
    private int acucares; //mg
    private int colesterol; //mg
    private int calcio; //mg
    private int ferro; //mg
    //voluntarias
    private HashMap<String, Float> voluntario; //nome, valor - mg

    public Alimento() {
        voluntario = new HashMap<>();
    }

    public Alimento(String nome, int porcao, int valorEnergetico, int carboidratos, int proteinas, int gordurasTotais, int gordurasSaturadas, int gordurasTrans, int fibraAlimentar, int sodio, int acucares, int colesterol, int calcio, int ferro) {
        this.nome = nome;
        this.porcao = porcao;
        this.valorEnergetico = valorEnergetico;
        this.carboidratos = carboidratos;
        this.proteinas = proteinas;
        this.gordurasTotais = gordurasTotais;
        this.gordurasSaturadas = gordurasSaturadas;
        this.gordurasTrans = gordurasTrans;
        this.fibraAlimentar = fibraAlimentar;
        this.sodio = sodio;
        this.acucares = acucares;
        this.colesterol = colesterol;
        this.calcio = calcio;
        this.ferro = ferro;
        voluntario = new HashMap<>();
    }

    public Alimento(String nome, int porcao, int valorEnergetico, int carboidratos, int proteinas, int gordurasTotais, int gordurasSaturadas, int gordurasTrans, int fibraAlimentar, int sodio, int acucares, int colesterol, int calcio, int ferro, HashMap<String, Float> voluntario) {
        this.nome = nome;
        this.porcao = porcao;
        this.valorEnergetico = valorEnergetico;
        this.carboidratos = carboidratos;
        this.proteinas = proteinas;
        this.gordurasTotais = gordurasTotais;
        this.gordurasSaturadas = gordurasSaturadas;
        this.gordurasTrans = gordurasTrans;
        this.fibraAlimentar = fibraAlimentar;
        this.sodio = sodio;
        this.acucares = acucares;
        this.colesterol = colesterol;
        this.calcio = calcio;
        this.ferro = ferro;
        this.voluntario = voluntario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPorcao() {
        return porcao;
    }

    public void setPorcao(int porcao) {
        this.porcao = porcao;
    }

    public int getValorEnergetico() {
        return valorEnergetico;
    }

    public void setValorEnergetico(int valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public int getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(int carboidratos) {
        this.carboidratos = carboidratos;
    }

    public int getProteinas() {
        return proteinas;
    }

    public void setProteinas(int proteinas) {
        this.proteinas = proteinas;
    }

    public int getGordurasTotais() {
        return gordurasTotais;
    }

    public void setGordurasTotais(int gordurasTotais) {
        this.gordurasTotais = gordurasTotais;
    }

    public int getGordurasSaturadas() {
        return gordurasSaturadas;
    }

    public void setGordurasSaturadas(int gordurasSaturadas) {
        this.gordurasSaturadas = gordurasSaturadas;
    }

    public int getGordurasTrans() {
        return gordurasTrans;
    }

    public void setGordurasTrans(int gordurasTrans) {
        this.gordurasTrans = gordurasTrans;
    }

    public int getFibraAlimentar() {
        return fibraAlimentar;
    }

    public void setFibraAlimentar(int fibraAlimentar) {
        this.fibraAlimentar = fibraAlimentar;
    }

    public int getSodio() {
        return sodio;
    }

    public void setSodio(int sodio) {
        this.sodio = sodio;
    }

    public int getAcucares() {
        return acucares;
    }

    public void setAcucares(int acucares) {
        this.acucares = acucares;
    }

    public int getColesterol() {
        return colesterol;
    }

    public void setColesterol(int colesterol) {
        this.colesterol = colesterol;
    }

    public int getCalcio() {
        return calcio;
    }

    public void setCalcio(int calcio) {
        this.calcio = calcio;
    }

    public int getFerro() {
        return ferro;
    }

    public void setFerro(int ferro) {
        this.ferro = ferro;
    }

    public HashMap<String, Float> getVoluntario() {
        return voluntario;
    }

    public void setVoluntario(HashMap<String, Float> voluntario) {
        this.voluntario = voluntario;
    }

    public void addVoluntario(String s, Float f){
        this.voluntario.put(s,f);
    }
}

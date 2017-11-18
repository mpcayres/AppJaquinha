package ihc.appjaquinha.database;

import java.util.HashMap;

public class Objetivos {
    //tempo, quantidade
    private HashMap<String, Float> valorEnergetico; //kcal
    private HashMap<String, Float> carboidratos; //g
    private HashMap<String, Float> proteinas; //g
    private HashMap<String, Float> gordurasTotais; //g
    private HashMap<String, Float> gordurasSaturadas; //g
    private HashMap<String, Float> gordurasTrans; //g
    private HashMap<String, Float> fibraAlimentar; //g
    private HashMap<String, Float> sodio; //mg
    private HashMap<String, Float> acucares; //mg
    private HashMap<String, Float> colesterol; //mg
    private HashMap<String, Float> calcio; //mg
    private HashMap<String, Float> ferro; //mg

    public Objetivos() {
        valorEnergetico = new HashMap<>();
        carboidratos = new HashMap<>();
        proteinas = new HashMap<>();
        gordurasTotais = new HashMap<>();
        gordurasSaturadas = new HashMap<>();
        gordurasTrans = new HashMap<>();
        fibraAlimentar = new HashMap<>();
        sodio = new HashMap<>();
        acucares = new HashMap<>();
        colesterol = new HashMap<>();
        calcio = new HashMap<>();
        ferro = new HashMap<>();
    }

    public HashMap<String, Float> getValorEnergetico() {
        return valorEnergetico;
    }

    public void setValorEnergetico(HashMap<String, Float> valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public void addValorEnergetico(String s, Float f){
        this.valorEnergetico.put(s,f);
    }

    public HashMap<String, Float> getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(HashMap<String, Float> carboidratos) {
        this.carboidratos = carboidratos;
    }

    public void addCarboidratos(String s, Float f){
        this.carboidratos.put(s,f);
    }

    public HashMap<String, Float> getProteinas() {
        return proteinas;
    }

    public void setProteinas(HashMap<String, Float> proteinas) {
        this.proteinas = proteinas;
    }

    public void addProteinas(String s, Float f){
        this.proteinas.put(s,f);
    }

    public HashMap<String, Float> getGordurasTotais() {
        return gordurasTotais;
    }

    public void setGordurasTotais(HashMap<String, Float> gordurasTotais) {
        this.gordurasTotais = gordurasTotais;
    }

    public void addGordurasTotais(String s, Float f){
        this.gordurasTotais.put(s,f);
    }

    public HashMap<String, Float> getGordurasSaturadas() {
        return gordurasSaturadas;
    }

    public void setGordurasSaturadas(HashMap<String, Float> gordurasSaturadas) {
        this.gordurasSaturadas = gordurasSaturadas;
    }

    public void addGordurasSaturadas(String s, Float f){
        this.gordurasSaturadas.put(s,f);
    }

    public HashMap<String, Float> getGordurasTrans() {
        return gordurasTrans;
    }

    public void setGordurasTrans(HashMap<String, Float> gordurasTrans) {
        this.gordurasTrans = gordurasTrans;
    }

    public void addGordurasTrans(String s, Float f){
        this.gordurasTrans.put(s,f);
    }

    public HashMap<String, Float> getFibraAlimentar() {
        return fibraAlimentar;
    }

    public void setFibraAlimentar(HashMap<String, Float> fibraAlimentar) {
        this.fibraAlimentar = fibraAlimentar;
    }

    public void addFibraAlimentar(String s, Float f){
        this.fibraAlimentar.put(s,f);
    }

    public HashMap<String, Float> getSodio() {
        return sodio;
    }

    public void setSodio(HashMap<String, Float> sodio) {
        this.sodio = sodio;
    }

    public void addSodio(String s, Float f){
        this.sodio.put(s,f);
    }

    public HashMap<String, Float> getAcucares() {
        return acucares;
    }

    public void setAcucares(HashMap<String, Float> acucares) {
        this.acucares = acucares;
    }

    public void addAcucares(String s, Float f){
        this.acucares.put(s,f);
    }

    public HashMap<String, Float> getColesterol() {
        return colesterol;
    }

    public void setColesterol(HashMap<String, Float> colesterol) {
        this.colesterol = colesterol;
    }

    public void addColesterol(String s, Float f){
        this.colesterol.put(s,f);
    }

    public HashMap<String, Float> getCalcio() {
        return calcio;
    }

    public void setCalcio(HashMap<String, Float> calcio) {
        this.calcio = calcio;
    }

    public void addCalcio(String s, Float f){
        this.calcio.put(s,f);
    }

    public HashMap<String, Float> getFerro() {
        return ferro;
    }

    public void setFerro(HashMap<String, Float> ferro) {
        this.ferro = ferro;
    }

    public void addFerro(String s, Float f){
        this.ferro.put(s,f);
    }
}

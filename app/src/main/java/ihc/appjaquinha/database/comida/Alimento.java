package ihc.appjaquinha.database.comida;

import java.util.HashMap;

public class Alimento {
    private String nome;
    //obrigatorios
    private Integer porcao; //g
    private Integer valorEnergetico; //kcal
    private Integer carboidratos; //g
    private Integer proteinas; //g
    private Integer gordurasTotais; //g
    private Integer gordurasSaturadas; //g
    private Integer gordurasTrans; //g
    private Integer fibraAlimentar; //g
    private Integer sodio; //mg
    //optativas
    private Integer acucares; //mg
    private Integer colesterol; //mg
    private Integer calcio; //mg
    private Integer ferro; //mg
    //voluntarias
    private HashMap<String, Float> voluntario; //nome, valor - mg

    public Alimento() {
        voluntario = new HashMap<>();
    }

    public Alimento(String nome, Integer porcao, Integer valorEnergetico, Integer carboidratos, Integer proteinas, Integer gordurasTotais, Integer gordurasSaturadas, Integer gordurasTrans, Integer fibraAlimentar, Integer sodio, Integer acucares, Integer colesterol, Integer calcio, Integer ferro) {
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

    public Alimento(String nome, Integer porcao, Integer valorEnergetico, Integer carboidratos, Integer proteinas, Integer gordurasTotais, Integer gordurasSaturadas, Integer gordurasTrans, Integer fibraAlimentar, Integer sodio, Integer acucares, Integer colesterol, Integer calcio, Integer ferro, HashMap<String, Float> voluntario) {
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

    public Integer getPorcao() {
        return porcao;
    }

    public void setPorcao(Integer porcao) {
        this.porcao = porcao;
    }

    public Integer getValorEnergetico() {
        return valorEnergetico;
    }

    public void setValorEnergetico(Integer valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public Integer getCarboidratos() {
        return carboidratos;
    }

    public void setCarboidratos(Integer carboidratos) {
        this.carboidratos = carboidratos;
    }

    public Integer getProteinas() {
        return proteinas;
    }

    public void setProteinas(Integer proteinas) {
        this.proteinas = proteinas;
    }

    public Integer getGordurasTotais() {
        return gordurasTotais;
    }

    public void setGordurasTotais(Integer gordurasTotais) {
        this.gordurasTotais = gordurasTotais;
    }

    public Integer getGordurasSaturadas() {
        return gordurasSaturadas;
    }

    public void setGordurasSaturadas(Integer gordurasSaturadas) {
        this.gordurasSaturadas = gordurasSaturadas;
    }

    public Integer getGordurasTrans() {
        return gordurasTrans;
    }

    public void setGordurasTrans(Integer gordurasTrans) {
        this.gordurasTrans = gordurasTrans;
    }

    public Integer getFibraAlimentar() {
        return fibraAlimentar;
    }

    public void setFibraAlimentar(Integer fibraAlimentar) {
        this.fibraAlimentar = fibraAlimentar;
    }

    public Integer getSodio() {
        return sodio;
    }

    public void setSodio(Integer sodio) {
        this.sodio = sodio;
    }

    public Integer getAcucares() {
        return acucares;
    }

    public void setAcucares(Integer acucares) {
        this.acucares = acucares;
    }

    public Integer getColesterol() {
        return colesterol;
    }

    public void setColesterol(Integer colesterol) {
        this.colesterol = colesterol;
    }

    public Integer getCalcio() {
        return calcio;
    }

    public void setCalcio(Integer calcio) {
        this.calcio = calcio;
    }

    public Integer getFerro() {
        return ferro;
    }

    public void setFerro(Integer ferro) {
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

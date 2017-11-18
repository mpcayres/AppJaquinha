package ihc.appjaquinha.database;

import java.util.List;

public class User {

    private String email;
    private String username;
    private String nascimento;
    private String sexo;
    private float peso;
    private int altura;
    private boolean amendoim, leite, mar, soja, trigo, lactose, gluten;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String email, String username, String nascimento, String sexo, float peso, int altura) {
        this.email = email;
        this.username = username;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura;
        amendoim = leite = mar = soja = trigo = lactose = gluten = false;
    }

    public void SetConfiguracoes(String username, String nascimento, String sexo, float peso, int altura) {
        this.username = username;
        this.nascimento = nascimento;
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura;
    }

    public void SetRestricoes(boolean amendoim, boolean leite, boolean mar,
                              boolean soja, boolean trigo, boolean lactose, boolean gluten) {
        this.amendoim = amendoim;
        this.leite = leite;
        this.mar = mar;
        this.soja = soja;
        this.trigo = trigo;
        this.lactose = lactose;
        this.trigo = trigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public boolean isAmendoim() {
        return amendoim;
    }

    public void setAmendoim(boolean amendoim) {
        this.amendoim = amendoim;
    }

    public boolean isLeite() {
        return leite;
    }

    public void setLeite(boolean leite) {
        this.leite = leite;
    }

    public boolean isMar() {
        return mar;
    }

    public void setMar(boolean mar) {
        this.mar = mar;
    }

    public boolean isSoja() {
        return soja;
    }

    public void setSoja(boolean soja) {
        this.soja = soja;
    }

    public boolean isTrigo() {
        return trigo;
    }

    public void setTrigo(boolean trigo) {
        this.trigo = trigo;
    }

    public boolean isLactose() {
        return lactose;
    }

    public void setLactose(boolean lactose) {
        this.lactose = lactose;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean gluten) {
        this.gluten = gluten;
    }
}
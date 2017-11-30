package ihc.appjaquinha.database;

public class Restricoes {
    private boolean amendoim, leite, mar, soja, trigo, lactose, gluten;

    public Restricoes(){
        amendoim = leite = mar = soja = trigo = lactose = gluten = false;
    }

    public Restricoes(boolean amendoim, boolean leite, boolean mar, boolean soja, boolean trigo, boolean lactose, boolean gluten) {
        this.amendoim = amendoim;
        this.leite = leite;
        this.mar = mar;
        this.soja = soja;
        this.trigo = trigo;
        this.lactose = lactose;
        this.gluten = gluten;
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
        this.gluten = gluten;
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

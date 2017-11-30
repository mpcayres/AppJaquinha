package ihc.appjaquinha.database.comida;

import java.util.ArrayList;
import java.util.List;

public class Geladeira {
    private List<Alimento> alimentoList;

    public Geladeira(){
        alimentoList = new ArrayList<>();
    }

    public Geladeira(List<Alimento> alimentoList) {
        this.alimentoList = alimentoList;
    }

    public List<Alimento> getAlimentoList() {
        return alimentoList;
    }

    public void setAlimentoList(List<Alimento> alimentoList) {
        this.alimentoList = alimentoList;
    }

    public void addAlimento(Alimento alimento){
        alimentoList.add(alimento);
    }
}

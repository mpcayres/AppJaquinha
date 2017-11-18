package ihc.appjaquinha.database;

import java.util.ArrayList;
import java.util.List;

public class ConsumoDia {
    private String data;
    private List<Alimento> alimentoList;

    public ConsumoDia(){
        alimentoList = new ArrayList<>();
    }

    public ConsumoDia(String data, List<Alimento> alimentoList) {
        this.data = data;
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

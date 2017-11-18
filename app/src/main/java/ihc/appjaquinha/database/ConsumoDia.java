package ihc.appjaquinha.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsumoDia {
    private String data;
    private HashMap<Alimento, Float> alimentoList; //alimento, quantidade (g)

    public ConsumoDia(){
        alimentoList = new HashMap<>();
    }

    public ConsumoDia(String data, HashMap<Alimento, Float> alimentoList) {
        this.data = data;
        this.alimentoList = alimentoList;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public HashMap<Alimento, Float> getAlimentoList() {
        return alimentoList;
    }

    public void setAlimentoList(HashMap<Alimento, Float> alimentoList) {
        this.alimentoList = alimentoList;
    }

    public void addAlimento(Alimento alimento, Float qtd){
        alimentoList.put(alimento, qtd);
    }
}

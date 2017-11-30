package ihc.appjaquinha.database.comida.diario;

import java.util.ArrayList;
import java.util.List;

public class Diario {
    private List<ConsumoDia> consumoDiaList;

    public Diario() {
        consumoDiaList = new ArrayList<>();
    }

    public Diario(List<ConsumoDia> consumoDiaList) {
        this.consumoDiaList = consumoDiaList;
    }

    public List<ConsumoDia> getConsumoDiaList() {
        return consumoDiaList;
    }

    public void setConsumoDiaList(List<ConsumoDia> consumoDiaList) {
        this.consumoDiaList = consumoDiaList;
    }

    public void addConsumoDia(ConsumoDia consumoDia){
        consumoDiaList.add(consumoDia);
    }
}

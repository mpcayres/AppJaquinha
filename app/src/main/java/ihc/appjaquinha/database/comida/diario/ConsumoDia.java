package ihc.appjaquinha.database.comida.diario;

import java.util.ArrayList;
import java.util.List;

import ihc.appjaquinha.database.comida.Alimento;

public class ConsumoDia {
    private String data;
    private List<ConsumoAlimento> consumoAlimentoList;

    public ConsumoDia(){
        consumoAlimentoList = new ArrayList<>();
    }

    public ConsumoDia(String data, List<ConsumoAlimento> consumoAlimentoList) {
        this.data = data;
        this.consumoAlimentoList = consumoAlimentoList;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<ConsumoAlimento> getConsumoAlimentoList() {
        return consumoAlimentoList;
    }

    public void setConsumoAlimentoList(List<ConsumoAlimento> consumoAlimentoList) {
        this.consumoAlimentoList = consumoAlimentoList;
    }

    public void addAlimento(Alimento alimento, Float qtd){
        consumoAlimentoList.add(new ConsumoAlimento(alimento, qtd));
    }
}

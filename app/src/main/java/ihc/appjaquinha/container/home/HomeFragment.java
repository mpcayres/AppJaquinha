package ihc.appjaquinha.container.home;


import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.database.comida.diario.ConsumoAlimento;
import ihc.appjaquinha.database.comida.diario.ConsumoDia;

import static ihc.appjaquinha.container.ContainerActivity.user;

public class HomeFragment extends Fragment {
    private String data;
    private TextView textData;

    HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<String> homeArrayList = new ArrayList<>();
    int posDiario = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textData = view.findViewById(R.id.data);
        if(data != null) textData.setText(data);

        recyclerView = view.findViewById(R.id.home_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(homeArrayList, getActivity(), this);
        recyclerView.setAdapter(homeRecyclerViewAdapter);

        view.findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContainerActivity) getActivity()).onCameraSelected();
            }
        });

        view.findViewById(R.id.adicionarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContainerActivity) getActivity()).onAlimentoSelected();
            }
        });
    }

    public void setData(String data){
        this.data = data;
        if(textData != null) textData.setText(data);
        findDia();
    }

    public void findDia(){
        if(user != null && user.getDiario() != null) {
            for (posDiario = user.getDiario().getConsumoDiaList().size() - 1; posDiario >= 0; posDiario--) {
                ConsumoDia c = user.getDiario().getConsumoDiaList().get(posDiario);
                if (c.getData().equals(data)) {
                    setHome(c);
                    break;
                }
            }
            if (posDiario < 0) {
                ConsumoDia consumoDia = new ConsumoDia();
                consumoDia.setData(data);
                user.getDiario().getConsumoDiaList().add(consumoDia);
                ((ContainerActivity) getActivity()).onDiarioEdited();
            }
        }
    }

    public void setHome(){
        if(posDiario >= 0){
            ConsumoDia c = user.getDiario().getConsumoDiaList().get(posDiario);
            setHome(c);
        }
    }

    public void setHome(ConsumoDia consumoDia) {
        homeArrayList.clear();
        for(ConsumoAlimento c : consumoDia.getConsumoAlimentoList()){
            homeArrayList.add(c.getAlimento().getNome() + " :: " + c.getQuantidade());
        }
        if(homeRecyclerViewAdapter != null) homeRecyclerViewAdapter.swap();
    }

    public ConsumoDia getConsumoDia(){
        if(posDiario >= 0) {
            for (posDiario = user.getDiario().getConsumoDiaList().size() - 1; posDiario >= 0; posDiario--) {
                ConsumoDia c = user.getDiario().getConsumoDiaList().get(posDiario);
                if (c.getData().equals(data)) {
                    return c;
                }
            }
            return null;
        }
        return user.getDiario().getConsumoDiaList().get(posDiario);
    }

    public String getData(){
        return data;
    }

    public interface HomeOnClickListener {
        public void onCameraSelected();
        public void onAlimentoSelected();
        public void onDiarioEdited();
    }
}

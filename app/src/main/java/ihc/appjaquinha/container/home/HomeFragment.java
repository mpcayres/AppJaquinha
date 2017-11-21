package ihc.appjaquinha.container.home;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.container.SearchBar;
import ihc.appjaquinha.database.comida.diario.ConsumoAlimento;
import ihc.appjaquinha.database.comida.diario.ConsumoDia;

import static ihc.appjaquinha.auth.LoginActivity.dateToString;
import static ihc.appjaquinha.container.ContainerActivity.user;

public class HomeFragment extends Fragment implements SearchBar.SearchBarListener {
    private String data;
    private SearchBar searchBar;
    HomeRecyclerViewAdapter homeRecyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<String> homeArrayList = new ArrayList<>();
    int posDiario = -1;
    boolean setSearchBar = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchBar = view.findViewById(R.id.geladeira_searchbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(data != null) setData(data);

        recyclerView = view.findViewById(R.id.home_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        homeRecyclerViewAdapter = new HomeRecyclerViewAdapter(homeArrayList, getActivity(), this);
        recyclerView.setAdapter(homeRecyclerViewAdapter);

        view.findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBar != null){
                    searchBar.dismissKeyboard();
                }
                setSearchBar = false;
                ((ContainerActivity) getActivity()).onCameraSelected();
            }
        });

        view.findViewById(R.id.adicionarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchBar != null){
                    searchBar.dismissKeyboard();
                }
                setSearchBar = false;
                ((ContainerActivity) getActivity()).onAlimentoSelected();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if(searchBar != null){
            searchBar.dismissKeyboard();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(searchBar != null){
            searchBar.dismissKeyboard();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setData(String data){
        this.data = data;
        Activity activity = getActivity();
        if(activity != null) ((ContainerActivity) activity).setToolbarText(data);
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
        } else{
            findDia();
        }
    }

    public void setHome(ConsumoDia consumoDia) {
        homeArrayList.clear();
        for(ConsumoAlimento c : consumoDia.getConsumoAlimentoList()){
            homeArrayList.add(c.getAlimento().getNome() + " :: " + c.getQuantidade());
        }
        if(homeRecyclerViewAdapter != null) homeRecyclerViewAdapter.swap();
        if(user != null && user.getGeladeira() != null){
            setupSearchBar();
        }
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

    public void setupSearchBar() {
        if(searchBar != null) {
            if(!setSearchBar) {
                searchBar.setupSearchBar(getActivity(), this, user.getGeladeira().getAlimentoList());
                setSearchBar = true;
            } else{
                searchBar.changeArrayAdapter(user.getGeladeira().getAlimentoList());
            }
        }
    }

    @Override
    public void onDropDownItemClicked(int indiceOriginal) {
        ((ContainerActivity) getActivity()).addAlimentoDiario(
                user.getGeladeira().getAlimentoList().get(indiceOriginal), data);
        searchBar.dismissKeyboard();
    }

    @Override
    public void onSearchBarClickedOrSomethingTyped() {
        searchBar.showDropDown();
    }

    public interface HomeOnClickListener {
        public void onCameraSelected();
        public void onAlimentoSelected();
        public void onDiarioEdited();
    }
}

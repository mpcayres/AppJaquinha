package ihc.appjaquinha.container.geladeira;

import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Calendar;

import ihc.appjaquinha.R;
import ihc.appjaquinha.auth.LoginActivity;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.container.SearchBar;
import ihc.appjaquinha.database.comida.Alimento;

import static ihc.appjaquinha.auth.LoginActivity.dateToString;
import static ihc.appjaquinha.container.ContainerActivity.user;

public class GeladeiraFragment extends Fragment implements SearchBar.SearchBarListener {

    private SearchBar searchBar;
    GeladeiraRecyclerViewAdapter geladeiraRecyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<String> geladeiraArrayList = new ArrayList<>();
    boolean setSearchBar = false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_geladeira, container, false);

        ((ContainerActivity) getActivity()).setToolbarText("Geladeira");
        searchBar = view.findViewById(R.id.geladeira_searchbar);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setGeladeira();

        recyclerView = view.findViewById(R.id.geladeira_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        geladeiraRecyclerViewAdapter = new GeladeiraRecyclerViewAdapter(geladeiraArrayList, getActivity());
        recyclerView.setAdapter(geladeiraRecyclerViewAdapter);

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
        searchBar.dismissKeyboard();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(searchBar != null){
            searchBar.dismissKeyboard();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setGeladeira() {
        if (user != null && user.getGeladeira() != null){
            geladeiraArrayList.clear();
            for (Alimento a : user.getGeladeira().getAlimentoList()) {
                geladeiraArrayList.add(a.getNome());
            }
            if(geladeiraRecyclerViewAdapter != null) geladeiraRecyclerViewAdapter.swap();
            setupSearchBar();
        }
    }

    private void setupSearchBar() {
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
                user.getGeladeira().getAlimentoList().get(indiceOriginal),
                dateToString(Calendar.getInstance(), "dd/MM/yyyy"));
        searchBar.dismissKeyboard();
    }

    @Override
    public void onSearchBarClickedOrSomethingTyped() {
        searchBar.showDropDown();
    }

    public interface GeladeiraOnClickListener {
        public void onCameraSelected();
        public void onAlimentoSelected();
        public void onAlimentoSelected(Alimento alimento);
        public void onAlimentoSelectedEdit(Alimento alimento, int position);
        public void onAlimentoRemoved(int position);
        public void addAlimentoDiario(final Alimento alimento, final String data);
    }
}

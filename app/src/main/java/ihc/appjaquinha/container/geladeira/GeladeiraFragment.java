package ihc.appjaquinha.container.geladeira;

import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.database.comida.Alimento;

import static ihc.appjaquinha.container.ContainerActivity.user;

public class GeladeiraFragment extends Fragment {

    GeladeiraRecyclerViewAdapter geladeiraRecyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<String> geladeiraArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_geladeira, container, false);
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

    public void setGeladeira() {
        geladeiraArrayList.clear();
        for(Alimento a : user.getGeladeira().getAlimentoList()){
            geladeiraArrayList.add(a.getNome());
        }
        if(geladeiraRecyclerViewAdapter != null) geladeiraRecyclerViewAdapter.swap();
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

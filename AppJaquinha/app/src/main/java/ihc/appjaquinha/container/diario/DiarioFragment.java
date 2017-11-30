package ihc.appjaquinha.container.diario;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.ArrayList;
import java.util.Calendar;

import ihc.appjaquinha.R;
import ihc.appjaquinha.auth.LoginActivity;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.database.comida.diario.ConsumoDia;

import static ihc.appjaquinha.container.ContainerActivity.user;

public class DiarioFragment extends Fragment {
    DiarioRecyclerViewAdapter diarioRecyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<String> diarioArrayList = new ArrayList<>();
    private DatePickerDialog data_Dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((ContainerActivity) getActivity()).setToolbarText("Diário");
        return inflater.inflate(R.layout.fragment_diario, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDiario();

        recyclerView = view.findViewById(R.id.diario_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        diarioRecyclerViewAdapter = new DiarioRecyclerViewAdapter(diarioArrayList, getActivity());
        recyclerView.setAdapter(diarioRecyclerViewAdapter);

        view.findViewById(R.id.adicionarButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar data_atual = Calendar.getInstance();
                data_Dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        Calendar dataPicker = Calendar.getInstance();
                        dataPicker.set(year, monthOfYear, dayOfMonth);

                        ConsumoDia consumoDia = new ConsumoDia();
                        consumoDia.setData(LoginActivity.dateToString(dataPicker, "dd/MM/yyyy"));
                        user.getDiario().getConsumoDiaList().add(consumoDia);
                        ((ContainerActivity) getActivity()).onDiarioEdited();

                    }

                }, data_atual.get(Calendar.YEAR), data_atual.get(Calendar.MONTH), data_atual.get(Calendar.DAY_OF_MONTH));
                data_Dialog.getDatePicker().setMaxDate(data_atual.getTimeInMillis());
                data_Dialog.show();
            }
        });
    }

    public void setDiario(){
        if(user != null && user.getDiario() != null) {
            diarioArrayList.clear();
            for (int i = 0; i < user.getDiario().getConsumoDiaList().size(); i++) {
                ConsumoDia consumoDia = user.getDiario().getConsumoDiaList().get(i);
                diarioArrayList.add(consumoDia.getData());
            }
            if (diarioRecyclerViewAdapter != null) diarioRecyclerViewAdapter.swap();
        }
    }

    public interface DiarioOnClickListener {
        public void onDiarioEdited();
        public void onHomeSelected(String data);
    }
}

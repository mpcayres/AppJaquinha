package ihc.appjaquinha.container.objetivos;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.database.objetivos.InfoObjetivo;

import static ihc.appjaquinha.container.ContainerActivity.user;

public class ObjetivosFragment  extends Fragment implements AdapterView.OnItemSelectedListener {
    private DatabaseReference mDatabase;
    private Spinner dropdown;
    private Boolean spinnerTouched = false;

    ObjetivosRecyclerViewAdapter objetivosRecyclerViewAdapter;
    RecyclerView recyclerView;
    ArrayList<String> objetivosArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ((ContainerActivity) getActivity()).setToolbarText("Objetivos");
        return inflater.inflate(R.layout.fragment_objetivos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dropdown = view.findViewById(R.id.spinner);
        String[] items = new String[]{"Valor Energético", "Carboidratos", "Proteínas",
        "Gorduras Totais", "Gorduras Saturadas", "Gorduras Trans", "Fibra Alimentar", "Sódio",
                "Açúcares", "Colesterol", "Cálcio", "Ferro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);
        dropdown.setSelection(0, false);
        dropdown.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                spinnerTouched = true;
                return false;
            }
        });
        dropdown.setOnItemSelectedListener(this);

        setObjetivos();

        recyclerView = view.findViewById(R.id.objetivos_recyclerview);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        objetivosRecyclerViewAdapter = new ObjetivosRecyclerViewAdapter(objetivosArrayList, getActivity());
        recyclerView.setAdapter(objetivosRecyclerViewAdapter);
    }

    public void setObjetivos(){
        objetivosArrayList.clear();
        if(user != null && user.getObjetivos() != null) {
            addList(user.getObjetivos().getValorEnergetico(), "Valor Energético", "kcal");
            addList(user.getObjetivos().getCarboidratos(), "Carboidratos", "g");
            addList(user.getObjetivos().getProteinas(), "Proteínas", "g");
            addList(user.getObjetivos().getGordurasTotais(), "Gorduras Totais", "g");
            addList(user.getObjetivos().getGordurasSaturadas(),"Gorduras Saturadas", "g");
            addList(user.getObjetivos().getGordurasTrans(), "Gorduras Trans", "g");
            addList(user.getObjetivos().getFibraAlimentar(), "Fibra Alimentar", "g");
            addList(user.getObjetivos().getSodio(), "Sódio", "mg");
            addList(user.getObjetivos().getAcucares(), "Açúcares", "mg");
            addList(user.getObjetivos().getColesterol(), "Colesterol", "mg");
            addList(user.getObjetivos().getCalcio(), "Cálcio", "mg");
            addList(user.getObjetivos().getFerro(), "Ferro", "mg");
        }
        if(objetivosRecyclerViewAdapter != null) objetivosRecyclerViewAdapter.swap();
    }

    private void addList(InfoObjetivo infoObjetivo, String campo, String valor){
        if(infoObjetivo != null && infoObjetivo.getQuantidade() != null && infoObjetivo.getTempo() != null){
            objetivosArrayList.add(campo + ": " + infoObjetivo.getQuantidade() +
                    " " + valor + " / " + infoObjetivo.getTempo() + " dias");
        }
    }

    public void onItemSelected(AdapterView<?> parent, View v, final int position, long id) {
        if (spinnerTouched) {
            String valor = "g";
            switch (position) {
                case 0:
                    valor = "kcal";
                    break;
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                    valor = "mg";
                    break;
            }

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            final EditText inputDia = new EditText(getContext());
            inputDia.setLayoutParams(lp);
            inputDia.setHint("Periodicidade (dias)");
            inputDia.setInputType(InputType.TYPE_CLASS_NUMBER);
            inputDia.setTextColor(Color.WHITE);
            inputDia.setHintTextColor(Color.GRAY);

            final EditText inputQtd = new EditText(getContext());
            inputQtd.setLayoutParams(lp);
            inputQtd.setHint("Quantidade (" + valor + ")");
            inputQtd.setInputType(InputType.TYPE_CLASS_NUMBER);
            inputQtd.setTextColor(Color.WHITE);
            inputQtd.setHintTextColor(Color.GRAY);

            LinearLayout layout = new LinearLayout(getContext());
            layout.addView(inputQtd);
            layout.addView(inputDia);

            builder.setView(layout);
            builder.setTitle(dropdown.getSelectedItem().toString())
                    .setMessage("Determine a quantidade (" + valor + ") e a periodicidade (dias)")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            switch (position) {
                                case 0:
                                    user.getObjetivos().setValorEnergetico(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 1:
                                    user.getObjetivos().setCarboidratos(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 2:
                                    user.getObjetivos().setProteinas(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 3:
                                    user.getObjetivos().setGordurasTotais(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 4:
                                    user.getObjetivos().setGordurasSaturadas(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 5:
                                    user.getObjetivos().setGordurasTrans(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 6:
                                    user.getObjetivos().setFibraAlimentar(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 7:
                                    user.getObjetivos().setSodio(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 8:
                                    user.getObjetivos().setAcucares(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 9:
                                    user.getObjetivos().setColesterol(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 10:
                                    user.getObjetivos().setCalcio(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                                case 11:
                                    user.getObjetivos().setFerro(
                                            new InfoObjetivo(Integer.parseInt(inputDia.getText().toString()),
                                                    Float.parseFloat(inputQtd.getText().toString())));
                                    break;
                            }
                            mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                            Toast.makeText(getActivity(), "Objetivos atualizados.",
                                    Toast.LENGTH_SHORT).show();
                            setObjetivos();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        spinnerTouched = false;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}

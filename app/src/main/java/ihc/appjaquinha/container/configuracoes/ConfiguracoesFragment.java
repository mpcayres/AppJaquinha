package ihc.appjaquinha.container.configuracoes;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Field;
import java.util.Calendar;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;

import static ihc.appjaquinha.auth.LoginActivity.dateToString;
import static ihc.appjaquinha.auth.LoginActivity.stringToDate;
import static ihc.appjaquinha.container.ContainerActivity.user;

public class ConfiguracoesFragment extends Fragment {
    private DatabaseReference mDatabase;
    private EditText nomeText, anoData, pesoText, alturaText;
    private Spinner diaData, mesData;
    private RadioButton sexoMasculino, sexoFeminino, sexoOutro;

    private String erroLog = "ConfiguracoesFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ((ContainerActivity) getActivity()).setToolbarText("Configurações");
        return inflater.inflate(R.layout.fragment_configuracoes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //emailText = view.findViewById(R.id.emailConfig);
        //senhaText = view.findViewById(R.id.senha);
        nomeText = view.findViewById(R.id.nomeConfig);
        pesoText = view.findViewById(R.id.pesoConfig);
        alturaText = view.findViewById(R.id.alturaConfig);

        sexoMasculino = view.findViewById(R.id.sexoMasculinoConfig);
        sexoFeminino = view.findViewById(R.id.sexoFemininoConfig);
        sexoOutro = view.findViewById(R.id.sexoOutroConfig);

        sexoMasculino.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sexoFeminino.setChecked(false);
                sexoOutro.setChecked(false);
                sexoMasculino.setError(null);
            }
        });

        sexoFeminino.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sexoMasculino.setChecked(false);
                sexoOutro.setChecked(false);
                sexoFeminino.setError(null);
            }
        });

        sexoOutro.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                sexoMasculino.setChecked(false);
                sexoFeminino.setChecked(false);
                sexoOutro.setError(null);
            }
        });
        /*
            Isso aqui tudo é para criar o seletor de data
        */
        //primeiro, os views
        diaData = view.findViewById(R.id.diaSpinnerConfig);
        mesData = view.findViewById(R.id.mesSpinnerConfig);
        anoData = view.findViewById(R.id.anoTextConfig);
        Log.v(erroLog, "Todos os Views relacionados");

        //Agora, colocando um ArrayAdapter no diaData, que pega o
        //array de inteiros e coloca como campos do spinner
        String[] dias31 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
        String[] dias30 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"};
        String[] dias29 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29"};
        String[] dias28 = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28"};
        final ArrayAdapter<String> adapterDias31 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, dias31);
        adapterDias31.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> adapterDias30 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, dias30);
        adapterDias30.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> adapterDias29 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, dias29);
        adapterDias29.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final ArrayAdapter<String> adapterDias28 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, dias28);
        adapterDias28.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        diaData.setAdapter(adapterDias31);
        //agora, limitando a altura do spinner por meio de um popup
        //SpinnerSize(diaData, 200);

        //Colocando os valores do segundo spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.meses, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mesData.setAdapter(adapter);
        //SpinnerSize(mesData, 200);
        mesData.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 1){
                    if(anoData != null && anoData.getText() != null && anoData.getText().toString().length() == 4){
                        int ano = Integer.parseInt(anoData.getText().toString());
                        if (ano % 4 == 0 && (ano % 400 == 0 || ano % 100 != 0)) {
                            diaData.setAdapter(adapterDias29);
                        } else{
                            diaData.setAdapter(adapterDias28);
                        }
                    } else {
                        diaData.setAdapter(adapterDias28);
                    }
                } else if(i == 3 || i == 5 || i == 8 || i == 10){
                    diaData.setAdapter(adapterDias30);
                } else{
                    diaData.setAdapter(adapterDias31);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        anoData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(diaData != null && mesData != null && mesData.getSelectedItemPosition() == 1 &&
                        anoData != null && anoData.getText() != null && anoData.getText().toString().length() == 4){
                    int ano = Integer.parseInt(anoData.getText().toString());
                    if (ano % 4 == 0 && (ano % 400 == 0 || ano % 100 != 0)) {
                        diaData.setAdapter(adapterDias29);
                    } else{
                        diaData.setAdapter(adapterDias28);
                    }
                }
            }
        });

        setConfiguracoes();

        view.findViewById(R.id.atualizarbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(erroLog, "Botão de cadastro clicado.");
                Animation wiggle = AnimationUtils.loadAnimation(getContext(), R.anim.wiggle);
                String nome = nomeText.getText().toString();
                String dia;
                int diaN = diaData.getSelectedItemPosition()+1;
                if(diaN < 10){
                    dia = "0" + diaN;
                } else{
                    dia = "" + diaN;
                }
                String mes;
                int mesN = mesData.getSelectedItemPosition()+1;
                if(mesN < 10){
                    mes = "0" + mesN;
                } else{
                    mes = "" + mesN;
                }
                String data = dia + "/" + mes + "/" + anoData.getText().toString();
                String sexo = "";
                if(sexoMasculino.isChecked()){
                    sexo = "MASCULINO";
                } else if(sexoFeminino.isChecked()){
                    sexo = "FEMININO";
                } else if(sexoOutro.isChecked()){
                    sexo = "OUTRO";
                }
                float peso = pesoText.getText().toString().isEmpty() ? 0 : Float.parseFloat(pesoText.getText().toString());
                int altura = alturaText.getText().toString().isEmpty() ? 0 : Integer.parseInt(alturaText.getText().toString());

                if(nome.isEmpty() || nome.equals("")) {
                    nomeText.startAnimation(wiggle);
                    nomeText.setError("Preencha seu nome de usuário");
                }
                else if(anoData.getText().toString().length() < 4 ||
                        Integer.parseInt(anoData.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR) ||
                        Integer.parseInt(anoData.getText().toString()) < Calendar.getInstance().get(Calendar.YEAR) - 150 ){
                    anoData.startAnimation(wiggle);
                    anoData.setError("Preencha com ano válido");
                }
                else if(sexo.isEmpty() || sexo.equals("")){
                    sexoMasculino.startAnimation(wiggle);
                    sexoFeminino.startAnimation(wiggle);
                    sexoOutro.startAnimation(wiggle);
                    sexoOutro.setError("Escolha uma opção");
                }
                else if(peso == 0) {
                    pesoText.startAnimation(wiggle);
                    pesoText.setError("Preencha sua massa corporal");
                }
                else if(altura == 0) {
                    alturaText.startAnimation(wiggle);
                    alturaText.setError("Preencha sua altura");
                } else {
                    user.SetConfiguracoes(nome, data, sexo, peso, altura);
                    mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                    Toast.makeText(getActivity(), "Usuário atualizado.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setConfiguracoes(){
        Log.v(erroLog, "setConfiguracoes.");
        if (user != null) {
            Log.v(erroLog, "user not null.");
            nomeText.setText(user.getUsername());
            Log.v(erroLog, "Name is set: " + nomeText.getText());
            //dataText.setText(user.getNascimento());
            String dataNascimento = user.getNascimento();
            Log.v(erroLog, "Data de nascimento: " + dataNascimento);
            int indexOfBar1 = dataNascimento.indexOf('/');
            Log.v(erroLog, "indexOfBar1: " + indexOfBar1);
            int indexOfBar2 = dataNascimento.indexOf('/',user.getNascimento().indexOf('/')+1);
            Log.v(erroLog, "indexOfBar2: " + indexOfBar2);

            String dia = dataNascimento.substring(0, indexOfBar1);
            Log.v(erroLog, "Dia: " + dia );
            diaData.setSelection(Integer.parseInt(dia)-1);

            String mes = dataNascimento.substring(indexOfBar1+1, indexOfBar2);
            Log.v(erroLog, "Mes: " + mes );
            mesData.setSelection(Integer.parseInt(mes)-1);

            String ano = user.getNascimento().substring(indexOfBar2+1);
            Log.v(erroLog, "Ano: " + ano );
            anoData.setText(ano);

            switch (user.getSexo()) {
                case "MASCULINO":
                    Log.v(erroLog, "Sexo is set: Masculino");
                    sexoMasculino.setChecked(true);
                    break;
                case "FEMININO":
                    Log.v(erroLog, "Sexo Feminino");
                    sexoFeminino.setChecked(true);
                    break;
                case "OUTRO":
                    Log.v(erroLog, "Sexo Outro");
                    sexoOutro.setChecked(true);
                    break;
            }
            pesoText.setText(String.valueOf(user.getPeso()));
            Log.v(erroLog, "Peso is set: " + pesoText.getText());
            alturaText.setText(String.valueOf(user.getAltura()));
            Log.v(erroLog, "Altura is set: " + alturaText.getText());

        }
        Log.v(erroLog, "all config set.");
    }
    private void SpinnerSize(Spinner spinner, int size){
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            android.widget.ListPopupWindow popupWindow = (android.widget.ListPopupWindow) popup.get(spinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(size);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {
            // silently fail...
        }
    }
}

package ihc.appjaquinha.auth;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import ihc.appjaquinha.R;

public class RegisterFragment extends Fragment {
    private OnRegisterInteractionListener mCallback;
    private EditText emailText, senhaText, nomeText, anoData, pesoText, alturaText;
    private Spinner diaData, mesData;
    private RadioButton sexoMasculino, sexoFeminino, sexoOutro;
    private ScrollView scrollview;
    private String erroLog = "RegisterFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        scrollview = view.findViewById(R.id.scrollRegister);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailText = view.findViewById(R.id.email);
        senhaText = view.findViewById(R.id.senha);
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

        view.findViewById(R.id.cadastrobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(erroLog, "Botão de cadastro clicado.");
                Animation wiggle = AnimationUtils.loadAnimation(getContext(), R.anim.wiggle);
                String email = emailText.getText().toString();
                String senha = senhaText.getText().toString();
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
                Log.v(erroLog, "Data de nascimento: " + data);
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

                if(email.isEmpty() || email.equals("")) {
                    scrollUp();
                    emailText.startAnimation(wiggle);
                    emailText.setError("Preencha seu email");
                }
                else if(!email.contains("@") || !email.contains(".")) {
                    scrollUp();
                    emailText.startAnimation(wiggle);
                    emailText.setError("Email inválido");
                }
                else if(senha.isEmpty() || senha.equals("")) {
                    scrollUp();
                    senhaText.startAnimation(wiggle);
                    senhaText.setError("Preencha sua senha");
                }
                else if(senha.length() < 6) {
                    scrollUp();
                    senhaText.startAnimation(wiggle);
                    senhaText.setError("Senha deve ter no mínimo 6 dígitos");
                }
                else if(nome.isEmpty() || nome.equals("")) {
                    scrollUp();
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
                }
                else if (mCallback != null) {
                    mCallback.onRegisterInteraction(email, senha, nome, data, sexo, peso, altura);
                }
            }
        });
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnRegisterInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private void scrollUp(){
        scrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // Ready, move up
                scrollview.fullScroll(View.FOCUS_UP);
            }
        });
    }

    interface OnRegisterInteractionListener {

        public void onRegisterInteraction(String email, String senha, String nome, String data, String sexo, float peso, int altura);

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

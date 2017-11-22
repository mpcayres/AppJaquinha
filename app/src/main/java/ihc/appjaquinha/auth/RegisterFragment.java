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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.List;

import ihc.appjaquinha.R;

public class RegisterFragment extends Fragment {
    private OnRegisterInteractionListener mCallback;
    private EditText emailText, senhaText, nomeText, anoData, sexoText, pesoText, alturaText;
    private TextView dataText;
    private DatePickerDialog data_Dialog;
    private Spinner diaData, mesData;
    private RadioButton sexoMasculino, sexoFeminino, sexoOutro;
    private String erroLog = "RegisterFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        emailText = view.findViewById(R.id.email);
        senhaText = view.findViewById(R.id.senha);
        nomeText = view.findViewById(R.id.nome);
        pesoText = view.findViewById(R.id.peso);
        alturaText = view.findViewById(R.id.altura);
        dataText = view.findViewById(R.id.data_de_nascimento);

        sexoMasculino = view.findViewById(R.id.sexoMasculino);
        sexoFeminino = view.findViewById(R.id.sexoFeminino);
        sexoOutro = view.findViewById(R.id.sexoOutro);

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
        diaData = view.findViewById(R.id.diaSpinner);
        mesData = view.findViewById(R.id.mesSpinner);
        anoData = view.findViewById(R.id.anoText);

        //Agora, colocando um ArrayAdapter no diaData, que pega o
        //array de inteiros e coloca como campos do spinner
        Integer[] dias31 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                       11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                                       21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
        Integer[] dias30 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
        Integer[] dias28 = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28};
        final ArrayAdapter<Integer> adapterDias31 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, dias31);
        final ArrayAdapter<Integer> adapterDias30 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, dias31);
        final ArrayAdapter<Integer> adapterDias28 = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, dias31);

        diaData.setAdapter(adapterDias31);
        //agora, limitando a altura do spinner por meio de um popup
        SpinnerSize(diaData, 200);

        //Colocando os valores do segundo spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                                                                            R.array.meses,
                                                                            android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mesData.setAdapter(adapter);
        //limitando o tamanho do spinner
        SpinnerSize(mesData, 200);

        view.findViewById(R.id.cadastrobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(erroLog, "Botão de cadastro clicado.");
                Animation wiggle = AnimationUtils.loadAnimation(getContext(), R.anim.wiggle);
                String email = emailText.getText().toString();
                String senha = senhaText.getText().toString();
                String nome = nomeText.getText().toString();
                String data = (diaData.getSelectedItemPosition()+1) + "/" + (mesData.getSelectedItemPosition()+1) + "/" + anoData.getText().toString();//dataText.getText().toString();
                Log.v(erroLog, "Data de nascimento: " + data);
                String sexo = "";
                if(sexoMasculino.isChecked()){
                    sexo = "MASCULINO";
                }else if(sexoFeminino.isChecked()){
                    sexo = "FEMININO";
                }else if(sexoOutro.isChecked()){
                    sexo = "OUTRO";
                }else{
                    sexoMasculino.startAnimation(wiggle);
                    sexoFeminino.startAnimation(wiggle);
                    sexoOutro.startAnimation(wiggle);
                    sexoOutro.setError("Escolha uma opção");
                }
                float peso = pesoText.getText().toString().isEmpty() ? 0 : Float.parseFloat(pesoText.getText().toString());
                int altura = alturaText.getText().toString().isEmpty() ? 0 : Integer.parseInt(alturaText.getText().toString());

                if(email.isEmpty() || email.equals("")) {
                    emailText.startAnimation(wiggle);
                    emailText.setError("Preencha seu email");
                }
                else if(!email.contains("@") || !email.contains(".")) {
                    emailText.startAnimation(wiggle);
                    emailText.setError("Email inválido");
                }
                else if(senha.isEmpty() || senha.equals("")) {
                    senhaText.startAnimation(wiggle);
                    senhaText.setError("Preencha sua senha");
                }
                else if(nome.isEmpty() || nome.equals("")) {
                    nomeText.startAnimation(wiggle);
                    nomeText.setError("Preencha seu nome de usuário");
                }

                else if(mesData.getSelectedItemPosition() == 1){
                    diaData.setAdapter(adapterDias28);
                    diaData.setSelection(0, true);
                    Log.v(erroLog, "Dia incompativel com mês Fevereiro");
                    dataText.startAnimation(wiggle);
                    dataText.setError("Dia inválido para o mês escolhido");
                }


                else if(Integer.parseInt(anoData.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR) ||
                        Integer.parseInt(anoData.getText().toString()) < Calendar.getInstance().get(Calendar.YEAR) - 150 ){
                    anoData.startAnimation(wiggle);
                    anoData.setError("Preencha com ano válido");
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

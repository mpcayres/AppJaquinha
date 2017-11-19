package ihc.appjaquinha.container.alimento;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.database.comida.Alimento;

public class AlimentoFragment extends Fragment {
    EditText textnome, textporcao, textvalorEnergetico, textcarboidratos, textproteinas, textgordurasTotais, textgordurasSaturadas, textgordurasTrans, textfibraAlimentar, textsodio, textacucares, textcolesterol, textcalcio, textferro;
    Alimento alimento;
    boolean edit = false;
    int position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alimento, container, false);

        textnome = view.findViewById(R.id.nome);
        textporcao = view.findViewById(R.id.porcao);
        textvalorEnergetico = view.findViewById(R.id.kcal);
        textcarboidratos = view.findViewById(R.id.carboidrato);
        textproteinas = view.findViewById(R.id.proteina);
        textgordurasTotais = view.findViewById(R.id.gordurasTotais);
        textgordurasSaturadas = view.findViewById(R.id.gordurasSat);
        textgordurasTrans = view.findViewById(R.id.gordurasTrans);
        textfibraAlimentar = view.findViewById(R.id.fibra);
        textsodio = view.findViewById(R.id.sodio);
        textacucares = view.findViewById(R.id.acucares);
        textcolesterol = view.findViewById(R.id.colesterol);
        textcalcio = view.findViewById(R.id.calcio);
        textferro = view.findViewById(R.id.ferro);

        fillForm();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.camerabutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContainerActivity) getActivity()).onCameraSelected();
            }
        });

        view.findViewById(R.id.salvarbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alimento alimento = new Alimento(textnome.getText().toString(),
                        textporcao.getText().toString().isEmpty() ? 0 : Integer.parseInt(textporcao.getText().toString()),
                        textvalorEnergetico.getText().toString().isEmpty() ? 0 : Integer.parseInt(textvalorEnergetico.getText().toString()),
                        textcarboidratos.getText().toString().isEmpty() ? 0 : Integer.parseInt(textcarboidratos.getText().toString()),
                        textproteinas.getText().toString().isEmpty() ? 0 : Integer.parseInt(textproteinas.getText().toString()),
                        textgordurasTotais.getText().toString().isEmpty() ? 0 : Integer.parseInt(textgordurasTotais.getText().toString()),
                        textgordurasSaturadas.getText().toString().isEmpty() ? 0 : Integer.parseInt(textgordurasSaturadas.getText().toString()),
                        textgordurasTrans.getText().toString().isEmpty() ? 0 : Integer.parseInt(textgordurasTrans.getText().toString()),
                        textfibraAlimentar.getText().toString().isEmpty() ? 0 : Integer.parseInt(textfibraAlimentar.getText().toString()),
                        textsodio.getText().toString().isEmpty() ? 0 : Integer.parseInt(textsodio.getText().toString()),
                        textacucares.getText().toString().isEmpty() ? 0 : Integer.parseInt(textacucares.getText().toString()),
                        textcolesterol.getText().toString().isEmpty() ? 0 : Integer.parseInt(textcolesterol.getText().toString()),
                        textcalcio.getText().toString().isEmpty() ? 0 : Integer.parseInt(textcalcio.getText().toString()),
                        textferro.getText().toString().isEmpty() ? 0 : Integer.parseInt(textferro.getText().toString()));
                if(!edit) ((ContainerActivity) getActivity()).onAlimentoCreated(alimento);
                else ((ContainerActivity) getActivity()).onAlimentoEdited(alimento, position);
            }
        });

        view.findViewById(R.id.cancelarbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void fillForm(){
        if(alimento.getNome() != null) textnome.setText(alimento.getNome());
        if(alimento.getPorcao() != null) textporcao.setText(String.valueOf(alimento.getPorcao()));
        if(alimento.getValorEnergetico() != null) textvalorEnergetico.setText(String.valueOf(alimento.getValorEnergetico()));
        if(alimento.getCarboidratos() != null) textcarboidratos.setText(String.valueOf(alimento.getCarboidratos()));
        if(alimento.getProteinas() != null) textproteinas.setText(String.valueOf(alimento.getProteinas()));
        if(alimento.getGordurasTotais() != null) textgordurasTotais.setText(String.valueOf(alimento.getGordurasTotais()));
        if(alimento.getGordurasSaturadas() != null) textgordurasSaturadas.setText(String.valueOf(alimento.getGordurasSaturadas()));
        if(alimento.getGordurasTrans() != null) textgordurasTrans.setText(String.valueOf(alimento.getGordurasTrans()));
        if(alimento.getFibraAlimentar() != null) textfibraAlimentar.setText(String.valueOf(alimento.getFibraAlimentar()));
        if(alimento.getSodio() != null) textsodio.setText(String.valueOf(alimento.getSodio()));
        if(alimento.getAcucares() != null) textacucares.setText(String.valueOf(alimento.getAcucares()));
        if(alimento.getColesterol() != null) textcolesterol.setText(String.valueOf(alimento.getColesterol()));
        if(alimento.getCalcio() != null) textcalcio.setText(String.valueOf(alimento.getCalcio()));
        if(alimento.getFerro() != null) textferro.setText(String.valueOf(alimento.getFerro()));
    }

    public void fillForm(Alimento alimento){
        this.alimento = alimento;
    }

    public void setEdit(boolean edit, int position){
        this.edit = edit;
        this.position = position;
    }

    public interface AlimentoOnClickListener {
        public void onCameraSelected();
        public void onAlimentoCreated(Alimento alimento);
        public void onAlimentoEdited(Alimento alimento, int position);
    };
}

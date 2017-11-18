package ihc.appjaquinha.container;

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
import ihc.appjaquinha.database.comida.Alimento;

public class AlimentoFragment extends Fragment {
    private DatabaseReference mDatabase;
    EditText textnome, textporcao, textvalorEnergetico, textcarboidratos, textproteinas, textgordurasTotais, textgordurasSaturadas, textgordurasTrans, textfibraAlimentar, textsodio, textacucares, textcolesterol, textcalcio, textferro;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return inflater.inflate(R.layout.fragment_alimento, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                ((ContainerActivity) getActivity()).onAlimentoCreated(alimento);
            }
        });

        view.findViewById(R.id.cancelarbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public void fillForm(Alimento alimento){

    }

    public interface AlimentoOnClickListener {
        public void onCameraSelected();
        public void onAlimentoCreated(Alimento alimento);
    };
}

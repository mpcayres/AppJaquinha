package ihc.appjaquinha.container.restricoes;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ihc.appjaquinha.R;

import static ihc.appjaquinha.container.ContainerActivity.user;

public class RestricoesFragment extends Fragment {
    private DatabaseReference mDatabase;
    private CheckBox amendoim, leite, mar, soja, trigo, lactose, gluten;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        return inflater.inflate(R.layout.fragment_restricoes, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        amendoim = view.findViewById(R.id.checkBoxAmendoinCastanha);
        leite = view.findViewById(R.id.checkBoxLeite);
        mar = view.findViewById(R.id.checkBoxPeixe);
        soja = view.findViewById(R.id.checkBoxSoja);
        trigo = view.findViewById(R.id.checkBoxTrigo);
        lactose = view.findViewById(R.id.checkBoxLactose);
        gluten = view.findViewById(R.id.checkBoxGluten);

        if (user != null && user.getRestricoes() != null) {
            amendoim.setChecked(user.getRestricoes().isAmendoim());
            leite.setChecked(user.getRestricoes().isLeite());
            mar.setChecked(user.getRestricoes().isMar());
            soja.setChecked(user.getRestricoes().isSoja());
            trigo.setChecked(user.getRestricoes().isTrigo());
            lactose.setChecked(user.getRestricoes().isLactose());
            gluten.setChecked(user.getRestricoes().isGluten());
        }

        view.findViewById(R.id.atualizarbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.SetRestricoes(amendoim.isChecked(), leite.isChecked(), mar.isChecked(),
                        soja.isChecked(), trigo.isChecked(), lactose.isChecked(), gluten.isChecked());
                mDatabase.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
            }
        });
    }
}

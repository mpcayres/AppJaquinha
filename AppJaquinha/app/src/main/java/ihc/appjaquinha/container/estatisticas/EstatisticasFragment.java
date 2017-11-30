package ihc.appjaquinha.container.estatisticas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;

public class EstatisticasFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((ContainerActivity) getActivity()).setToolbarText("Estatísticas");
        return inflater.inflate(R.layout.fragment_estatisticas, container, false);
    }
}

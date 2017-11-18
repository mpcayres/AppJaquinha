package ihc.appjaquinha.container.home;


import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;

public class HomeFragment extends Fragment {
    private String data;
    private TextView textData;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textData = view.findViewById(R.id.data);
        if(data != null) textData.setText(data);

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

    public void setData(String data){
        this.data = data;
        if(textData != null) textData.setText(data);
    }

    public String getData(){
        return data;
    }

    public interface HomeOnClickListener {
        public void onCameraSelected();
        public void onAlimentoSelected();
    }
}

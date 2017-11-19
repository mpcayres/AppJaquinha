package ihc.appjaquinha.container.geladeira;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

import ihc.appjaquinha.R;
import ihc.appjaquinha.auth.LoginActivity;
import ihc.appjaquinha.container.ContainerActivity;

import static ihc.appjaquinha.container.ContainerActivity.user;

public class GeladeiraRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<String> sessionsArrayList;
    private Context context;

    public GeladeiraRecyclerViewAdapter(ArrayList<String> sessions, Context context) {
        this.sessionsArrayList = sessions;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_geladeira, parent, false);

        GeladeiraViewHolder holder = new GeladeiraViewHolder(view, context);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final GeladeiraViewHolder sessionsHolder = (GeladeiraViewHolder) holder;

        String title  = sessionsArrayList.get(position);

        sessionsHolder.titleTextView.setText(title);

        sessionsHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContainerActivity) context).onAlimentoSelectedEdit(
                        user.getGeladeira().getAlimentoList().get(sessionsHolder.getAdapterPosition()),
                        sessionsHolder.getAdapterPosition());
            }
        });

        sessionsHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar data_atual = Calendar.getInstance();
                String data = LoginActivity.dateToString(data_atual, "dd/MM/yyyy");
                ((ContainerActivity) context).addAlimentoDiario(
                        user.getGeladeira().getAlimentoList().get(sessionsHolder.getAdapterPosition()), data);
            }
        });

        sessionsHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ContainerActivity) context).onAlimentoRemoved(
                        sessionsHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionsArrayList.size();
    }

    public void swap() {
        notifyDataSetChanged();
    }
}

package ihc.appjaquinha.container.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;
import ihc.appjaquinha.database.comida.diario.ConsumoDia;

import static ihc.appjaquinha.container.ContainerActivity.user;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<String> sessionsArrayList;
    private Context context;
    private Fragment fragment;

    public HomeRecyclerViewAdapter(ArrayList<String> sessions, Context context, Fragment fragment) {
        this.sessionsArrayList = sessions;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_home, parent, false);

        HomeViewHolder holder = new HomeViewHolder(view, context);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final HomeViewHolder sessionsHolder = (HomeViewHolder) holder;

        String title  = sessionsArrayList.get(position);

        sessionsHolder.titleTextView.setText(title);

        sessionsHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsumoDia consumoDia = ((HomeFragment) fragment).getConsumoDia();
                if(consumoDia != null) consumoDia.getConsumoAlimentoList().remove(sessionsHolder.getAdapterPosition());
                ((ContainerActivity) context).onDiarioEdited();
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

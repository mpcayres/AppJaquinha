package ihc.appjaquinha.container.objetivos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ihc.appjaquinha.R;

public class ObjetivosRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<String> sessionsArrayList;
    private Context context;


    public ObjetivosRecyclerViewAdapter(ArrayList<String> sessions, Context context) {
        this.sessionsArrayList = sessions;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_objetivos, parent, false);

        ObjetivosViewHolder holder = new ObjetivosViewHolder(view, context);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ObjetivosViewHolder sessionsHolder = (ObjetivosViewHolder) holder;

        String title  = sessionsArrayList.get(position);

        sessionsHolder.titleTextView.setText(title);
    }

    @Override
    public int getItemCount() {
        return sessionsArrayList.size();
    }

    public void swap() {
        notifyDataSetChanged();
    }
}

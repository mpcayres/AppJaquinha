package ihc.appjaquinha.container.diario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ihc.appjaquinha.R;

public class DiarioRecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<String> sessionsArrayList;
    private Context context;


    public DiarioRecyclerViewAdapter(ArrayList<String> sessions, Context context) {
        this.sessionsArrayList = sessions;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_diario, parent, false);

        DiarioViewHolder holder = new DiarioViewHolder(view, context);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        DiarioViewHolder sessionsHolder = (DiarioViewHolder) holder;

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

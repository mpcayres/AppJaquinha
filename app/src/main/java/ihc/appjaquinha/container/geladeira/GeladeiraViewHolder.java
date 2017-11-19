package ihc.appjaquinha.container.geladeira;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ihc.appjaquinha.R;

public class GeladeiraViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    TextView titleTextView;
    ImageButton edit, add, delete;

    public GeladeiraViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        titleTextView = itemView.findViewById(R.id.Food);
        edit = itemView.findViewById(R.id.imageButtonEdit);
        add = itemView.findViewById(R.id.imageButtonAdd);
        delete = itemView.findViewById(R.id.imageButtonDelete);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
    }
}

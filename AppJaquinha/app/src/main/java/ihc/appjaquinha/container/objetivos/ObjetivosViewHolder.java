package ihc.appjaquinha.container.objetivos;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ihc.appjaquinha.R;

public class ObjetivosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    TextView titleTextView;

    public ObjetivosViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        titleTextView = (TextView) itemView.findViewById(R.id.objetivoText);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
    }
}

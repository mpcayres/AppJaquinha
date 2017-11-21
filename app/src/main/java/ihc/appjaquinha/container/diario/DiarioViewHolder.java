package ihc.appjaquinha.container.diario;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ihc.appjaquinha.R;
import ihc.appjaquinha.container.ContainerActivity;

public class DiarioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    TextView titleTextView;

    DiarioViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        titleTextView = itemView.findViewById(R.id.dataText);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        ((ContainerActivity) context).onHomeSelected(titleTextView.getText().toString());
    }
}

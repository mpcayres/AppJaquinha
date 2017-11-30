package ihc.appjaquinha.container.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ihc.appjaquinha.R;

public class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;
    TextView titleTextView;
    ImageButton delete;

    public HomeViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        titleTextView = itemView.findViewById(R.id.Food);
        delete = itemView.findViewById(R.id.imageButtonDelete);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
    }
}

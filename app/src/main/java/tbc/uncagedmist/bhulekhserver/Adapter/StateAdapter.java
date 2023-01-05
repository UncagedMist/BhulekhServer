package tbc.uncagedmist.bhulekhserver.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import tbc.uncagedmist.bhulekhserver.Common.Common;
import tbc.uncagedmist.bhulekhserver.Common.MyApplicationClass;
import tbc.uncagedmist.bhulekhserver.Interface.ItemClickListener;
import tbc.uncagedmist.bhulekhserver.Model.State;
import tbc.uncagedmist.bhulekhserver.R;
import tbc.uncagedmist.bhulekhserver.ResultActivity;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

    Context context;
    List<State> states;

    public StateAdapter(Context context, List<State> states) {
        this.context = context;
        this.states = states;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.my_item_list,parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {

        Picasso.get()
                .load(states.get(position).image)
                .into(holder.stateImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.stateName.setText(states.get(position).name);
                        holder.stateDesc.setText(states.get(position).description);
                    }

                    @Override
                    public void onError(Exception e) {
                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Intent intent = new Intent(context, ResultActivity.class);
                Common.CURRENT_STATE = states.get(position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return states.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView stateImage;
        TextView stateName,stateDesc;

        ItemClickListener itemClickListener;

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stateImage = itemView.findViewById(R.id.stateImage);
            stateName= itemView.findViewById(R.id.stateName);
            stateDesc = itemView.findViewById(R.id.stateDesc);

            stateName.setSelected(true);
            stateDesc.setSelected(true);

            Typeface face = Typeface.createFromAsset(context.getAssets(),
                    "fonts/sport.ttf");
            stateDesc.setTypeface(face);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view);
        }
    }
}

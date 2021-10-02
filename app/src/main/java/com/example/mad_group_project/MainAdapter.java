package com.example.mad_group_project;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.jetbrains.annotations.NotNull;

//import de.hdodenhof.circleimageview.CircleImageView;
//Used to main_item to recycler view
public class MainAdapter extends FirebaseRecyclerAdapter<purchases, MainAdapter.myViewHolder> {

    int TotalPrice =0;
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull @NotNull FirebaseRecyclerOptions<purchases> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position, @NonNull @NotNull purchases model) {

        holder.name.setText(model.getName());
        holder.price.setText("Rs." + model.getPrice().toString());
        holder.date.setText("Purchase on" + model.getDate());

//// Calc Total Amount
        TotalPrice = (int) (TotalPrice + model.getPrice());
        Intent intent = new Intent("Total Spending");
        intent.putExtra("TotalPrice",TotalPrice);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



        Glide.with(holder.img.getContext())
                .load(model.getImg())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                //.circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

    }

    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        //Bind myViewHolder & return it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ///////////////////////
        //CircleImageView img;
        ImageView img;
        TextView name,price,date;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);




            //img = (CircleImageView)itemView.findViewById(R.id.img1);
            img = (ImageView)itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.nametext);
            price = (TextView)itemView.findViewById(R.id.pricetext);
            date = (TextView)itemView.findViewById(R.id.datetext);

        }
    }


}



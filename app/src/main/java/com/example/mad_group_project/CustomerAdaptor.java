package com.example.mad_group_project;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAdaptor extends FirebaseRecyclerAdapter<cusreview, CustomerAdaptor.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CustomerAdaptor(@NonNull @NotNull FirebaseRecyclerOptions<cusreview> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position, @NonNull @NotNull cusreview model) {

        holder.name.setText(model.getCusname());
        holder.description.setText(model.getCusdescription());
        holder.rating.setRating(Float.parseFloat(model.getRating()));

        Glide.with(holder.img.getContext())
                .load(model.getCusurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
    }
    @NonNull
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        //Bind myViewHolder & return it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_item, parent, false);
        return new myViewHolder(view);
    }
    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView name,description;
        RatingBar rating;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (CircleImageView)itemView.findViewById(R.id.img1);
            name = (TextView)itemView.findViewById(R.id.item_name);
            description = (TextView)itemView.findViewById(R.id.item_review);
            rating=(RatingBar)itemView.findViewById(R.id.rating);


        }
    }




}

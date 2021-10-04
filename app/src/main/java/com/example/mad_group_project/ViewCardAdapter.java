package com.example.mad_group_project;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class ViewCardAdapter extends FirebaseRecyclerAdapter<CardModel, ViewCardAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ViewCardAdapter(@NonNull @NotNull FirebaseRecyclerOptions<CardModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder,final int position, @NonNull @NotNull CardModel model) {

        holder.name.setText(model.getName());
        holder.number.setText(model.getNumber().toString());
        holder.date.setText(model.getDate());

        holder.BTN_REMOVE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder Builder = new AlertDialog.Builder(holder.itemView.getContext());
                Builder.setTitle("Are You Sure");
                Builder.setMessage("Your card will be Deleted");

                Builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Card Details").child("Cus1")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });
                Builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.itemView.getContext(),"cancel.",Toast.LENGTH_SHORT ).show();
                    }
                });
                Builder.show();
            }
        });

        Glide.with(holder.img.getContext())
                .load(model.getImage())
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        TextView name,number,date;
        Button BTN_REMOVE;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);




            //img = (CircleImageView)itemView.findViewById(R.id.img1);
            img = (ImageView)itemView.findViewById(R.id.Image1);
            name = (TextView)itemView.findViewById(R.id.Crd_Name);
            number = (TextView)itemView.findViewById(R.id.Crd_Number);
            date = (TextView)itemView.findViewById(R.id.Crd_Date);

            BTN_REMOVE=itemView.findViewById(R.id.BTN_REMOVE);

        }
    }


}



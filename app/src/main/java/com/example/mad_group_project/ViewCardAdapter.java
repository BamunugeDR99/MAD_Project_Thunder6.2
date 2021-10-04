package com.example.mad_group_project;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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

        holder.BTN_SELECT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("Cart Message", "Go to  Cart");



                Map<String, Object> map = new HashMap<>();


                map.put("name",model.getName() );
                map.put("number",model.getNumber());
                map.put("date", model.getDate());
                map.put("Image",model.getImage());
                map.put("type", model.getCardType());

                Log.d("Map", map.toString());

                FirebaseDatabase.getInstance().getReference().child("Card").child("CD1").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                Intent intent = new Intent(holder.img.getContext(), ConfirmOrder.class);

                                intent.putExtra("Type",model.getCardType().toString());

                                Log.d("PositionS",getRef(position).getKey().toString() );

                                String positions = getRef(position).getKey().toString();

                                intent.putExtra("Position", positions);
                                intent.putExtra("Number", model.getNumber());
//                                intent.putExtra("Type", model.getCardType());

                                holder.img.getContext().startActivity(intent);

//                                Log.d("Button Message", "Ebuwa");

//                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
//                                builder.setTitle("Success");
//                                builder.setMessage("Item is Added to Your Cart");

//                                Toast.makeText(holder.name.getContext(), "Item Added To Wish List Successfully !", Toast.LENGTH_SHORT);

//                                builder.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {

                                Toast.makeText(holder.name.getContext(),"Oops Error Occured !", Toast.LENGTH_SHORT);

//
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                                builder.setMessage("Oops Something is wrong! Try again later !");

                                builder.show();

                            }
                        });
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
        Button BTN_REMOVE, BTN_SELECT;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);




            //img = (CircleImageView)itemView.findViewById(R.id.img1);
            img = (ImageView)itemView.findViewById(R.id.Image1);
            name = (TextView)itemView.findViewById(R.id.Crd_Name);
            number = (TextView)itemView.findViewById(R.id.Crd_Number);
            date = (TextView)itemView.findViewById(R.id.Crd_Date);

            BTN_REMOVE=itemView.findViewById(R.id.BTN_REMOVE);
            BTN_SELECT=itemView.findViewById(R.id.BTN_Select);

        }
    }


}



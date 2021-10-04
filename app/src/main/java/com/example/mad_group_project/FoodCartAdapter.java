package com.example.mad_group_project;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class FoodCartAdapter extends FirebaseRecyclerAdapter<FoodCart, FoodCartAdapter.myViewHolder> {

    int TotalPrice =0;
    int OverAllTotalPrice =0;
    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FoodCartAdapter(@NonNull FirebaseRecyclerOptions<FoodCart> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull FoodCart model) {

        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());
        holder.price.setText(model.getFinalPrice());

        Glide.with(holder.food_image.getContext())
                .load(model.getFoodImage())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.food_image);

        //// Calc Total Amount

        //// Calc Total Amount
        TotalPrice =  TotalPrice + (Integer.parseInt(model.getPrice()) * Integer.parseInt(model.getQuantity()));
        Log.d("Total Price", String.valueOf(TotalPrice));
        Intent intent = new Intent("Total Spending");
        intent.putExtra("TotalPrice",TotalPrice);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);







        holder.quantitys.setText(model.getQuantity());



        holder.increment.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Integer number = Integer.parseInt(holder.quantitys.getText().toString());
              number  += 1;
              Integer newPrice = Integer.parseInt(model.getPrice())  * number;
              holder.quantitys.setText(String.valueOf(number));
              holder.price.setText(String.valueOf(newPrice));





              Map<String, Object> map = new HashMap<>();


              map.put("name",model.getName() );
              map.put("price", model.getPrice());
              map.put("description", model.getDescription());
              map.put("foodImage",model.getFoodImage());
              map.put("quantity", String.valueOf(number));
              map.put("finalPrice", String.valueOf(Integer.parseInt(model.getPrice()) * number));

              Log.d("Map", map.toString());

              FirebaseDatabase.getInstance().getReference().child("Cart").child("C1")
                       .child(getRef(position).getKey()).updateChildren(map);



              holder.price.setText(String.valueOf(newPrice));












          }
      });



    holder.decrement.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Integer number = Integer.parseInt(holder.quantitys.getText().toString());
        number  -= 1;
        if(number == 0){
            holder.quantitys.setText("1");
        }
        Integer newPrice = Integer.parseInt(model.getPrice())  * number;
        holder.quantitys.setText(String.valueOf(number));
        holder.price.setText(String.valueOf(newPrice));



        Map<String, Object> map = new HashMap<>();


        map.put("name",model.getName() );
        map.put("price", model.getPrice());
        map.put("description", model.getDescription());
        map.put("foodImage",model.getFoodImage());
        map.put("quantity", String.valueOf(number));
        map.put("finalPrice", String.valueOf(Integer.parseInt(model.getPrice()) * number));

        Log.d("Map", map.toString());

        FirebaseDatabase.getInstance().getReference().child("Cart").child("C1")
                .child(getRef(position).getKey()).updateChildren(map);


        holder.price.setText(String.valueOf(newPrice));
        holder.price.setText(model.getFinalPrice());

    }
});


        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure tou want to delete?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Cart").child("C1")
                                .child(getRef(position).getKey()).removeValue();
//                        Toast.makeText(holder.name.getContext(), "Removed", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.name.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });




    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_edit_cart,parent,false);

        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{


        TextView name, description, price,total,quantitys;
        ImageView food_image;
        //ElegantNumberButton number;
        ImageButton increment,decrement;
        Button btn_delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.foodNtext);
            description = (TextView) itemView.findViewById(R.id.foodDtext);
            price = (TextView) itemView.findViewById(R.id.foodPtext);
            //number = itemView.findViewById(R.id.txt_amount);
            total = itemView.findViewById(R.id.total);
            food_image = (ImageView) itemView.findViewById(R.id.food_image);
            increment = (ImageButton) itemView.findViewById(R.id.increment);
            decrement =(ImageButton)  itemView.findViewById(R.id.decrement);
            quantitys = itemView.findViewById(R.id.quantitys);

            btn_delete= itemView.findViewById(R.id.btn_delete);

            //ConstraintLayout myCart = (ConstraintLayout)itemView.findViewById(R.id.my_cart);
        }


    }


}

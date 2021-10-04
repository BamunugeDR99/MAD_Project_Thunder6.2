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

public class  WishListAdapter extends FirebaseRecyclerAdapter<WishListModel, WishListAdapter.myViewHolder> {

    float Total = 0f;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WishListAdapter(@NonNull @NotNull FirebaseRecyclerOptions<WishListModel> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull WishListAdapter.myViewHolder holder, int position, @NonNull @NotNull WishListModel model) {

        holder.Itemname.setText(model.getItemName().toString());
        holder.Price.setText("Rs " + model.getPrice().toString() + ".00");
        holder.Rating.setText(model.getRatings().toString());
        holder.Reviews.setText(model.getReviews().toString() + " Reviews");



        Glide.with(holder.img.getContext())
                .load(model.getImgurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                //.circleCrop()

                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);





        //On Delete function
        holder.wl_btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Itemname.getContext());
                builder.setTitle("Are you sure tou want to delete?");
                builder.setMessage("This item will be removed from your wishlist");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {    

                        FirebaseDatabase.getInstance().getReference().child("WishList").child("C1")
                                .child(getRef(position).getKey()).removeValue();




                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.Itemname.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();

            }
        });



        holder.wl_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Log.d("Cart Message", "Go to  Cart");



                Map<String, Object> map = new HashMap<>();


                map.put("name",model.getItemName() );
                map.put("price", model.getPrice());
                map.put("description", model.getDescription());
                map.put("foodImage",model.getImgurl());
                map.put("quantity", "1");
                map.put("finalPrice", model.getPrice());

                Log.d("Map", map.toString());

                FirebaseDatabase.getInstance().getReference().child("Cart").child("C1").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {


                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Itemname.getContext());
                                builder.setTitle("Success");
                                builder.setMessage("Item is Added to Your Cart");

                                Toast.makeText(holder.Itemname.getContext(), "Item Added To Wish List Successfully !", Toast.LENGTH_SHORT);

                                builder.show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure( Exception e) {

                                Toast.makeText(holder.Itemname.getContext(),"Oops Error Occured !", Toast.LENGTH_SHORT);


                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.Itemname.getContext());
                                builder.setTitle("Error");
                                builder.setMessage("Oops Something is wrong! Try again later !");



                                builder.show();

                            }
                        });















            }
        });



    }

    @NonNull
    @NotNull
    @Override
    public WishListAdapter.myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        //Bind myViewHolder & return it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);
        return new WishListAdapter.myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ///////////////////////
        //CircleImageView img;
        ImageView img;
        TextView Itemname,Price,Rating,Reviews;
        Button wl_btn_remove, wl_addToCart;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);




            //img = (CircleImageView)itemView.findViewById(R.id.img1);

            img = (ImageView)itemView.findViewById(R.id.img1);
            Itemname = (TextView)itemView.findViewById(R.id.wl_itemName);
            Price= (TextView)itemView.findViewById(R.id.wl_price);
            Rating = (TextView)itemView.findViewById(R.id.wl_rating);
            Reviews  = (TextView)itemView.findViewById(R.id.wl_noOfReviews);
            wl_btn_remove = (Button)itemView.findViewById(R.id.wl_btn_remove);
            wl_addToCart = (Button)itemView.findViewById(R.id.wl_btn_addtoCart);

        }
    }


}

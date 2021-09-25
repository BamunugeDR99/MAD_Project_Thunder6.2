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

public class  WishListAdapter extends FirebaseRecyclerAdapter<WishListModel, WishListAdapter.myViewHolder> {


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
        holder.Price.setText(model.getPrice().toString());
        holder.Rating.setText(model.getRatings().toString());
        holder.Reviews.setText(model.getReviews().toString());



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
                builder.setMessage("Deleted data cannot be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("WishList")
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
        Button wl_btn_remove;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);




            //img = (CircleImageView)itemView.findViewById(R.id.img1);

            img = (ImageView)itemView.findViewById(R.id.img1);
            Itemname = (TextView)itemView.findViewById(R.id.wl_itemName);
            Price= (TextView)itemView.findViewById(R.id.wl_price);
            Rating = (TextView)itemView.findViewById(R.id.wl_rating);
            Reviews  = (TextView)itemView.findViewById(R.id.wl_noOfReviews);
            wl_btn_remove = (Button)itemView.findViewById(R.id.wl_btn_remove);

        }
    }


}

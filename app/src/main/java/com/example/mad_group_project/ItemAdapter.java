package com.example.mad_group_project;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ItemAdapter extends FirebaseRecyclerAdapter<ItemModel, ItemAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ItemAdapter(@NonNull @NotNull FirebaseRecyclerOptions<ItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder, int position, @NonNull @NotNull ItemModel model) {



        holder.Itemname.setText(model.getItemName().toString());
        holder.Price.setText("Rs " + model.getPrice().toString());
        holder.Rating.setText(model.getRatings().toString());

        holder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.activity_single_item_view))
                        .setExpanded(true, 1800)
                        .create();

                //dialogPlus.show();



                View view = dialogPlus.getHolderView();

                ImageView imgI ;

                TextView price, productName, ratings, description ;

                price = view.findViewById(R.id.PriceView);
                productName = view.findViewById(R.id.Name);
                ratings = view.findViewById(R.id.RateNo);
                description = view.findViewById(R.id.Description);
                imgI = view.findViewById(R.id.imageView);

                ImageButton imgbtn1 = (ImageButton) view.findViewById(R.id.GoToReviews_Btn);
                ImageButton imgbtn2 = (ImageButton) view.findViewById(R.id.AddtoWislList_Btn);


                price.setText("Rs" + model.getPrice() );
                productName.setText(model.getItemName());
                ratings.setText(model.getRatings());
                description.setText(model.getDescription());

                Glide.with(imgI.getContext())
                        .load(model.getImage())
                        .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                        //.circleCrop()

                        .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .into(imgI);







                dialogPlus.show();


                imgbtn1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(holder.img.getContext(), customer_reviews.class);
                        holder.img.getContext().startActivity(intent);

                        Log.d("Button Message", "Ebuwa");
                    }
                });


                imgbtn2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Log.d("Button Message", "Ebuwa2");



                    }
                });


//                btnUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Map<String, Object> map = new HashMap<>();
//
//                        map.put("Name", name.getText().toString());
//                        map.put("Course", course.getText().toString());
//                        map.put("Email", email.getText().toString());
//                        map.put("turl", turl.getText().toString());
//
//
//                        FirebaseDatabase.getInstance().getReference().child("Teachers")
//                                .child(getRef(position).getKey()).updateChildren(map)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//
//                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT);
//                                        dialogPlus.dismiss();
//                                    }
//                                })
//
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure( Exception e) {
//                                        Toast.makeText(holder.name.getContext(), "Error While Updating Data", Toast.LENGTH_SHORT);
//                                    }
//                                });
//
//
//                    }
//                });































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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_gift_item, parent, false);
        return new ItemAdapter.myViewHolder(view);


    }

    class myViewHolder extends RecyclerView.ViewHolder{


        ImageView img;
        TextView Itemname,Price,Rating;

        CardView itemCard;

        public myViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            img = (ImageView)itemView.findViewById(R.id.img1);
            Itemname = (TextView)itemView.findViewById(R.id.ItemName);
            Price= (TextView)itemView.findViewById(R.id.Price);
            Rating = (TextView)itemView.findViewById(R.id.Rating);
            itemCard = (CardView)itemView.findViewById(R.id.ItemCard);
        }
    }
}

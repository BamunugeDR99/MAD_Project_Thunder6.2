package com.example.mad_group_project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class YourAdaptor extends FirebaseRecyclerAdapter<yourreviews, YourAdaptor.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public YourAdaptor(@NonNull @NotNull FirebaseRecyclerOptions<yourreviews> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull @NotNull myViewHolder holder, final int position, @NonNull @NotNull yourreviews model) {

        holder.name.setText(model.getName());
        holder.description.setText(model.getDescription());


        Glide.with(holder.img.getContext())
                .load(model.getImageurl())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);


        //Bind Updatepopup to viewholder

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200)
                        .create();

                //dialogPlus.show();
                View view = dialogPlus.getHolderView();

                EditText description;

//                name = view.findViewById(R.id.txtName);
                description = view.findViewById(R.id.txtName);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

//                name.setText(model.getName());
                description.setText(model.getDescription());

                dialogPlus.show();


                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Map<String, Object> map = new HashMap<>();

                        map.put("description",description.getText().toString());


                        FirebaseDatabase.getInstance().getReference().child("Reviews")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT);
                                        dialogPlus.dismiss();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error While Updating Data", Toast.LENGTH_SHORT);
                                    }
                                });


                    }
                });





            }
        });

        //On Delete function
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure tou want to delete?");
                builder.setMessage("Deleted data cannot be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        FirebaseDatabase.getInstance().getReference().child("Reviews")
                                .child(getRef(position).getKey()).removeValue();




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
    @NotNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        //Bind myViewHolder & return it
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.your_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ///////////////////////
        CircleImageView img;
        //        ImageView img;
        TextView name,description;
        Button btn_edit, btn_delete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);




            img = (CircleImageView)itemView.findViewById(R.id.img);
//            img = (ImageView)itemView.findViewById(R.id.img);
            name = (TextView)itemView.findViewById(R.id.rev_name);
            description = (TextView)itemView.findViewById(R.id.rev_review);

            btn_edit = (Button)itemView.findViewById(R.id.btn_edit);
            btn_delete = (Button)itemView.findViewById(R.id.btn_delete);

        }
    }




}

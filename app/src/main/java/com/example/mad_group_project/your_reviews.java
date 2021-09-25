package com.example.mad_group_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class your_reviews extends AppCompatActivity {

    RecyclerView rv;

    YourAdaptor yourAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_reviews);

        //Assigning recycler view
        rv = (RecyclerView) findViewById(R.id.rv);

        rv.setLayoutManager(new LinearLayoutManager(this));



        FirebaseRecyclerOptions<yourreviews> options =
                new FirebaseRecyclerOptions.Builder<yourreviews>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reviews"), yourreviews.class)
                        .build();


        yourAdaptor = new YourAdaptor(options);

        rv.setAdapter(yourAdaptor);
    }


    @Override
    protected void onStart() {
        super.onStart();

        yourAdaptor.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        yourAdaptor.stopListening();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.search,menu);
//        MenuItem item= menu.findItem(R.id.search);
//        SearchView searchView = (SearchView)item.getActionView();
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                txtSearch(query);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String query) {
//                txtSearch(query);
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void txtSearch(String str)
//    {
//        FirebaseRecyclerOptions<review> options =
//                new FirebaseRecyclerOptions.Builder<review>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Reviews").orderByChild("name").startAt(str).endAt(str+"~"), review.class)
//                        .build();
//        mainAdapter = new MainAdapter(options);
//        mainAdapter.startListening();
//        recyclerView.setAdapter(mainAdapter);
//    }

}
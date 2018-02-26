package com.example.danilo.cloudcamera;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danilo on 9/13/17.
 */

public class Gallery extends Fragment {
    private RecyclerView recyclerView;
    private GridLayoutManager grid;
    private Gallery.MyAdapter mAdapter;
    private Controller m;
    DatabaseReference myRef;
    ArrayList<String> listOfimages;
    private Button logout;
    FirebaseUser user;
    private FirebaseAuth mAuth;



    public void fetchAllDataFromFirbase(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    listOfimages.add(x.getValue(String.class));
                    //Log.d("value", "Value is: " + value);

                    mAdapter.notifyDataSetChanged();

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.gallery, container, false);
        user = mAuth.getInstance().getCurrentUser();

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        logout = (Button) activity.findViewById(R.id.button5);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user != null) {
                    Intent myIntent = new Intent(getActivity(), MainActivity.class);
                    mAuth.signOut();
                    //getActivity().startActivity(myIntent);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.start, new loginregister()).commit();
                }
            }
        });

        m = new Controller();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        fetchAllDataFromFirbase();

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        grid = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(grid);

        listOfimages = new ArrayList<>();

        // define an adapter

            mAdapter = new Gallery.MyAdapter(listOfimages);
            recyclerView.setAdapter(mAdapter);




        return view;
    }


    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> values;
        private StorageReference mStorageRef;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView Imageview;


            public ViewHolder(View v) {
                super(v);
                Imageview = (ImageView) v.findViewById(R.id.imageView2);

            }
        }



        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(List<String> myDataset) {
            values = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            LayoutInflater inflater = LayoutInflater.from( parent.getContext());

            View v = inflater.inflate(R.layout.row, parent, false);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            final String imageUrlStr = values.get(position);
            Glide.with(holder.Imageview.getContext())
                    .load(imageUrlStr)
                    .into(holder.Imageview);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return values.size();
        }

    }
    }

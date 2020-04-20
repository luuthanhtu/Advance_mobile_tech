package com.e.assigement2_mobi;

import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import ui.GameReviewRecyclerAdapter;
import model.GameReview;
import util.GameReviewApi;

public class GameReviewListActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore database = FirebaseFirestore.getInstance();
    private StorageReference storageReference;
    private List<GameReview> gameReviews;
    private RecyclerView recyclerView;
    private GameReviewRecyclerAdapter gameReviewRecyclerAdapter;

    private CollectionReference collectionReference = database.collection("Journal");
    private TextView noReviewEntry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_review_list);

        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        noReviewEntry = findViewById(R.id.list_no_reviews);

        gameReviews = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add:
                //Take users to add Journal
                if (user != null && firebaseAuth != null) {
                    startActivity(new Intent(GameReviewListActivity.this,
                            PostGameReviewActivity.class));
                    //finish();
                }
                break;
            case R.id.action_signout:
                //sign user out!
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut();

                    startActivity(new Intent(GameReviewListActivity.this,
                            MainActivity.class));
                    //finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.whereEqualTo("userId", GameReviewApi.getInstance()
                .getUserId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            for (QueryDocumentSnapshot journals : queryDocumentSnapshots) {
                                GameReview gameReview = journals.toObject(GameReview.class);
                                gameReviews.add(gameReview);
                            }

                            //Invoke recyclerview
                            gameReviewRecyclerAdapter = new GameReviewRecyclerAdapter(GameReviewListActivity.this,
                                    gameReviews);
                            recyclerView.setAdapter(gameReviewRecyclerAdapter);
                            gameReviewRecyclerAdapter.notifyDataSetChanged();

                        }else {
                            noReviewEntry.setVisibility(View.VISIBLE);

                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

    }
}

package com.example.pokemongame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayGameActivity extends AppCompatActivity {

    String roomId,side;
    ImageView image1,image2;

    public TextView hp1,name1,attack1,defense1,speed1;
    public ImageView img1;
    Button playBtn1;
    public String pokName1,pokeHp1,pokeattack1,pokeDefense1,pokeSpeed1,imageurl1;

    public TextView hp2,name2,attack2,defense2,speed2;
    public ImageView img2;
    Button playBtn2;
    public String pokName2,pokeHp2,pokeattack2,pokeDefense2,pokeSpeed2,imageurl2;

    public TextView roomText;

    FirebaseFirestore db;
    public Boolean player=false,card=false;
    public int rounds=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_play_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Intent intent = getIntent();
        roomId = intent.getStringExtra("Room Id");
        side = intent.getStringExtra("Side");


        name1 = findViewById(R.id.name1);
        hp1 = findViewById(R.id.hp1);
        attack1 = findViewById(R.id.attack1);
        defense1 = findViewById(R.id.defense1);
        speed1 = findViewById(R.id.speed1);
        img1 =  findViewById(R.id.image1);
        playBtn1 = (Button) findViewById(R.id.play1);


        name2 = findViewById(R.id.name2);
        hp2 = findViewById(R.id.hp2);
        attack2 = findViewById(R.id.attack2);
        defense2 = findViewById(R.id.defense2);
        speed2 = findViewById(R.id.speed2);
        img2 =  findViewById(R.id.image2);
        playBtn2 = (Button) findViewById(R.id.play2);

        roomText = findViewById(R.id.roomIdText);

        roomText.setText("Room ID:"+roomId);

        if(side.equals("Left")){
            firbaseDatabase();
            playBtn2.setVisibility(View.GONE);

            // Live Monitoring of Player Joining
            db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("Rooms").document(roomId);
            docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot snapshot,
                                    @Nullable FirebaseFirestoreException e) {
                    if (e != null) {
                        Log.w("GetData", "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        Log.d("GetData", "Current data: " + snapshot.getData());
                        if((boolean) snapshot.getData().get("Player 2 Status")){

                            Toast.makeText(PlayGameActivity.this, "Player Joined", Toast.LENGTH_SHORT).show();
                            player = true;

                            // For First Card Only
                            firstCardGenerate1();


                        }
                    } else {
                        Log.d("GetData", "Current data: null");
                    }
                }
            });

        }
        else if (side.equals("Right")) {
            playBtn1.setVisibility(View.GONE);

            db = FirebaseFirestore.getInstance();
            Map<String, Object> data = new HashMap<>();
            data.put("Player 2 Status",true);

            db.collection("Rooms").document(roomId)
                    .set(data, SetOptions.merge());
            firstCardGenerate2();
        }

        playBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player){



                    generateCard1();
                    rounds = rounds+1;
                }
                else {
                    Toast.makeText(PlayGameActivity.this, "Player Not Joined", Toast.LENGTH_SHORT).show();
                }
            }
        });

        playBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rounds = rounds+1;
                generateCard2();
            }
        });
    }

    public void firbaseDatabase() {
        db = FirebaseFirestore.getInstance();

        // Add a new document with a generated ID
        Map<String, Object> roomData = new HashMap<>();
        roomData.put("ID", roomId);
        roomData.put("Player 1 Name", "Anmol");
        roomData.put("Player 2 Name", "Aryan");
        roomData.put("Player 1 Status",true);
        roomData.put("Player 2 Status",false);
        roomData.put("Player 1 Score",0);
        roomData.put("Player 2 Score",0);

        db.collection("Rooms").document(roomId)
                .set(roomData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("ASDFG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Ashok Anand", "Error writing document", e);
                    }
                });
    }

    public void generateCard1(){
        Random random = new Random();

        int r = random.nextInt(1025)+1;//10001-10277
        String uid = String.valueOf(r);

        Call<UserModel> nameCall = APIClient.getInstance().getApi().getNameData(uid);
        nameCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();

                    pokName1 = userModel.getName().toUpperCase();
                    pokeHp1 = ""+userModel.getStats().get(0).getBaseStat();
                    pokeattack1 = ""+userModel.getStats().get(1).getBaseStat();
                    pokeDefense1 = ""+userModel.getStats().get(2).getBaseStat();
                    pokeSpeed1 = ""+userModel.getStats().get(5).getBaseStat();
                    imageurl1 = userModel.getSprites().getOther().getHome().getFrontDefault();

                    name1.setText(pokName1);
                    hp1.setText("HP "+pokeHp1);
                    attack1.setText(pokeattack1);
                    defense1.setText(pokeDefense1);
                    speed1.setText(pokeSpeed1);

//                    Glide.with(PlayGameActivity.this)
//                            .load(imageurl1)
//                            .centerCrop()
//                            .into(img1);

                    Picasso.get().load(imageurl1).into(img1);

                    db = FirebaseFirestore.getInstance();

                    // Add a new document with a generated ID
                    Map<String, Object> pokeData = new HashMap<>();
                    pokeData.put("Name", pokName1);
                    pokeData.put("HP", pokeHp1);
                    pokeData.put("Attack", pokeattack1);
                    pokeData.put("Defense", pokeDefense1);
                    pokeData.put("Speed", pokeSpeed1);
                    pokeData.put("Image Url", imageurl1);

                    db.collection("Rooms").document(roomId).collection("Pokemon Stats").document("Anmol"+rounds)
                            .set(pokeData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("ASDFG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Ashok Anand", "Error writing document", e);
                                }
                            });

                    db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("Rooms").document(roomId).collection("Pokemon Stats").document("Aryan"+rounds);
                    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot snapshot,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w("GetData", "Listen failed.", e);
                                return;
                            }

                            if (snapshot != null && snapshot.exists()) {
                                Log.d("GetData", "Current data: " + snapshot.getData());
//                                    Toast.makeText(PlayGameActivity.this, "Got Data", Toast.LENGTH_SHORT).show();

                                name2.setText((String)snapshot.getData().get("Name"));
                                hp2.setText((String)snapshot.getData().get("HP"));
                                attack2.setText((String)snapshot.getData().get("Attack"));
                                defense2.setText((String)snapshot.getData().get("Defense"));
                                speed2.setText((String)snapshot.getData().get("Speed"));

//                                    Glide.with(PlayGameActivity.this)
//                                            .load(imageurl1)
//                                            .centerCrop()
//                                            .into(img1);

                                Picasso.get().load((String)snapshot.getData().get("Image Url")).into(img2);

                            } else {
                                Log.d("GetData", "Current data: null");
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Toast.makeText(PlayGameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("Anmol Anand", "onFailure: "+throwable.getMessage());
            }
        });
    }

    public void generateCard2(){
        Random random = new Random();

        int r = random.nextInt(1025)+1;//10001-10277
        String uid = String.valueOf(r);

        Call<UserModel> nameCall = APIClient.getInstance().getApi().getNameData(uid);
        nameCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();

                    pokName2 = userModel.getName().toUpperCase();
                    pokeHp2 = ""+userModel.getStats().get(0).getBaseStat();
                    pokeattack2 = ""+userModel.getStats().get(1).getBaseStat();
                    pokeDefense2 = ""+userModel.getStats().get(2).getBaseStat();
                    pokeSpeed2 = ""+userModel.getStats().get(5).getBaseStat();
                    imageurl2 = userModel.getSprites().getOther().getHome().getFrontDefault();

                    name2.setText(pokName2);
                    hp2.setText("HP "+pokeHp2);
                    attack2.setText(pokeattack2);
                    defense2.setText(pokeDefense2);
                    speed2.setText(pokeSpeed2);

//                    Glide.with(PlayGameActivity.this)
//                            .load(imageurl2)
//                            .centerCrop()
//                            .into(img2);

                    Picasso.get().load(imageurl2).into(img2);


                    db = FirebaseFirestore.getInstance();

                    // Add a new document with a generated ID
                    Map<String, Object> roomData = new HashMap<>();
                    roomData.put("Name", pokName2);
                    roomData.put("HP", pokeHp2);
                    roomData.put("Attack", pokeattack2);
                    roomData.put("Defense", pokeDefense2);
                    roomData.put("Speed", pokeSpeed2);
                    roomData.put("Image Url", imageurl2);

                    db.collection("Rooms").document(roomId).collection("Pokemon Stats").document("Aryan"+rounds)
                            .set(roomData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("ASDFG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Ashok Anand", "Error writing document", e);
                                }
                            });

                    db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("Rooms").document(roomId).collection("Pokemon Stats").document("Anmol"+rounds);
                    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot snapshot,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.w("GetData", "Listen failed.", e);
                                return;
                            }

                            if (snapshot != null && snapshot.exists()) {
                                Log.d("GetData", "Current data: " + snapshot.getData());
//                                    Toast.makeText(PlayGameActivity.this, "Got Data", Toast.LENGTH_SHORT).show();

                                name1.setText((String)snapshot.getData().get("Name"));
                                hp1.setText((String)snapshot.getData().get("HP"));
                                attack1.setText((String)snapshot.getData().get("Attack"));
                                defense1.setText((String)snapshot.getData().get("Defense"));
                                speed1.setText((String)snapshot.getData().get("Speed"));

//                                    Glide.with(PlayGameActivity.this)
//                                            .load(imageurl1)
//                                            .centerCrop()
//                                            .into(img1);

                                Picasso.get().load((String)snapshot.getData().get("Image Url")).into(img1);

                            } else {
                                Log.d("GetData", "Current data: null");
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Toast.makeText(PlayGameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("Anmol Anand", "onFailure: "+throwable.getMessage());
            }
        });
    }

    public void firstCardGenerate1(){
        Random random = new Random();

        int r = random.nextInt(1025)+1;//10001-10277
        String uid = String.valueOf(r);

        Call<UserModel> nameCall = APIClient.getInstance().getApi().getNameData(uid);
        nameCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();

                    pokName2 = userModel.getName().toUpperCase();
                    pokeHp2 = ""+userModel.getStats().get(0).getBaseStat();
                    pokeattack2 = ""+userModel.getStats().get(1).getBaseStat();
                    pokeDefense2 = ""+userModel.getStats().get(2).getBaseStat();
                    pokeSpeed2 = ""+userModel.getStats().get(5).getBaseStat();
                    imageurl2 = userModel.getSprites().getOther().getHome().getFrontDefault();

                    name2.setText(pokName2);
                    hp2.setText("HP "+pokeHp2);
                    attack2.setText(pokeattack2);
                    defense2.setText(pokeDefense2);
                    speed2.setText(pokeSpeed2);

//                    Glide.with(PlayGameActivity.this)
//                            .load(imageurl2)
//                            .centerCrop()
//                            .into(img2);

                    Picasso.get().load(imageurl2).into(img2);


                    db = FirebaseFirestore.getInstance();

                    // Add a new document with a generated ID
                    Map<String, Object> roomData = new HashMap<>();
                    roomData.put("Name", pokName2);
                    roomData.put("HP", pokeHp2);
                    roomData.put("Attack", pokeattack2);
                    roomData.put("Defense", pokeDefense2);
                    roomData.put("Speed", pokeSpeed2);
                    roomData.put("Image Url", imageurl2);

                    db.collection("Rooms").document(roomId).collection("Pokemon Stats").document("Aryan"+rounds)
                            .set(roomData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("ASDFG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Ashok Anand", "Error writing document", e);
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Toast.makeText(PlayGameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("Anmol Anand", "onFailure: "+throwable.getMessage());
            }
        });
    }

    public void firstCardGenerate2(){
        Random random = new Random();

        int r = random.nextInt(1025)+1;//10001-10277
        String uid = String.valueOf(r);

        Call<UserModel> nameCall = APIClient.getInstance().getApi().getNameData(uid);
        nameCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();

                    pokName2 = userModel.getName().toUpperCase();
                    pokeHp2 = ""+userModel.getStats().get(0).getBaseStat();
                    pokeattack2 = ""+userModel.getStats().get(1).getBaseStat();
                    pokeDefense2 = ""+userModel.getStats().get(2).getBaseStat();
                    pokeSpeed2 = ""+userModel.getStats().get(5).getBaseStat();
                    imageurl2 = userModel.getSprites().getOther().getHome().getFrontDefault();

                    name2.setText(pokName2);
                    hp2.setText("HP "+pokeHp2);
                    attack2.setText(pokeattack2);
                    defense2.setText(pokeDefense2);
                    speed2.setText(pokeSpeed2);

//                    Glide.with(PlayGameActivity.this)
//                            .load(imageurl2)
//                            .centerCrop()
//                            .into(img2);

                    Picasso.get().load(imageurl2).into(img2);


                    db = FirebaseFirestore.getInstance();

                    // Add a new document with a generated ID
                    Map<String, Object> roomData = new HashMap<>();
                    roomData.put("Name", pokName2);
                    roomData.put("HP", pokeHp2);
                    roomData.put("Attack", pokeattack2);
                    roomData.put("Defense", pokeDefense2);
                    roomData.put("Speed", pokeSpeed2);
                    roomData.put("Image Url", imageurl2);

                    db.collection("Rooms").document(roomId).collection("Pokemon Stats").document("Aryan"+rounds)
                            .set(roomData)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("ASDFG", "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w("Ashok Anand", "Error writing document", e);
                                }
                            });

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Toast.makeText(PlayGameActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.e("Anmol Anand", "onFailure: "+throwable.getMessage());
            }
        });
    }
}
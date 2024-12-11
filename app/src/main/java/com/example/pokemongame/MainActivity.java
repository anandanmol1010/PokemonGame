package com.example.pokemongame;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public TextView hp,name,attack,defense,speed;
    public ImageView img;
    public String pokName,pokeHp,pokeattack,pokeDefense,pokeSpeed,imageurl;
    Button generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.name);
        hp = findViewById(R.id.hp);
        attack = findViewById(R.id.attack);
        defense = findViewById(R.id.defense);
        speed = findViewById(R.id.speed);
        img =  findViewById(R.id.image);
        generate = findViewById(R.id.generate);
        generateCard();

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateCard();
            }
        });

    }

    public void generateCard(){
        Random random = new Random();

        int r = random.nextInt(1025)+1;//10001-10277
        String uid = String.valueOf(r);

        Call<UserModel> nameCall = APIClient.getInstance().getApi().getNameData(uid);
        nameCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();

                    pokName = userModel.getName();
                    pokeHp = ""+userModel.getStats().get(0).getBaseStat();
                    pokeattack= ""+userModel.getStats().get(1).getBaseStat();
                    pokeDefense= ""+userModel.getStats().get(2).getBaseStat();
                    pokeSpeed= ""+userModel.getStats().get(5).getBaseStat();
                    imageurl = userModel.getSprites().getOther().getHome().getFrontDefault();

                    name.setText(pokName.toUpperCase());
                    hp.setText("HP "+pokeHp);
                    attack.setText(pokeattack);
                    defense.setText(pokeDefense);
                    speed.setText(pokeSpeed);

//                    Glide.with(MainActivity.this)
//                            .load(imageurl)
//                            .centerCrop()
//                            .into(img);

                    Picasso.get().load(imageurl)
                            .into(img);

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                Toast.makeText(MainActivity.this, "Error1", Toast.LENGTH_SHORT).show();
                Log.e("Anmol Anand", "onFailure: "+throwable.getMessage());
            }
        });
    }
}
package com.example.pokemongame;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {

    Button generateCard,playGame;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        generateCard = findViewById(R.id.generateCard);
        playGame = findViewById(R.id.playGame);

        generateCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, MainActivity.class));
            }
        });

        playGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });
    }

    public void showDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialogbox);
        dialog.show();

        Button createRoom,joinRoom;
        EditText roomId;

        createRoom = (Button) dialog.findViewById(R.id.create);
        joinRoom = (Button) dialog.findViewById(R.id.join);
        roomId = (EditText) dialog.findViewById(R.id.room);

        createRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Random random = new Random();
                String str = String.format("%04d", random.nextInt(10000));
                Intent intent = new Intent(HomeActivity.this, PlayGameActivity.class);
                intent.putExtra("Room Id",str);
                intent.putExtra("Side","Left");
                startActivity(intent);
            }
        });

        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = roomId.getText().toString();
                if(str.length()==4){

                    db = FirebaseFirestore.getInstance();
                    DocumentReference docRef = db.collection("Rooms").document(str);
                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(HomeActivity.this, PlayGameActivity.class);
                                    intent.putExtra("Room Id",str);
                                    intent.putExtra("Side","Right");
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(HomeActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(HomeActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(HomeActivity.this, "Enter Valid Room ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
package com.example.a1pic1word;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button play;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=findViewById(R.id.play);
        textView=findViewById(R.id.level);
        sharedPreferences=getSharedPreferences("memory",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        if(!sharedPreferences.contains("score"))
        {
            System.out.println("not available");
            editor.putInt("score",200);
            editor.commit();
        }
        else {
            System.out.println("score available");
        }

        //0-1
        //1-2
        //2-3
        int level=sharedPreferences.getInt("level",0);
        System.out.println("currentlevl="+level);
        if(level==0)
        {
            textView.setText(""+(level+1));
        }
        else {
            textView.setText(""+(level+2));
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                if(level==0)
                {
                    intent.putExtra("pos",level);
                }
                else {
                    intent.putExtra("pos",level+1);
                }
                startActivity(intent);
            }
        });
    }
}
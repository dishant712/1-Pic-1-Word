package com.example.a1pic1word;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    int t=0;
    ArrayList<String> listImages;
    ArrayList<String> templistImages=new ArrayList<>();
    ImageView imageView;
    String ans="";
    int pos=0;
    String[] arr;
    Button[] viewarr;
    Button[] b;
    LinearLayout linearLayout;
    String alpha="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    ArrayList randomans=new ArrayList();
    Animation animation;
    ImageButton imageButton;
    ArrayList<Map> position;
    StringBuilder stringBuilder=new StringBuilder();
    TextView textView;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    TextView score;
    int tempscore=0;
    int totalscore=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageButton=findViewById(R.id.hint);
        textView=findViewById(R.id.level);
        score=findViewById(R.id.score);
        sharedPreferences=getSharedPreferences("memory",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        totalscore=sharedPreferences.getInt("score",0);
        score.setText("coins="+totalscore);

        animation= AnimationUtils.loadAnimation(this,R.anim.anim1);

        pos=getIntent().getIntExtra("pos",0);
        System.out.println("pos="+pos);
        textView.setText(""+(pos+1));




        String[] images = new String[0];
        try {
            images = getAssets().list("images");
            listImages= new ArrayList<String>(Arrays.asList(images));
            for (int i=0;i<listImages.size();i++)
            {
                if(listImages.get(i).endsWith(".webp"))
                {
                    templistImages.add(listImages.get(i));
                }
            }
            System.out.println(templistImages);

            ans=templistImages.get(pos);
            arr=ans.split("\\.");
            ans=arr[0];

            System.out.println(ans);
        } catch (IOException e) {
            e.printStackTrace();
        }

        position=new ArrayList();
        imageView=findViewById(R.id.image1);
        linearLayout=findViewById(R.id.linear);


        InputStream inputstream= null;
        try {
            inputstream = getAssets().open("images/" +templistImages.get(pos));
            Drawable drawable = Drawable.createFromStream(inputstream, null);
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        viewarr=new Button[ans.length()];
        for (int i = 0; i < ans.length(); i++)
        {
            System.out.println("hello");

            viewarr[i]=new Button(this);
            viewarr[i].setId(i);
            int width = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._40sdp);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(width, width);
            int margin = getResources().getDimensionPixelSize(com.intuit.sdp.R.dimen._5sdp);
            layoutParams.setMargins(margin,margin,margin,margin);
            viewarr[i].setLayoutParams(layoutParams);
            viewarr[i].setBackgroundResource(R.drawable.edit_text);
            linearLayout.addView(viewarr[i]);
            viewarr[i].setOnClickListener(this);

        }

        b=new Button[10];
        for(int i=0;i<10;i++)
        {
            int id=getResources().getIdentifier("b"+i,"id",getPackageName());
            b[i]=findViewById(id);
            b[i].setOnClickListener(this);
        }

        for(int i=0;i<10-ans.length();i++)
        {
            int random=new Random().nextInt(alpha.length());
            randomans.add(alpha.charAt(random));
//            b[i].setText(""+alpha.charAt(random));
        }
        for(int i=0;i<ans.length();i++)
        {
            randomans.add(ans.charAt(i));
        }

        Collections.shuffle(randomans);

        for(int i=0;i<10;i++)
        {
            b[i].setText(""+randomans.get(i));
            System.out.println(randomans);
        }
        imageButton.setOnClickListener(v -> {
            tempscore=sharedPreferences.getInt("score",0);
            if(tempscore>=50)
            {
                for(int i=0;i<ans.length();i++)
                {
                    if(viewarr[i].getText().toString().isEmpty())
                    {
                        viewarr[i].setText(""+ans.charAt(i));

                        tempscore=tempscore-50;
                        editor.putInt("score",tempscore);
                        editor.commit();
                        totalscore=sharedPreferences.getInt("score",0);
                        score.setText("coins="+totalscore);
                        stringBuilder.delete(0,stringBuilder.length());
                        for(int t=0;t<ans.length();t++)
                        {
                            String text=viewarr[t].getText().toString();
                            stringBuilder.append(text);
                            String tempans=stringBuilder.toString();
                            System.out.println("ans="+tempans);
                            if(tempans.equalsIgnoreCase(ans))
                            {
                                editor.putInt("level",pos);
                                tempscore=sharedPreferences.getInt("score",0);
                                tempscore=tempscore+50;
                                editor.putInt("score",tempscore);
                                editor.commit();

                                System.out.println("match");
                                Intent intent=new Intent(MainActivity2.this,MainActivity2.class);
                                pos=pos+1;
                                intent.putExtra("pos",pos);
                                startActivity(intent);
                                finish();
                            }
                            else {
                                System.out.println("not match");
                            }
                        }
                        break;
                    }

                }

            }
            else {
                Toast.makeText(this, "score not available", Toast.LENGTH_SHORT).show();
            }


        });
    }
    public void start(View view)
    {
        imageButton.startAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        for(int i=0;i<b.length;i++)
        {
            if(b[i].getId()==v.getId())
            {
                System.out.println("pos="+i+"=>value="+b[i].getText());
               if(!b[i].getText().toString().isEmpty() && t!=ans.length())
               {
                   for(int k=0;k<ans.length();k++)
                   {
                       if(viewarr[k].getText().toString().isEmpty())
                       {
                           viewarr[k].setText(b[i].getText());
                           b[i].setText("");
                           Map m= new HashMap();
                           m.put(k,i);
                           position.add(m);
                           System.out.println(position);
                           t++;
                           break;
                       }
                   }
                   stringBuilder.delete(0,stringBuilder.length());
                   for(int t=0;t<ans.length();t++)
                   {
                       String text=viewarr[t].getText().toString();
                       stringBuilder.append(text);
                       String tempans=stringBuilder.toString();
                       System.out.println("ans="+tempans);
                       if(tempans.equalsIgnoreCase(ans))
                       {
                           editor.putInt("level",pos);
                           int tempscore=sharedPreferences.getInt("score",0);
                           tempscore=tempscore+50;
                           editor.putInt("score",tempscore);
                           editor.commit();

                           System.out.println("match");
                           Intent intent=new Intent(MainActivity2.this,MainActivity2.class);
                           pos=pos+1;
                           intent.putExtra("pos",pos);
                           startActivity(intent);
                           finish();
                       }
                       else {
                           System.out.println("not match");
                       }
                   }
               }

            }

        }

        for(int i=0;i<ans.length();i++)
        {
            if(v.getId()==viewarr[i].getId())
            {
                if(!viewarr[i].getText().toString().isEmpty())
                {
                    System.out.println("pos="+i+"=>"+viewarr[i].getText());
                    for(int k=0;k<position.size();k++)
                    {
                        Map<Integer,Integer> m=position.get(k);
                        System.out.println("==="+m);
                        Set s=m.keySet();

                        if(s.contains(i))
                        {
                            Map<Integer,Integer> m1=position.get(k);
                            System.out.println("reverse="+m1);
                            b[m1.get(i)].setText(viewarr[i].getText());
                            viewarr[i].setText("");
                            position.remove(m1);
                            System.out.println(position);
                            t--;
                        }
                        else {
                            System.out.println("hello");
                        }
//
                    }
                }
            }
        }
    }
}
//3-0
//4-1
//1-2
//5-3
//[3,4,1,5]
//3,4,5
//4,5

//from to

package com.github.alexdochioiu.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.alexdochioiu.k2jcompat.K2JCompat;

import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Integer length = take("dsa")
                .also((str) -> Log.i("MainActivity", "test"))
                .also((str) -> Log.i("MainActivity", str))
                .let(String::length)
                .also((len) -> Log.i("MainActivity", "t " + len))
                .let((len) -> (Integer) null)
                ._also((val) -> Log.i("MainActivity", "t " + (val + 2) ))
                .unwrap();
    }
}

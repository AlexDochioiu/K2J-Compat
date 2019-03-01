/*
 * Copyright 2019 Alexandru Iustin Dochioiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.alexdochioiu.example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.alexdochioiu.k2jcompat.K2JCompat;

import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

/**
 * Created by Alexandru Iustin Dochioiu on 01-Mar-19
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Integer length = take("ddsa")
                .also((str) -> Log.i("MainActivity", "test"))
                .also((str) -> Log.i("MainActivity", str))
                .let(String::length)
                .takeUnless((len) -> len == 3)
                .unwrap();

        Log.i("MainActivity", "onCreate: " + length);
    }
}

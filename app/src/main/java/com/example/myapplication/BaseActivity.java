package com.example.myapplication;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.main_page:
                Intent main_intent = new Intent(this,MainActivity.class);
                startActivity(main_intent);
                return true;
            case R.id.sign_in:
                Intent sign_intent = new Intent(this,UserRegister.class);
                startActivity(sign_intent);
                return true;
            case R.id.start_AR:
                Intent ar_intent = new Intent(this,ar_screen.class);
                startActivity(ar_intent);
                return true;
            case R.id.achievement_page:
                Intent achievement_intent = new Intent(this,Achievement.class);
                startActivity(achievement_intent);
                return true;
            case R.id.word_list:
                Intent wordList_intent = new Intent(this,WordList.class);
                startActivity(wordList_intent);
                return true;
            case R.id.logIn:
                //logica cierre sesión
                Intent logIn_intent = new Intent(this,LogIn.class);
                startActivity(logIn_intent);
                return true;
            /*case R.id.CerrarSesion:
                //logica cierre sesión*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

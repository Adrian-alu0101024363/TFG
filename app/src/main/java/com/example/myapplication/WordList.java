package com.example.myapplication;
import static com.example.myapplication.db.DbHelper.TABLE_WORDS;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.RequestOptions;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.db.DbHelper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class WordList extends BaseActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordlist);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        adapter.loadDataFromDatabase();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        //private String[] texts = {"出来れば貴様のお母さんにうんこしたい。ダニエルはくそたれ", "Texto 2", "Texto 3"};
        //private int[] images = {R.drawable.castle, R.drawable.osaka, R.drawable.temple, R.drawable.view};
        private List<String> texts = new ArrayList<>();
        private List<String> images = new ArrayList<>();
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.textView.setText(texts.get(position));
            String imagePath = images.get(position);
            Uri imageUri = Uri.parse(imagePath);
            Bitmap bitmap = getBitmapFromUri(imageUri);
            bitmap = rotateImage(bitmap,90);
            if (bitmap != null) {
                holder.imageView.setImageBitmap(bitmap);
            }
        }

        @Override
        public int getItemCount() {
            return texts.size();
        }
        public void loadDataFromDatabase() {
            // Limpiar las listas actuales
            texts.clear();
            images.clear();

            // Consultar los datos de la base de datos
            DbHelper dbHelper = new DbHelper(WordList.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(DbHelper.TABLE_WORDS, null, null, null, null, null, null);

            // Recorrer el cursor y agregar los valores a las listas
            if (cursor.moveToFirst()) {
                int textoIndex = cursor.getColumnIndex("texto");
                int imageurlIndex = cursor.getColumnIndex("imageurl");
                do {
                    String texto = cursor.getString(textoIndex);
                    String imageurl = cursor.getString(imageurlIndex);

                    texts.add(texto);
                    images.add(imageurl);

                } while (cursor.moveToNext());
            }

            cursor.close();
            db.close();

            // Notificar al adaptador que los datos han cambiado
            notifyDataSetChanged();
        }
        private Bitmap getBitmapFromUri(Uri uri) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                return BitmapFactory.decodeStream(inputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }
        private Bitmap rotateImage(Bitmap bitmap, float degrees) {
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
    }
}


/*
            DbHelper dbHelper = new DbHelper(WordList.this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String query = "SELECT texto FROM " + TABLE_WORDS + " ORDER BY id DESC LIMIT 1";
            Cursor cursor = db.rawQuery(query, null);

            String lastText = "";

            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("texto");
                if (columnIndex != -1) {
                    lastText = cursor.getString(columnIndex);
                }
            }
            cursor.close();
            db.close();
            int length = texts.length;
            String[] newTexts = new String[length + 1];
            System.arraycopy(texts, 0, newTexts, 0 , length);
            newTexts[length] = lastText;
            texts = newTexts;
 */
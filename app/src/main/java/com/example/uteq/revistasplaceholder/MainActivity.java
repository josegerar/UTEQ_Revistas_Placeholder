package com.example.uteq.revistasplaceholder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.uteq.revistasplaceholder.model.Revista;
import com.example.uteq.revistasplaceholder.webservice.Asynchtask;
import com.example.uteq.revistasplaceholder.webservice.WebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mindorks.placeholderview.PlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Asynchtask {
    PlaceHolderView phvGallery;
    Type revistaType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phvGallery = (PlaceHolderView) findViewById(R.id.phv_gallery);

        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://revistas.uteq.edu.ec/ws/journals.php", datos, this, this);
        ws.execute("");


// (Optional): If customization is Required then use Builder with the PlaceHolderView
        phvGallery.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(this, 3));
        revistaType = new TypeToken<List<Revista>>() {
        }.getType();

    }

    @Override
    public void processFinish(String result) throws JSONException {
        ArrayList<Revista> revistas = new Gson().fromJson(result, revistaType);
        for (Revista rev : revistas) {
            phvGallery.addView(new GalleryImage(getApplicationContext(), rev.getPortada()));
        }
    }
}
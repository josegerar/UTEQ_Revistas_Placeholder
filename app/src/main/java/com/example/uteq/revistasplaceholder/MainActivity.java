package com.example.uteq.revistasplaceholder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.uteq.revistasplaceholder.webservice.Asynchtask;
import com.mindorks.placeholderview.PlaceHolderView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Asynchtask {
    PlaceHolderView phvGallery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phvGallery = (PlaceHolderView) findViewById(R.id.phv_gallery);

// (Optional): If customization is Required then use Builder with the PlaceHolderView
        phvGallery.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new GridLayoutManager(this, 3));

//        phvGallery
//                .addView(new GalleryImage(getApplicationContext(), url1))
//                .addView(new GalleryImage(getApplicationContext(), url2))
//                .addView(new GalleryImage(getApplicationContext(), url3))
//                .addView(new GalleryImage(getApplicationContext(), url4));
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONObject pais=  new JSONObject(result);

    }
}
package com.example.uteq.revistasplaceholder;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uteq.revistasplaceholder.model.Revista;
import com.example.uteq.revistasplaceholder.model.Volumen;
import com.example.uteq.revistasplaceholder.webservice.Asynchtask;
import com.example.uteq.revistasplaceholder.webservice.WebService;
import com.mindorks.placeholderview.PlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolumenActivity extends AppCompatActivity implements Asynchtask {

    PlaceHolderView phvGallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volumen);
        Bundle bundle = getIntent().getExtras();
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://revistas.uteq.edu.ec/ws/issues.php?j_id=" + bundle.getString("journal_id"), datos, this, this);
        ws.execute("");

        phvGallery = (PlaceHolderView) findViewById(R.id.phv_gallery);
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray array = new JSONArray(result);
        ArrayList<Volumen> volumenes = new ArrayList<>();
        for (int i = 0; i < array.length(); i++){
            JSONObject jsonObject = array.getJSONObject(i);
            Volumen volumen1 = new Volumen();
            volumen1.setCover(jsonObject.getString("cover"));
            volumen1.setDate_published(jsonObject.getString("date_published"));
            volumen1.setDoi(jsonObject.getString("doi"));
            volumen1.setIssue_id(jsonObject.getString("issue_id"));
            volumen1.setNumber(jsonObject.getString("number"));
            volumen1.setYear(jsonObject.getString("year"));
            volumen1.setTitle(jsonObject.getString("title"));
            volumen1.setVolume(jsonObject.getString("volume"));
            volumenes.add(volumen1);
            phvGallery.addView(new GalleryVolumen(getApplicationContext(), volumen1));
        }
    }
}
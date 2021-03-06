package com.example.uteq.revistasplaceholder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.uteq.revistasplaceholder.model.Articulo;
import com.example.uteq.revistasplaceholder.model.Autor;
import com.example.uteq.revistasplaceholder.model.Galery;
import com.example.uteq.revistasplaceholder.model.Revista;
import com.example.uteq.revistasplaceholder.util.FileDownloader;
import com.example.uteq.revistasplaceholder.util.Util;
import com.example.uteq.revistasplaceholder.webservice.Asynchtask;
import com.example.uteq.revistasplaceholder.webservice.WebService;
import com.mindorks.placeholderview.PlaceHolderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ArticulosActivity extends AppCompatActivity implements Asynchtask, IDownloadFile {

    PlaceHolderView phvGallery;
    String issue_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articulos);

        ActivityCompat.requestPermissions(ArticulosActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                1);

        ActivityCompat.requestPermissions(ArticulosActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);

        Bundle bundle = getIntent().getExtras();
        issue_id = bundle.getString("issue_id");
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService("https://revistas.uteq.edu.ec/ws/pubs.php?i_id=" + issue_id, datos, this, this);
        ws.execute("");
        phvGallery = (PlaceHolderView) findViewById(R.id.phv_gallery);
        phvGallery.getBuilder()
                .setHasFixedSize(false)
                .setItemViewCacheSize(10)
                .setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void processFinish(String result) throws JSONException {
        JSONArray array = new JSONArray(result);
        ArrayList<Articulo> articulos = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsonObject = array.getJSONObject(i);
            Articulo articulo = new Articulo();
            articulo.setTitle(jsonObject.getString("title"));

            articulo.setAutors(this.getAutores(jsonObject.getJSONArray("authors")));

            articulo.setGaleries(this.getGaleries(jsonObject.getJSONArray("galeys")));

            articulos.add(articulo);
            phvGallery.addView(new GalleryArticulos(getApplicationContext(), articulo, this));
        }
    }

    private ArrayList<Galery> getGaleries(JSONArray galeysArrayJSON) throws JSONException {
        ArrayList<Galery> galeries = new ArrayList<>();
        for (int j = 0; j < galeysArrayJSON.length(); j++) {
            JSONObject objectGalery = galeysArrayJSON.getJSONObject(j);
            Galery galery = new Galery();
            galery.setFile_id(objectGalery.getString("file_id"));
            galery.setGalley_id(objectGalery.getString("galley_id"));
            galery.setLabel(objectGalery.getString("label"));
            galery.setUrlViewGalley(objectGalery.getString("UrlViewGalley"));
            galeries.add(galery);
        }
        return galeries;
    }

    private ArrayList<Autor> getAutores(JSONArray jsonArrayAutores) throws JSONException {
        ArrayList<Autor> autors = new ArrayList<>();
        for (int j = 0; j < jsonArrayAutores.length(); j++) {
            JSONObject objectAuthor = jsonArrayAutores.getJSONObject(j);
            Autor autor = new Autor();
            autor.setEmail(objectAuthor.getString("email"));
            autor.setFiliacion(objectAuthor.getString("filiacion"));
            autor.setNombres(objectAuthor.getString("nombres"));
            autors.add(autor);
        }
        return autors;
    }

    @Override
    public void download(String url, String nombre, String ext) {
        new DownloadFile().execute(url, nombre, ext);
    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            String ext = strings[2];  // -> maven.pdf
            String extStorageDirectory = getApplicationContext().getFilesDir().toString();
            File folder = new File(extStorageDirectory, "pdf");
            if (!folder.exists()) {
                folder.mkdir();
            }
            int count = 0;
            File file = new File(folder, fileName + count + ext);
            if (file.exists()) {
                while (file.exists()) {
                    count++;
                    file = new File(folder, fileName + count + ext);
                }
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, file);
            Util.mostrarPDF(fileName + count + ext, getApplicationContext(), file.toString());
            return null;
        }

    }
}
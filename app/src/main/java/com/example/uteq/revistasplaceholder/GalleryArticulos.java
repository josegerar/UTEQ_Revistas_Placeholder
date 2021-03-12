package com.example.uteq.revistasplaceholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.uteq.revistasplaceholder.model.Articulo;
import com.example.uteq.revistasplaceholder.model.Autor;
import com.example.uteq.revistasplaceholder.model.Galery;
import com.example.uteq.revistasplaceholder.model.Revista;
import com.example.uteq.revistasplaceholder.util.Util;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Recycle;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Animate(Animate.CARD_TOP_IN_DESC)
@Layout(R.layout.item_articulos)
public class GalleryArticulos {

    @View(R.id.textTituloArticulo)
    TextView textTituloArticulo;

    @View(R.id.textAutores)
    TextView textAutores;

    @Position
    int position;

    private Context context;
    private Articulo articulo;


    public GalleryArticulos(Context context, Articulo articulo) {
        this.context = context;
        this.articulo = articulo;
    }

    /*
     * This method is called when the view is rendered
     * onResolved method could be named anything, Example: onAttach
     */
    @Resolve
    public void onResolved() {
        // do something here
        // example: load imageView with url image
        textTituloArticulo.setText(articulo.getTitle());
        String autores = "";
        for (Autor autor: articulo.getAutors()){
            autores = autores.concat(autor.getNombres());
            autores = autores.concat(", ");
        }
        textAutores.setText(autores);
    }

    /*
     * This method is called when the view holder is recycled
     * and used to display view for the next data set
     */
    @Recycle
    public void onRecycled() {
        // do something here
        // Example: clear some references used by earlier rendering

    }

    /*
     * This method is called when the view with id image_view is clicked.
     * onImageViewClick method could be named anything.
     */

    @Click(R.id.descargarPDF)
    public void onItemdescargarPDFClick() {
        boolean existe = false;
        for (Galery galery: articulo.getGaleries()){
            if (galery.getLabel().equals("PDF")){
                existe = true;
                Util.downloadFile(galery.getUrlViewGalley(), "article" + galery.getFile_id(), ".pdf", context);
            }
        }
        if (!existe){
            Toast.makeText(context, "El archivo solicitado no existe",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Click(R.id.descargarHTML)
    public void onItemdescargarHTMLClick() {

    }
}

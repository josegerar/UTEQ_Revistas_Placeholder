package com.example.uteq.revistasplaceholder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.uteq.revistasplaceholder.model.Revista;
import com.example.uteq.revistasplaceholder.model.Volumen;
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
@Layout(R.layout.item_volumen)
public class GalleryVolumen {

    @View(R.id.card_view_volumen)
    CardView cardView;

    @View(R.id.image_view_volumen)
    ImageView imageView;

    @View(R.id.textTitle_volumen)
    TextView textTitle;

    @View(R.id.textVolumen)
    TextView textVolumen;

    @Position
    int position;


    private Context context;
    private Volumen volumen;

    public GalleryVolumen(Context context, Volumen volumen) {
        this.context = context;
        this.volumen = volumen;
    }

    /*
     * This method is called when the view is rendered
     * onResolved method could be named anything, Example: onAttach
     */
    @Resolve
    public void onResolved() {
        // do something here
        // example: load imageView with url image
        Glide.with(context)
                .load(volumen.getCover())
                .into(imageView);
        textTitle.setText(volumen.getTitle());
        textVolumen.setText(volumen.getVolume());
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

    @Click(R.id.vervolumen)
    public void onItemClick() {
        Bundle bundle = new Bundle();
        bundle.putString("issue_id", volumen.getIssue_id());
        Intent intent = new Intent(context, VolumenActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}

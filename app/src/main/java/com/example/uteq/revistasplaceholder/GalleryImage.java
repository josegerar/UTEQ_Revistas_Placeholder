package com.example.uteq.revistasplaceholder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.uteq.revistasplaceholder.model.Revista;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.LongClick;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Recycle;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

@NonReusable
@Animate(Animate.CARD_TOP_IN_DESC)
@Layout(R.layout.item_gallery_image)
public class GalleryImage {

    @View(R.id.card_view)
    CardView cardView;

    @View(R.id.image_view)
    ImageView imageView;

    @View(R.id.lblName)
    TextView textViewNombre;

    @View(R.id.lblDescription)
    TextView textViewDescription;

    @Position
    int position;

    private Context context;
    private Revista revista;

    public GalleryImage(Context context, Revista revista) {
        this.context = context;
        this.revista = revista;
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
                .load(revista.getPortada())
                .into(imageView);
        textViewDescription.setText(revista.getDescription());
        textViewNombre.setText(revista.getName());
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
    @Click(R.id.image_view)
    public void onImageViewClick() {
        // do something
    }

    @LongClick(R.id.image_view)
    public void onImageViewLongClick() {
        // do something
    }

    @Click(R.id.verrevista)
    public void onItemClick() {
        Bundle bundle = new Bundle();
        bundle.putString("journal_id", revista.getJournal_id());
        Intent intent = new Intent(context, VolumenActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

}
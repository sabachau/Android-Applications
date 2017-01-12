package com.csaba.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csaba.flixter.R;
import com.csaba.flixter.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by saba on 01/01/17.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie>{

    private static int orientation;

    //View Lookup cache
    private static class ViewHolder{
        TextView title;
        TextView overview;
        ImageView image;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies){
     super(context, android.R.layout.simple_list_item_1,movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        orientation = this.getContext().getResources().getConfiguration().orientation;
        //get the data item for position
        Movie movie = getItem(position);

        ViewHolder viewHolder; // view Lookup cache stored in tag

        //check the existing view is reused
        if(convertView == null){

            // If there's no view to re-use, inflate a brand new view for row

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_movie,parent,false);
            viewHolder.title= (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.overview= (TextView) convertView.findViewById(R.id.tvOverview);
            viewHolder.image= (ImageView) convertView.findViewById(R.id.ivMovieImage);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {

        // View is being recycled, retrieve the viewHolder object from tag

        viewHolder = (ViewHolder) convertView.getTag();

        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        viewHolder.title.setText(movie.getOriginalTitle());
        viewHolder.overview.setText(movie.getOverview());

        if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            Picasso.with(getContext()).load(movie.getBackdropPath())
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(viewHolder.image);
        }else if(orientation == Configuration.ORIENTATION_PORTRAIT){
            Picasso.with(getContext()).load(movie.getPosterPath()).fit().centerCrop()
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(viewHolder.image);
        }
        // Return the completed view to render on screen
        return convertView;

    }
}

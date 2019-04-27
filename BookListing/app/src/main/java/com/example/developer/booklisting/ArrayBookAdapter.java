package com.example.developer.booklisting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by binar on 12/11/2017.
 */

public class ArrayBookAdapter extends ArrayAdapter<Book> {
  public  ArrayBookAdapter(Context context, ArrayList<Book> data)
    {
        super(context,0,data);
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;
        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.books,parent,false);
        }
        Book book=getItem(position);
        ImageView image=(ImageView) listItemView.findViewById(R.id.image);
        TextView title=(TextView) listItemView.findViewById(R.id.nametitle);
        TextView author=(TextView) listItemView.findViewById(R.id.nameauthor);

        Picasso.with(getContext()).load(book.getImage()).into(image);
        title.setText(book.getTitle());
        author.setText(book.getAuthor());

        return listItemView;

}
}

package com.example.developer.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.y;

/**
 * Created by binar on 12/11/2017.
 */

public class AsynctaskLoader extends AsyncTaskLoader<List<Book>>{
   String api;
    public AsynctaskLoader(Context context,String api)

    {super(context);this.api=api;}

    @Override
    protected void onStartLoading() {forceLoad();}

    @Override
    public List<Book> loadInBackground() {
        if (api==null) return null;
        System.out.println("api"+api);
        List<Book> data=Query.fetchData(api);
        return data;
    }
}

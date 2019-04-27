package com.example.developer.booklisting;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import java.lang.String;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    TextView bad;
    ProgressBar progressBar;
    ListView listView;
    SearchView searchView;
    Button searchButton;
    ArrayBookAdapter adapter;
    LoaderManager loaderManager=getLoaderManager();
    static String GooGle_api;
    static int Loader_id=1;
    private String searchword;
    static String api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bad=(TextView) findViewById(R.id.badresposive);
        progressBar=(ProgressBar) findViewById(R.id.progress_bar);
        listView=(ListView) findViewById(R.id.list_item);
        searchView=(SearchView) findViewById(R.id.search_msg);
        searchButton=(Button) findViewById(R.id.Search);
        progressBar.setVisibility(View.GONE);
        GooGle_api="https://www.googleapis.com/books/v1/volumes?q=";
        api=GooGle_api;
        listView.setEmptyView(bad);


        adapter =new ArrayBookAdapter(MainActivity.this,new ArrayList<Book>());
        listView.setAdapter(adapter);
        loaderManager.initLoader(Loader_id,null,MainActivity.this);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                searchword=searchView.getQuery().toString();
                 api=GooGle_api+wordSearch(searchword);
                progressBar.setVisibility(View.VISIBLE);
                bad.setText(null);
                if(isNetwordConnectivety(MainActivity.this))
                {
                    loaderManager.restartLoader(Loader_id,null,MainActivity.this);
                }
                else{
                    Log.w(null,"you are not connected");
                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });
     listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             Book book = adapter.getItem(i);
             Uri urlIntent = Uri.parse(book.getUrl());
             Intent intent = new Intent(Intent.ACTION_VIEW, urlIntent);
             startActivity(intent);
         }
     });

    }

    public boolean isNetwordConnectivety(Context context)
    {
        ConnectivityManager connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return networkInfo !=null&& networkInfo.isConnected();

    }

    public String wordSearch(String word)
    {
        word.trim();
        word.toLowerCase();
        StringBuilder stringBuilder=new StringBuilder();
        String [] words=word.split(" ");
        for(int x=0;x<words.length;x++)
        {
            stringBuilder.append(words[x]);
            if (x != words.length - 1) stringBuilder.append("%20");

        }
        return stringBuilder.toString();


    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new AsynctaskLoader(MainActivity.this,api);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
     progressBar.setVisibility(View.GONE);
     adapter.clear();
        if(books==null||books.isEmpty())
        {
            Log.w(null,"No Have Book");
        }

        adapter.addAll(books);

    }

    @Override
    public  void onLoaderReset(Loader<List<Book>> loader) {
      adapter.clear();
    }
}

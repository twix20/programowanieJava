package com.example.twix20.easypostviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core.JsonplaceholderService;
import com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core.JsonplaceholderServiceFactory;
import com.example.twix20.easypostviewer.com.example.twix20.easypostviewer.core.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PostListActivity extends AppCompatActivity {

    JsonplaceholderService apiService = JsonplaceholderServiceFactory.Create();

    private int userId;
    private ListView listview;
    private EditText filter;
    private PostArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        //Add back button
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getIntExtra("USER_ID", 0);
        listview = (ListView) findViewById(R.id.listview);
        filter = (EditText) findViewById(R.id.editTextFilter);

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (PostListActivity.this).adapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        apiService.getUserPosts(userId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(posts -> {
                adapter = new PostArrayAdapter(this, android.R.layout.simple_list_item_1, posts);
                listview.setAdapter(adapter);
            });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private class PostArrayAdapter extends ArrayAdapter<Post> {

        private final List<Post> posts;
        private final Context context;
        private Filter filter;

        public PostArrayAdapter(Context context, int textViewResourceId, List<Post> objects) {
            super(context, textViewResourceId, objects);

            this.context = context;
            this.posts = objects;
        }

        @Override
        public Filter getFilter() {
            if (filter == null)
                filter = new PostFilter<Post>(posts);

            return filter;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.item_post, parent, false);

            TextView id = (TextView) rowView.findViewById(R.id.textViewId);
            TextView title = (TextView) rowView.findViewById(R.id.textViewTitle);
            TextView body = (TextView) rowView.findViewById(R.id.textViewBody);

            Post p = this.posts.get(position);

            id.setText(Integer.toString(p.getId()));
            title.setText(p.getTitle());
            body.setText(p.getBody());

            return rowView;
        }

        private class PostFilter<T extends Post> extends Filter {

            private ArrayList<T> sourceObjects;

            public PostFilter(List<T> objects) {
                sourceObjects = new ArrayList<T>();
                synchronized (this) {
                    sourceObjects.addAll(objects);
                }
            }

            @Override
            protected FilterResults performFiltering(CharSequence chars) {
                String filterSeq = chars.toString().toLowerCase();
                FilterResults result = new FilterResults();
                if (filterSeq != null && filterSeq.length() > 0) {
                    ArrayList<Post> filtered = new ArrayList<>();
                    for (T p : sourceObjects) {
                        // the filtering itself:
                        if (p.getBody().contains(chars) || p.getTitle().contains(chars))
                            filtered.add(p);
                    }
                    result.count = filtered.size();
                    result.values = filtered;
                } else {
                    // add all objects
                    synchronized (this) {
                        result.values = sourceObjects;
                        result.count = sourceObjects.size();
                    }
                }
                return result;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                // NOTE: this function is *always* called from the UI thread.
                ArrayList<T> filtered = (ArrayList<T>) results.values;
                notifyDataSetChanged();
                clear();
                for (int i = 0, l = filtered.size(); i < l; i++)
                    add((Post) filtered.get(i));
                notifyDataSetInvalidated();
            }
        }
    }
}

package com.example.serg.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serg.testapp.mylist.MyListContent;
import com.example.serg.testapp.parse_logic.JsonFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class ItemListActivity extends AppCompatActivity {

    private boolean mTwoPane;
    JSONArray array = null;
    URL url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        String JSON = getApplicationContext().getSharedPreferences("com.example.serg.testapp", Context.MODE_PRIVATE).getString("JSONArray", "");
        if (!JSON.equals("")) {
            array = JsonFactory.parseStringToArray(JSON);
        } else {
            Log.e("ItemListActivity", "No Data Received");
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(array));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<MyListContent.MyListItem> mValues = new ArrayList<>();

        public SimpleItemRecyclerViewAdapter(JSONArray items) {
            for (int i = 0; i < items.length(); i++) {
                try {
                    JSONObject jo = items.getJSONObject(i);
                    MyListContent.MyListItem di = new MyListContent.MyListItem(jo.getString("id"), jo.getString("first_name"), jo.getString("last_name"), jo.getString("email"), jo.getString("gender"), jo.getString("ip_address"));
                    mValues.add(di);
                    MyListContent.addItem(di);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = this.mValues.get(position);
            holder.mIdView.setText(mValues.get(position).id);
            holder.mFNameView.setText(mValues.get(position).first_name);
            holder.mLNameView.setText(mValues.get(position).last_name);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mFNameView;
            public final TextView mLNameView;
            public MyListContent.MyListItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mFNameView = (TextView) view.findViewById(R.id.first_name);
                mLNameView = (TextView) view.findViewById(R.id.last_name);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mFNameView.getText() + "'";
            }
        }
    }
}

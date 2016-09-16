package com.example.serg.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.serg.testapp.mylist.MyListContent;

public class ItemDetailFragment extends Fragment {
    public static final String ARG_ITEM_ID = "id";

    private MyListContent.MyListItem mItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = MyListContent.getItemMap().get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.first_name + " " + mItem.last_name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.id)).setText(mItem.id);
            ((TextView) rootView.findViewById(R.id.first_name)).setText(mItem.first_name);
            ((TextView) rootView.findViewById(R.id.last_name)).setText(mItem.last_name);
            ((TextView) rootView.findViewById(R.id.email)).setText(mItem.email);
            ((TextView) rootView.findViewById(R.id.gender)).setText(mItem.gender);
            ((TextView) rootView.findViewById(R.id.ip_address)).setText(mItem.ip_address);
        }

        return rootView;
    }
}

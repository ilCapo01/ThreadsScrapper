package com.fxpscrapper.bar771.fxpscrapper;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<ThreadItem> {

    public CustomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomAdapter(Context context, int resource, List<ThreadItem> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.threadrow, null);
        }
        final ThreadItem threadItem = getItem(position);

        if (threadItem != null) {
            TextView threadName = (TextView) v.findViewById(R.id.threadName);
            Button openThread= (Button) v.findViewById(R.id.openThread);

            threadName.setText(threadItem.toString());
            openThread.setText("=>");
            openThread.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(threadItem.getUrl()));
                        //MainActivity.con.startActivity(myIntent);
                        getContext().startActivity(myIntent);
                    } catch (ActivityNotFoundException e) { e.printStackTrace();}
                }
            });
        }
        return v;
    }

}
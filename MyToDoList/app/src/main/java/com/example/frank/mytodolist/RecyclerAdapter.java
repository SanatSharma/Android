package com.example.frank.mytodolist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sanat on 9/28/2015.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView recyclerText;

        public ViewHolder(View itemView)
        {
            super(itemView);
            recyclerText = (TextView) itemView.findViewById("recycler_text_view");
        }
    }

    //Instance Vars for adapter
    private ArrayList<String> itemData;

    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
    }
}

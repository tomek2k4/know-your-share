package com.pum.tomasz.knowyourshare.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pum.tomasz.knowyourshare.R;
import com.pum.tomasz.knowyourshare.Utilities;
import com.pum.tomasz.knowyourshare.data.Product;

import java.util.List;

/**
 * Created by tomasz on 02.08.2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    List<Product> list;


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mProductNameTextView;
        public TextView mDateTextView;
        public TextView mValueTextView;
        public TextView mUnitTypeTextView;
        public ViewHolder(View v) {
            super(v);
            mProductNameTextView = (TextView) v.findViewById(R.id.row_product_name_textview);
            mDateTextView = (TextView) v.findViewById(R.id.row_buy_date_textview);
            mValueTextView = (TextView) v.findViewById(R.id.row_product_value_textview);
            mUnitTypeTextView = (TextView) v.findViewById(R.id.row_product_unit_type_textview);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Toast.makeText(v.getContext(), "Clicked on " + getPosition(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public MyRecyclerViewAdapter(List<Product> list) {
        this.list = list;
    }

    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.product_row_layout, viewGroup, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder viewHolder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        viewHolder.mProductNameTextView.setText(list.get(position).getName());
        viewHolder.mDateTextView.setText(Utilities.convertDateToString(list.get(position).getBuyDate()));
        viewHolder.mValueTextView.setText(Utilities.DOUBLE_CUT_ZERO_FMT.format(list.get(position).getSize()));
        viewHolder.mUnitTypeTextView.setText(list.get(position).getMeasureUnitString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

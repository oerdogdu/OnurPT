package com.eon.atoi.onurpt.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.utils.XmlFaqUtils;

/**
 * Created by Atoi on 6.12.2017.
 */

public class FaqAdapter extends ArrayAdapter<XmlFaqUtils.FAQ> {

    private final Context context;
    private final XmlFaqUtils.FAQ[] items;

    static class ViewHolder {
        public TextView title;
        public TextView text;
        public LinearLayout background;
    }

    public FaqAdapter(Context context, XmlFaqUtils.FAQ[] spans) {
        super(context, R.layout.faq_item);
        this.context = context;
        this.items = spans;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            rowView = inflater.inflate(R.layout.faq_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.background = (LinearLayout) rowView.findViewById(R.id.faq_item);
            viewHolder.title = (TextView) rowView.findViewById(R.id.faq_title);
            viewHolder.text = (TextView) rowView.findViewById(R.id.faq_text);

            rowView.setTag(viewHolder);
        }

        final ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.title.setText(items[position].question);
        holder.text.setText(items[position].text);

        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.text.getVisibility() != View.VISIBLE) {
                    holder.text.setVisibility(View.VISIBLE);
                } else {
                    holder.text.setVisibility(View.GONE);
                }
            }
        });

        if (holder.text.getVisibility() != View.GONE) {
            holder.text.setVisibility(View.GONE);
        }

        return rowView;
    }
}

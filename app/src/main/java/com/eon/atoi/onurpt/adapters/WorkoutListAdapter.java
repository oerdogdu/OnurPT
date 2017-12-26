package com.eon.atoi.onurpt.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eon.atoi.onurpt.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Atoi on 2.12.2017.
 */

public class WorkoutListAdapter extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater = null;
    private ArrayList mData;

    public WorkoutListAdapter(Activity activity, Map<String, Integer> map) {
        this.activity = activity;
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return mData.size();
    }

    public Map.Entry<String, Integer> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView == null)
            vi = inflater.inflate(R.layout.workout_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.workout_title);
        ImageView thumb_image = (ImageView) vi.findViewById(R.id.workout_image);

        Map.Entry<String, Integer> workoutSingle = getItem(position);

        title.setText(workoutSingle.getKey());
        thumb_image.setImageResource(workoutSingle.getValue());

        return vi;
    }
}

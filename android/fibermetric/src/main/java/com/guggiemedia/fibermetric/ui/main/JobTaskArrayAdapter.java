package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;



/**
 *
 */
public class JobTaskArrayAdapter extends ArrayAdapter<JobTaskModel> {
    private Context _context;

    public JobTaskArrayAdapter(JobTaskModelList list, Context context) {
        super(context, R.layout.row_jobtask, list);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        JobTaskModel model = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_jobtask, parent, false);
        }

        ImageView imageTaskState = (ImageView) convertView.findViewById(R.id.imageTaskState);

        Integer stateImageResource = null;

        switch (model.getState()) {
            case ACTIVE:
                stateImageResource = R.drawable.ic_task_active;
                break;
            case COMPLETE:
                stateImageResource = R.drawable.ic_task_completed;
                break;
            case SCHEDULE:
                stateImageResource = R.drawable.ic_task_scheduled;
                break;
            case SUSPEND:
                stateImageResource = R.drawable.ic_task_suspended;
                break;
        }

        if (stateImageResource != null) {
            imageTaskState.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), stateImageResource));
        }

        TextView tvRowNdx = (TextView) convertView.findViewById(R.id.tvRowNdx);
        tvRowNdx.setText(Integer.toString(1 + position));

        TextView tvRowName = (TextView) convertView.findViewById(R.id.tvRowName);
        tvRowName.setText(model.getName());

        return convertView;
    }
}

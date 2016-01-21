package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.lib.db.TaskDetailModel;
import com.guggiemedia.fibermetric.lib.db.TaskDetailModelList;

import net.go_factory.tundro.R;


/**
 *
 */
public class TaskDetailArrayAdapter extends ArrayAdapter<TaskDetailModel> {

    public TaskDetailArrayAdapter(TaskDetailModelList list, Context context) {
        super(context, R.layout.row_jobtask, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskDetailModel model = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_jobtask, parent, false);
        }

        ImageView imageTaskState = (ImageView) convertView.findViewById(R.id.imageTaskState);
        imageTaskState.setVisibility(View.GONE);

        TextView tvRowNdx = (TextView) convertView.findViewById(R.id.tvRowNdx);
        tvRowNdx.setText(Integer.toString(1+position));

        TextView tvRowName = (TextView) convertView.findViewById(R.id.tvRowName);
        tvRowName.setText(model.getDescription());

        return convertView;
    }
}

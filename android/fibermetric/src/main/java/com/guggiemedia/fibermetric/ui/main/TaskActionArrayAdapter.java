package com.guggiemedia.fibermetric.ui.main;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.R;
import com.guggiemedia.fibermetric.lib.db.TaskActionModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionModelList;
import com.guggiemedia.fibermetric.lib.db.TaskActionStateEnum;



/**
 *
 */
public class TaskActionArrayAdapter extends ArrayAdapter<TaskActionModel> {
    public static final String LOG_TAG = TaskActionArrayAdapter.class.getName();

    private Context _context;

    public TaskActionArrayAdapter(TaskActionModelList list, Context context) {
        super(context, R.layout.row_jobtask, list);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TaskActionModel model = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_task_action, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);


        if (model.getState() == TaskActionStateEnum.COMPLETE) {
            // checkmark

        } else if (model.getState() == TaskActionStateEnum.CANCELED) {
            imageView.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.ic_close_red));
        } else {
            Integer actionImageResourceId = null;

            switch (model.getAction()) {

                case AUDIO_RECORD:
                    actionImageResourceId = R.drawable.ic_mic_red;
                    break;
                case CAMERA:
                    actionImageResourceId = R.drawable.ic_camera_red;
                    break;
                case CAPTURE_INTEGER:
                    actionImageResourceId = R.drawable.ic_capture_red;
                    break;
                case CAPTURE_NOTE:
                    actionImageResourceId = R.drawable.ic_note_red;
                    break;
                case SCAN_BARCODE:
                    actionImageResourceId = R.drawable.ic_barcode;
                    break;
                default:
                    Log.i(LOG_TAG, "unknown action:" + model.getAction());
            }

            if (actionImageResourceId != null) {
                imageView.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), actionImageResourceId));
            }

        }

        TextView tvRowName = (TextView) convertView.findViewById(R.id.tvRowName);
        tvRowName.setText(model.getDescription());

        return convertView;
    }
}

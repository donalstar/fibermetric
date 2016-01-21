package com.guggiemedia.fibermetric.ui.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.guggiemedia.fibermetric.lib.chain.CommandFacade;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModelList;
import com.guggiemedia.fibermetric.lib.db.SiteModel;

import net.go_factory.tundro.R;



/**
 *
 */
public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.ViewHolder> {
    public static final String LOG_TAG = JobListAdapter.class.getName();

    private MainActivityFragmentEnum _parent = MainActivityFragmentEnum.UNKNOWN;

    private MainActivityListener _listener;

    private Context _context;

    private JobTaskModelList _jobList;

    public JobListAdapter(JobTaskModelList jobList, MainActivityFragmentEnum parent, Activity activity) {
        _jobList = jobList;
        _parent = parent;
        _listener = (MainActivityListener) activity;
        _context = activity;
    }

    public void refresh(JobTaskModelList jobList) {
        _jobList = jobList;

        notifyDataSetChanged();;
    }

    @Override
    public int getItemCount() {
        return _jobList.size();
    }

    @Override
    public int getItemViewType(int position) {
        JobTaskModel model;
        model = _jobList.get(position);
        if (model.isStunt()) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        JobTaskModel model = _jobList.get(position);

        SiteModel siteModel = CommandFacade.siteSelectByRowId(model.getSite(), _context);

        viewHolder.rowId = model.getId();

        if (model.isStunt()) {
            switch (model.getState()) {
                case ACTIVE:
                    viewHolder.state.setText(R.string.jobStateActive);
                    break;
                case COMPLETE:


                    viewHolder.state.setText(R.string.jobStateCompleted);
                    break;
                case SCHEDULE:
                    viewHolder.state.setText(R.string.jobStateScheduled);
                    break;
                case SUSPEND:
                    viewHolder.state.setText(R.string.jobStateSuspended);
                    break;
                default:
                    Log.i(LOG_TAG, "unknown state:" + model.getState() + ":row:" + model.getId());
            }
        } else {
            viewHolder.duration.setText(model.formatter3(model.getDuration()));
            viewHolder.name.setText(model.getName());
            viewHolder.asset.setText(model.getAsset());
            viewHolder.site.setText(siteModel.getName());

            switch (model.getState()) {
                case ACTIVE:
                    viewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.ic_job_active_list));
                    break;
                case COMPLETE:
                    viewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.ic_job_comleted_list));
                    break;
                case SCHEDULE:
                    viewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.ic_job_scheduled_list));
                    break;
                case SUSPEND:
                    viewHolder.icon.setImageBitmap(BitmapFactory.decodeResource(_context.getResources(), R.drawable.ic_job_suspended_list));
                    break;
                default:
                    Log.i(LOG_TAG, "unknown state:" + model.getState() + ":row:" + model.getId());
            }
        }

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong(JobViewFragment.ARG_PARAM_JOB_ID, viewHolder.rowId);

                _listener.fragmentPush(_parent, new Bundle(), MainActivityFragmentEnum.JOB_VIEW, bundle);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(LOG_TAG, "createView:" + viewType);

        View view = null;

        switch (viewType) {
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job1, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_job2, parent, false);
                break;
            default:
                throw new IllegalArgumentException("unknown view type:" + viewType);
        }

        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View view;

        public Long rowId;
        public final ImageView icon;
        public final TextView duration;
        public final TextView name;
        public final TextView asset;
        public final TextView site;
        public final TextView state;

        public ViewHolder(View arg) {
            super(arg);

            view = arg;

            icon = (ImageView) view.findViewById(R.id.imageRowState);

            duration = (TextView) view.findViewById(R.id.tvRowDuration);
            name = (TextView) view.findViewById(R.id.tvRowName);
            asset = (TextView) view.findViewById(R.id.tvRowAsset);
            site = (TextView) view.findViewById(R.id.tvRowSite);
            state = (TextView) view.findViewById(R.id.tvRowState);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + name.getText();
        }
    }
}
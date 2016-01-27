package com.guggiemedia.fibermetric.lib.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.guggiemedia.fibermetric.lib.Constant;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
public class JobTaskModel implements DataBaseModel, Serializable {
    private Long _id;
    private Long _parent;
    private Long _site;
    private Integer _orderNdx;
    private Integer _starRating;
    private String _comment;
    private boolean _jobFlag;
    private String _description;
    private String _name;
    private String _ticket;
    private String _assignedBy;
    private String _asset;
    private JobStateEnum _jobState;
    private JobTaskPriorityEnum _jobTaskPriority;
    private Integer _progress;
    private JobTypeEnum _jobType;
    private Integer _duration;
    private Date _deadlineTime;
    private Date _updateTime;
    private boolean _stuntFlag; //TRUE, this is a header for JobListFragment

    @Override
    public void setDefault() {
        _id = 0L;
        _parent = 0L;
        _site = 0L;
        _orderNdx = 0;
        _starRating = 0;
        _comment = "";
        _name = "Unknown";
        _jobFlag = false;
        _jobState = JobStateEnum.SCHEDULE;
        _jobTaskPriority = JobTaskPriorityEnum.UNKNOWN;
        _progress = 0;
        _jobType = JobTypeEnum.REPAIR;
        _ticket = "Unknown";
        _assignedBy = "Unknown";
        _duration = 0;
        _asset = "Unknown";
        _deadlineTime = Constant.NO_DATE;

        _updateTime = Constant.NO_DATE;
        _description = "No Description";
        _stuntFlag = false;
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(JobTaskTable.Columns.NAME, _name);
        cv.put(JobTaskTable.Columns.PARENT, _parent);
        cv.put(JobTaskTable.Columns.SITE, _site);
        cv.put(JobTaskTable.Columns.ORDER_NDX, _orderNdx);
        cv.put(JobTaskTable.Columns.JOB_FLAG, (_jobFlag) ? Constant.SQL_TRUE : Constant.SQL_FALSE);
        cv.put(JobTaskTable.Columns.JOB_STATE, _jobState.toString());
        cv.put(JobTaskTable.Columns.JOB_PRIORITY, _jobTaskPriority.toString());
        cv.put(JobTaskTable.Columns.PROGRESS, _progress);
        cv.put(JobTaskTable.Columns.JOB_TYPE, _jobType.toString());
        cv.put(JobTaskTable.Columns.TICKET, _ticket);
        cv.put(JobTaskTable.Columns.ASSIGNED_BY, _assignedBy);
        cv.put(JobTaskTable.Columns.DURATION, formatter3(_duration));
        cv.put(JobTaskTable.Columns.DURATION_MM, _duration);
        cv.put(JobTaskTable.Columns.DEADLINE, formatter2(_deadlineTime));
        cv.put(JobTaskTable.Columns.DEADLINE_MS, _deadlineTime.getTime());
        cv.put(JobTaskTable.Columns.ASSET, _asset);
        cv.put(JobTaskTable.Columns.STAR_RATING, _starRating);
        cv.put(JobTaskTable.Columns.COMMENT, _comment);
        cv.put(JobTaskTable.Columns.UPDATE_TIME, formatter1(_updateTime));
        cv.put(JobTaskTable.Columns.UPDATE_TIME_MS, _updateTime.getTime());
        cv.put(JobTaskTable.Columns.DESCRIPTION, _description);
        return cv;
    }

    @Override
    public void fromCursor(Cursor cursor) {
        _id = cursor.getLong(cursor.getColumnIndex(JobTaskTable.Columns._ID));
        _name = cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.NAME));
        _parent = cursor.getLong(cursor.getColumnIndex(JobTaskTable.Columns.PARENT));
        _site = cursor.getLong(cursor.getColumnIndex(JobTaskTable.Columns.SITE));
        _orderNdx = cursor.getInt(cursor.getColumnIndex(JobTaskTable.Columns.ORDER_NDX));
        _jobFlag = (cursor.getInt(cursor.getColumnIndex(JobTaskTable.Columns.JOB_FLAG)) != 0);
        _jobState = JobStateEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.JOB_STATE)));
        _jobTaskPriority = JobTaskPriorityEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.JOB_PRIORITY)));
        _progress = cursor.getInt(cursor.getColumnIndex(JobTaskTable.Columns.PROGRESS));
        _jobType = JobTypeEnum.discoverMatchingEnum(cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.JOB_TYPE)));
        _ticket = cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.TICKET));
        _assignedBy = cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.ASSIGNED_BY));
        _duration = cursor.getInt(cursor.getColumnIndex(JobTaskTable.Columns.DURATION_MM));
        _deadlineTime = new Date(cursor.getLong(cursor.getColumnIndex(JobTaskTable.Columns.DEADLINE_MS)));
        _asset = cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.ASSET));
        _starRating = cursor.getInt(cursor.getColumnIndex(JobTaskTable.Columns.STAR_RATING));
        _comment = cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.COMMENT));
        _updateTime = new Date(cursor.getLong(cursor.getColumnIndex(JobTaskTable.Columns.UPDATE_TIME_MS)));
        _description = cursor.getString(cursor.getColumnIndex(JobTaskTable.Columns.DESCRIPTION));
    }

    @Override
    public String getTableName() {
        return JobTaskTable.TABLE_NAME;
    }

    @Override
    public Uri getTableUri() {
        return JobTaskTable.CONTENT_URI;
    }

    public Long getId() {
        return _id;
    }

    public void setId(Long id) {
        _id = id;
    }

    public String getName() {
        return _name;
    }

    public void setName(String arg) {
        _name = arg;
    }

    public Long getParent() {
        return _parent;
    }

    public void setParent(long arg) {
        _parent = arg;
    }

    public Long getSite() {
        return _site;
    }

    public void setSite(long arg) {
        _site = arg;
    }

    public Integer getOrderNdx() {
        return _orderNdx;
    }

    public void setOrderNdx(int arg) {
        _orderNdx = arg;
    }

    public Boolean isJob() {
        return _jobFlag;
    }

    public void setJobFlag(boolean arg) {
        _jobFlag = arg;
    }

    public JobStateEnum getState() {
        return _jobState;
    }

    public void setState(JobStateEnum arg) {
        Log.i("JobTaskModel", "SETTING JOB STATE " + arg);
        _jobState = arg;
    }

    public JobTaskPriorityEnum getJobPriority() {
        return _jobTaskPriority;
    }

    public void setJobPriority(JobTaskPriorityEnum jobTaskPriority) {
        this._jobTaskPriority = jobTaskPriority;
    }

    public Integer getProgress() {
        return _progress;
    }

    public void setProgress(Integer progress) {
        this._progress = progress;
    }

    public JobTypeEnum getType() {
        return _jobType;
    }

    public void setType(JobTypeEnum arg) {
        _jobType = arg;
    }

    public String getTicket() {
        return _ticket;
    }

    public void setTicket(String arg) {
        _ticket = arg;
    }

    public String getAssignedBy() {
        return _assignedBy;
    }

    public void setAssignedBy(String arg) {
        _assignedBy = arg;
    }

    public Integer getDuration() {
        return _duration;
    }

    public void setDuration(Integer arg) {
        _duration = arg;
    }

    public Date getDeadLine() {
        return _deadlineTime;
    }

    public void setDeadLine(Date arg) {
        _deadlineTime = arg;
    }

    public String getAsset() {
        return _asset;
    }

    public void setAsset(String arg) {
        _asset = arg;
    }

    public Integer getStarRating() {
        return _starRating;
    }

    public void setStarRating(int arg) {
        _starRating = arg;
    }

    public String getComment() {
        return _comment;
    }

    public void setComment(String comment) {
        this._comment = comment;
    }

    public Date getUpdateTime() {
        return _updateTime;
    }

    public void setUpdateTime(Date arg) {
        _updateTime = arg;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String arg) {
        _description = arg;
    }

    public Boolean isStunt() {
        return _stuntFlag;
    }

    public void setStuntFlag(boolean arg) {
        _stuntFlag = arg;
    }

    public static String formatter1(Date arg) {
        //18 Jun 2011 09:53:00 -0700
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
        return sdf.format(arg);
    }

    public static String formatter2(Date arg) {
        //18 Jun 2011
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(arg);
    }

    public static String formatter3(int minutes) {
        if (minutes < 1) {
            return "0m";
        }

        int hours = minutes / 60;
        int remainder = minutes % 60;

        if (hours < 1) {
            return Long.toString(minutes) + "m";
        }

        if (remainder == 0) {
            return Long.toString(hours) + "h";
        }

        return Long.toString(hours) + "h " + Long.toString(remainder) + "m";
    }
}
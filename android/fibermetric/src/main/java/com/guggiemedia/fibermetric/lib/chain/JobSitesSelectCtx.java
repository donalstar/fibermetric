package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.JobSiteModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;

import java.util.Date;
import java.util.List;

/**
 * Created by donal on 9/22/15.
 */
public class JobSitesSelectCtx extends AbstractCmdCtx {

    private List<SiteModel> _sites;
    private List<JobTaskModel> _jobTasks;
    private List<JobSiteModel> _jobSites;

    private Date _deadlineDate;

    public JobSitesSelectCtx(Context androidContext) {
        super(CommandEnum.JOBSITES_SELECT, androidContext);
    }

    public void setSites(List<SiteModel> sites) {

        _sites = sites;
    }

    public List<SiteModel> getSites() {
        return _sites;
    }

    public void setJobTasks(List<JobTaskModel> jobTasks) {
        _jobTasks = jobTasks;
    }

    public List<JobTaskModel> getJobTasks() {
        return _jobTasks;
    }

    public List<JobSiteModel> getJobSites() {
        return _jobSites;
    }

    public void setJobSites(List<JobSiteModel> jobSites) {
        this._jobSites = jobSites;
    }

    public Date getDeadlineDate() {
        return _deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
        this._deadlineDate = deadlineDate;
    }
}


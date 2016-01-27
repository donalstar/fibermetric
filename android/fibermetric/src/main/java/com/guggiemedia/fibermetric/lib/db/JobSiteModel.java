package com.guggiemedia.fibermetric.lib.db;


/**
 *
 */
public class JobSiteModel {
    private JobTaskModel _jobTask;
    private SiteModel _site;


    public JobTaskModel getJobTask() {
        return _jobTask;
    }

    public void setJobTask(JobTaskModel jobTask) {
        this._jobTask = jobTask;
    }

    public SiteModel getSite() {
        return _site;
    }

    public void setSite(SiteModel site) {
        this._site = site;
    }

}
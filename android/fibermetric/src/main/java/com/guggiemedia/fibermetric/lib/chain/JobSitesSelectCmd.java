package com.guggiemedia.fibermetric.lib.chain;


import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.JobSiteModel;
import com.guggiemedia.fibermetric.lib.db.JobTaskModel;
import com.guggiemedia.fibermetric.lib.db.SiteModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by donal on 9/22/15.
 */
public class JobSitesSelectCmd extends AbstractCmd {
    public static final String LOG_TAG = JobSitesSelectCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final JobSitesSelectCtx ctx = (JobSitesSelectCtx) context;

        Date date = ctx.getDeadlineDate();

        List<JobTaskModel> jobTaskModels = (date == null)
                ? _contentFacade.selectJobTaskAll(context.getAndroidContext())
                : _contentFacade.selectJobTaskForDeadlineDate(date, ctx.getAndroidContext());

        List<SiteModel> siteModels = _contentFacade.selectSiteAll(ctx.getAndroidContext());

        ctx.setSites(siteModels);
        ctx.setJobTasks(jobTaskModels);

        List<JobSiteModel> jobSites = new ArrayList<>();

        for (JobTaskModel jobTask : jobTaskModels) {
            SiteModel siteModel = getSiteForJobTask(jobTask, siteModels);

            JobSiteModel jobSiteModel = new JobSiteModel();

            jobSiteModel.setJobTask(jobTask);
            jobSiteModel.setSite(siteModel);

            jobSites.add(jobSiteModel);
        }

        ctx.setJobSites(jobSites);

        return true;
    }

    /**
     * @param jobTask
     * @param siteModels
     * @return
     */
    private SiteModel getSiteForJobTask(JobTaskModel jobTask, List<SiteModel> siteModels) {
        SiteModel siteModel = null;

        for (SiteModel model : siteModels) {
            if (jobTask.getSite().equals(model.getId())) {
                siteModel = model;
                break;
            }
        }

        return siteModel;
    }
}



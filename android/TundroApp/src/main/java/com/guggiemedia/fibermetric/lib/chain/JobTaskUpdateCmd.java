package com.guggiemedia.fibermetric.lib.chain;

import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.TaskActionModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionStateEnum;
import com.guggiemedia.fibermetric.lib.db.TaskDetailModel;

/**
 * insert/update a jobtask
 */
public class JobTaskUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = JobTaskUpdateCmd.class.getName();

    private final ContentFacade _contentFacade = new ContentFacade();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        boolean result = false;

        final JobTaskUpdateCtx ctx = (JobTaskUpdateCtx) context;

        _contentFacade.updateJobTask(ctx.getJobTask(), ctx.getAndroidContext());

        for (TaskActionModel action:ctx.getActionList()) {
            action.setParent(ctx.getJobTask().getId());
            action.setState(TaskActionStateEnum.ACTIVE);

            switch (action.getAction()) {
                case AUDIO_RECORD:
                    action.setDescription("Record Noise");
                    break;
                case SCAN_BARCODE:
                    action.setDescription("Scan Barcode");
                    break;
                case CAMERA:
                    action.setDescription("Capture Image");
                    break;
            }

            _contentFacade.updateTaskAction(action, ctx.getAndroidContext());
        }

        for (TaskDetailModel detail:ctx.getDetailList()) {
            detail.setParent(ctx.getJobTask().getId());
            _contentFacade.updateTaskDetail(detail, ctx.getAndroidContext());
        }

        return returnToSender(ctx, ResultEnum.OK);
    }
}
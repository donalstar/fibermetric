package com.guggiemedia.fibermetric.lib.chain;



import com.guggiemedia.fibermetric.lib.db.DataBaseFacade;
import com.guggiemedia.fibermetric.lib.db.TaskActionModel;
import com.guggiemedia.fibermetric.lib.db.TaskActionTable;

/**
 * Select a task action by parent ID
 */
public class TaskActionUpdateCmd extends AbstractCmd {
    public static final String LOG_TAG = TaskActionUpdateCmd.class.getName();

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final TaskActionUpdateCtx ctx = (TaskActionUpdateCtx) context;

        boolean result;

        try {
            DataBaseFacade dbf = new DataBaseFacade(ctx.getAndroidContext());
            TaskActionModel model = (TaskActionModel) dbf.selectModel(ctx.getActionId(), new TaskActionTable());
            model.setState(ctx.getState());
            dbf.updateModel(model);

            result = returnToSender(ctx, ResultEnum.OK);
        } catch(Exception exception) {
            result = returnToSender(ctx, ResultEnum.FAIL);
        }

        return result;
    }
}
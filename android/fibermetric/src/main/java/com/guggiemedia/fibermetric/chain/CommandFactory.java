package com.guggiemedia.fibermetric.chain;


/**
 *
 */
public class CommandFactory {
    public static final String LOG_TAG = CommandFactory.class.getName();

    /**
     * @param context
     * @return
     */
    public static Boolean execute(AbstractCmdCtx context) {
        AbstractCmd command = null;

        switch (context.getCommand()) {

            case ADDED_ITEM_UPDATE:
                command = new AddedItemUpdateCmd();
                break;

            case HISTORY_UPDATE:
                command = new DailyRecordUpdateCmd();
                break;

            case ITEM_UPDATE:
                command = new ItemUpdateCmd();
                break;

            case SELECT_BY_ROW_ID:
                command = new SelectByRowIdCmd();
                break;

            case HISTORY_SELECT:
                command = new DailyRecordSelectCmd();
                break;

            default:
                throw new IllegalArgumentException("unknown command:" + command);
        }

        try {
            command.execute(context);

            if (context.isSuccess()) {
                return AbstractCmdCtx.CONTINUE_PROCESSING;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return AbstractCmdCtx.PROCESSING_COMPLETE;
    }

    /**
     * @param contextList
     */
    public static void execute(ContextList contextList) {
        for (AbstractCmdCtx context : contextList) {
            Boolean flag = execute(context);
            if (flag == AbstractCmdCtx.PROCESSING_COMPLETE) {
                return;
            }
        }
    }
}

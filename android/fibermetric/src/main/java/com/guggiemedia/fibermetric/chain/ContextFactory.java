package com.guggiemedia.fibermetric.chain;

import android.content.Context;


/**
 *
 */
public class ContextFactory {
    public static final String LOG_TAG = ContextFactory.class.getName();

    public static AbstractCmdCtx factory(CommandEnum command, Context androidContext) {
        AbstractCmdCtx result = null;

        switch(command) {

            case HISTORY_SELECT:
                result = new DailyRecordSelectCtx(androidContext);
                break;

            case HISTORY_UPDATE:
                result = new DailyRecordUpdateCtx(androidContext);
                break;

            case ITEM_UPDATE:
                result = new ItemUpdateCtx(androidContext);
                break;

            case SELECT_BY_ROW_ID:
                result = new SelectByRowIdCtx(androidContext);
                break;

            default:
                throw new IllegalArgumentException("unknown command:" + command);
        }

        return result;
    }
}

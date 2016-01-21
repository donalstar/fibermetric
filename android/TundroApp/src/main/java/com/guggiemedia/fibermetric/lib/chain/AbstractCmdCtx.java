package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.EventModel;


/**
 *
 */
public abstract class AbstractCmdCtx {
    public static final boolean CONTINUE_PROCESSING = true;
    public static final boolean PROCESSING_COMPLETE = false;

    protected Boolean _successFlag = false;
    protected CommandEnum _command = CommandEnum.UNKNOWN;
    protected ResultEnum _result = ResultEnum.UNKNOWN;

    protected Context _androidContext;

    public AbstractCmdCtx(CommandEnum command, Context androidContext) {
        _command = command;
        _androidContext = androidContext;
    }

    public void writeEvent(String message) {
        EventModel eventModel = new EventModel();
        eventModel.setDefault();
        eventModel.setNote("fixme");

        System.out.println("fixme:AbstractCmdCtx.writeEvent");

        //TODO Add commandenum to message

//        DataBaseFacade dataBaseFacade = new DataBaseFacade(context);
//        dataBaseFacade.insertEvent(eventModel, ctx.getAndroidContext());
    }

    public Context getAndroidContext() {
        return _androidContext;
    }

    public void setAndroidContext(Context arg) {
        _androidContext = arg;
    }

    public CommandEnum getCommand() {
        return _command;
    }

    public Boolean isSuccess() {
        return _successFlag;
    }

    public void setSuccess(boolean flag) {
        _successFlag = flag;
    }

    public ResultEnum getResultCode() {
        return _result;
    }

    public void setResultCode(ResultEnum arg) {
        _result = arg;
    }
}

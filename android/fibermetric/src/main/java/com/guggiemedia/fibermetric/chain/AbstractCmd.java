package com.guggiemedia.fibermetric.chain;


/**
 *
 */
public abstract class AbstractCmd {

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public abstract Boolean execute(AbstractCmdCtx context) throws Exception;

    /**
     *
     * @param context
     * @param resultCode
     * @return
     */
    public Boolean returnToSender(AbstractCmdCtx context, ResultEnum resultCode) {
        context.setResultCode(resultCode);

        if (resultCode == ResultEnum.OK) {
            context.setSuccess(true);
            return AbstractCmdCtx.CONTINUE_PROCESSING;
        }

        context.setSuccess(false);
        return AbstractCmdCtx.PROCESSING_COMPLETE;
    }
}

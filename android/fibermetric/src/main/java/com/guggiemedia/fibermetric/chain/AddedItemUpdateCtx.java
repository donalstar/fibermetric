package com.guggiemedia.fibermetric.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.db.AddedItemModel;


/**
 * Created by donal
 */
public class AddedItemUpdateCtx extends AbstractCmdCtx {
    private AddedItemModel _model = new AddedItemModel();

    public AddedItemUpdateCtx(Context androidContext) {
        super(CommandEnum.ADDED_ITEM_UPDATE, androidContext);
    }

    public AddedItemModel getModel() {
        return _model;
    }

    public void setModel(AddedItemModel arg) {
        _model = arg;
    }
}

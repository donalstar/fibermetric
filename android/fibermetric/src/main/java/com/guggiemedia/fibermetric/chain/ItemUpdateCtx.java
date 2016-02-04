package com.guggiemedia.fibermetric.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.db.ItemModel;


/**
 * Created by donal
 */
public class ItemUpdateCtx extends AbstractCmdCtx {
    private ItemModel _model = new ItemModel();

    public ItemUpdateCtx(Context androidContext) {
        super(CommandEnum.ITEM_UPDATE, androidContext);
    }

    public ItemModel getModel() {
        return _model;
    }

    public void setModel(ItemModel arg) {
        _model = arg;
    }
}

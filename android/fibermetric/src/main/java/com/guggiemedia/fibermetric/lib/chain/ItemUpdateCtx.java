package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ItemModel;


/**
 * Created by donal
 */
public class ItemUpdateCtx extends AbstractCmdCtx {
    private ItemModel _model = new ItemModel();
    private String _newCustodian;
    private String _siteId;

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

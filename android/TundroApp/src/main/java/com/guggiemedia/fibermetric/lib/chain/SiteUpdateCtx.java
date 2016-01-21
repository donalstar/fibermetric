package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.SiteModel;


/**
 * insert/update site command context
 */
public class SiteUpdateCtx extends AbstractCmdCtx {
    private SiteModel _model = new SiteModel();

    public SiteUpdateCtx(Context androidContext) {
        super(CommandEnum.SITE_UPDATE, androidContext);
    }

    public SiteModel getModel() {
        return _model;
    }

    public void setModel(SiteModel arg) {
        _model = arg;
    }
}

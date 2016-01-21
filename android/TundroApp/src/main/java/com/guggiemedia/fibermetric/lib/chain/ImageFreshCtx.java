package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;

import com.guggiemedia.fibermetric.lib.db.ImageModel;


/**
 * fresh image processing
 */
public class ImageFreshCtx extends AbstractCmdCtx {
    private ImageModel _model;

    public ImageFreshCtx(Context androidContext) {
        super(CommandEnum.IMAGE_FRESH, androidContext);
    }

    public ImageModel getImageModel() { return _model; }
    public void setImageModel(ImageModel arg) { _model = arg; }
}
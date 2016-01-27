package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.graphics.Bitmap;

import com.guggiemedia.fibermetric.lib.db.ImageModel;
import com.guggiemedia.fibermetric.lib.utility.ImageTypeEnum;

import java.io.File;

/**
 * retrieve bitmap from file system
 */
public class ImageGetCtx extends AbstractCmdCtx {
    private File _file;
    private ImageModel _model = null;
    private ImageTypeEnum _imageType = ImageTypeEnum.UNKNOWN;
    private Bitmap _bitmap = null;

    public ImageGetCtx(Context androidContext) {
        super(CommandEnum.IMAGE_GET, androidContext);
    }

    public File getOriginalFile() {
        return _file;
    }
    public void setOriginalFile(File arg) {
        _file = arg;
    }

    public ImageModel getImageModel() { return _model; }
    public void setImageModel(ImageModel arg) { _model = arg; }

    public ImageTypeEnum getImageType() { return _imageType; }
    public void setImageType(ImageTypeEnum arg) { _imageType = arg; }

    public Bitmap getBitmap() { return _bitmap; }
    public void setBitmap(Bitmap arg) { _bitmap = arg; }
}
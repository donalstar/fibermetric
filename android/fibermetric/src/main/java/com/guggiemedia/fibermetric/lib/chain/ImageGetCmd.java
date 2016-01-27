package com.guggiemedia.fibermetric.lib.chain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.guggiemedia.fibermetric.lib.utility.FileHelper;
import com.guggiemedia.fibermetric.lib.utility.ImageTypeEnum;

import java.io.File;

/**
 * fresh image processing
 */
public class ImageGetCmd extends AbstractCmd {

    public static final String LOG_TAG = ImageGetCmd.class.getName();


    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final ImageGetCtx ctx = (ImageGetCtx) context;

        File originalFile = null;

        ImageTypeEnum imageType = ctx.getImageType();

        if (ctx.getImageModel() == null) {
            originalFile = ctx.getOriginalFile();
        } else {
            originalFile = new File(ctx.getImageModel().getFileName());
        }

        File bitmapFile = FileHelper.getOutputPhotoFile(originalFile, imageType, ctx.getAndroidContext());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        ctx.setBitmap(BitmapFactory.decodeFile(bitmapFile.getAbsolutePath(), options));

        return returnToSender(ctx, ResultEnum.OK);
    }
}

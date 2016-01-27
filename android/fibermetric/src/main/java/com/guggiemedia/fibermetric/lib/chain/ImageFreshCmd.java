package com.guggiemedia.fibermetric.lib.chain;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.guggiemedia.fibermetric.lib.db.ContentFacade;
import com.guggiemedia.fibermetric.lib.db.ImageModel;
import com.guggiemedia.fibermetric.lib.utility.FileHelper;
import com.guggiemedia.fibermetric.lib.utility.ImageTypeEnum;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * fresh image processing
 */
public class ImageFreshCmd extends AbstractCmd {

    public static final String LOG_TAG = ImageFreshCmd.class.getName();

    public static final int THUMBNAIL_SIZE = 64;

    /**
     *
     * @param context
     * @return
     * @throws Exception
     */
    public Boolean execute(AbstractCmdCtx context) throws Exception {
        final ImageFreshCtx ctx = (ImageFreshCtx) context;

        ImageModel model = ctx.getImageModel();

        ContentFacade contentFacade = new ContentFacade();
        contentFacade.updateImage(model, ctx.getAndroidContext());

        File original = FileHelper.getOutputPhotoFile(new File(model.getFileName()), ImageTypeEnum.ORIGINAL, ctx.getAndroidContext());

        makeSubSample(original, ctx.getAndroidContext());
        makeThumbNail(original, ctx.getAndroidContext());

        return returnToSender(ctx, ResultEnum.OK);
    }

    private void makeSubSample(File original, Context context) {
        FileOutputStream fos = null;

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(original));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);

            fos = new FileOutputStream(FileHelper.getOutputPhotoFile(original, ImageTypeEnum.SUBSAMPLE, context));
            fos.write(baos.toByteArray());
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch(Exception exception) {
                // empty
            }
        }
    }

    private void makeThumbNail(File original, Context context) {
        FileOutputStream fos = null;

        try {
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(original));
            bitmap = Bitmap.createScaledBitmap(bitmap, THUMBNAIL_SIZE, THUMBNAIL_SIZE, false);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

            fos = new FileOutputStream(FileHelper.getOutputPhotoFile(original, ImageTypeEnum.THUMBNAIL, context));
            fos.write(baos.toByteArray());
        } catch(Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch(Exception exception) {
                // empty
            }
        }
    }
}

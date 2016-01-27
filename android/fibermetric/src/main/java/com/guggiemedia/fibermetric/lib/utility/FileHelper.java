package com.guggiemedia.fibermetric.lib.utility;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 *
 */
public class FileHelper {

    /**
     * Retrieve GoFactory persistent device token
     * Create if not exist
     * @param context
     * @return device token
     */
    public static String getDeviceToken(Context context) {
        String token = UUID.randomUUID().toString();

        // file location chosen to evade removal when application removed
        File newDirectory = new File(Environment.getExternalStorageDirectory(), "net.go_factory");
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }

        File tokenFile = new File(newDirectory, "tundro.token");

        boolean readFlag = false;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(tokenFile));
            token = reader.readLine();
            readFlag = true;
        } catch(Exception exception) {
            //exception.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch(Exception exception) {
                    // empty
                }
            }
        }

        if (!readFlag) {
            BufferedWriter writer = null;

            try {
                writer = new BufferedWriter(new FileWriter(tokenFile));
                writer.write(token);
            } catch(Exception exception) {
                //exception.printStackTrace();
                System.out.println("please tell Guy that getDeviceToken is broken, thank you");
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch(Exception exception) {
                        // empty
                    }
                }
            }
        }

        return token;
    }

    /**
     * Return fully qualified file name for a fresh image
     * @param imageType
     * @return
     */
    public static File getOutputPhotoFile(Date date, ImageTypeEnum imageType, Context context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
        return getOutputPhotoFile(new File(timeStamp + ".jpg"), imageType, context);
    }

    /**
     * Return fully qualified file name for an existing image
     * @param original root file
     * @param imageType
     * @param context
     * @return
     */
    public static File getOutputPhotoFile(File oldFile, ImageTypeEnum imageType, Context context) {
        return new File(context.getExternalFilesDir(imageType.toString()), oldFile.getName());
    }

    /**
     * inhibit MediaScanner
     * @param context
     */
    public static void writeNoMedia(Context context) {
        File file = new File(context.getExternalFilesDir(null), ".nomedia");

        try {
            file.createNewFile();
        } catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}

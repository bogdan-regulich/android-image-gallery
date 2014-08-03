package com.foocompany.imagegallery.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Bogdan on 03-Aug-14.
 */
public final class IOUtils {

    private IOUtils() { }

    /**
     * @return true - copy was successful, false - not.
     */
    public static boolean copy(InputStream inStream, OutputStream outStream) {

        if (inStream == null || outStream == null)
            return false;

        byte[] buffer = new byte[4 * 1024];

        int bytesCount;

        try {
            while ((bytesCount = inStream.read(buffer)) != -1) {

                outStream.write(buffer, 0, bytesCount);
            }
            return true;

        } catch (IOException ex) {
            ex.printStackTrace();

            return false;

        } finally {
            try {
                inStream.close();
                outStream.flush();
                outStream.close();
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }
}

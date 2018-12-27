package example.hs.baselibrary.utils;

import android.graphics.Bitmap;

import java.util.Collection;

public class ObjectEmptyUtils {

    private ObjectEmptyUtils(){

    }

    private static <T> boolean isNullOrEmpty(Collection<T> c) {
        return (c == null) || (c.size() == 0);
    }

    private static boolean isNullOrEmpty(String s) {
        return (s == null) || (s.length() == 0);
    }

    private static boolean isNullOrEmpty(Bitmap bmp) {
        return (bmp == null) || (bmp.isRecycled());
    }

    public static <T> boolean isNullOrEmpty(T t) {
        if (t instanceof Bitmap) {
            return isNullOrEmpty((Bitmap) t);
        } else if (t instanceof String) {
            return isNullOrEmpty((String) t);
        } else if (t instanceof Collection) {
            return isNullOrEmpty((Collection<?>) t);
        } else {
            return t == null;
        }
    }

    public static <T> boolean isNullOrEmpty(T[] arr) {
        return (arr == null) || (arr.length == 0);
    }

}

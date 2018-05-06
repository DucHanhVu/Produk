package vn.viviu.produk.utils;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class StorageUtil {
    private FirebaseStorage storage;
    private static final String BASE_URL = "gs://produkdms.appspot.com/";
    public static final int TYPE_AVATAR = 1;
    public static final int TYPE_IMAGE = 0;

    public StorageUtil() {
        storage = FirebaseStorage.getInstance();
    }

    public StorageReference getImage(String name, int type) {
        String url;
        if (type == TYPE_IMAGE)
            url = BASE_URL + "Image/" + name + ".jpg";
        else if (type == TYPE_AVATAR)
            url = BASE_URL + "Avatar/" + name;
        else
            url = null;
        return storage.getReferenceFromUrl(url);
    }
}

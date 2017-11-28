package com.icow.basiclibrary.utils;

import android.content.Context;
import android.os.storage.StorageManager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhujun on 2017/11/5
 */

public class StorageUtil {

    public static List<StorageInfo> getAllMountedStorage(Context context) {
        return getAllStorage(context,true);
    }

    public static List<StorageInfo> getAllStorage(Context context,boolean isMounted) {
        ArrayList<StorageInfo> storages = new ArrayList<StorageInfo>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);

            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new StorageInfo(path);

                    Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                    String state = (String) getVolumeState.invoke(storageManager, info.path);
                    info.state = state;

                    Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                    info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                    if(isMounted){
                        if(info.isMounted()){
                            storages.add(info);
                        }
                    }else{
                        storages.add(info);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        storages.trimToSize();
        return storages;
    }
    public static class StorageInfo {
        public String path;
        public String state;
        public boolean isRemoveable;
        public StorageInfo(String path) {
            this.path = path;
        }
        public boolean isMounted() {
            return "mounted".equals(state);
        }
        @Override
        public String toString() {
            return "StorageInfo [path=" + path + ", state=" + state
                    + ", isRemoveable=" + isRemoveable + "]";
        }
    }
}

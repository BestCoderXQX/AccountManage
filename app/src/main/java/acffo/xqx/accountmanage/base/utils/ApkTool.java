package acffo.xqx.accountmanage.base.utils;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import acffo.xqx.accountmanage.base.entity.AppEntity;

/**
 * Created by 徐启鑫 on 2017/11/24.
 */

public class ApkTool {

    static String TAG = "ApkTool";
    public static ArrayList mLocalInstallApps =null;
    public static ArrayList<AppEntity> scanLocalInstallAppList(PackageManager packageManager) {
        ArrayList myAppInfos =new ArrayList();
        try{
            ArrayList<PackageInfo> packageInfos = (ArrayList<PackageInfo>) packageManager.getInstalledPackages(0);
            for(int i =0;i < packageInfos.size();i++) {
                PackageInfo packageInfo = packageInfos.get(i);
                //过滤掉系统app
                if((ApplicationInfo.FLAG_SYSTEM& packageInfo.applicationInfo.flags) !=0) {
                    continue;
                }
                AppEntity myAppInfo =new AppEntity();
                myAppInfo.setName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
                Drawable drawable = packageInfo.applicationInfo.loadIcon(packageManager);
                if(drawable ==null) {
                    continue;
                }
                myAppInfo.setImg(drawable);
                myAppInfos.add(myAppInfo);
            }
        }catch(Exception e) {
            Log.e(TAG,"===============获取应用包信息失败");
        }
        return myAppInfos;
    }
}

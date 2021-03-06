package com.common.com.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.common.com.util.tusi.ViewUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 * 6.0权限管理
 */
public class PermissionsUtils {

    /**
     * 申请单个权限
     */
    public static void requestPermissionone(final Activity context, String permission) {

        XXPermissions.with(context)
                .permission(permission)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            // ViewUtils.makeToast(context,"获取权限成功",1000);
                        } else {
                            ViewUtils.makeToast(context, "请开启权限！", 1000);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                          /*  ViewUtils.makeToast(context, "请开启权限！", 1000);
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(context);*/
                            ViewUtils.makeToast(context, "请开启所有权限！", 1000);
                            XXPermissions.gotoPermissionSettings(context);
                        } else {
                            ViewUtils.makeToast(context, "获取权限失败", 1000);
                        }
                    }
                });
    }
    /**
     * 申请某些的权限
     */
    public static void requestPermissionmust(final Activity context, String[] permission) {

        XXPermissions.with(context)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .constantRequest()
                .permission(permission)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            // ViewUtils.makeToast(context,"获取权限成功",1000);
                        } else {
                            ViewUtils.makeToast(context, "请开启所有权限！", 1000);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                          /*  ViewUtils.makeToast(context, "请开启权限！", 1000);
                            XXPermissions.gotoPermissionSettings(context);*/
                            ViewUtils.makeToast(context, "请开启所有权限！", 1000);
                            XXPermissions.gotoPermissionSettings(context);
                        } else {
                            ViewUtils.makeToast(context, "获取权限失败", 1000);
                        }
                    }
                });
    }

    /**
     * 检查某个权限是否全部授予了
     */
    public static boolean isHasPermission(final Activity context, String permission) {
        if (XXPermissions.isHasPermission(context, permission)) {

            return true;
        } else {
            return false;

        }
    }



    /**
     * 申请基本的权限
     */
    public static void requestPermissionselect(final Activity context) {

        XXPermissions.with(context)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
                .permission(Permission.CALL_PHONE, Permission.WRITE_EXTERNAL_STORAGE,
                        Permission.READ_EXTERNAL_STORAGE, Permission.CAMERA)
                //WRITE_EXTERNAL_STORAGE文件写权限，READ_EXTERNAL_STORAGE文件读权限，CAMERA相机相册权限
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            // ViewUtils.makeToast(context,"获取权限成功",1000);
                        } else {
                            //ViewUtils.makeToast(context, "请开启权限！", 1000);
                        }
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                           /* //如果是被永久拒绝就跳转到应用权限系统设置页面
                            ViewUtils.makeToast(context, "请开启权限！", 1000);
                            XXPermissions.gotoPermissionSettings(context);*/
                        } else {
                            ViewUtils.makeToast(context, "获取权限失败", 1000);
                        }
                    }
                });
    }
    /**
     * 获取相机权限
     * @param activity
     * @return
     */
    public static boolean CAMERA(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA},
                    101);
            return false;
        }
        return true;
    }
    /**
     * 拨打电话权限
     *
     * @param activity
     * @return
     */
    public static boolean CALL_PHONE(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    102);
            return false;
        }

        return true;
    }

    /**
     *
     * @param //安装未知来源的应用权限
     * @return
     */
    public static boolean INSTALL_PACKAGES(Activity activity) {
        int checkSelfPermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.REQUEST_INSTALL_PACKAGES);
        if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.REQUEST_INSTALL_PACKAGES},
                    999);
            return false;
        } else {
            return true;
        }
    }

}

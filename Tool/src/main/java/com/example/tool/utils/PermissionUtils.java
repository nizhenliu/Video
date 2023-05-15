package com.example.tool.utils;

import android.os.Build;

import com.tbruyelle.rxpermissions2.RxPermissions;

import androidx.fragment.app.FragmentActivity;
import io.reactivex.functions.Consumer;

/**
 * @author hahajing 企鹅：444511958
 * @version 1.0.0
 * @createDate 2022/4/15 10:49
 * @description
 * @updateUser hahajing
 * @updateDate 2022/4/15 10:49
 * @updateRemark
 */
public class PermissionUtils {
    private PermissionUtils(){}
    static class HOLDER{
        private static PermissionUtils INSTNACE=new PermissionUtils();
    }
    public static PermissionUtils getInstance(){
        return HOLDER.INSTNACE;
    }

    /**
     * 请求权限
     */
    public void requestAppNeedPermission(FragmentActivity activity,PermissionCallback callback,String... premissions) {
        if (Build.VERSION.SDK_INT>=23){
            RxPermissions rxPermissions=new RxPermissions(activity);
            rxPermissions.request(
                   premissions).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean result) throws Exception {
                    if (result){
                        callback.onSuccess();
                    }else {
                        callback.onFailed();
                    }
                }
            });
        }

    }

    public interface PermissionCallback{
        void onSuccess();
        void onFailed();
    }
}

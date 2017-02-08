package com.xiandong.fst.tools.dbmanager;

import android.util.Log;

import com.xiandong.fst.model.bean.UserBean;
import com.xiandong.fst.model.entity.UserEntity;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

/**
 * 数据库管理
 */

public class AppDbManager {

    private static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig() {
        daoConfig = new DbManager.DaoConfig();
        daoConfig.setDbName("fst_db") //设置数据库名
                .setDbVersion(1) //设置数据库版本,每次启动应用时将会检查该版本号,
                //发现数据库版本低于这里设置的值将进行数据库升级并触发DbUpgradeListener
                .setAllowTransaction(true)//设置是否开启事务,默认为false关闭事务
//                .setDbDir(new File("/data/data/"));//数据库路径 不写默认app私有目录
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                }).setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

            }
        });
        return daoConfig;
    }

    public static void saveUserData(UserBean userBean, boolean isLogIn) {
        UserBean.UserEntity user = userBean.getUser();
        DbManager db = x.getDb(getDaoConfig());
        UserEntity entity = new UserEntity();
        entity.setUserId(user.getId());
        entity.setUserFlag(user.getFlag());
        entity.setUserImg(user.getImg());
        entity.setUserJiFen(user.getJifen());
        entity.setUserName(user.getNicheng());
        entity.setUserPassword(user.getPassword());
        entity.setUserPayPsw(user.getZfpassword());
        entity.setUserPhone(user.getPhone());
        entity.setUserPosition(user.getPosition());
        entity.setUserReferer(user.getReferer());
        entity.setUserRenZheng(user.getRenzheng());
        entity.setUserRestTime(user.getReferertime());
        entity.setUserTime(user.getTime());
        entity.setUserBalance(user.getYue());
        entity.setUserCome(user.getCome());
        entity.setUserTags(user.getTags());
        entity.setUserTongXunLu(user.getTongxunlu());
        entity.setUserWXOpenId(user.getOpenid());
        entity.setUserWXUnionid(user.getAccesstoken());
        entity.setUserLogIn(isLogIn);
        try {
            db.saveOrUpdate(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    public static void saveLogInData(boolean isLogIn) {
        DbManager db = x.getDb(getDaoConfig());
        UserEntity entity = getLastUser();
        entity.setUserLogIn(isLogIn);
        try {
            db.saveOrUpdate(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static void saveUserChange(UserEntity entity) {
        DbManager db = x.getDb(getDaoConfig());
        try {
            db.saveOrUpdate(entity);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    public static UserEntity getLastUser() {
        DbManager db = x.getDb(getDaoConfig());
        try {
            List<UserEntity> list = db.findAll(UserEntity.class);
            if (list != null && list.size() > 0) {
//                for (UserEntity user : list) {
//                    Log.d("AppDbManager", user.getUserName() + "::" + user.getUserId() + "::" + user.isUserLogIn());
//                }
                return list.get(list.size() - 1);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserId() {
        if (getLastUser() != null)
            return getLastUser().getUserId();
        else
            return "";
    }
}

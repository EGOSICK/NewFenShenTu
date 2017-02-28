package com.xiandong.fst.tools.chat;

import android.content.Context;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.easeui.DemoHelper;
import com.hyphenate.exceptions.HyphenateException;
import com.xiandong.fst.application.BaseApplication;
import com.xiandong.fst.tools.dbmanager.AppDbManager;
import com.xiandong.fst.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/1/22.
 */

public class ChatTools {

    public static void initChat(final Context context) {
        EMClient.getInstance().login(AppDbManager.getUserId(), "000000"
                , new EMCallBack() {//回调
                    @Override
                    public void onSuccess() {

                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        if (!StringUtil.isEmpty(AppDbManager.getLastUser().getUserName())) {
                            EMClient.getInstance().updateCurrentUserNick(AppDbManager.getLastUser().getUserName());
                        }
                        EMClient.getInstance().addConnectionListener(new EMConnectionListener() {
                            @Override
                            public void onConnected() {

                            }

                            @Override
                            public void onDisconnected(int i) {

                            }
                        });

                        DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(AppDbManager.getLastUser().getUserName());
                        DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(AppDbManager.getLastUser().getUserImg());
                        DemoHelper.getInstance().setCurrentUserName(AppDbManager.getUserId()); // 环信Id
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }

                    @Override
                    public void onError(int code, String message) {

                    }
                });
    }


    public interface chooseGroupComplete {
        void complete(String s);
    }

    /* 创建群组
    * @param groupName 群组名称
    * @param desc 群组简介
    * @param allMembers 群组初始成员，如果只有自己传null即可
    * @param reason 邀请成员加入的reason
    * @param option 群组类型选项，可以设置群组最大用户数(默认200)及群组类型@see {@link EMGroupStyle}
    * @return 创建好的group
    * @throws HyphenateException
    **/
    public static String creatGroup(String groupName, String... user) {
        String id = null;

        EMGroupManager.EMGroupOptions option = new EMGroupManager.EMGroupOptions();
        option.maxUsers = 100;
        option.style = EMGroupManager.EMGroupStyle.EMGroupStylePublicOpenJoin;
        try {
            EMGroup group = EMClient.getInstance().groupManager().createGroup(groupName,
                    "", user, "reason", option);
            id = group.getGroupId();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static void chooseGroup(final String name,
                                   final chooseGroupComplete complete, final String... user) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //从服务器获取自己加入的和创建的群组列表，此api获取的群组sdk会自动保存到内存和db。
                try {
                    List<EMGroup> grouplist = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();//需异步处理
                    List<String> groupNames = new ArrayList<>();
                    for (EMGroup group : grouplist) {
                        groupNames.add(group.getGroupName());
                    }
                    if (groupNames.contains(name)) {
                        for (int i = 0; i < grouplist.size(); i++) {
                            if (name.equals(grouplist.get(i).getGroupName())) {
                                if (complete != null) {
                                    Log.d("ChatTools", grouplist.get(i).getGroupId());
                                    complete.complete(grouplist.get(i).getGroupId());
                                }
                            }
                        }
                    } else {
                        if (complete != null)
                            complete.complete(creatGroup(name, user));
                    }
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}

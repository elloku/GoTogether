package com.xlman.factory.presenter.message;

import com.xlman.factory.model.db.Group;
import com.xlman.factory.model.db.Message;
import com.xlman.factory.model.db.User;
import com.xlman.factory.model.db.view.MemberUserModel;
import com.xlman.factory.presenter.BaseContract;

import java.util.List;

/**
 * create by xlman on 13:46 2018/2/15
 */
public interface ChatContract {
    interface Presenter extends BaseContract.Presenter {
        // 发送文字
        void pushText(String content);

        // 发送语音
        void pushAudio(String path, long time);

        // 发送图片
        void pushImages(String[] paths);

        // 重新发送一个消息，返回是否调度成功
        boolean rePush(Message message);
    }

    // 界面的基类
    interface View<InitModel> extends BaseContract.RecyclerView<Presenter, Message> {
        // 初始化的Model
        void onInit(InitModel model);
    }

    // 人聊天的界面
    interface UserView extends View<User> {

    }

    // 群聊天的界面
    interface GroupView extends View<Group> {
        // 显示管理员菜单
        void showAdminOption(boolean isAdmin);

        // 初始化成员信息
        void onInitGroupMembers(List<MemberUserModel> members, long moreCount);
    }
}

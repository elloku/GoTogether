package com.xlman.factory.presenter.contarct;

import com.xlman.factory.model.db.User;
import com.xlman.factory.presenter.BaseContract;

/**
 *
 * Created by xlman on 2018/2/11.
 */
public interface PersonalContract {
    interface Presenter extends BaseContract.Presenter {
        User getUserPersonal();// 获取用户信息
        void update(String desc);// 更新用户的描述
    }

    interface View extends BaseContract.View<Presenter> {
        String getUserId();

        // 加载数据完成
        void onLoadDone(User user);

        // 是否发起聊天
        void allowSayHello(boolean isAllow);

        // 设置关注状态
        void setFollowStatus(boolean isFollow);

        // 更新成功回调
        void updateSucceed();
    }
}

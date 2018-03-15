package com.xlman.factory.presenter.contarct;

import com.xlman.factory.model.card.UserCard;
import com.xlman.factory.presenter.BaseContract;

/**
 * 关注的接口定义
 *
 * Created by xlman on 2018/2/10.
 */

public interface FollowContract {
    // 任务调度者
    interface Presenter extends BaseContract.Presenter {
        // 关注一个人
        void follow(String id);
    }

    interface View extends BaseContract.View<Presenter> {
        // 成功的情况下返回一个用户的信息
        void onFollowSucceed(UserCard userCard);
    }
}

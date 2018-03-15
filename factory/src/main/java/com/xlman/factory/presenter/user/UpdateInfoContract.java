package com.xlman.factory.presenter.user;


import com.xlman.factory.presenter.BaseContract;

/**
 * 更新用户信息的基本的契约
 *
 */
public interface UpdateInfoContract {
    interface Presenter extends BaseContract.Presenter {
        // 更新
        void update(String photoFilePath, String desc, boolean isMan);
    }

    interface View extends BaseContract.View<Presenter> {
        // 回调成功
        void updateSucceed();
    }
}

package com.xlman.factory.presenter.group;

import com.xlman.factory.model.db.view.MemberUserModel;
import com.xlman.factory.presenter.BaseContract;

/**
 * create by xlman on 13:30 2018/3/3
 */
public interface GroupMembersContract {
    interface Presenter extends BaseContract.Presenter {
        // 具有一个刷新的方法
        void refresh();
    }

    // 界面
    interface View extends BaseContract.RecyclerView<Presenter, MemberUserModel> {
        // 获取群的ID
        String getGroupId();
    }
}

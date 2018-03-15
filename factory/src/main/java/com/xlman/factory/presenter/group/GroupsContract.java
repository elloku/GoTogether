package com.xlman.factory.presenter.group;

import com.xlman.factory.model.db.Group;
import com.xlman.factory.presenter.BaseContract;

/**
 * 我的群列表契约
 *
 * create by xlman on 18:33 2018/3/1
 */
public interface GroupsContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Group> {

    }
}

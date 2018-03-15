package com.xlman.factory.presenter.message;

import com.xlman.factory.model.db.Session;
import com.xlman.factory.presenter.BaseContract;

/**
 * create by xlman on 16:37 2018/2/15
 */
public interface SessionContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, Session> {

    }
}

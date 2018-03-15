package com.xlman.factory.presenter.contarct;

import com.xlman.factory.model.db.User;
import com.xlman.factory.presenter.BaseContract;

/**
 * Created by xlman on 2018/2/11.
 */

public interface ContactContract {
    // 什么都不需要额外定义，开始就是调用start即可
    interface Presenter extends BaseContract.Presenter {

    }

    // 都在基类完成了
    interface View extends BaseContract.RecyclerView<Presenter, User> {

    }
}

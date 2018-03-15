package com.xlman.factory.presenter.group;

import com.xlman.factory.model.Author;
import com.xlman.factory.presenter.BaseContract;

/**
 * 拼车信息创建的方法接口
 *
 * create by xlman on 10:38 2018/3/1
 */
public interface GroupCreateContract {
    interface Presenter extends BaseContract.Presenter {
        // 创建
        void create(String name, String desc, String time);

        // 更改一个Model的选中状态
        void changeSelect(ViewModel model, boolean isSelected);
    }

    interface View extends BaseContract.RecyclerView<Presenter, ViewModel> {
        // 创建成功
        void onCreateSucceed();
    }

    class ViewModel {
        // 用户信息
        public Author author;
        // 是否选中
        public boolean isSelected;
    }

}

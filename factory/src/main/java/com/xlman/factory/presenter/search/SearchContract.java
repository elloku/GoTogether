package com.xlman.factory.presenter.search;

import com.xlman.factory.model.card.GroupCard;
import com.xlman.factory.model.card.UserCard;
import com.xlman.factory.presenter.BaseContract;

import java.util.List;

/**
 *
 * Created by xlman on 2018/2/10.
 */

public interface SearchContract {
    interface Presenter extends BaseContract.Presenter {
        // 搜索内容
        void search(String content);
    }

    // 搜索人的界面
    interface UserView extends BaseContract.View<Presenter> {
        void onSearchDone(List<UserCard> userCards);
    }

    // 搜索群的界面
    interface GroupView extends BaseContract.View<Presenter> {
        void onSearchDone(List<GroupCard> groupCards);
    }
}

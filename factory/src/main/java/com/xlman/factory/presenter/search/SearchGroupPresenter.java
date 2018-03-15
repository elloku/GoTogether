package com.xlman.factory.presenter.search;

import com.xlman.factory.data.DataSource;
import com.xlman.factory.data.helper.GroupHelper;
import com.xlman.factory.model.card.GroupCard;
import com.xlman.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.List;

import retrofit2.Call;

/**
 * 搜索群的逻辑实现
 *
 * Created by xlman on 2018/2/10.
 */
public class SearchGroupPresenter extends BasePresenter<SearchContract.GroupView>
        implements SearchContract.Presenter, DataSource.Callback<List<GroupCard>> {
    private Call searchCall;
    public SearchGroupPresenter(SearchContract.GroupView view) {
        super(view);
    }

    @Override
    public void search(String content) {
        start();

        Call call = searchCall;
        if (call != null && !call.isCanceled()) {
            // 如果有上一次的请求，并且没有取消，
            // 则调用取消请求操作
            call.cancel();
        }
        searchCall = GroupHelper.search(content, this);
    }


    @Override
    public void onDataNotAvailable(final int strRes) {
        // 搜索失败
        final SearchContract.GroupView view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.showError(strRes);
                }
            });
        }
    }

    @Override
    public void onDataLoaded(final List<GroupCard> groupCards) {
        // 搜索成功
        final SearchContract.GroupView view = getView();
        if (view != null) {
            Run.onUiAsync(new Action() {
                @Override
                public void call() {
                    view.onSearchDone(groupCards);
                }
            });
        }
    }
}

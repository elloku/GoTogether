package com.xlman.factory.presenter.group;

import android.support.v7.util.DiffUtil;

import com.xlman.factory.data.group.GroupsDataSource;
import com.xlman.factory.data.group.GroupsRepository;
import com.xlman.factory.data.helper.GroupHelper;
import com.xlman.factory.model.db.Group;
import com.xlman.factory.presenter.BaseSourcePresenter;
import com.xlman.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 * 我的群组Presenter
 *
 * create by xlman on 18:51 2018/3/1
 */
public class GroupsPresenter extends BaseSourcePresenter<Group, Group,
        GroupsDataSource, GroupsContract.View> implements GroupsContract.Presenter {

    public GroupsPresenter(GroupsContract.View view) {
        super(new GroupsRepository(), view);
    }

    @Override
    public void start() {
        super.start();

        // 加载网络数据, 以后可以优化到下拉刷新中
        // 只有用户下拉进行网络请求刷新
        GroupHelper.refreshGroups();
    }

    @Override
    public void onDataLoaded(List<Group> groups) {
        final GroupsContract.View view = getView();
        if (view == null)
            return;

        // 对比差异
        List<Group> old = view.getRecyclerAdapter().getItems();
        DiffUiDataCallback<Group> callback = new DiffUiDataCallback<>(old, groups);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);

        // 界面刷新
        refreshData(result, groups);
    }
}

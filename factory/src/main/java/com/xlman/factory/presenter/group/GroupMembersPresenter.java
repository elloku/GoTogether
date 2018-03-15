package com.xlman.factory.presenter.group;

import com.xlman.factory.Factory;
import com.xlman.factory.data.helper.GroupHelper;
import com.xlman.factory.model.db.view.MemberUserModel;
import com.xlman.factory.presenter.BaseRecyclerPresenter;

import java.util.List;

/**
 * create by xlman on 13:31 2018/3/3
 */
public class GroupMembersPresenter extends BaseRecyclerPresenter<MemberUserModel, GroupMembersContract.View>
        implements GroupMembersContract.Presenter {

    public GroupMembersPresenter(GroupMembersContract.View view) {
        super(view);
    }

    @Override
    public void refresh() {
        // 显示Loading
        start();

        // 异步加载
        Factory.runOnAsync(loader);
    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {
            GroupMembersContract.View view = getView();
            if (view == null)
                return;

            String groupId = view.getGroupId();

            // 传递数量为-1 代表查询所有
            List<MemberUserModel> models = GroupHelper.getMemberUsers(groupId, -1);

            refreshData(models);
        }
    };
}

package com.xlman.factory.presenter.group;

import android.text.TextUtils;

import com.xlman.factory.Factory;
import com.xlman.factory.R;
import com.xlman.factory.data.DataSource;
import com.xlman.factory.data.helper.GroupHelper;
import com.xlman.factory.data.helper.UserHelper;
import com.xlman.factory.model.api.group.GroupCreateModel;
import com.xlman.factory.model.card.GroupCard;
import com.xlman.factory.model.db.view.UserSampleModel;
import com.xlman.factory.presenter.BaseRecyclerPresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 群创建界面的Presenter
 *
 * create by xlman on 10:39 2018/3/1
 */
public class GroupCreatePresenter extends BaseRecyclerPresenter<GroupCreateContract.ViewModel, GroupCreateContract.View>
        implements GroupCreateContract.Presenter, DataSource.Callback<GroupCard> {

    private Set<String> users = new HashSet<>();

    public GroupCreatePresenter(GroupCreateContract.View view) {
        super(view);
    }

    @Override
    public void start() {
        super.start();
        // 加载
        Factory.runOnAsync(loader);
    }

    @Override
    public void create(final String name, final String desc, final String time) {
        GroupCreateContract.View view = getView();
        view.showLoading();
        // 判断参数
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(desc) ||
                TextUtils.isEmpty(time) || users.size() == 0) {
            view.showError(R.string.label_group_create_invalid);
            return;
        }

        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                // 进行网络请求
                GroupCreateModel model = new GroupCreateModel(name, desc, time, users);
                GroupHelper.create(model, GroupCreatePresenter.this);
            }
        });
    }

    @Override
    public void changeSelect(GroupCreateContract.ViewModel model, boolean isSelected) {
        if (isSelected)
            users.add(model.author.getId());
        else
            users.remove(model.author.getId());
    }

    private Runnable loader = new Runnable() {
        @Override
        public void run() {
            List<UserSampleModel> sampleModels = UserHelper.getSampleContact();
            List<GroupCreateContract.ViewModel> models = new ArrayList<>();
            for (UserSampleModel sampleModel : sampleModels) {
                GroupCreateContract.ViewModel viewModel = new GroupCreateContract.ViewModel();
                viewModel.author = sampleModel;
                models.add(viewModel);
            }
            refreshData(models);
        }
    };

    @Override
    public void onDataLoaded(GroupCard groupCard) {
        // 成功
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupCreateContract.View view = getView();
                if (view != null) {
                    view.onCreateSucceed();
                }
            }
        });
    }

    @Override
    public void onDataNotAvailable(final int strRes) {
        // 失败情况
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                GroupCreateContract.View view = getView();
                if (view != null) {
                    view.showError(strRes);
                }
            }
        });
    }
}

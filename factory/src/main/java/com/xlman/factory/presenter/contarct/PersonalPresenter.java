package com.xlman.factory.presenter.contarct;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import com.xlman.factory.Factory;
import com.xlman.factory.R;
import com.xlman.factory.data.DataSource;
import com.xlman.factory.data.helper.UserHelper;
import com.xlman.factory.model.api.user.UserUpdateModel;
import com.xlman.factory.model.card.UserCard;
import com.xlman.factory.model.db.User;
import com.xlman.factory.persistence.Account;
import com.xlman.factory.presenter.BasePresenter;

import net.qiujuer.genius.kit.handler.Run;
import net.qiujuer.genius.kit.handler.runable.Action;

/**
 * Created by xlman on 2018/2/11.
 */
public class PersonalPresenter extends BasePresenter<PersonalContract.View>
        implements PersonalContract.Presenter, DataSource.Callback<UserCard> {

    private User user;

    public PersonalPresenter(PersonalContract.View view) {
        super(view);
    }


    @Override
    public void start() {   super.start();

        // 个人界面用户数据优先从网络拉取
        Factory.runOnAsync(new Runnable() {
            @Override
            public void run() {
                PersonalContract.View view = getView();
                if (view != null) {
                    String id = view.getUserId();
                    User user = UserHelper.searchFirstOfNet(id);
                    onLoaded(user);
                }
            }
        });
    }

    @Override
    public void update(final String desc) {
        start();
        final PersonalContract.View view = getView();
        if (TextUtils.isEmpty(desc)) {
            view.showError(R.string.data_account_update_invalid_desc);
        } else {
            Factory.runOnAsync(new Runnable() {
                @Override
                public void run() {
                    // 构建Model
                    UserUpdateModel model = new UserUpdateModel("", "", desc, 0);
                    // 进行网络请求，上传
                    UserHelper.update(model, PersonalPresenter.this);
                }
            });
        }
    }

    // 进行界面的设置
    private void onLoaded(final User user) {
        this.user = user;
        // 是否就是我自己
        final boolean isSelf = user.getId().equalsIgnoreCase(Account.getUserId());
        // 是否已经关注
        final boolean isFollow = isSelf || user.isFollow();
        // 已经关注同时不是自己才能聊天
        final boolean allowSayHello = isFollow && !isSelf;

        // 切换到Ui线程
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                    final PersonalContract.View view = getView();
                    if (view == null)
                        return;
                    view.onLoadDone(user);
                    view.setFollowStatus(isFollow);
                    view.allowSayHello(allowSayHello);
            }
        });
    }

    @Override
    public User getUserPersonal() {
        return user;
    }

    @Override
    public void onDataLoaded(UserCard userCard) {
        final PersonalContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.updateSucceed();
            }
        });
    }

    @Override
    public void onDataNotAvailable(@StringRes final int strRes) {
        final PersonalContract.View view = getView();
        if (view == null)
            return;
        // 强制执行在主线程中
        Run.onUiAsync(new Action() {
            @Override
            public void call() {
                view.showError(strRes);
            }
        });
    }
}
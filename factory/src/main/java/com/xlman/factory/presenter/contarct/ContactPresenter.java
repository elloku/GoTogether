package com.xlman.factory.presenter.contarct;

import android.support.v7.util.DiffUtil;

import com.xlman.common.widget.recycler.RecyclerAdapter;
import com.xlman.factory.data.DataSource;
import com.xlman.factory.data.helper.UserHelper;
import com.xlman.factory.data.user.ContactDataSource;
import com.xlman.factory.data.user.ContactRepository;
import com.xlman.factory.model.db.User;
import com.xlman.factory.presenter.BaseSourcePresenter;
import com.xlman.factory.utils.DiffUiDataCallback;

import java.util.List;

/**
 *
 * Created by xlman on 2018/2/11.
 */
public class ContactPresenter extends BaseSourcePresenter<User, User, ContactDataSource, ContactContract.View>
        implements ContactContract.Presenter, DataSource.SucceedCallback<List<User>> {


    public ContactPresenter(ContactContract.View view) {
        // 初始化数据仓库
        super(new ContactRepository(), view);
    }

    @Override
    public void start() {
        super.start();
        // 加载网络数据
        UserHelper.refreshContacts();
    }

    // 运行到这里的时候是子线程
    @Override
    public void onDataLoaded(List<User> users) {
        // 无论怎么操作，数据变更，最终都会通知到这里来
        final ContactContract.View view = getView();
        if (view == null)
            return;
        RecyclerAdapter<User> adapter = view.getRecyclerAdapter();
        List<User> old = adapter.getItems();
        // 进行数据对比
        DiffUtil.Callback callback = new DiffUiDataCallback<>(old, users);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        // 调用基类方法进行界面刷新
        refreshData(result, users);
    }
}


package com.xlman.factory.data.user;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xlman.factory.data.BaseDbRepository;
import com.xlman.factory.data.DataSource;
import com.xlman.factory.model.db.User;
import com.xlman.factory.model.db.User_Table;
import com.xlman.factory.persistence.Account;

import java.util.List;

/**
 * 联系人仓库
 * <p>
 * Created by xlman on 2018/2/12.
 */
public class ContactRepository extends BaseDbRepository<User> implements ContactDataSource {

    @Override
    public void load(DataSource.SucceedCallback<List<User>> callback) {
        super.load(callback);

        // 加载本地数据库数据
        SQLite.select()
                .from(User.class)
                .where(User_Table.isFollow.eq(true))
                .and(User_Table.id.notEq(Account.getUserId()))
                .orderBy(User_Table.name, true)
                .limit(100)
                .async()
                .queryListResultCallback(this)
                .execute();
    }

    @Override
    protected boolean isRequired(User user) {
        return user.isFollow() && !user.getId().equals(Account.getUserId());
    }
}

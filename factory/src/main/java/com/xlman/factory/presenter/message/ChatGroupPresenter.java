package com.xlman.factory.presenter.message;

import com.xlman.factory.data.helper.GroupHelper;
import com.xlman.factory.data.message.MessageGroupRepository;
import com.xlman.factory.model.db.Group;
import com.xlman.factory.model.db.Message;
import com.xlman.factory.model.db.view.MemberUserModel;
import com.xlman.factory.persistence.Account;

import java.util.List;

/**
 * 群聊天的逻辑
 *
 * create by xlman on 13:56 2018/2/15
 */
public class ChatGroupPresenter extends ChatPresenter<ChatContract.GroupView>
        implements ChatContract.Presenter {

    public ChatGroupPresenter(ChatContract.GroupView view, String receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageGroupRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_GROUP);
    }

    @Override
    public void start() {
        super.start();

        // 拿群的信息
        Group group = GroupHelper.findFromLocal(mReceiverId);
        if (group != null) {
            // 初始化操作
            ChatContract.GroupView view = getView();

            boolean isAdmin = Account.getUserId().equalsIgnoreCase(group.getOwner().getId());
            view.showAdminOption(isAdmin);

            // 基础信息初始化
            view.onInit(group);

            // 成员初始化
            List<MemberUserModel> models = group.getLatelyGroupMembers();
            final long memberCount = group.getGroupMemberCount();
            // 没有显示的成员的数量
            long moreCount = memberCount - models.size();
            view.onInitGroupMembers(models, moreCount);
        }
    }
}

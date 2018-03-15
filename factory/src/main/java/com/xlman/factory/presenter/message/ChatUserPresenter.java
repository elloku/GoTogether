package com.xlman.factory.presenter.message;

import com.xlman.factory.data.helper.UserHelper;
import com.xlman.factory.data.message.MessageRepository;
import com.xlman.factory.model.db.Message;
import com.xlman.factory.model.db.User;

/**
 * create by xlman on 13:53 2018/2/15
 */
public class ChatUserPresenter extends ChatPresenter<ChatContract.UserView>
        implements ChatContract.Presenter {
    public ChatUserPresenter(ChatContract.UserView view, String receiverId) {
        // 数据源，View，接收者，接收者的类型
        super(new MessageRepository(receiverId), view, receiverId, Message.RECEIVER_TYPE_NONE);
    }

    @Override
    public void start() {
        super.start();

        // 从本地拿这个人的信息
        User receiver = UserHelper.findFromLocal(mReceiverId);
        getView().onInit(receiver);
    }
}

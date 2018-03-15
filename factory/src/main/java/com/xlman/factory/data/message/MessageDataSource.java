package com.xlman.factory.data.message;

import com.xlman.factory.data.DbDataSource;
import com.xlman.factory.model.db.Message;

/**
 * 消息的数据源定义，他的实现是：MessageRepository, MessageGroupRepository
 * 关注的对象是Message表
 *
 *
 * create by xlman on 13:49 2018/2/15
 */
public interface MessageDataSource extends DbDataSource<Message> {
}

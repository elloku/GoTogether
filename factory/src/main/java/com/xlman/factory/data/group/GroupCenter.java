package com.xlman.factory.data.group;

import com.xlman.factory.model.card.GroupCard;
import com.xlman.factory.model.card.GroupMemberCard;

/**
 * 群中心的接口定义
 *
 * Created by xlman on 2018/2/12.
 */

public interface GroupCenter {
    // 群卡片的处理
    void dispatch(GroupCard... cards);

    // 群成员的处理
    void dispatch(GroupMemberCard... cards);
}

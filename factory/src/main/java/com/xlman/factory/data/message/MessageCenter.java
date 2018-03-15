package com.xlman.factory.data.message;

import com.xlman.factory.model.card.MessageCard;

/**
 * Created by xlman on 2018/2/12.
 */

public interface MessageCenter {
    void dispatch(MessageCard... cards);
}

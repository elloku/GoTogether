package com.xlman.factory.model;

/**
 * 基础用户接口
 *
 * Created by xlman on 2018/2/11.
 */
public interface Author {
    String getId();

    void setId(String id);

    String getName();

    void setName(String name);

    String getPortrait();

    void setPortrait(String portrait);
}

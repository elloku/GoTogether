package com.xlman.factory.model.api.group;

import java.util.HashSet;
import java.util.Set;

/**
 * create by xlman on 18:21 2018/3/1
 */
public class GroupMemberAddModel {
    private Set<String> users = new HashSet<>();

    public GroupMemberAddModel(Set<String> users) {
        this.users = users;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}

package ru.mera.lib.model;

import java.util.List;

public class RoleModel {
    private List<String> roles;

    public RoleModel(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

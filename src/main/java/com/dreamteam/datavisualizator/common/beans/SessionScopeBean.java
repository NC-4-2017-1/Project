package com.dreamteam.datavisualizator.common.beans;

import com.dreamteam.datavisualizator.models.User;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Serializable;


@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionScopeBean implements Serializable {

    private CustomerProject customerProject;
    private File file;
    private User user;

    public SessionScopeBean() {
        this.customerProject = new CustomerProject();
    }

    public CustomerProject getCustomerProject() {
        return customerProject;
    }

    public void setCustomerProject(CustomerProject customerProject) {
        this.customerProject = customerProject;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

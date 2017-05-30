package com.dreamteam.datavisualizator.common.beans;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionScopeBean {

    private CustomerProject customerProject;
    private File file;

    public SessionScopeBean() {
        this.customerProject = new CustomerProject();
    }

    public CustomerProject getCustomerProject() {
        return customerProject;
    }

    public void setCustomerProject(CustomerProject customerProject) {
        this.customerProject = customerProject;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

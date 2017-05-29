package com.dreamteam.datavisualizator.common.beans;

import java.io.File;

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

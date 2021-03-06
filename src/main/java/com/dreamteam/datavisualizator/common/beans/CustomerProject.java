package com.dreamteam.datavisualizator.common.beans;

import com.dreamteam.datavisualizator.common.dateconverter.DateFormat;
import com.dreamteam.datavisualizator.models.Graphic;
import com.dreamteam.datavisualizator.models.ProjectTypes;
import com.dreamteam.datavisualizator.models.impl.DataVisualizationProject;

import java.io.File;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class CustomerProject implements Serializable {

    private ProjectTypes type;
    private String name;
    private String description;
    private BigInteger author;
    private List<Graphic> graphics;
    private Map<BigInteger, String> selectors;
    BigInteger idProject;
    private Graphic graphic;
    private String serverName;
    private String port;
    private String sid;
    private String userName;
    private String password;
    private File file;
    private String fileType;
    private DateFormat dateFormat;


    private DataVisualizationProject savedProject;

    public DataVisualizationProject getSavedProject() {
        return savedProject;
    }

    public void setSavedProject(DataVisualizationProject savedProject) {
        this.savedProject = savedProject;
    }

    public DateFormat getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public BigInteger getIdProject() {
        return idProject;
    }

    public void setIdProject(BigInteger idProject) {
        this.idProject = idProject;
    }

    public ProjectTypes getType() {
        return type;
    }

    public void setType(ProjectTypes type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getAuthor() {
        return author;
    }

    public void setAuthor(BigInteger author) {
        this.author = author;
    }

    public List<Graphic> getGraphics() {
        return graphics;
    }

    public void setGraphics(List<Graphic> graphics) {
        this.graphics = graphics;
    }

    public Map<BigInteger, String> getSelectors() {
        return selectors;
    }

    public void setSelectors(Map<BigInteger, String> selectors) {
        this.selectors = selectors;
    }

    public Graphic getGraphic() {
        return graphic;
    }

    public void setGraphic(Graphic graphic) {
        this.graphic = graphic;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

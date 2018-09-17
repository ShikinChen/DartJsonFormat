package me.shiki.dartjsonformat.model;

import java.util.Set;

public class MustacheEntity {
    private String fileName;
    private String dir;
    private Set<ClsEntity> clsEntities;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MustacheEntity(String fileName, Set<ClsEntity> clsEntities) {
        this.fileName = fileName;
        this.clsEntities = clsEntities;
    }

    public Set<ClsEntity> getClsEntities() {
        return clsEntities;
    }

    public void setClsEntities(Set<ClsEntity> clsEntities) {
        this.clsEntities = clsEntities;
    }
}

package me.shiki.dartjsonformat.model;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author shiki
 */
public class ClsEntity {
    private String name;
    private String className;
    private String propName;
    private String clsType;
    private String value;
    private boolean isJsonAnnotation = false;
    private Set<ClsEntity> properties = new LinkedHashSet();
    private String fromJson;
    private String clsTypeName;

    public String getClsTypeName() {
        return clsTypeName;
    }

    public void setClsTypeName(String clsTypeName) {
        this.clsTypeName = clsTypeName;
    }

    public String getFromJson() {
        return fromJson;
    }

    public void setFromJson(String fromJson) {
        this.fromJson = fromJson;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClsEntity)) {
            return false;
        }
        ClsEntity clsEntity = (ClsEntity) o;
        return Objects.equals(getName(), clsEntity.getName()) &&
                getClsType() == clsEntity.getClsType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getClsType());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isJsonAnnotation() {
        return isJsonAnnotation;
    }

    public void setJsonAnnotation(boolean jsonAnnotation) {
        isJsonAnnotation = jsonAnnotation;
    }

    public String getClsType() {
        return clsType;
    }

    public void setClsType(String clsType) {
        this.clsType = clsType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<ClsEntity> getProperties() {
        return properties;
    }

    public void setProperties(Set<ClsEntity> properties) {
        this.properties = properties;
    }
}

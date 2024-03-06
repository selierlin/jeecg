package org.jeecg.modules.system.model;

import org.jeecg.modules.system.entity.SysCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 部门表 存储树结构数据的实体类
 * <p>
 *
 * @Author Steve
 * @Since 2019-01-22
 */
public class SysCategoryTreeModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对应SysCategory中的id字段,前端数据树中的key
     */
    private String key;

    /**
     * 对应SysCategory中的id字段,前端数据树中的value
     */
    private String value;

    /**
     * 对应depart_name字段,前端数据树中的title
     */
    private String title;


    private boolean isLeaf;
    // 以下所有字段均与SysCategory相同

    /**
     * 主键
     */
    private java.lang.String id;
    /**
     * 父级节点
     */
    private java.lang.String pid;
    /**
     * 类型名称
     */
    private java.lang.String name;
    /**
     * 类型编码
     */
    private java.lang.String code;
    /**
     * 创建人
     */
    private java.lang.String createBy;
    /**
     * 创建日期
     */
    private java.util.Date createTime;
    /**
     * 更新人
     */
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    private java.lang.String sysOrgCode;
    /**
     * 是否有子节点
     */
    private java.lang.String hasChild;

    private List<SysCategoryTreeModel> children = new ArrayList<>();


    /**
     * 将SysCategory对象转换成SysCategoryTreeModel对象
     *
     * @param sysCategory
     */
    public SysCategoryTreeModel(SysCategory sysCategory) {
        this.key = sysCategory.getId();
        this.value = sysCategory.getCode();
        this.title = sysCategory.getName();
        this.id = sysCategory.getId();
        this.pid = sysCategory.getPid();
        this.name = sysCategory.getName();
        this.code = sysCategory.getCode();
        this.sysOrgCode = sysCategory.getSysOrgCode();
        this.hasChild = sysCategory.getHasChild();
        this.createBy = sysCategory.getCreateBy();
        this.createTime = sysCategory.getCreateTime();
        this.updateBy = sysCategory.getUpdateBy();
        this.updateTime = sysCategory.getUpdateTime();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    public boolean getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(boolean isleaf) {
        this.isLeaf = isleaf;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getSysOrgCode() {
        return sysOrgCode;
    }

    public void setSysOrgCode(String sysOrgCode) {
        this.sysOrgCode = sysOrgCode;
    }

    public String getHasChild() {
        return hasChild;
    }

    public void setHasChild(String hasChild) {
        this.hasChild = hasChild;
    }

    public List<SysCategoryTreeModel> getChildren() {
        return children;
    }

    public void setChildren(List<SysCategoryTreeModel> children) {
        this.children = children;
    }

    public SysCategoryTreeModel() {
    }

    /**
     * 重写equals方法
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SysCategoryTreeModel model = (SysCategoryTreeModel) o;
        return Objects.equals(id, model.id) &&
                Objects.equals(pid, model.pid) &&
                Objects.equals(name, model.name) &&
                Objects.equals(code, model.code) &&
                Objects.equals(hasChild, model.hasChild) &&
                Objects.equals(createBy, model.createBy) &&
                Objects.equals(createTime, model.createTime) &&
                Objects.equals(updateBy, model.updateBy) &&
                Objects.equals(updateTime, model.updateTime) &&
                Objects.equals(children, model.children);
    }

    /**
     * 重写hashCode方法
     */
    @Override
    public int hashCode() {

        return Objects.hash(id, pid, name, code, hasChild,
                createBy, createTime, updateBy, updateTime,
                children);
    }

}

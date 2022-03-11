package org.jeecg.modules.system.model;

import org.jeecg.modules.system.entity.SysCategory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 封装树结构的部门的名称的实体类
 * <p>
 * 
 * @Author Steve
 * @Since 2019-01-22 
 *
 */
public class CategoryIdModel implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键ID
    private String key;

    // 主键ID
    private String value;

    // 部门名称
    private String title;
    
    List<CategoryIdModel> children = new ArrayList<>();
    
    /**
     * 将SysCategoryTreeModel的部分数据放在该对象当中
     * @param treeModel
     * @return
     */
    public CategoryIdModel convert(SysCategoryTreeModel treeModel) {
        this.key = treeModel.getId();
        this.value = treeModel.getId();
        this.title = treeModel.getName();
        return this;
    }
    
    /**
     * 该方法为用户部门的实现类所使用
     * @param SysCategory
     * @return
     */
    public CategoryIdModel convertByUserDepart(SysCategory SysCategory) {
        this.key = SysCategory.getId();
        this.value = SysCategory.getId();
        this.title = SysCategory.getName();
        return this;
    } 

    public List<CategoryIdModel> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryIdModel> children) {
        this.children = children;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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
}

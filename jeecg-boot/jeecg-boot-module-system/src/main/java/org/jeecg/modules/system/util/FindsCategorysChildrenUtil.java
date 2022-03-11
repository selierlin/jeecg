package org.jeecg.modules.system.util;

import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.system.entity.SysCategory;
import org.jeecg.modules.system.model.CategoryIdModel;
import org.jeecg.modules.system.model.SysCategoryTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * <P>
 * 对应部门的表,处理并查找树级数据
 * <P>
 * 
 * @Author: Steve
 * @Date: 2019-01-22
 */
public class FindsCategorysChildrenUtil {

	//部门树信息-树结构
	//private static List<SysCategoryTreeModel> SysCategoryTreeList = new ArrayList<SysCategoryTreeModel>();
	
	//部门树id-树结构
    //private static List<CategoryIdModel> idList = new ArrayList<>();


    /**
     * queryTreeList的子方法 ====1=====
     * 该方法是s将SysCategory类型的list集合转换成SysCategoryTreeModel类型的集合
     */
    public static List<SysCategoryTreeModel> wrapTreeDataToTreeList(List<SysCategory> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
    	List<CategoryIdModel> idList = new ArrayList<>();
        List<SysCategoryTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysCategory depart = recordList.get(i);
            records.add(new SysCategoryTreeModel(depart));
        }
        List<SysCategoryTreeModel> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

    /**
     * 获取 CategoryIdModel
     * @param recordList
     * @return
     */
    public static List<CategoryIdModel> wrapTreeDataToDepartIdTreeList(List<SysCategory> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<CategoryIdModel> idList = new ArrayList<CategoryIdModel>();
        List<SysCategoryTreeModel> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysCategory depart = recordList.get(i);
            records.add(new SysCategoryTreeModel(depart));
        }
        findChildren(records, idList);
        return idList;
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<SysCategoryTreeModel> findChildren(List<SysCategoryTreeModel> recordList,
                                                         List<CategoryIdModel> departIdList) {

        List<SysCategoryTreeModel> treeList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysCategoryTreeModel branch = recordList.get(i);
            if ("0".equals(branch.getPid())) {
                treeList.add(branch);
                CategoryIdModel CategoryIdModel = new CategoryIdModel().convert(branch);
                departIdList.add(CategoryIdModel);
            }
        }
        getGrandChildren(treeList,recordList,departIdList);
        
        //idList = departIdList;
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<SysCategoryTreeModel> treeList,List<SysCategoryTreeModel> recordList,List<CategoryIdModel> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            SysCategoryTreeModel model = treeList.get(i);
            CategoryIdModel idModel = idList.get(i);
            for (int i1 = 0; i1 < recordList.size(); i1++) {
                SysCategoryTreeModel m = recordList.get(i1);
                if (m.getPid()!=null && m.getPid().equals(model.getId())) {
                    model.getChildren().add(m);
                    CategoryIdModel dim = new CategoryIdModel().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }

    }
    

    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<SysCategoryTreeModel> treeList) {

        for (int i = 0; i < treeList.size(); i++) {
            SysCategoryTreeModel model = treeList.get(i);
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            }else{
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }
        // SysCategoryTreeList = treeList;
    }
}

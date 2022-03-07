package org.jeecg.modules.demo.engineer.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.WorkFlow;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 流程表
 * @Author: jeecg-boot
 * @Date: 2021-12-19
 * @Version: V1.0
 */
public interface IWorkFlowService extends IService<WorkFlow> {

    /**
     * 获取审批下个节点
     *
     * @param id     工单id
     * @param isPass 是否通过
     * @param stepId 当前节点
     * @param remark 备注
     * @return
     */
    Result<Integer> getStedId(String id, Integer isPass, Integer stepId, String remark);

    Result getWorkFlowByStepId(Integer stepId);

    /**
     * 查询对应角色可操作的步骤
     *
     * @param roleIds 角色集合
     * @return 可操作的步骤
     */
    Result getWorkFlowStep(List<String> roleIds);


    /**
     * 查询对应角色操作的步骤
     *
     * @param roleIds  角色集合
     * @param isFinish 是否完成
     * @return
     */
    Result getWorkFlowStep(List<String> roleIds, Boolean isFinish);

    /**
     * 用户已审核的工单
     *
     * @param userName 用户名称
     * @return
     */
    Result getCompleteTaskId(String userName);
}

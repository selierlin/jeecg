package org.jeecg.modules.demo.engineer.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.WorkFlow;
import com.baomidou.mybatisplus.extension.service.IService;

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
}

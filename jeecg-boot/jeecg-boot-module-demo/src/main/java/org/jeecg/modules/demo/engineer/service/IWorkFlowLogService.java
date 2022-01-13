package org.jeecg.modules.demo.engineer.service;

import org.jeecg.modules.demo.engineer.entity.WorkFlowLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 流程记录表
 * @Author: jeecg-boot
 * @Date: 2022-01-12
 * @Version: V1.0
 */
public interface IWorkFlowLogService extends IService<WorkFlowLog> {

    /**
     * 获取流程记录-通过工单号
     * @param taskId 工单号
     * @return
     */
    List<WorkFlowLog> getByTaskId(String taskId);

    /**
     * 保存流程记录
     *
     * @param taskId 工单号
     * @param flowId 流程id
     * @param isPass 是否通过
     * @param stepId 步骤id
     * @param remark 备注
     * @return
     */
    boolean saveByTaskId(String taskId, String flowId, Integer isPass, Integer stepId, String remark);
}

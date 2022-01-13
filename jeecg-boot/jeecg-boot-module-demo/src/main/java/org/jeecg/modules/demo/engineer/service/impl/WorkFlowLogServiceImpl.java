package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.WorkFlow;
import org.jeecg.modules.demo.engineer.entity.WorkFlowLog;
import org.jeecg.modules.demo.engineer.mapper.WorkFlowLogMapper;
import org.jeecg.modules.demo.engineer.service.IWorkFlowLogService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 流程记录表
 * @Author: jeecg-boot
 * @Date: 2022-01-12
 * @Version: V1.0
 */
@Service
public class WorkFlowLogServiceImpl extends ServiceImpl<WorkFlowLogMapper, WorkFlowLog> implements IWorkFlowLogService {

    @Autowired
    IWorkFlowService workFlowService;

    @Override
    public List<WorkFlowLog> getByTaskId(String id) {
        QueryWrapper<WorkFlowLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_id", id).orderByAsc("create_time");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public boolean saveByTaskId(String id, String flowId, Integer isPass, Integer stepId, String remark) {
        Result workFlowResult = workFlowService.getWorkFlowByStepId(stepId);
        WorkFlow workFlow = (WorkFlow) workFlowResult.getResult();
        WorkFlowLog workFlowLog = new WorkFlowLog();
        workFlowLog.setFlowId(flowId);
        workFlowLog.setStepId(stepId);
        workFlowLog.setStepName(workFlow.getStepName());
        workFlowLog.setIsPass(isPass);
        workFlowLog.setDescription(remark);
        workFlowLog.setTaskId(id);
        return save(workFlowLog);
    }
}

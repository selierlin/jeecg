package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.demo.engineer.entity.WorkFlow;
import org.jeecg.modules.demo.engineer.mapper.WorkFlowMapper;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.springframework.stereotype.Service;

/**
 * @Description: 流程表
 * @Author: jeecg-boot
 * @Date: 2021-12-19
 * @Version: V1.0
 */
@Service
public class WorkFlowServiceImpl extends ServiceImpl<WorkFlowMapper, WorkFlow> implements IWorkFlowService {


    @Override
    public Result getStedId(String id, Integer isPass, Integer stepId, String remark) {
        QueryWrapper<WorkFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("step_id", stepId);
        WorkFlow workFlow = getOne(queryWrapper);
        if (workFlow == null) {
            return Result.error("找不到流程信息");
        }
        if (workFlow.getNextStepId() == null) {
            return Result.error("此工单已经完结！");
        }

        // 需要跳转的流程id
        int toStepId;
        // 判断审批是否通过
        if (CommonConstant.PASS == isPass) {
            toStepId = workFlow.getNextStepId();
        } else if (CommonConstant.NO_PASS == isPass) {
            // 查找前一个节点的信息
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("next_step_id", stepId);
            workFlow = getOne(queryWrapper);
            if (workFlow == null) {
                return Result.error("此工单当前不可回退");
            }
            toStepId = workFlow.getStepId();
        } else {
            return Result.error("参数异常");
        }
        return Result.OK(toStepId);
    }

    @Override
    public Result getWorkFlowByStepId(Integer stepId) {
        QueryWrapper<WorkFlow> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("step_id", stepId);
        WorkFlow workFlow = getOne(queryWrapper);
        if (workFlow == null) {
            return Result.error("找不到流程信息");
        }
        return Result.OK(workFlow);
    }
}

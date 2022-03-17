package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.modules.demo.engineer.entity.WorkFlow;
import org.jeecg.modules.demo.engineer.entity.WorkFlowLog;
import org.jeecg.modules.demo.engineer.mapper.WorkFlowMapper;
import org.jeecg.modules.demo.engineer.service.IWorkFlowLogService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Description: 流程表
 * @Author: jeecg-boot
 * @Date: 2021-12-19
 * @Version: V1.0
 */
@Service
public class WorkFlowServiceImpl extends ServiceImpl<WorkFlowMapper, WorkFlow> implements IWorkFlowService {

    @Autowired
    IWorkFlowLogService workFlowLogService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;

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

    /**
     * 查询对应角色可操作的步骤
     *
     * @param roleIds 角色集合
     * @return 可操作的步骤
     */
    @Override
    public Result getWorkFlowStep(List<String> roleIds) {
        return getWorkFlowStep(roleIds, true);
    }

    /**
     * 查询对应角色可操作的步骤
     *
     * @param roleIds  角色集合
     * @param isFinish 是否完成
     * @return
     */
    @Override
    public Result getWorkFlowStep(List<String> roleIds, Boolean isFinish) {
        QueryWrapper<WorkFlow> flowQueryWrapper = new QueryWrapper<>();
        flowQueryWrapper.select("step_id");
        // 管理员
        if (!roleIds.contains("f6817f48af4fb3af11b9e8bf182f618b")) {
            flowQueryWrapper.in("role_id", roleIds);
        }
        if (isFinish != null) {
            if (isFinish) {
                flowQueryWrapper.isNull("next_step_id");
            } else {
                flowQueryWrapper.isNotNull("next_step_id");
            }
        }
        List<Object> stepIds = this.listObjs(flowQueryWrapper);
        if (CollectionUtils.isEmpty(stepIds)) {
            return Result.error("该用户没有可审核步骤");
        }
        return Result.OK(stepIds);
    }
    /**
     * 查询对应角色可操作的步骤
     *
     * @return
     */
    @Override
    public Result getWorkFlowUnFinishStep() {
        QueryWrapper<WorkFlow> flowQueryWrapper = new QueryWrapper<>();
        flowQueryWrapper.select("step_id");
        flowQueryWrapper.isNotNull("next_step_id");
        List<Object> stepIds = this.listObjs(flowQueryWrapper);
        if (CollectionUtils.isEmpty(stepIds)) {
            return Result.error("无流程数据");
        }
        return Result.OK(stepIds);
    }

    /**
     * 用户已审核的工单
     *
     * @param userName 用户名称
     * @return
     */
    @Override
    public Result getCompleteTaskId(String userName) {
        QueryWrapper<WorkFlowLog> logQueryWrapper = new QueryWrapper<>();
        logQueryWrapper.select("task_id");
        logQueryWrapper.eq("create_by", userName);
        List<Object> objects = workFlowLogService.listObjs(logQueryWrapper);
        return Result.OK(objects);
    }

    /**
     * 判断用户是否有权限
     *
     * @param userName 用户名称
     * @return
     */
    @Override
    public Result checkUserRole(String userName, int stepId) {
        List<String> roleIds = sysBaseAPI.getRoleIdsByUsername(userName);
        if (CollectionUtils.isEmpty(roleIds)) {
            return Result.error("请重新登录");
        }
        Result<List<Object>> workFlowStep = getWorkFlowStep(roleIds, false);
        if (!workFlowStep.isSuccess()) {
            return Result.error(workFlowStep.getMessage());
        }
        List<Object> stepIds = workFlowStep.getResult();
        if (stepIds.contains(stepId)) {
            return Result.OK();
        }
        return Result.error("无审批权限");
    }
}

package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.ApprovalRecords;
import org.jeecg.modules.demo.engineer.mapper.ApprovalRecordsMapper;
import org.jeecg.modules.demo.engineer.service.IApprovalRecordsService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowLogService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 审批记录
 * @Author: jeecg-boot
 * @Date: 2021-12-11
 * @Version: V1.0
 */
@Service
public class ApprovalRecordsServiceImpl extends ServiceImpl<ApprovalRecordsMapper, ApprovalRecords> implements IApprovalRecordsService {

    private final static Logger logger = LoggerFactory.getLogger(ApprovalRecordsServiceImpl.class);

    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWorkFlowLogService workFlowLogService;

    @Override
    public Result<?> audit(String id, String userName, Integer isPass, String remark) {
        ApprovalRecords approvalRecords = getById(id);
        if (approvalRecords == null) {
            return Result.error("找不到此记录");
        }
        Integer stepId = approvalRecords.getStepId();
        // 校验当前用户是否有审批权限
        Result checkResult = workFlowService.checkUserRole(userName, approvalRecords.getStepId());
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        Result result = workFlowService.getStedId(id, isPass, stepId, remark);
        if (result.isSuccess()) {
            approvalRecords.setState(isPass);
            approvalRecords.setStepId((Integer) result.getResult());
            approvalRecords.setApprovalOpinion(remark);
            // 创建审批日志数据
            Result workFlowResult = workFlowService.getWorkFlowByStepId(stepId);
            if (workFlowResult.isSuccess()) {
                boolean save = workFlowLogService.saveByTaskId(id, approvalRecords.getApprovalType(), isPass, stepId, remark);
                logger.info("update work_flow_log result={}", save);
            }
            boolean update = updateById(approvalRecords);
            return update ? Result.OK() : Result.error("审批失败");
        } else {
            return result;
        }
    }

}

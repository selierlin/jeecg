package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.ApprovalRecords;
import org.jeecg.modules.demo.engineer.mapper.ApprovalRecordsMapper;
import org.jeecg.modules.demo.engineer.service.IApprovalRecordsService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
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

    @Autowired
    IWorkFlowService workFlowService;

    @Override
    public Result<?> audit(String id, Integer isPass, String remark) {
        ApprovalRecords approvalRecords = getById(id);
        if (approvalRecords == null) {
            return Result.error("找不到此记录");
        }
        Integer stepId = approvalRecords.getStepId();
        Result result = workFlowService.getStedId(id, isPass, stepId, remark);
        if (result.isSuccess()) {
            approvalRecords.setState(isPass);
            approvalRecords.setStepId((Integer) result.getResult());
            approvalRecords.setApprovalOpinion(remark);
            // approvalRecords.setUpdateBy(JwtUtil.getUserNameByToken(request));
            // approvalRecords.setUpdateTime(new Date());

            boolean update = updateById(approvalRecords);
            return update ? Result.OK() : Result.error("审批失败");
        } else {
            return result;
        }
    }

}

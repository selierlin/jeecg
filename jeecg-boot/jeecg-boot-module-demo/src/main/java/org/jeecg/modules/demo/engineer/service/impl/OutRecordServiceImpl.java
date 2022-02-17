package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.OutRecord;
import org.jeecg.modules.demo.engineer.mapper.OutRecordMapper;
import org.jeecg.modules.demo.engineer.service.IOutRecordService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowLogService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 外部文件记录
 * @Author: jeecg-boot
 * @Date: 2022-02-15
 * @Version: V1.0
 */
@Service
public class OutRecordServiceImpl extends ServiceImpl<OutRecordMapper, OutRecord> implements IOutRecordService {

    private final static Logger logger = LoggerFactory.getLogger(ApprovalRecordsServiceImpl.class);

    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWorkFlowLogService workFlowLogService;

    @Override
    public Result<?> audit(String id, Integer isPass, String remark) {
        OutRecord outRecord = getById(id);
        if (outRecord == null) {
            return Result.error("找不到此记录");
        }
        Integer stepId = outRecord.getStepId();
        Result result = workFlowService.getStedId(id, isPass, stepId, remark);
        if (result.isSuccess()) {
            outRecord.setState(isPass);
            outRecord.setStepId((Integer) result.getResult());
            outRecord.setApprovalOpinion(remark);
            // 创建审批日志数据
            Result workFlowResult = workFlowService.getWorkFlowByStepId(stepId);
            if (workFlowResult.isSuccess()) {
                boolean save = workFlowLogService.saveByTaskId(id, "OUT", isPass, stepId, remark);
                logger.info("update work_flow_log result={}", save);
            }
            boolean update = updateById(outRecord);
            return update ? Result.OK() : Result.error("审批失败");
        } else {
            return result;
        }
    }
}

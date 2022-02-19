package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.SupervisionDiary;
import org.jeecg.modules.demo.engineer.mapper.SupervisionDiaryMapper;
import org.jeecg.modules.demo.engineer.service.ISupervisionDiaryService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowLogService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 监理日记/志
 * @Author: jeecg-boot
 * @Date: 2022-02-19
 * @Version: V1.0
 */
@Service
public class SupervisionDiaryServiceImpl extends ServiceImpl<SupervisionDiaryMapper, SupervisionDiary> implements ISupervisionDiaryService {

    private final static Logger logger = LoggerFactory.getLogger(SupervisionDiaryServiceImpl.class);

    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWorkFlowLogService workFlowLogService;

    @Override
    public Result<?> audit(String id, Integer isPass, String remark) {
        SupervisionDiary record = getById(id);
        if (record == null) {
            return Result.error("找不到此记录");
        }
        Integer stepId = record.getStepId();
        Result result = workFlowService.getStedId(id, isPass, stepId, remark);
        if (result.isSuccess()) {
            record.setState(isPass);
            record.setStepId((Integer) result.getResult());
            record.setApprovalOpinion(remark);
            // 创建审批日志数据
            Result workFlowResult = workFlowService.getWorkFlowByStepId(stepId);
            if (workFlowResult.isSuccess()) {
                boolean save = workFlowLogService.saveByTaskId(id, "SUPERVISION", isPass, stepId, remark);
                logger.info("update work_flow_log result={}", save);
            }
            boolean update = updateById(record);
            return update ? Result.OK() : Result.error("审批失败");
        } else {
            return result;
        }
    }
}

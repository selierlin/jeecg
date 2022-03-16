package org.jeecg.modules.demo.engineer.service.impl;

import org.jeecg.modules.demo.engineer.entity.AgreedMinute;
import org.jeecg.modules.demo.engineer.mapper.AgreedMinuteMapper;
import org.jeecg.modules.demo.engineer.service.IAgreedMinuteService;
import org.springframework.stereotype.Service;
import org.jeecg.modules.demo.engineer.service.IWorkFlowLogService;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.jeecg.common.api.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 会议纪要
 * @Author: jeecg-boot
 * @Date:   2022-03-13
 * @Version: V1.0
 */
@Service
public class AgreedMinuteServiceImpl extends ServiceImpl<AgreedMinuteMapper, AgreedMinute> implements IAgreedMinuteService {

    private final static Logger logger = LoggerFactory.getLogger(NoticeRecordServiceImpl.class);

    @Autowired
    IWorkFlowService workFlowService;
    @Autowired
    IWorkFlowLogService workFlowLogService;

    @Override
    public Result<?> audit(String id, String userName, Integer isPass, String remark) {
        AgreedMinute record = getById(id);
        if (record == null) {
            return Result.error("找不到此记录");
        }
        Integer stepId = record.getStepId();
        // 校验当前用户是否有审批权限
        Result checkResult = workFlowService.checkUserRole(userName, record.getStepId());
        if (!checkResult.isSuccess()) {
            return checkResult;
        }
        Result result = workFlowService.getStedId(id, isPass, stepId, remark);
        if (result.isSuccess()) {
            record.setState(isPass);
            record.setStepId((Integer) result.getResult());
            record.setApprovalOpinion(remark);
            // 创建审批日志数据
            Result workFlowResult = workFlowService.getWorkFlowByStepId(stepId);
            if (workFlowResult.isSuccess()) {
                boolean save = workFlowLogService.saveByTaskId(id, "AGREED", isPass, stepId, remark);
                logger.info("update work_flow_log result={}", save);
            }
            boolean update = updateById(record);
            return update ? Result.OK() : Result.error("审批失败");
        } else {
            return result;
        }
    }
}

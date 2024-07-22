package org.jeecg.modules.demo.engineer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.ApprovalRecords;

/**
 * @Description: 审批记录
 * @Author: jeecg-boot
 * @Date: 2021-12-11
 * @Version: V1.0
 */
public interface IApprovalRecordsService extends IService<ApprovalRecords> {

    /**
     * 审批
     *
     * @param id       工单id
     * @param userName 登录用户
     * @param isPass   是否通过
     * @param remark   备注
     * @return
     */
    Result<?> audit(String id, String userName, Integer isPass, String remark);
}

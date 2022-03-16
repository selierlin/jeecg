package org.jeecg.modules.demo.engineer.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.AgreedMinute;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 会议纪要
 * @Author: jeecg-boot
 * @Date:   2022-03-13
 * @Version: V1.0
 */
public interface IAgreedMinuteService extends IService<AgreedMinute> {
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

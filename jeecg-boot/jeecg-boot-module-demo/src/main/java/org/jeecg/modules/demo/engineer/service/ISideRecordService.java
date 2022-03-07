package org.jeecg.modules.demo.engineer.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.SideRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 旁站记录
 * @Author: jeecg-boot
 * @Date:   2022-02-18
 * @Version: V1.0
 */
public interface ISideRecordService extends IService<SideRecord> {

    Result<?> audit(String id, String userName, Integer isPass, String approvalOpinion);
}

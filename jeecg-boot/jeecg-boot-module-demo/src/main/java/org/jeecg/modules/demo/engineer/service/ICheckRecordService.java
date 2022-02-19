package org.jeecg.modules.demo.engineer.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.CheckRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 巡查记录
 * @Author: jeecg-boot
 * @Date:   2022-02-19
 * @Version: V1.0
 */
public interface ICheckRecordService extends IService<CheckRecord> {

    Result<?> audit(String id, Integer isPass, String approvalOpinion);
}

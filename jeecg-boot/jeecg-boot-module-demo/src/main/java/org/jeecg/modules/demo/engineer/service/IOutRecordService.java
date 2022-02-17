package org.jeecg.modules.demo.engineer.service;

import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.OutRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 外部文件记录
 * @Author: jeecg-boot
 * @Date:   2022-02-15
 * @Version: V1.0
 */
public interface IOutRecordService extends IService<OutRecord> {

    Result<?> audit(String id, Integer isPass, String approvalOpinion);
}

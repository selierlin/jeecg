package org.jeecg.modules.demo.engineer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.NoticeRecord;

/**
 * @Description: 通知单
 * @Author: jeecg-boot
 * @Date: 2022-02-19
 * @Version: V1.0
 */
public interface INoticeRecordService extends IService<NoticeRecord> {

    Result<?> audit(String id, String userName, Integer isPass, String approvalOpinion);

}

package org.jeecg.modules.demo.engineer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.SupervisionDiary;

/**
 * @Description: 监理日记/志
 * @Author: jeecg-boot
 * @Date: 2022-02-19
 * @Version: V1.0
 */
public interface ISupervisionDiaryService extends IService<SupervisionDiary> {

    Result<?> audit(String id, String userName, Integer isPass, String approvalOpinion);
}

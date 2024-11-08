package org.jeecg.modules.demo.engineer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.entity.AnnualReport;

/**
 * @Description: 月季年报
 * @Author: jeecg-boot
 * @Date: 2022-02-19
 * @Version: V1.0
 */
public interface IAnnualReportService extends IService<AnnualReport> {

    Result<?> audit(String id, String userName, Integer isPass, String approvalOpinion);
}

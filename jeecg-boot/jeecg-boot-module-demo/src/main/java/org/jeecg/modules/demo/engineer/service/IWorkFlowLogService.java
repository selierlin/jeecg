package org.jeecg.modules.demo.engineer.service;

import org.jeecg.modules.demo.engineer.entity.WorkFlowLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 流程记录表
 * @Author: jeecg-boot
 * @Date:   2022-01-12
 * @Version: V1.0
 */
public interface IWorkFlowLogService extends IService<WorkFlowLog> {

    List<WorkFlowLog> getByTaskId(String id);
}

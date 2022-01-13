package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jeecg.modules.demo.engineer.entity.WorkFlowLog;
import org.jeecg.modules.demo.engineer.mapper.WorkFlowLogMapper;
import org.jeecg.modules.demo.engineer.service.IWorkFlowLogService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 流程记录表
 * @Author: jeecg-boot
 * @Date: 2022-01-12
 * @Version: V1.0
 */
@Service
public class WorkFlowLogServiceImpl extends ServiceImpl<WorkFlowLogMapper, WorkFlowLog> implements IWorkFlowLogService {

    @Override
    public List<WorkFlowLog> getByTaskId(String id) {
        QueryWrapper<WorkFlowLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("task_id", id).orderByAsc("create_time");
        return baseMapper.selectList(queryWrapper);
    }
}

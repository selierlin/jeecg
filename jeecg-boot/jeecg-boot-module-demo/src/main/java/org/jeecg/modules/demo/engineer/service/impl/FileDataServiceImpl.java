package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.modules.demo.engineer.entity.FileData;
import org.jeecg.modules.demo.engineer.mapper.FileDataMapper;
import org.jeecg.modules.demo.engineer.service.IFileDataService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 文档文件
 * @Author: jeecg-boot
 * @Date:   2022-03-10
 * @Version: V1.0
 */
@Service
public class FileDataServiceImpl extends ServiceImpl<FileDataMapper, FileData> implements IFileDataService {
}

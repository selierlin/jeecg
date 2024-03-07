package org.jeecg.modules.demo.engineer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.demo.engineer.entity.FileData;
import org.jeecg.modules.demo.engineer.mapper.FileDataMapper;
import org.jeecg.modules.demo.engineer.service.IFileDataService;
import org.springframework.stereotype.Service;

/**
 * @Description: 文档文件
 * @Author: jeecg-boot
 * @Date: 2022-03-10
 * @Version: V1.0
 */
@Service
public class FileDataServiceImpl extends ServiceImpl<FileDataMapper, FileData> implements IFileDataService {
}

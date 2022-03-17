package org.jeecg.modules.demo.engineer.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jeecg.common.system.vo.DictModel;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
public class TaskModel {
    /**
     * 任务id
     */
    private String id;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 申请人
     */
    private String createBy;

}

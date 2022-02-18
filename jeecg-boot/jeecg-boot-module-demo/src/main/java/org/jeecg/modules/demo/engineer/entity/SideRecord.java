package org.jeecg.modules.demo.engineer.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 旁站记录
 * @Author: jeecg-boot
 * @Date:   2022-02-18
 * @Version: V1.0
 */
@Data
@TableName("side_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="side_record对象", description="旁站记录")
public class SideRecord implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**工点*/
	@Excel(name = "工点", width = 15)
    @ApiModelProperty(value = "工点")
    private java.lang.String worksite;
	/**旁站记录表名*/
	@Excel(name = "旁站记录表名", width = 15)
    @ApiModelProperty(value = "旁站记录表名")
    private java.lang.String sideName;
	/**开始时间*/
	@Excel(name = "开始时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date startTime;
	/**结束时间*/
	@Excel(name = "结束时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date endTime;
	/**有无异常*/
	@Excel(name = "有无异常", width = 15, dicCode = "abnormal")
	@Dict(dicCode = "abnormal")
    @ApiModelProperty(value = "有无异常")
    private java.lang.String abnormal;
	/**旁站人*/
	@Excel(name = "旁站人", width = 15)
    @ApiModelProperty(value = "旁站人")
    private java.lang.String sideUser;
	/**文件*/
	@Excel(name = "文件", width = 15)
    @ApiModelProperty(value = "文件")
    private java.lang.String sideFile;
}

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
 * @Description: 会议纪要
 * @Author: jeecg-boot
 * @Date:   2022-03-13
 * @Version: V1.0
 */
@Data
@TableName("agreed_minute")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="agreed_minute对象", description="会议纪要")
public class AgreedMinute implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**上传人*/
    @ApiModelProperty(value = "上传人")
    private java.lang.String createBy;
	/**上传日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "上传日期")
    private java.util.Date createTime;
	/**审阅人*/
    @ApiModelProperty(value = "审阅人")
    private java.lang.String updateBy;
	/**审阅日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "审阅日期")
    private java.util.Date updateTime;
	/**来源*/
	@Excel(name = "来源", width = 15)
    @ApiModelProperty(value = "来源")
    private java.lang.String source;
	/**报表文件*/
	@Excel(name = "报表文件", width = 15)
    @ApiModelProperty(value = "报表文件")
    private java.lang.String fileSource;
	/**处理结果*/
	@Excel(name = "处理结果", width = 15)
    @ApiModelProperty(value = "处理结果")
    private java.lang.Integer state;
	/**步骤*/
	@Excel(name = "步骤", width = 15)
    @ApiModelProperty(value = "步骤")
    private java.lang.Integer stepId;
	/**审批意见*/
	@Excel(name = "审批意见", width = 15)
    @ApiModelProperty(value = "审批意见")
    private java.lang.String approvalOpinion;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**工程名称*/
	@Excel(name = "工程名称", width = 15)
    @ApiModelProperty(value = "工程名称")
    private java.lang.String projectName;
	/**密级*/
	@Excel(name = "密级", width = 15)
    @ApiModelProperty(value = "密级")
    private java.lang.String level;
	/**文档名称*/
	@Excel(name = "文档名称", width = 15)
    @ApiModelProperty(value = "文档名称")
    private java.lang.String fileName;
	/**保密管理人*/
	@Excel(name = "保密管理人", width = 15)
    @ApiModelProperty(value = "保密管理人")
    private java.lang.String manaName;
	/**签发时间*/
	@Excel(name = "签发时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "签发时间")
    private java.util.Date signTime;
	/**页数*/
	@Excel(name = "页数", width = 15)
    @ApiModelProperty(value = "页数")
    private java.lang.Integer pageNums;
	/**传阅范围*/
	@Excel(name = "传阅范围", width = 15)
    @ApiModelProperty(value = "传阅范围")
    private java.lang.String readScope;
	/**签收人*/
	@Excel(name = "签收人", width = 15)
    @ApiModelProperty(value = "签收人")
    private java.lang.String signUser;
	/**传阅后处理*/
	@Excel(name = "传阅后处理", width = 15)
    @ApiModelProperty(value = "传阅后处理")
    private java.lang.String readDeal;
	/**处理时间*/
	@Excel(name = "处理时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "处理时间")
    private java.util.Date dealTime;
	/**会议地点*/
	@Excel(name = "会议地点", width = 15)
    @ApiModelProperty(value = "会议地点")
    private java.lang.String meetAddr;
	/**会议时间*/
	@Excel(name = "会议时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "会议时间")
    private java.util.Date meetTime;
	/**建设单位*/
	@Excel(name = "建设单位", width = 15)
    @ApiModelProperty(value = "建设单位")
    private java.lang.String buildName;
	/**承建单位*/
	@Excel(name = "承建单位", width = 15)
    @ApiModelProperty(value = "承建单位")
    private java.lang.String contractName;
	/**监理单位*/
	@Excel(name = "监理单位", width = 15)
    @ApiModelProperty(value = "监理单位")
    private java.lang.String superName;
	/**内容*/
	@Excel(name = "内容", width = 15)
    @ApiModelProperty(value = "内容")
    private java.lang.String content;
	/**决议*/
	@Excel(name = "决议", width = 15)
    @ApiModelProperty(value = "决议")
    private java.lang.String resolution;
	/**回执文件*/
	@Excel(name = "回执文件", width = 15)
    @ApiModelProperty(value = "回执文件")
    private java.lang.String backFile;
}

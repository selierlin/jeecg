package org.jeecg.modules.demo.engineer.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @Description: 外部文件记录
 * @Author: jeecg-boot
 * @Date:   2022-02-15
 * @Version: V1.0
 */
@Data
@TableName("out_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="out_record对象", description="外部文件记录")
public class OutRecord implements Serializable {
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
	/**文件标题*/
	@Excel(name = "文件标题", width = 15)
    @ApiModelProperty(value = "文件标题")
    private java.lang.String title;
	/**主要内容*/
	@Excel(name = "主要内容", width = 15)
    @ApiModelProperty(value = "主要内容")
    private java.lang.String content;
	/**发件人*/
	@Excel(name = "发件人", width = 15, dicCode = "sender")
	@Dict(dicCode = "sender")
    @ApiModelProperty(value = "发件人")
    private java.lang.String sender;
	/**文号*/
	@Excel(name = "文号", width = 15)
    @ApiModelProperty(value = "文号")
    private java.lang.String uid;
	/**收件时间*/
	@Excel(name = "收件时间", width = 15, format = "yyyy-MM-dd")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "收件时间")
    private java.util.Date receiptTime;
	/**来源*/
	@Excel(name = "来源", width = 15)
    @ApiModelProperty(value = "来源")
    private java.lang.String source;
	/**报表文件*/
	@Excel(name = "报表文件", width = 15)
    @ApiModelProperty(value = "报表文件")
    private java.lang.String fileSource;
	/**处理结果*/
	@Excel(name = "处理结果", width = 15, dicCode = "deal_state")
	@Dict(dicCode = "deal_state")
    @ApiModelProperty(value = "处理结果")
    private java.lang.Integer state;
	/**步骤*/
	@Excel(name = "步骤", width = 15, dictTable = "work_flow", dicText = "step_name", dicCode = "step_id")
	@Dict(dictTable = "work_flow", dicText = "step_name", dicCode = "step_id")
    @ApiModelProperty(value = "步骤")
    private java.lang.Integer stepId;
	/**审批意见*/
	@Excel(name = "审批意见", width = 15)
    @ApiModelProperty(value = "审批意见")
    private java.lang.String approvalOpinion;
    /**是否通过*/
    @TableField(exist = false)
    @ApiModelProperty(value = "是否通过")
    private java.lang.Integer pass;
}

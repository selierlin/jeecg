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
 * @Description: 暂停令
 * @Author: jeecg-boot
 * @Date:   2022-02-19
 * @Version: V1.0
 */
@Data
@TableName("moratorium")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="moratorium对象", description="暂停令")
public class Moratorium implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**工点*/
	@Excel(name = "工点", width = 15)
    @ApiModelProperty(value = "工点")
    private java.lang.String worksite;
	/**事由*/
	@Excel(name = "事由", width = 15)
    @ApiModelProperty(value = "事由")
    private java.lang.String reason;
	/**附件*/
	@Excel(name = "附件", width = 15)
    @ApiModelProperty(value = "附件")
    private java.lang.String fileSource;
	/**拟稿人*/
    @ApiModelProperty(value = "拟稿人")
    private java.lang.String createBy;
	/**审批人*/
    @ApiModelProperty(value = "审批人")
    private java.lang.String updateBy;
	/**收件单位*/
	@Excel(name = "收件单位", width = 15)
    @ApiModelProperty(value = "收件单位")
    private java.lang.String receiverUnit;
	/**审批意见*/
	@Excel(name = "审批意见", width = 15)
    @ApiModelProperty(value = "审批意见")
    private java.lang.String approvalOpinion;
	/**送达回执*/
	@Excel(name = "送达回执", width = 15)
    @ApiModelProperty(value = "送达回执")
    private java.lang.String deliveryReceip;
	/**回复*/
	@Excel(name = "回复", width = 15)
    @ApiModelProperty(value = "回复")
    private java.lang.String reply;
	/**闭合状态*/
	@Excel(name = "闭合状态", width = 15, dicCode = "closed")
	@Dict(dicCode = "closed")
    @ApiModelProperty(value = "闭合状态")
    private java.lang.Integer closed;
	/**来源*/
	@Excel(name = "来源", width = 15)
    @ApiModelProperty(value = "来源")
    private java.lang.String source;
	/**状态*/
	@Excel(name = "状态", width = 15, dicCode = "flow_state")
	@Dict(dicCode = "flow_state")
    @ApiModelProperty(value = "状态")
    private java.lang.Integer state;
	/**步骤*/
	@Excel(name = "步骤", width = 15, dictTable = "work_flow", dicText = "step_name", dicCode = "step_id")
	@Dict(dictTable = "work_flow", dicText = "step_name", dicCode = "step_id")
    @ApiModelProperty(value = "步骤")
    private java.lang.Integer stepId;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**是否通过*/
    @TableField(exist = false)
    @ApiModelProperty(value = "是否通过")
    private java.lang.Integer pass;
}

package org.jeecg.modules.demo.engineer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 审批记录
 * @Author: jeecg-boot
 * @Date: 2021-12-11
 * @Version: V1.0
 */
@Data
@TableName("approval_records")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "approval_records对象", description = "审批记录")
public class ApprovalRecords implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private java.lang.String createBy;
    /**
     * 申请日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "申请日期")
    private java.util.Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
    /**
     * 报表类型
     */
    @Excel(name = "报表类型", width = 15, dicCode = "approval_type")
    @Dict(dicCode = "approval_type")
    @ApiModelProperty(value = "报表类型")
    private java.lang.String approvalType;
    /**
     * 主要内容
     */
    @Excel(name = "主要内容", width = 15)
    @ApiModelProperty(value = "主要内容")
    private java.lang.String content;
    /**
     * 发件单位
     */
    @Excel(name = "发件单位", width = 15)
    @ApiModelProperty(value = "发件单位")
    private java.lang.String unitName;
    /**
     * 收件时间
     */
    @Excel(name = "收件时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收件时间")
    private java.util.Date receiptTime;
    /**
     * 审批意见
     */
    @Excel(name = "审批意见", width = 15)
    @ApiModelProperty(value = "审批意见")
    private java.lang.String approvalOpinion;
    /**
     * 返回时间
     */
    @Excel(name = "返回时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "返回时间")
    private java.util.Date returnTime;
    /**
     * 来源
     */
    @Excel(name = "来源", width = 15)
    @ApiModelProperty(value = "来源")
    private java.lang.String source;
    /**
     * 报审文件
     */
    @Excel(name = "报审文件", width = 15)
    @ApiModelProperty(value = "报审文件")
    private java.lang.String fileSource;
    /**
     * 状态
     */
    @Excel(name = "状态", width = 15, dicCode = "flow_state")
    @Dict(dicCode = "flow_state")
    @ApiModelProperty(value = "状态")
    private java.lang.Integer state;
    /**
     * 步骤
     */
    @Excel(name = "步骤", width = 15, dictTable = "work_flow", dicText = "step_name", dicCode = "step_id")
    @Dict(dictTable = "work_flow", dicText = "step_name", dicCode = "step_id")
    @ApiModelProperty(value = "步骤")
    private java.lang.Integer stepId;
    /**
     * 是否通过
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "是否通过")
    private java.lang.Integer pass;
}

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
 * @Description: 巡查记录
 * @Author: jeecg-boot
 * @Date: 2022-02-19
 * @Version: V1.0
 */
@Data
@TableName("check_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "check_record对象", description = "巡查记录")
public class CheckRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
    /**
     * 工点
     */
    @Excel(name = "工点", width = 15)
    @ApiModelProperty(value = "工点")
    private java.lang.String worksite;
    /**
     * 巡查记录表名
     */
    @Excel(name = "巡查记录表名", width = 15)
    @ApiModelProperty(value = "巡查记录表名")
    private java.lang.String sideName;
    /**
     * 开始时间
     */
    @Excel(name = "开始时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private java.util.Date startTime;
    /**
     * 结束时间
     */
    @Excel(name = "结束时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private java.util.Date endTime;
    /**
     * 有无异常
     */
    @Excel(name = "有无异常", width = 15, dicCode = "abnormal")
    @Dict(dicCode = "abnormal")
    @ApiModelProperty(value = "有无异常")
    private java.lang.Integer abnormal;
    /**
     * 巡查人
     */
    @Excel(name = "巡查人", width = 15)
    @ApiModelProperty(value = "巡查人")
    private java.lang.String checkUser;
    /**
     * 文件
     */
    @Excel(name = "文件", width = 15)
    @ApiModelProperty(value = "文件")
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
     * 审批意见
     */
    @Excel(name = "审批意见", width = 15)
    @ApiModelProperty(value = "审批意见")
    private java.lang.String approvalOpinion;
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
     * 是否通过
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "是否通过")
    private java.lang.Integer pass;
}

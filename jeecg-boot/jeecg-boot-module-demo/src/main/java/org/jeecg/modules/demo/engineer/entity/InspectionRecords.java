package org.jeecg.modules.demo.engineer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
import java.util.Date;

/**
 * @Description: 报验记录
 * @Author: jeecg-boot
 * @Date: 2022-02-15
 * @Version: V1.0
 */
@Data
@TableName("inspection_records")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "inspection_records对象", description = "报验记录")
public class InspectionRecords implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 所属部门
     */
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
    /**
     * 报表类型
     */
    @Excel(name = "报表类型", width = 15, dicCode = "inspection_type")
    @Dict(dicCode = "inspection_type")
    @ApiModelProperty(value = "报表类型")
    private String inspectionType;
    /**
     * 报验内容
     */
    @Excel(name = "报验内容", width = 15)
    @ApiModelProperty(value = "报验内容")
    private String reportContent;
    /**
     * 报验人
     */
    @Excel(name = "报验人", width = 15, dicCode = "contractor")
    @Dict(dicCode = "contractor")
    @ApiModelProperty(value = "报验人")
    private String reportor;
    /**
     * 报验时间
     */
    @Excel(name = "报验时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "报验时间")
    private Date reportTime;
    /**
     * 验收人
     */
    @ApiModelProperty(value = "验收人")
    private String createBy;
    /**
     * 验收时间
     */
    @Excel(name = "验收时间", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "验收时间")
    private Date acceptTime;
    /**
     * 验收结果
     */
    @Excel(name = "验收结果", width = 15, dicCode = "check_result")
    @Dict(dicCode = "check_result")
    @ApiModelProperty(value = "验收结果")
    private String acceptResult;
    /**
     * 备注
     */
    @Excel(name = "备注", width = 15)
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 报表文件
     */
    @Excel(name = "报表文件", width = 15)
    @ApiModelProperty(value = "报表文件")
    private String fileSource;
}

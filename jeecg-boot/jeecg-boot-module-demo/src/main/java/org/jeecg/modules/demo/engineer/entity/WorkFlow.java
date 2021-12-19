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
 * @Description: 流程表
 * @Author: jeecg-boot
 * @Date:   2021-12-19
 * @Version: V1.0
 */
@Data
@TableName("work_flow")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="work_flow对象", description="流程表")
public class WorkFlow implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**流程id*/
	@Excel(name = "流程id", width = 15)
    @ApiModelProperty(value = "流程id")
    private Integer flowId;
	/**步骤id*/
	@Excel(name = "步骤id", width = 15)
    @ApiModelProperty(value = "步骤id")
    private Integer stepId;
	/**步骤名称*/
	@Excel(name = "步骤名称", width = 15)
    @ApiModelProperty(value = "步骤名称")
    private String stepName;
	/**下一个步骤id*/
	@Excel(name = "下一个步骤id", width = 15)
    @ApiModelProperty(value = "下一个步骤id")
    private Integer nextStepId;
	/**角色*/
	@Excel(name = "角色", width = 15, dictTable = "sys_role", dicText = "role_name", dicCode = "role_code")
	@Dict(dictTable = "sys_role", dicText = "role_name", dicCode = "role_code")
    @ApiModelProperty(value = "角色")
    private String roleId;
}

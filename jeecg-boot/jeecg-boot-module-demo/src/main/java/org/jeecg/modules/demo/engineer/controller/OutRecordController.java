package org.jeecg.modules.demo.engineer.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.engineer.entity.ApprovalRecords;
import org.jeecg.modules.demo.engineer.entity.OutRecord;
import org.jeecg.modules.demo.engineer.service.IOutRecordService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.engineer.service.IWorkFlowService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 外部文件记录
 * @Author: jeecg-boot
 * @Date:   2022-02-15
 * @Version: V1.0
 */
@Api(tags="外部文件记录")
@RestController
@RequestMapping("/engineer/outRecord")
@Slf4j
public class OutRecordController extends JeecgController<OutRecord, IOutRecordService> {
	@Autowired
	private IOutRecordService outRecordService;
	 @Autowired
	 private ISysBaseAPI sysBaseAPI;
	 @Autowired
	 private IWorkFlowService workFlowService;
	/**
	 * 分页列表查询
	 *
	 * @param outRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "外部文件记录-分页列表查询")
	@ApiOperation(value="外部文件记录-分页列表查询", notes="外部文件记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(OutRecord outRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		// 查询当前登录用户可以查看到的步骤
		List<String> roleIds = sysBaseAPI.getRoleIdsByUsername(JwtUtil.getUserNameByToken(req));
		if (CollectionUtils.isEmpty(roleIds)) {
			return Result.OK(roleIds);
		}
		Result<List<Object>> workFlowStep = workFlowService.getWorkFlowStep(roleIds);
		if (!workFlowStep.isSuccess()) {
			return Result.OK(workFlowStep.getMessage());
		}
		List<Object> stepIds = workFlowStep.getResult();
		QueryWrapper<OutRecord> queryWrapper = QueryGenerator.initQueryWrapper(outRecord, req.getParameterMap());
		queryWrapper.in("step_id", stepIds);
		Page<OutRecord> page = new Page<OutRecord>(pageNo, pageSize);
		IPage<OutRecord> pageList = outRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param outRecord
	 * @return
	 */
	@AutoLog(value = "外部文件记录-添加")
	@ApiOperation(value="外部文件记录-添加", notes="外部文件记录-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody OutRecord outRecord) {
		outRecord.setStepId(201);
		outRecordService.save(outRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param outRecord
	 * @return
	 */
	@AutoLog(value = "外部文件记录-编辑")
	@ApiOperation(value="外部文件记录-编辑", notes="外部文件记录-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody OutRecord outRecord) {
		outRecordService.updateById(outRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "外部文件记录-通过id删除")
	@ApiOperation(value="外部文件记录-通过id删除", notes="外部文件记录-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		outRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "外部文件记录-批量删除")
	@ApiOperation(value="外部文件记录-批量删除", notes="外部文件记录-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.outRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "外部文件记录-通过id查询")
	@ApiOperation(value="外部文件记录-通过id查询", notes="外部文件记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		OutRecord outRecord = outRecordService.getById(id);
		if(outRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(outRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param outRecord
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OutRecord outRecord) {
        return super.exportXls(request, outRecord, OutRecord.class, "外部文件记录");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, OutRecord.class);
    }

	 /**
	  * 外部
	  *
	  * @param outRecord 外部记录
	  * @return
	  */
	 @AutoLog(value = "外部记录-外部")
	 @ApiOperation(value = "外部记录-外部", notes = "外部记录-外部")
	 @PutMapping(value = "/audit")
	 public Result<?> audit(@RequestBody OutRecord outRecord) {
		 String id = outRecord.getId();
		 Integer isPass = outRecord.getPass();
		 if (StringUtils.isBlank(id) || (isPass == null || isPass < 0)) {
			 return Result.error("参数校验失败");
		 }
		 return outRecordService.audit(id, isPass, outRecord.getApprovalOpinion());
	 }



}

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
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.engineer.entity.AgreedMinute;
import org.jeecg.modules.demo.engineer.entity.AnnualReport;
import org.jeecg.modules.demo.engineer.service.IAgreedMinuteService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

 /**
 * @Description: 会议纪要
 * @Author: jeecg-boot
 * @Date:   2022-03-13
 * @Version: V1.0
 */
@Api(tags="会议纪要")
@RestController
@RequestMapping("/engineer/agreedMinute")
@Slf4j
public class AgreedMinuteController extends JeecgController<AgreedMinute, IAgreedMinuteService> {
	@Autowired
	private IAgreedMinuteService agreedMinuteService;
	
	/**
	 * 分页列表查询
	 *
	 * @param agreedMinute
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "会议纪要-分页列表查询")
	@ApiOperation(value="会议纪要-分页列表查询", notes="会议纪要-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(AgreedMinute agreedMinute,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<AgreedMinute> queryWrapper = QueryGenerator.initQueryWrapper(agreedMinute, req.getParameterMap());
		Page<AgreedMinute> page = new Page<AgreedMinute>(pageNo, pageSize);
		IPage<AgreedMinute> pageList = agreedMinuteService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param agreedMinute
	 * @return
	 */
	@AutoLog(value = "会议纪要-添加")
	@ApiOperation(value="会议纪要-添加", notes="会议纪要-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody AgreedMinute agreedMinute) {
		agreedMinute.setStepId(301);
		agreedMinuteService.save(agreedMinute);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param agreedMinute
	 * @return
	 */
	@AutoLog(value = "会议纪要-编辑")
	@ApiOperation(value="会议纪要-编辑", notes="会议纪要-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody AgreedMinute agreedMinute) {
		agreedMinuteService.updateById(agreedMinute);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会议纪要-通过id删除")
	@ApiOperation(value="会议纪要-通过id删除", notes="会议纪要-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		agreedMinuteService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "会议纪要-批量删除")
	@ApiOperation(value="会议纪要-批量删除", notes="会议纪要-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.agreedMinuteService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "会议纪要-通过id查询")
	@ApiOperation(value="会议纪要-通过id查询", notes="会议纪要-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		AgreedMinute agreedMinute = agreedMinuteService.getById(id);
		if(agreedMinute==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(agreedMinute);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param agreedMinute
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AgreedMinute agreedMinute) {
        return super.exportXls(request, agreedMinute, AgreedMinute.class, "会议纪要");
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
        return super.importExcel(request, response, AgreedMinute.class);
    }


	 /**
	  * 审批
	  *
	  * @param record 记录
	  * @return
	  */
	 @AutoLog(value = "旁听记录-旁听")
	 @ApiOperation(value = "旁听记录-旁听", notes = "旁听记录-审批")
	 @PutMapping(value = "/audit")
	 public Result<?> audit(@RequestBody AgreedMinute record) {
		 String id = record.getId();
		 Integer isPass = record.getPass();
		 if (StringUtils.isBlank(id) || (isPass == null || isPass < 0)) {
			 return Result.error("参数校验失败");
		 }
		 return agreedMinuteService.audit(id, isPass, record.getApprovalOpinion());
	 }

}

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
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.engineer.entity.WorkFlow;
import org.jeecg.modules.demo.engineer.service.IWorkFlowService;

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
 * @Description: 流程表
 * @Author: jeecg-boot
 * @Date:   2021-12-19
 * @Version: V1.0
 */
@Api(tags="流程表")
@RestController
@RequestMapping("/engineer/workFlow")
@Slf4j
public class WorkFlowController extends JeecgController<WorkFlow, IWorkFlowService> {
	@Autowired
	private IWorkFlowService workFlowService;
	
	/**
	 * 分页列表查询
	 *
	 * @param workFlow
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "流程表-分页列表查询")
	@ApiOperation(value="流程表-分页列表查询", notes="流程表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(WorkFlow workFlow,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<WorkFlow> queryWrapper = QueryGenerator.initQueryWrapper(workFlow, req.getParameterMap());
		Page<WorkFlow> page = new Page<WorkFlow>(pageNo, pageSize);
		IPage<WorkFlow> pageList = workFlowService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param workFlow
	 * @return
	 */
	@AutoLog(value = "流程表-添加")
	@ApiOperation(value="流程表-添加", notes="流程表-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody WorkFlow workFlow) {
		workFlowService.save(workFlow);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param workFlow
	 * @return
	 */
	@AutoLog(value = "流程表-编辑")
	@ApiOperation(value="流程表-编辑", notes="流程表-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody WorkFlow workFlow) {
		workFlowService.updateById(workFlow);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "流程表-通过id删除")
	@ApiOperation(value="流程表-通过id删除", notes="流程表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		workFlowService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "流程表-批量删除")
	@ApiOperation(value="流程表-批量删除", notes="流程表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.workFlowService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "流程表-通过id查询")
	@ApiOperation(value="流程表-通过id查询", notes="流程表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		WorkFlow workFlow = workFlowService.getById(id);
		if(workFlow==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(workFlow);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param workFlow
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, WorkFlow workFlow) {
        return super.exportXls(request, workFlow, WorkFlow.class, "流程表");
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
        return super.importExcel(request, response, WorkFlow.class);
    }

}

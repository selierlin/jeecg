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
import org.jeecg.modules.demo.engineer.entity.InspectionRecords;
import org.jeecg.modules.demo.engineer.service.IInspectionRecordsService;

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
 * @Description: 报验记录
 * @Author: jeecg-boot
 * @Date:   2022-02-07
 * @Version: V1.0
 */
@Api(tags="报验记录")
@RestController
@RequestMapping("/engineer/inspectionRecords")
@Slf4j
public class InspectionRecordsController extends JeecgController<InspectionRecords, IInspectionRecordsService> {
	@Autowired
	private IInspectionRecordsService inspectionRecordsService;
	
	/**
	 * 分页列表查询
	 *
	 * @param inspectionRecords
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "报验记录-分页列表查询")
	@ApiOperation(value="报验记录-分页列表查询", notes="报验记录-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(InspectionRecords inspectionRecords,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<InspectionRecords> queryWrapper = QueryGenerator.initQueryWrapper(inspectionRecords, req.getParameterMap());
		Page<InspectionRecords> page = new Page<InspectionRecords>(pageNo, pageSize);
		IPage<InspectionRecords> pageList = inspectionRecordsService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param inspectionRecords
	 * @return
	 */
	@AutoLog(value = "报验记录-添加")
	@ApiOperation(value="报验记录-添加", notes="报验记录-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody InspectionRecords inspectionRecords) {
		inspectionRecordsService.save(inspectionRecords);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param inspectionRecords
	 * @return
	 */
	@AutoLog(value = "报验记录-编辑")
	@ApiOperation(value="报验记录-编辑", notes="报验记录-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody InspectionRecords inspectionRecords) {
		inspectionRecordsService.updateById(inspectionRecords);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报验记录-通过id删除")
	@ApiOperation(value="报验记录-通过id删除", notes="报验记录-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		inspectionRecordsService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "报验记录-批量删除")
	@ApiOperation(value="报验记录-批量删除", notes="报验记录-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.inspectionRecordsService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "报验记录-通过id查询")
	@ApiOperation(value="报验记录-通过id查询", notes="报验记录-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		InspectionRecords inspectionRecords = inspectionRecordsService.getById(id);
		if(inspectionRecords==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(inspectionRecords);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param inspectionRecords
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, InspectionRecords inspectionRecords) {
        return super.exportXls(request, inspectionRecords, InspectionRecords.class, "报验记录");
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
        return super.importExcel(request, response, InspectionRecords.class);
    }

}

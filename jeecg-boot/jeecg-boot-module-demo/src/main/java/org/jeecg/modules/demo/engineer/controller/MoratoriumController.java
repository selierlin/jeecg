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
import org.jeecg.modules.demo.engineer.entity.Moratorium;
import org.jeecg.modules.demo.engineer.service.IMoratoriumService;

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
 * @Description: 暂停令
 * @Author: jeecg-boot
 * @Date:   2022-02-19
 * @Version: V1.0
 */
@Api(tags="暂停令")
@RestController
@RequestMapping("/engineer/moratorium")
@Slf4j
public class MoratoriumController extends JeecgController<Moratorium, IMoratoriumService> {
	@Autowired
	private IMoratoriumService moratoriumService;
	
	/**
	 * 分页列表查询
	 *
	 * @param moratorium
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "暂停令-分页列表查询")
	@ApiOperation(value="暂停令-分页列表查询", notes="暂停令-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(Moratorium moratorium,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<Moratorium> queryWrapper = QueryGenerator.initQueryWrapper(moratorium, req.getParameterMap());
		Page<Moratorium> page = new Page<Moratorium>(pageNo, pageSize);
		IPage<Moratorium> pageList = moratoriumService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param moratorium
	 * @return
	 */
	@AutoLog(value = "暂停令-添加")
	@ApiOperation(value="暂停令-添加", notes="暂停令-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody Moratorium moratorium) {
		moratorium.setStepId(301);
		moratoriumService.save(moratorium);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param moratorium
	 * @return
	 */
	@AutoLog(value = "暂停令-编辑")
	@ApiOperation(value="暂停令-编辑", notes="暂停令-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody Moratorium moratorium) {
		moratoriumService.updateById(moratorium);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "暂停令-通过id删除")
	@ApiOperation(value="暂停令-通过id删除", notes="暂停令-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		moratoriumService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "暂停令-批量删除")
	@ApiOperation(value="暂停令-批量删除", notes="暂停令-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.moratoriumService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "暂停令-通过id查询")
	@ApiOperation(value="暂停令-通过id查询", notes="暂停令-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		Moratorium moratorium = moratoriumService.getById(id);
		if(moratorium==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(moratorium);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param moratorium
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, Moratorium moratorium) {
        return super.exportXls(request, moratorium, Moratorium.class, "暂停令");
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
        return super.importExcel(request, response, Moratorium.class);
    }

	 /**
	  * 审批
	  *
	  * @param record 记录
	  * @return
	  */
	 @AutoLog(value = "记录-")
	 @ApiOperation(value = "记录-", notes = "记录-审批")
	 @PutMapping(value = "/audit")
	 public Result<?> audit(@RequestBody Moratorium record) {
		 String id = record.getId();
		 Integer isPass = record.getPass();
		 if (StringUtils.isBlank(id) || (isPass == null || isPass < 0)) {
			 return Result.error("参数校验失败");
		 }
		 return moratoriumService.audit(id, isPass, record.getApprovalOpinion());
	 }
}
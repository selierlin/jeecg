package org.jeecg.modules.demo.engineer.controller;

import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.engineer.entity.FileData;
import org.jeecg.modules.demo.engineer.service.IFileDataService;

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
 * @Description: 文档文件
 * @Author: jeecg-boot
 * @Date:   2022-03-10
 * @Version: V1.0
 */
@Api(tags="文档文件")
@RestController
@RequestMapping("/engineer/fileData")
@Slf4j
public class FileDataController extends JeecgController<FileData, IFileDataService> {
	@Autowired
	private IFileDataService fileDataService;
	
	/**
	 * 分页列表查询
	 *
	 * @param fileData
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = "文档文件-分页列表查询")
	@ApiOperation(value="文档文件-分页列表查询", notes="文档文件-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(FileData fileData,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<FileData> queryWrapper = QueryGenerator.initQueryWrapper(fileData, req.getParameterMap());
		Page<FileData> page = new Page<FileData>(pageNo, pageSize);
		IPage<FileData> pageList = fileDataService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param fileData
	 * @return
	 */
	@AutoLog(value = "文档文件-添加")
	@ApiOperation(value="文档文件-添加", notes="文档文件-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody FileData fileData) {
		if (StringUtils.isNotBlank(fileData.getFileSource())) {
			String[] split = fileData.getFileSource().split("/");
			String fileName = split[split.length - 1];
			String substring = fileName.substring(fileName.lastIndexOf("_"), fileName.lastIndexOf("."));
			fileName = fileName.replaceAll(substring, "");
			fileData.setFileName(fileName);
		}
		fileDataService.save(fileData);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param fileData
	 * @return
	 */
	@AutoLog(value = "文档文件-编辑")
	@ApiOperation(value="文档文件-编辑", notes="文档文件-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody FileData fileData) {
		fileDataService.updateById(fileData);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文档文件-通过id删除")
	@ApiOperation(value="文档文件-通过id删除", notes="文档文件-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		fileDataService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "文档文件-批量删除")
	@ApiOperation(value="文档文件-批量删除", notes="文档文件-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.fileDataService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "文档文件-通过id查询")
	@ApiOperation(value="文档文件-通过id查询", notes="文档文件-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		FileData fileData = fileDataService.getById(id);
		if(fileData==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(fileData);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param fileData
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FileData fileData) {
        return super.exportXls(request, fileData, FileData.class, "文档文件");
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
        return super.importExcel(request, response, FileData.class);
    }


}

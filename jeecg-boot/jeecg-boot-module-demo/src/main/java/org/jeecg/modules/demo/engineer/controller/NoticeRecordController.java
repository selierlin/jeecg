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
import org.jeecg.modules.demo.engineer.entity.NoticeRecord;
import org.jeecg.modules.demo.engineer.entity.SideRecord;
import org.jeecg.modules.demo.engineer.service.INoticeRecordService;

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
 * @Description:  通知单
 * @Author: jeecg-boot
 * @Date:   2022-02-19
 * @Version: V1.0
 */
@Api(tags=" 通知单")
@RestController
@RequestMapping("/engineer/noticeRecord")
@Slf4j
public class NoticeRecordController extends JeecgController<NoticeRecord, INoticeRecordService> {
	@Autowired
	private INoticeRecordService noticeRecordService;
	
	/**
	 * 分页列表查询
	 *
	 * @param noticeRecord
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	@AutoLog(value = " 通知单-分页列表查询")
	@ApiOperation(value=" 通知单-分页列表查询", notes=" 通知单-分页列表查询")
	@GetMapping(value = "/list")
	public Result<?> queryPageList(NoticeRecord noticeRecord,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<NoticeRecord> queryWrapper = QueryGenerator.initQueryWrapper(noticeRecord, req.getParameterMap());
		Page<NoticeRecord> page = new Page<NoticeRecord>(pageNo, pageSize);
		IPage<NoticeRecord> pageList = noticeRecordService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param noticeRecord
	 * @return
	 */
	@AutoLog(value = " 通知单-添加")
	@ApiOperation(value=" 通知单-添加", notes=" 通知单-添加")
	@PostMapping(value = "/add")
	public Result<?> add(@RequestBody NoticeRecord noticeRecord) {
		noticeRecord.setStepId(301);
		noticeRecordService.save(noticeRecord);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param noticeRecord
	 * @return
	 */
	@AutoLog(value = " 通知单-编辑")
	@ApiOperation(value=" 通知单-编辑", notes=" 通知单-编辑")
	@PutMapping(value = "/edit")
	public Result<?> edit(@RequestBody NoticeRecord noticeRecord) {
		noticeRecordService.updateById(noticeRecord);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = " 通知单-通过id删除")
	@ApiOperation(value=" 通知单-通过id删除", notes=" 通知单-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		noticeRecordService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = " 通知单-批量删除")
	@ApiOperation(value=" 通知单-批量删除", notes=" 通知单-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<?> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.noticeRecordService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = " 通知单-通过id查询")
	@ApiOperation(value=" 通知单-通过id查询", notes=" 通知单-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<?> queryById(@RequestParam(name="id",required=true) String id) {
		NoticeRecord noticeRecord = noticeRecordService.getById(id);
		if(noticeRecord==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(noticeRecord);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param noticeRecord
    */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, NoticeRecord noticeRecord) {
        return super.exportXls(request, noticeRecord, NoticeRecord.class, " 通知单");
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
        return super.importExcel(request, response, NoticeRecord.class);
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
	 public Result<?> audit(@RequestBody SideRecord record) {
		 String id = record.getId();
		 Integer isPass = record.getPass();
		 if (StringUtils.isBlank(id) || (isPass == null || isPass < 0)) {
			 return Result.error("参数校验失败");
		 }
		 return noticeRecordService.audit(id, isPass, record.getApprovalOpinion());
	 }
}
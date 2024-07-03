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
import org.jeecg.modules.demo.engineer.entity.AnnualReport;
import org.jeecg.modules.demo.engineer.entity.ApprovalRecords;
import org.jeecg.modules.demo.engineer.entity.SideRecord;
import org.jeecg.modules.demo.engineer.service.IAnnualReportService;

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
 * @Description: 月季年报
 * @Author: jeecg-boot
 * @Date: 2022-02-19
 * @Version: V1.0
 */
@Api(tags = "月季年报")
@RestController
@RequestMapping("/engineer/annualReport")
@Slf4j
public class AnnualReportController extends JeecgController<AnnualReport, IAnnualReportService> {
    @Autowired
    private IAnnualReportService annualReportService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IWorkFlowService workFlowService;

    /**
     * 分页列表查询
     *
     * @param annualReport
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "月季年报-分页列表查询")
    @ApiOperation(value = "月季年报-分页列表查询", notes = "月季年报-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(AnnualReport annualReport,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
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
        QueryWrapper<AnnualReport> queryWrapper = QueryGenerator.initQueryWrapper(annualReport, req.getParameterMap());
        queryWrapper.in("step_id", stepIds);
        Page<AnnualReport> page = new Page<AnnualReport>(pageNo, pageSize);
        IPage<AnnualReport> pageList = annualReportService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 分页待办列表查询
     *
     * @param annualReport
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "月季年报-分页待办列表查询")
    @ApiOperation(value = "月季年报-分页待办列表查询", notes = "月季年报-分页待办列表查询")
    @GetMapping(value = "/todo")
    public Result<?> queryPageTODOList(AnnualReport annualReport,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req) {
        // 查询当前登录用户可以查看到的步骤
        List<String> roleIds = sysBaseAPI.getRoleIdsByUsername(JwtUtil.getUserNameByToken(req));
        if (CollectionUtils.isEmpty(roleIds)) {
            return Result.OK(roleIds);
        }
        Result<List<Object>> workFlowStep = workFlowService.getWorkFlowStep(roleIds, false);
        if (!workFlowStep.isSuccess()) {
            return Result.OK(workFlowStep.getMessage());
        }
        List<Object> stepIds = workFlowStep.getResult();
        QueryWrapper<AnnualReport> queryWrapper = QueryGenerator.initQueryWrapper(annualReport, req.getParameterMap());
        queryWrapper.in("step_id", stepIds);
        Page<AnnualReport> page = new Page<AnnualReport>(pageNo, pageSize);
        IPage<AnnualReport> pageList = annualReportService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 分页已办列表查询
     *
     * @param annualReport
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "月季年报-分页已办列表查询")
    @ApiOperation(value = "月季年报-分页已办列表查询", notes = "月季年报-分页已办列表查询")
    @GetMapping(value = "/complete")
    public Result<?> queryPageCompleteList(AnnualReport annualReport,
                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                           HttpServletRequest req) {
        // 查询当前登录用户可以查看到的步骤
        List<String> roleIds = sysBaseAPI.getRoleIdsByUsername(JwtUtil.getUserNameByToken(req));
        if (CollectionUtils.isEmpty(roleIds)) {
            return Result.OK(roleIds);
        }
        Result<List<Object>> workFlowStep = workFlowService.getWorkFlowStep(roleIds, null);
        if (!workFlowStep.isSuccess()) {
            return Result.OK(workFlowStep.getMessage());
        }
        List<Object> stepIds = workFlowStep.getResult();

        // 查询当前用户已审核的工单
        Result<List<Integer>> taskIdsResult = workFlowService.getCompleteTaskId(JwtUtil.getUserNameByToken(req));
        if (!taskIdsResult.isSuccess()) {
            return Result.error(taskIdsResult.getMessage());
        }
        List<Integer> taskIds = taskIdsResult.getResult();
        if (CollectionUtils.isEmpty(taskIds)) {
            return Result.OK(taskIds);
        }
        QueryWrapper<AnnualReport> queryWrapper = QueryGenerator.initQueryWrapper(annualReport, req.getParameterMap());
        queryWrapper.in("id", taskIds);
        queryWrapper.in("step_id", stepIds);
        Page<AnnualReport> page = new Page<AnnualReport>(pageNo, pageSize);
        IPage<AnnualReport> pageList = annualReportService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param annualReport
     * @return
     */
    @AutoLog(value = "月季年报-添加")
    @ApiOperation(value = "月季年报-添加", notes = "月季年报-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody AnnualReport annualReport) {
        annualReport.setStepId(301);
        annualReportService.save(annualReport);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param annualReport
     * @return
     */
    @AutoLog(value = "月季年报-编辑")
    @ApiOperation(value = "月季年报-编辑", notes = "月季年报-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody AnnualReport annualReport) {
        annualReportService.updateById(annualReport);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "月季年报-通过id删除")
    @ApiOperation(value = "月季年报-通过id删除", notes = "月季年报-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        annualReportService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "月季年报-批量删除")
    @ApiOperation(value = "月季年报-批量删除", notes = "月季年报-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.annualReportService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "月季年报-通过id查询")
    @ApiOperation(value = "月季年报-通过id查询", notes = "月季年报-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        AnnualReport annualReport = annualReportService.getById(id);
        if (annualReport == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(annualReport);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param annualReport
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, AnnualReport annualReport) {
        return super.exportXls(request, annualReport, AnnualReport.class, "月季年报");
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
        return super.importExcel(request, response, AnnualReport.class);
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
    public Result<?> audit(@RequestBody AnnualReport record, HttpServletRequest req) {
        String id = record.getId();
        Integer isPass = record.getPass();
        if (StringUtils.isBlank(id) || (isPass == null || isPass < 0)) {
            return Result.error("参数校验失败");
        }
        return annualReportService.audit(id, JwtUtil.getUserNameByToken(req), isPass, record.getApprovalOpinion());
    }
}

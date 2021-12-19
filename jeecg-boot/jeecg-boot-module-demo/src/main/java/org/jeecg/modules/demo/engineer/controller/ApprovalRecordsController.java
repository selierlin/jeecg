package org.jeecg.modules.demo.engineer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.engineer.entity.ApprovalRecords;
import org.jeecg.modules.demo.engineer.service.IApprovalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 审批记录
 * @Author: jeecg-boot
 * @Date: 2021-12-11
 * @Version: V1.0
 */
@Api(tags = "审批记录")
@RestController
@RequestMapping("/engineer/approvalRecords")
@Slf4j
public class ApprovalRecordsController extends JeecgController<ApprovalRecords, IApprovalRecordsService> {
    @Autowired
    private IApprovalRecordsService approvalRecordsService;

    /**
     * 分页列表查询
     *
     * @param approvalRecords 审批记录
     * @param pageNo          页面
     * @param pageSize        行数
     * @param req             请求对象
     * @return
     */
    @AutoLog(value = "审批记录-分页列表查询")
    @ApiOperation(value = "审批记录-分页列表查询", notes = "审批记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(ApprovalRecords approvalRecords,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<ApprovalRecords> queryWrapper = QueryGenerator.initQueryWrapper(approvalRecords, req.getParameterMap());
        Page<ApprovalRecords> page = new Page<ApprovalRecords>(pageNo, pageSize);
        IPage<ApprovalRecords> pageList = approvalRecordsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param approvalRecords 审批记录
     * @return
     */
    @AutoLog(value = "审批记录-添加")
    @ApiOperation(value = "审批记录-添加", notes = "审批记录-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody ApprovalRecords approvalRecords) {
        approvalRecordsService.save(approvalRecords);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param approvalRecords 审批记录
     * @return
     */
    @AutoLog(value = "审批记录-编辑")
    @ApiOperation(value = "审批记录-编辑", notes = "审批记录-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody ApprovalRecords approvalRecords) {
        approvalRecordsService.updateById(approvalRecords);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "审批记录-通过id删除")
    @ApiOperation(value = "审批记录-通过id删除", notes = "审批记录-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        approvalRecordsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "审批记录-批量删除")
    @ApiOperation(value = "审批记录-批量删除", notes = "审批记录-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.approvalRecordsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "审批记录-通过id查询")
    @ApiOperation(value = "审批记录-通过id查询", notes = "审批记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        ApprovalRecords approvalRecords = approvalRecordsService.getById(id);
        if (approvalRecords == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(approvalRecords);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param approvalRecords 审批记录
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ApprovalRecords approvalRecords) {
        return super.exportXls(request, approvalRecords, ApprovalRecords.class, "审批记录");
    }

    /**
     * 审批
     *
     * @param id     工单id
     * @param isPass 是否通过
     * @param remark 备注
     * @return
     */
    @AutoLog(value = "审批记录-审批")
    @ApiOperation(value = "审批记录-审批", notes = "审批记录-审批")
    @PostMapping(value = "/audit")
    public Result<?> audit(@RequestParam(name = "id", required = true) String id,
                           @RequestParam(name = "isPass", required = true) Integer isPass,
                           @RequestParam(name = "remark", required = false) String remark
    ) {
        if (StringUtils.isBlank(id) || isPass == null) {
            return Result.error("参数校验失败");
        }
        return approvalRecordsService.audit(id, isPass, remark);
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
        return super.importExcel(request, response, ApprovalRecords.class);
    }

}

package org.jeecg.modules.demo.engineer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.engineer.entity.FileTemplate;
import org.jeecg.modules.demo.engineer.service.IFileTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @Description: 表单模板
 * @Author: jeecg-boot
 * @Date: 2022-03-13
 * @Version: V1.0
 */
@Api(tags = "表单模板")
@RestController
@RequestMapping("/engineer/fileTemplate")
@Slf4j
public class FileTemplateController extends JeecgController<FileTemplate, IFileTemplateService> {
    @Autowired
    private IFileTemplateService fileTemplateService;

    /**
     * 分页列表查询
     *
     * @param fileTemplate
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "表单模板-分页列表查询")
    @ApiOperation(value = "表单模板-分页列表查询", notes = "表单模板-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(FileTemplate fileTemplate,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req) {
        QueryWrapper<FileTemplate> queryWrapper = QueryGenerator.initQueryWrapper(fileTemplate, req.getParameterMap());
        Page<FileTemplate> page = new Page<FileTemplate>(pageNo, pageSize);
        IPage<FileTemplate> pageList = fileTemplateService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param fileTemplate
     * @return
     */
    @AutoLog(value = "表单模板-添加")
    @ApiOperation(value = "表单模板-添加", notes = "表单模板-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody FileTemplate fileTemplate) {
        fileTemplateService.save(fileTemplate);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param fileTemplate
     * @return
     */
    @AutoLog(value = "表单模板-编辑")
    @ApiOperation(value = "表单模板-编辑", notes = "表单模板-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody FileTemplate fileTemplate) {
        fileTemplateService.updateById(fileTemplate);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "表单模板-通过id删除")
    @ApiOperation(value = "表单模板-通过id删除", notes = "表单模板-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        fileTemplateService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "表单模板-批量删除")
    @ApiOperation(value = "表单模板-批量删除", notes = "表单模板-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.fileTemplateService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "表单模板-通过id查询")
    @ApiOperation(value = "表单模板-通过id查询", notes = "表单模板-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        FileTemplate fileTemplate = fileTemplateService.getById(id);
        if (fileTemplate == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(fileTemplate);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param fileTemplate
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, FileTemplate fileTemplate) {
        return super.exportXls(request, fileTemplate, FileTemplate.class, "表单模板");
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
        return super.importExcel(request, response, FileTemplate.class);
    }

}

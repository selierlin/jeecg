package org.jeecg.modules.demo.engineer.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.demo.engineer.entity.*;
import org.jeecg.modules.demo.engineer.service.*;
import org.jeecg.modules.demo.engineer.vo.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 首页信息
 * @Author: jeecg-boot
 * @Date: 2022-03-13
 * @Version: V1.0
 */
@Api(tags = "首页信息")
@RestController
@RequestMapping("/engineer/task")
@Slf4j
public class TaskController {
    @Autowired
    private IAgreedMinuteService agreedMinuteService;
    @Autowired
    private IAnnualReportService annualReportService;
    @Autowired
    private IApprovalRecordsService approvalRecordsService;
    @Autowired
    private ICheckRecordService checkRecordService;
    @Autowired
    private IInspectionRecordsService inspectionRecordsService;
    @Autowired
    private IMoratoriumService moratoriumService;
    @Autowired
    private INoticeRecordService noticeRecordService;
    @Autowired
    private IOutRecordService outRecordService;
    @Autowired
    private ISideRecordService sideRecordService;
    @Autowired
    private ISupervisionDiaryService supervisionDiaryService;
    @Autowired
    private ISysBaseAPI sysBaseAPI;
    @Autowired
    private IWorkFlowService workFlowService;


    /**
     * 分页我的待办查询
     *
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "首页信息-分页我的待办查询")
    @ApiOperation(value = "首页信息-分页我的待办查询", notes = "首页信息-分页我的待办查询")
    @GetMapping(value = "/todo")
    public Result<?> queryPageTODOList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
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
        ArrayList<TaskModel> records = new ArrayList<>();
        findAgreedMinute(stepIds, records, "");
        findAnnualReport(stepIds, records, "");
        findApprovalRecords(stepIds, records, "");
        findCheckRecord(stepIds, records, "");
        findInspectionRecords(stepIds, records, "");
        findMoratorium(stepIds, records, "");
        findNoticeRecord(stepIds, records, "");
        findOutRecord(stepIds, records, "");
        findSideRecord(stepIds, records, "");
        findSupervisionDiary(stepIds, records, "");

        records.sort((x, y) -> x.getCreateTime().getTime() > y.getCreateTime().getTime() ? 0 : -1);
        return Result.OK(records.subList(0, Math.min(records.size(),5)));
    }


    /**
     * 分页我的在办查询
     *
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "首页信息-分页我的在办查询")
    @ApiOperation(value = "首页信息-分页我的在办查询", notes = "首页信息-分页我的在办查询")
    @GetMapping(value = "/mine")
    public Result<?> queryPageCompleteList(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                           HttpServletRequest req) {

        // 查询当前登录用户可以查看到的步骤
        String userName = JwtUtil.getUserNameByToken(req);
        Result<List<Object>> workFlowStep = workFlowService.getWorkFlowUnFinishStep();
        if (!workFlowStep.isSuccess()) {
            return Result.OK(workFlowStep.getMessage());
        }
        List<Object> stepIds = workFlowStep.getResult();
        ArrayList<TaskModel> records = new ArrayList<>();
        findAgreedMinute(stepIds, records, userName);
        findAnnualReport(stepIds, records, userName);
        findApprovalRecords(stepIds, records, userName);
        findCheckRecord(stepIds, records, userName);
        findInspectionRecords(stepIds, records, userName);
        findMoratorium(stepIds, records, userName);
        findNoticeRecord(stepIds, records, userName);
        findOutRecord(stepIds, records, userName);
        findSideRecord(stepIds, records, userName);
        findSupervisionDiary(stepIds, records, userName);

        records.sort((x, y) -> x.getCreateTime().getTime() > y.getCreateTime().getTime() ? 0 : -1);
        return Result.OK(records.subList(0, Math.min(records.size(),5)));
    }

    private void findAgreedMinute(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<AgreedMinute> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<AgreedMinute> agreedMinutes = agreedMinuteService.list(queryWrapper);
        for (AgreedMinute agreedMinute : agreedMinutes) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(agreedMinute.getId());
            taskModel.setTaskName("会议纪要");
            taskModel.setCreateTime(agreedMinute.getCreateTime());
            taskModel.setCreateBy(agreedMinute.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findAnnualReport(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<AnnualReport> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<AnnualReport> list = annualReportService.list(queryWrapper);
        for (AnnualReport bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("月季年报");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findApprovalRecords(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<ApprovalRecords> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<ApprovalRecords> list = approvalRecordsService.list(queryWrapper);
        for (ApprovalRecords bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("审批记录");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findCheckRecord(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<CheckRecord> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<CheckRecord> list = checkRecordService.list(queryWrapper);
        for (CheckRecord bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("巡查记录");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findInspectionRecords(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        /*QueryWrapper<InspectionRecords> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<InspectionRecords> list = inspectionRecordsService.list(queryWrapper);
        for (InspectionRecords bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("报验记录");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }*/
    }

    private void findMoratorium(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<Moratorium> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<Moratorium> list = moratoriumService.list(queryWrapper);
        for (Moratorium bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("暂停令");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findNoticeRecord(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<NoticeRecord> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<NoticeRecord> list = noticeRecordService.list(queryWrapper);
        for (NoticeRecord bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("通知单");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findOutRecord(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<OutRecord> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<OutRecord> list = outRecordService.list(queryWrapper);
        for (OutRecord bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("外部文件记录");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findSideRecord(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<SideRecord> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<SideRecord> list = sideRecordService.list(queryWrapper);
        for (SideRecord bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("旁站记录");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

    private void findSupervisionDiary(List<Object> stepIds, ArrayList<TaskModel> records, String userName) {
        QueryWrapper<SupervisionDiary> queryWrapper = new QueryWrapper<>();
        if (!CollectionUtils.isEmpty(stepIds)) {
            queryWrapper.in("step_id", stepIds);
        }
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.in("create_by", userName);
        }
        List<SupervisionDiary> list = supervisionDiaryService.list(queryWrapper);
        for (SupervisionDiary bean : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setId(bean.getId());
            taskModel.setTaskName("监理日记/志");
            taskModel.setCreateTime(bean.getCreateTime());
            taskModel.setCreateBy(bean.getCreateBy());
            records.add(taskModel);
        }
    }

}

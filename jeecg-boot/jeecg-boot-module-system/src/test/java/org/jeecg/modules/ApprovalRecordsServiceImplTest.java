package org.jeecg.modules;

import org.jeecg.JeecgSystemApplication;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.demo.engineer.service.IApprovalRecordsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,classes = JeecgSystemApplication.class)
@SuppressWarnings({"FieldCanBeLocal", "SpringJavaAutowiredMembersInspection"})
public class ApprovalRecordsServiceImplTest {

    @Resource
    private IApprovalRecordsService approvalRecordsService;

    @Test
    public void audit() {
        String id = "1468591776904683521";
        int isPass = 1;
        String remark = "通过了";
        Result<?> audit = approvalRecordsService.audit(id, "jianli", isPass, remark);
        System.out.println(audit);
    }
}

<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="报表类型">
              <j-dict-select-tag
                placeholder="请选择报表类型"
                v-model="queryParam.approvalType"
                dictCode="approval_type"
              />
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'" />
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('审批记录')">导出</a-button>
      <a-upload
        name="file"
        :showUploadList="false"
        :multiple="false"
        :headers="tokenHeader"
        :action="importExcelUrl"
        @change="handleImportExcel"
      >
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query
        :fieldList="superFieldList"
        ref="superQueryModal"
        @handleSuperQuery="handleSuperQuery"
      ></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete" />删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择
        <a style="font-weight: 600">{{ selectedRowKeys.length }}</a
        >项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{ x: true }"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        class="j-table-force-nowrap"
        @change="handleTableChange"
      >
        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无图片</span>
          <img
            v-else
            :src="getImgView(text)"
            height="25px"
            alt=""
            style="max-width: 80px; font-size: 12px; font-style: italic"
          />
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px; font-style: italic">无文件</span>
          <a-button v-else :ghost="true" type="primary" icon="download" size="small" @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>
          <a-divider type="vertical" />
          <a @click="handleLog(record.id)">查看日志</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>
      </a-table>
    </div>
    <a-modal :visible="logVisible" @cancel="logVisible = false" @ok="logVisible = false" width="60%">
      <work-flow-log-modal :data="logData" ref="modalLogForm"></work-flow-log-modal>
    </a-modal>
    <approval-records-modal ref="modalForm" @ok="modalFormOk"></approval-records-modal>
    <approval-records-modal1 ref="modalForm1"></approval-records-modal1>
  </a-card>
</template>

<script>
import '@/assets/less/TableExpand.less'
import { mixinDevice } from '@/utils/mixin'
import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import ApprovalRecordsModal from './modules/ApprovalRecordsModal'
import ApprovalRecordsModal1 from './modules/ApprovalRecordsModal1'
import WorkFlowLogModal from './modules/WorkFlowLogModal'
import { filterMultiDictText } from '@/components/dict/JDictSelectUtil'
import { axios } from '@/utils/request'

export default {
  name: 'ApprovalRecordsList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    ApprovalRecordsModal,
    ApprovalRecordsModal1,
    WorkFlowLogModal,
  },
  data() {
    return {
      description: '审批记录管理页面',
      logVisible: false,
      logData: [],
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: 'center',
          customRender: function (t, r, index) {
            return parseInt(index) + 1
          },
        },
        {
          title: '报表类型',
          align: 'center',
          dataIndex: 'approvalType_dictText',
        },
        {
          title: '主要内容',
          align: 'center',
          dataIndex: 'content',
        },
        {
          title: '发件单位',
          align: 'center',
          dataIndex: 'unitName',
        },
        {
          title: '收件时间',
          align: 'center',
          dataIndex: 'receiptTime',
        },
        {
          title: '返回时间',
          align: 'center',
          dataIndex: 'returnTime',
        },
        {
          title: '来源',
          align: 'center',
          dataIndex: 'source',
        },
        {
          title: '报审文件',
          align: 'center',
          dataIndex: 'fileSource',
          scopedSlots: { customRender: 'fileSlot' },
        },
        {
          title: '申请人',
          align: 'center',
          dataIndex: 'createBy',
        },
        {
          title: '申请日期',
          align: 'center',
          dataIndex: 'createTime',
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: 'center',
          fixed: 'right',
          width: 147,
          scopedSlots: { customRender: 'action' },
        },
      ],
      url: {
        list: '/engineer/approvalRecords/list',
        delete: '/engineer/approvalRecords/delete',
        deleteBatch: '/engineer/approvalRecords/deleteBatch',
        exportXlsUrl: '/engineer/approvalRecords/exportXls',
        importExcelUrl: 'engineer/approvalRecords/importExcel',
        audit: 'engineer/approvalRecords/audit',
        log: 'engineer/workFlowLog/queryByTaskId',
      },
      dictOptions: {},
      superFieldList: [],
    }
  },
  created() {
    this.getSuperFieldList()
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
    },
  },
  methods: {
    initDictConfig() {},
    handleAudit(obj) {
      this.$refs.modalForm1.audit(obj)
    },
    handleLog(id) {
      axios
        .get(`${this.url.log}?taskId=${id}`, {
          taskId: id,
        })
        .then((res) => {
          if (res.success) {
            this.logData = res.result
            this.logVisible = true
          }else{
            this.$message.warn('服务器出现错误');
          }
        })
    },
    getSuperFieldList() {
      let fieldList = []
      fieldList.push({ type: 'string', value: 'createBy', text: '申请人', dictCode: '' })
      fieldList.push({ type: 'datetime', value: 'createTime', text: '申请日期' })
      fieldList.push({ type: 'string', value: 'approvalType', text: '报表类型', dictCode: 'approval_type' })
      fieldList.push({ type: 'string', value: 'content', text: '主要内容', dictCode: '' })
      fieldList.push({ type: 'string', value: 'unitName', text: '发件单位', dictCode: '' })
      fieldList.push({ type: 'datetime', value: 'receiptTime', text: '收件时间' })
      fieldList.push({ type: 'string', value: 'approvalOpinion', text: '审批意见', dictCode: '' })
      fieldList.push({ type: 'datetime', value: 'returnTime', text: '返回时间' })
      fieldList.push({ type: 'string', value: 'source', text: '来源', dictCode: '' })
      fieldList.push({ type: 'string', value: 'fileSource', text: '报审文件', dictCode: '' })
      fieldList.push({ type: 'int', value: 'state', text: '状态', dictCode: 'flow_state' })
      fieldList.push({ type: 'int', value: 'stepId', text: '步骤', dictCode: 'work_flow,step_name,step_id' })
      this.superFieldList = fieldList
    },
  },
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>
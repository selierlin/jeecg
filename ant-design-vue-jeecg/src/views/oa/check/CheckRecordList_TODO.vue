<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    <!-- 操作按钮区域 -->
    <div class="table-operator">
    <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <!-- 高级查询区域 -->
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleAudit(record)">审批</a>
          <a-divider type="vertical" />
          <a @click="handleLog(record.id)">查看日志</a>
          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <a-modal :visible="logVisible" @cancel="logVisible = false" @ok="logVisible = false" width="60%">
      <work-flow-log-modal :data="logData" ref="modalLogForm"></work-flow-log-modal>
    </a-modal>
    <check-record-modal ref="modalForm" @ok="modalFormOk"></check-record-modal>
    <check-record-modal1 ref="modalForm1" @ok="modalFormOk"></check-record-modal1>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CheckRecordModal from './modules/CheckRecordModal'
  import CheckRecordModal1 from './modules/CheckRecordModal1'
  import WorkFlowLogModal from './modules/WorkFlowLogModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { axios } from '@/utils/request'
  export default {
    name: 'CheckRecordList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      CheckRecordModal,
      CheckRecordModal1,
      WorkFlowLogModal
    },
    data () {
      return {
        description: '巡查记录管理页面',
        logVisible: false,
        logData: [],
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'工点',
            align:"center",
            dataIndex: 'worksite'
          },
          {
            title:'巡查记录表名',
            align:"center",
            dataIndex: 'sideName'
          },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'startTime'
          },
          {
            title:'结束时间',
            align:"center",
            dataIndex: 'endTime'
          },
          {
            title:'有无异常',
            align:"center",
            dataIndex: 'abnormal_dictText'
          },
          {
            title:'巡查人',
            align:"center",
            dataIndex: 'checkUser'
          },
          {
            title:'文件',
            align:"center",
            dataIndex: 'fileSource',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'状态',
            align:"center",
            dataIndex: 'state_dictText'
          },
          {
            title:'步骤',
            align:"center",
            dataIndex: 'stepId_dictText'
          },
          {
            title:'申请人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'申请日期',
            align:"center",
            dataIndex: 'createTime'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/engineer/checkRecord/todo",
          delete: "/engineer/checkRecord/delete",
          deleteBatch: "/engineer/checkRecord/deleteBatch",
          exportXlsUrl: "/engineer/checkRecord/exportXls",
          importExcelUrl: "engineer/checkRecord/importExcel",
          audit: 'engineer/checkRecord/audit',
          log: 'engineer/workFlowLog/queryByTaskId',
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
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
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'string',value:'worksite',text:'工点',dictCode:''})
        fieldList.push({type:'string',value:'sideName',text:'巡查记录表名',dictCode:''})
        fieldList.push({type:'datetime',value:'startTime',text:'开始时间'})
        fieldList.push({type:'datetime',value:'endTime',text:'结束时间'})
        fieldList.push({type:'int',value:'abnormal',text:'有无异常',dictCode:'abnormal'})
        fieldList.push({type:'string',value:'checkUser',text:'巡查人',dictCode:''})
        fieldList.push({type:'string',value:'fileSource',text:'文件',dictCode:''})
        fieldList.push({type:'int',value:'state',text:'状态',dictCode:'flow_state'})
        fieldList.push({type:'int',value:'stepId',text:'步骤',dictCode:'work_flow,step_name,step_id'})
        fieldList.push({type:'string',value:'approvalOpinion',text:'审批意见',dictCode:''})
        fieldList.push({type:'string',value:'createBy',text:'申请人',dictCode:''})
        fieldList.push({type:'datetime',value:'createTime',text:'申请日期'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
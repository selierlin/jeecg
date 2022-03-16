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
    <supervision-diary-modal ref="modalForm" @ok="modalFormOk"></supervision-diary-modal>
    <supervision-diary-modal1 ref="modalForm1" ></supervision-diary-modal1>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import SupervisionDiaryModal from './modules/SupervisionDiaryModal'
  import SupervisionDiaryModal1 from './modules/SupervisionDiaryModal1'
  import WorkFlowLogModal from './modules/WorkFlowLogModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { axios } from '@/utils/request'
  export default {
    name: 'SupervisionDiaryList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      SupervisionDiaryModal,
      SupervisionDiaryModal1,
      WorkFlowLogModal
    },
    data () {
      return {
        description: '监理日记/志管理页面',
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
            title:'日记主人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'日记起始日期',
            align:"center",
            dataIndex: 'startTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'日记结束日期',
            align:"center",
            dataIndex: 'endTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'上传日期',
            align:"center",
            dataIndex: 'createTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'审阅人',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'审阅日期',
            align:"center",
            dataIndex: 'updateTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'来源',
            align:"center",
            dataIndex: 'source'
          },
          {
            title:'报表文件',
            align:"center",
            dataIndex: 'fileSource',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'处理结果',
            align:"center",
            dataIndex: 'state_dictText'
          },
          {
            title:'步骤',
            align:"center",
            dataIndex: 'stepId_dictText'
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
          list: "/engineer/supervisionDiary/todo",
          delete: "/engineer/supervisionDiary/delete",
          deleteBatch: "/engineer/supervisionDiary/deleteBatch",
          exportXlsUrl: "/engineer/supervisionDiary/exportXls",
          importExcelUrl: "engineer/supervisionDiary/importExcel",
          audit: 'engineer/supervisionDiary/audit',
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
      handlePrint(id) {
        let url = window._CONFIG['domianURL'] + '/jmreport/view/655257991467216896?id='+id;
        window.open(url);
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
        fieldList.push({type:'string',value:'createBy',text:'日记主人',dictCode:''})
        fieldList.push({type:'date',value:'startTime',text:'日记起始日期'})
        fieldList.push({type:'date',value:'endTime',text:'日记结束日期'})
        fieldList.push({type:'date',value:'createTime',text:'上传日期'})
        fieldList.push({type:'string',value:'updateBy',text:'审阅人',dictCode:''})
        fieldList.push({type:'date',value:'updateTime',text:'审阅日期'})
        fieldList.push({type:'string',value:'source',text:'来源',dictCode:''})
        fieldList.push({type:'string',value:'fileSource',text:'报表文件',dictCode:''})
        fieldList.push({type:'int',value:'state',text:'处理结果',dictCode:'flow_state'})
        fieldList.push({type:'int',value:'stepId',text:'步骤',dictCode:'work_flow,step_name,step_id'})
        fieldList.push({type:'string',value:'projectName',text:'工程名称',dictCode:''})
        fieldList.push({type:'string',value:'level',text:'密级',dictCode:''})
        fieldList.push({type:'string',value:'fileName',text:'文档名称',dictCode:''})
        fieldList.push({type:'string',value:'manaName',text:'保密管理人',dictCode:''})
        fieldList.push({type:'date',value:'signTime',text:'签发时间'})
        fieldList.push({type:'int',value:'pageNums',text:'页数',dictCode:''})
        fieldList.push({type:'string',value:'readScope',text:'传阅范围',dictCode:''})
        fieldList.push({type:'string',value:'signUser',text:'签收人',dictCode:''})
        fieldList.push({type:'string',value:'readDeal',text:'传阅后处理',dictCode:''})
        fieldList.push({type:'date',value:'dealTime',text:'处理时间'})
        fieldList.push({type:'string',value:'superName',text:'监理单位名称',dictCode:''})
        fieldList.push({type:'date',value:'superTime',text:'日期'})
        fieldList.push({type:'string',value:'superUser',text:'监理人员',dictCode:''})
        fieldList.push({type:'string',value:'content',text:'内容',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
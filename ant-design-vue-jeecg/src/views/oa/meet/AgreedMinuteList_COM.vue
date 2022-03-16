<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="工程名称">
              <a-input placeholder="请输入工程名称" v-model="queryParam.projectName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <a-form-item label="密级">
              <a-input placeholder="请输入密级" v-model="queryParam.level"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="签发时间">
                <j-date placeholder="请选择签发时间" v-model="queryParam.signTime"></j-date>
              </a-form-item>
            </a-col>
            <a-col :xl="6" :lg="7" :md="8" :sm="24">
              <a-form-item label="签收人">
                <a-input placeholder="请输入签收人" v-model="queryParam.signUser"></a-input>
              </a-form-item>
            </a-col>
          </template>
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
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
    <agreed-minute-modal ref="modalForm" @ok="modalFormOk"></agreed-minute-modal>
    <agreed-minute-modal1 ref="modalForm1"></agreed-minute-modal1>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import AgreedMinuteModal from './modules/AgreedMinuteModal'
  import AgreedMinuteModal1 from './modules/AgreedMinuteModal1'
  import WorkFlowLogModal from './modules/WorkFlowLogModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { axios } from '@/utils/request'
  export default {
    name: 'AgreedMinuteList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      AgreedMinuteModal,
      AgreedMinuteModal1,
      WorkFlowLogModal
    },
    data () {
      return {
        description: '会议纪要管理页面',
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
            title:'工程名称',
            align:"center",
            dataIndex: 'projectName'
          },
          {
            title:'密级',
            align:"center",
            dataIndex: 'level'
          },
          {
            title:'文档名称',
            align:"center",
            dataIndex: 'fileName'
          },
          {
            title:'保密管理人',
            align:"center",
            dataIndex: 'manaName'
          },
          {
            title:'签发时间',
            align:"center",
            dataIndex: 'signTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'页数',
            align:"center",
            dataIndex: 'pageNums'
          },
          {
            title:'传阅范围',
            align:"center",
            dataIndex: 'readScope'
          },
          {
            title:'签收人',
            align:"center",
            dataIndex: 'signUser'
          },
          {
            title:'传阅后处理',
            align:"center",
            dataIndex: 'readDeal'
          },
          {
            title:'处理时间',
            align:"center",
            dataIndex: 'dealTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'会议地点',
            align:"center",
            dataIndex: 'meetAddr'
          },
          {
            title:'会议时间',
            align:"center",
            dataIndex: 'meetTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'建设单位',
            align:"center",
            dataIndex: 'buildName'
          },
          {
            title:'承建单位',
            align:"center",
            dataIndex: 'contractName'
          },
          {
            title:'监理单位',
            align:"center",
            dataIndex: 'superName'
          },
          {
            title:'决议',
            align:"center",
            dataIndex: 'resolution'
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
          list: "/engineer/agreedMinute/complete",
          delete: "/engineer/agreedMinute/delete",
          deleteBatch: "/engineer/agreedMinute/deleteBatch",
          exportXlsUrl: "/engineer/agreedMinute/exportXls",
          importExcelUrl: "engineer/agreedMinute/importExcel",
          audit: 'engineer/agreedMinute/audit',
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
        fieldList.push({type:'string',value:'fileSource',text:'报表文件',dictCode:''})
        fieldList.push({type:'int',value:'state',text:'处理结果',dictCode:'flow_state'})
        fieldList.push({type:'int',value:'stepId',text:'步骤',dictCode:'work_flow,step_name,step_id'})
        fieldList.push({type:'string',value:'approvalOpinion',text:'审批意见',dictCode:''})
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
        fieldList.push({type:'string',value:'meetAddr',text:'会议地点',dictCode:''})
        fieldList.push({type:'date',value:'meetTime',text:'会议时间'})
        fieldList.push({type:'string',value:'buildName',text:'建设单位',dictCode:''})
        fieldList.push({type:'string',value:'contractName',text:'承建单位',dictCode:''})
        fieldList.push({type:'string',value:'superName',text:'监理单位',dictCode:''})
        fieldList.push({type:'string',value:'content',text:'内容',dictCode:''})
        fieldList.push({type:'string',value:'resolution',text:'决议',dictCode:''})
        fieldList.push({type:'string',value:'backFile',text:'回执文件',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
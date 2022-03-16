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
    <moratorium-modal ref="modalForm" @ok="modalFormOk"></moratorium-modal>
    <moratorium-modal1 ref="modalForm1"></moratorium-modal1>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MoratoriumModal from './modules/MoratoriumModal'
  import MoratoriumModal1 from './modules/MoratoriumModal1'
  import WorkFlowLogModal from './modules/WorkFlowLogModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'
  import { axios } from '@/utils/request'

  export default {
    name: 'MoratoriumList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      MoratoriumModal,
      MoratoriumModal1,
      WorkFlowLogModal
    },
    data () {
      return {
        description: '暂停令管理页面',
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
            title:'事由',
            align:"center",
            dataIndex: 'reason'
          },
          {
            title:'附件',
            align:"center",
            dataIndex: 'fileSource',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'拟稿人',
            align:"center",
            dataIndex: 'createBy'
          },
          {
            title:'审批人',
            align:"center",
            dataIndex: 'updateBy'
          },
          {
            title:'收件单位',
            align:"center",
            dataIndex: 'receiverUnit'
          },
          {
            title:'审批意见',
            align:"center",
            dataIndex: 'approvalOpinion'
          },
          {
            title:'送达回执',
            align:"center",
            dataIndex: 'deliveryReceip',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'回复',
            align:"center",
            dataIndex: 'reply',
            scopedSlots: {customRender: 'fileSlot'}
          },
          {
            title:'闭合状态',
            align:"center",
            dataIndex: 'closed_dictText'
          },
          {
            title:'来源',
            align:"center",
            dataIndex: 'source'
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
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/engineer/moratorium/list",
          delete: "/engineer/moratorium/delete",
          deleteBatch: "/engineer/moratorium/deleteBatch",
          exportXlsUrl: "/engineer/moratorium/exportXls",
          importExcelUrl: "engineer/moratorium/importExcel",
          audit: 'engineer/moratorium/audit',
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
        fieldList.push({type:'string',value:'reason',text:'事由',dictCode:''})
        fieldList.push({type:'string',value:'fileSource',text:'附件',dictCode:''})
        fieldList.push({type:'string',value:'createBy',text:'拟稿人',dictCode:''})
        fieldList.push({type:'string',value:'updateBy',text:'审批人',dictCode:''})
        fieldList.push({type:'string',value:'receiverUnit',text:'收件单位',dictCode:''})
        fieldList.push({type:'string',value:'approvalOpinion',text:'审批意见',dictCode:''})
        fieldList.push({type:'string',value:'deliveryReceip',text:'送达回执',dictCode:''})
        fieldList.push({type:'string',value:'reply',text:'回复',dictCode:''})
        fieldList.push({type:'int',value:'closed',text:'闭合状态',dictCode:'closed'})
        fieldList.push({type:'string',value:'source',text:'来源',dictCode:''})
        fieldList.push({type:'int',value:'state',text:'状态',dictCode:'flow_state'})
        fieldList.push({type:'int',value:'stepId',text:'步骤',dictCode:'work_flow,step_name,step_id'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>
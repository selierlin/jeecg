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
      <a-button type="primary" icon="download" @click="handleExportXls('巡查记录')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl"
                @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal"
                     @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作
          <a-icon type="down"/>
        </a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a
          style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
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
          <img v-else :src="getImgView(text)" height="25px" alt=""
               style="max-width:80px;font-size: 12px;font-style: italic;"/>
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

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
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

    <check-record-modal ref="modalForm" @ok="modalFormOk"></check-record-modal>
  </a-card>
</template>

<script>

import '@/assets/less/TableExpand.less'
import {mixinDevice} from '@/utils/mixin'
import {JeecgListMixin} from '@/mixins/JeecgListMixin'
import CheckRecordModal from './modules/CheckRecordModal'

export default {
  name: 'CheckRecordList',
  mixins: [JeecgListMixin, mixinDevice],
  components: {
    CheckRecordModal
  },
  data() {
    return {
      description: '巡查记录管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key: 'rowIndex',
          width: 60,
          align: "center",
          customRender: function (t, r, index) {
            return parseInt(index) + 1;
          }
        },
        {
          title: '工点',
          align: "center",
          dataIndex: 'worksite'
        },
        {
          title: '巡查记录表名',
          align: "center",
          dataIndex: 'sideName'
        },
        {
          title: '开始时间',
          align: "center",
          dataIndex: 'startTime'
        },
        {
          title: '结束时间',
          align: "center",
          dataIndex: 'endTime'
        },
        {
          title: '有无异常',
          align: "center",
          dataIndex: 'abnormal_dictText'
        },
        {
          title: '巡查人',
          align: "center",
          dataIndex: 'checkUser'
        },
        {
          title: '文件',
          align: "center",
          dataIndex: 'fileSource',
          scopedSlots: {customRender: 'fileSlot'}
        },
        {
          title: '状态',
          align: "center",
          dataIndex: 'state_dictText'
        },
        {
          title: '步骤',
          align: "center",
          dataIndex: 'stepId_dictText'
        },
        {
          title: '申请人',
          align: "center",
          dataIndex: 'createBy'
        },
        {
          title: '申请日期',
          align: "center",
          dataIndex: 'createTime'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align: "center",
          fixed: "right",
          width: 147,
          scopedSlots: {customRender: 'action'}
        }
      ],
      url: {
        list: "/engineer/checkRecord/list",
        delete: "/engineer/checkRecord/delete",
        deleteBatch: "/engineer/checkRecord/deleteBatch",
        exportXlsUrl: "/engineer/checkRecord/exportXls",
        importExcelUrl: "engineer/checkRecord/importExcel",

      },
      dictOptions: {},
      superFieldList: [],
    }
  },
  created() {
    this.getSuperFieldList();
  },
  computed: {
    importExcelUrl: function () {
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    },
  },
  methods: {
    initDictConfig() {
    },
    getSuperFieldList() {
      let fieldList = [];
      fieldList.push({type: 'string', value: 'worksite', text: '工点', dictCode: ''})
      fieldList.push({type: 'string', value: 'sideName', text: '巡查记录表名', dictCode: ''})
      fieldList.push({type: 'datetime', value: 'startTime', text: '开始时间'})
      fieldList.push({type: 'datetime', value: 'endTime', text: '结束时间'})
      fieldList.push({type: 'int', value: 'abnormal', text: '有无异常', dictCode: 'abnormal'})
      fieldList.push({type: 'string', value: 'checkUser', text: '巡查人', dictCode: ''})
      fieldList.push({type: 'string', value: 'fileSource', text: '文件', dictCode: ''})
      fieldList.push({type: 'int', value: 'state', text: '状态', dictCode: 'flow_state'})
      fieldList.push({type: 'int', value: 'stepId', text: '步骤', dictCode: 'work_flow,step_name,step_id'})
      fieldList.push({type: 'string', value: 'approvalOpinion', text: '审批意见', dictCode: ''})
      fieldList.push({type: 'string', value: 'createBy', text: '申请人', dictCode: ''})
      fieldList.push({type: 'datetime', value: 'createTime', text: '申请日期'})
      this.superFieldList = fieldList
    }
  }
}
</script>
<style scoped>
@import '~@assets/less/common.less';
</style>
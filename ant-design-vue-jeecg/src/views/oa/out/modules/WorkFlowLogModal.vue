<template>
  <div>
    <a-table :columns="columns" :data-source="data"> </a-table>
  </div>
</template>
<script>
const columns = [
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
    title: '步骤',
    dataIndex: 'stepName',
    key: 'stepName',
  },
  {
    title: '操作人',
    dataIndex: 'createBy',
    key: 'createBy',
  },
  {
    title: '操作时间',
    key: 'createTime',
    dataIndex: 'createTime',
  },
  {
    title: '意见',
    key: 'isPass',
    dataIndex: 'isPass',
  },
  {
    title: '备注',
    key: 'description',
    dataIndex: 'description',
  },
]

const data = []

export default {
  data() {
    return {
      columns,
      visible: false,
    }
  },
  props: {
    data: {
      type: Array,
      default: [],
    },
  },
}
</script>
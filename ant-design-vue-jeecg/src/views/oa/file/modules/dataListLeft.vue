<template>
  <a-card :loading="cardLoading" :bordered="false" style="height: 100%;">
    <a-spin :spinning="loading">
      <a-input-search @search="handleSearch" style="width:100%;margin-top: 10px" placeholder="输入文件夹名称查询..." enterButton />

      <a-tree
        showLine
        checkStrictly
        :expandedKeys.sync="expandedKeys"
        :selectedKeys="selectedKeys"
        :dropdownStyle="{maxHeight:'200px',overflow:'auto'}"
        :treeData="treeDataSource"
        @select="handleTreeSelect"
      />
    </a-spin>
  </a-card>
</template>

<script>
  import { getAction } from '@/api/manage'
  const searchByKeywords   = (params)=>getAction("/sys/category/searchBy",params);
  const queryDepartTreeList = (params)=>getAction("/sys/category/queryTreeList",params);

  export default {
    name: 'AddressListLeft',
    props: ['value'],
    data() {
      return {
        cardLoading: true,
        loading: false,
        treeDataSource: [],
        selectedKeys: [],
        expandedKeys: []
      }
    },
    created() {
      this.queryTreeData()
    },
    methods: {

      queryTreeData(keyword) {
        this.commonRequestThen(queryDepartTreeList({
          pcode: 'B03'
        }))
      },

      handleSearch(value) {
        if (value) {
          this.commonRequestThen(searchByKeywords({ keyWord: value }))
        } else {
          this.queryTreeData()
        }
      },

      handleTreeSelect(selectedKeys, event) {
        if (selectedKeys.length > 0 && this.selectedKeys[0] !== selectedKeys[0]) {
          this.selectedKeys = [selectedKeys[0]]
          let id = event.node.dataRef.id
          this.emitInput(id)
        }
      },

      emitInput(code) {
        this.$emit('input', code)
      },

      commonRequestThen(promise) {
        this.loading = true
        promise.then(res => {
          if (res.success) {
            this.treeDataSource = res.result
          } else {
            this.$message.warn(res.message)
            console.error('查询失败:', res)
          }
        }).finally(() => {
          this.loading = false
          this.cardLoading = false
        })
      },

    }
  }
</script>

<style scoped>

</style>
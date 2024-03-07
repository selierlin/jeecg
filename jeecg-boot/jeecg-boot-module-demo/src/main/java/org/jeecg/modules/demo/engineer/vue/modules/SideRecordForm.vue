<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="工点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="worksite">
              <a-input v-model="model.worksite" placeholder="请输入工点"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="旁站记录表名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sideName">
              <a-input v-model="model.sideName" placeholder="请输入旁站记录表名"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="startTime">
              <j-date placeholder="请选择开始时间" v-model="model.startTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="endTime">
              <j-date placeholder="请选择结束时间" v-model="model.endTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="有无异常" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="abnormal">
              <j-dict-select-tag type="list" v-model="model.abnormal" dictCode="abnormal" placeholder="请选择有无异常"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="旁站人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sideUser">
              <a-input v-model="model.sideUser" placeholder="请输入旁站人"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sideFile">
              <j-upload v-model="model.sideFile"></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审批意见" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approvalOpinion">
              <a-textarea v-model="model.approvalOpinion" rows="4" placeholder="请输入审批意见"/>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

import {httpAction} from '@/api/manage'

export default {
  name: 'SideRecordForm',
  components: {},
  props: {
    //表单禁用
    disabled: {
      type: Boolean,
      default: false,
      required: false
    }
  },
  data() {
    return {
      model: {},
      labelCol: {
        xs: {span: 24},
        sm: {span: 5},
      },
      wrapperCol: {
        xs: {span: 24},
        sm: {span: 16},
      },
      confirmLoading: false,
      validatorRules: {},
      url: {
        add: "/engineer/sideRecord/add",
        edit: "/engineer/sideRecord/edit",
        queryById: "/engineer/sideRecord/queryById"
      }
    }
  },
  computed: {
    formDisabled() {
      return this.disabled
    },
  },
  created() {
    //备份model原始值
    this.modelDefault = JSON.parse(JSON.stringify(this.model));
  },
  methods: {
    add() {
      this.edit(this.modelDefault);
    },
    edit(record) {
      this.model = Object.assign({}, record);
      this.visible = true;
    },
    submitForm() {
      const that = this;
      // 触发表单验证
      this.$refs.form.validate(valid => {
        if (valid) {
          that.confirmLoading = true;
          let httpurl = '';
          let method = '';
          if (!this.model.id) {
            httpurl += this.url.add;
            method = 'post';
          } else {
            httpurl += this.url.edit;
            method = 'put';
          }
          httpAction(httpurl, this.model, method).then((res) => {
            if (res.success) {
              that.$message.success(res.message);
              that.$emit('ok');
            } else {
              that.$message.warning(res.message);
            }
          }).finally(() => {
            that.confirmLoading = false;
          })
        }

      })
    },
  }
}
</script>
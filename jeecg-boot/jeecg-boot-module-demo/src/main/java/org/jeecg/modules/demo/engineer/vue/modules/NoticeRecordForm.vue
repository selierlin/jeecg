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
            <a-form-model-item label="事由" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="reason">
              <a-input v-model="model.reason" placeholder="请输入事由"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="附件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fileSource">
              <j-upload v-model="model.fileSource"></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="收件单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="receiverUnit">
              <a-input v-model="model.receiverUnit" placeholder="请输入收件单位"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审批意见" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approvalOpinion">
              <a-textarea v-model="model.approvalOpinion" rows="4" placeholder="请输入审批意见"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="送达回执" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="deliveryReceip">
              <j-upload v-model="model.deliveryReceip"></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="回复" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="reply">
              <j-upload v-model="model.reply"></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="闭合状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="closed">
              <j-dict-select-tag type="list" v-model="model.closed" dictCode="closed" placeholder="请选择闭合状态"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="source">
              <a-input v-model="model.source" placeholder="请输入来源"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="state">
              <j-dict-select-tag type="list" v-model="model.state" dictCode="flow_state" placeholder="请选择状态"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="步骤" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="stepId">
              <j-dict-select-tag type="list" v-model="model.stepId" dictCode="work_flow,step_name,step_id"
                                 placeholder="请选择步骤"/>
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
  name: 'NoticeRecordForm',
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
      model: {
        source: "PC",
      },
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
        add: "/engineer/noticeRecord/add",
        edit: "/engineer/noticeRecord/edit",
        queryById: "/engineer/noticeRecord/queryById"
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
<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="步骤id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="stepId">
              <a-input-number v-model="model.stepId" placeholder="请输入步骤id" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="流程id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="flowId">
              <a-input-number v-model="model.flowId" placeholder="请输入流程id" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="步骤名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="stepName">
              <a-input v-model="model.stepName" placeholder="请输入步骤名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="是否通过" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="isPass">
              <a-input-number v-model="model.isPass" placeholder="请输入是否通过" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="描述" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="description">
              <a-input v-model="model.description" placeholder="请输入描述"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核人id" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="auditUserId">
              <a-input-number v-model="model.auditUserId" placeholder="请输入审核人id" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核人名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="auditUserName">
              <a-input v-model="model.auditUserName" placeholder="请输入审核人名称"></a-input>
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
  name: 'WorkFlowLogForm',
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
        add: "/com.engineer/workFlowLog/add",
        edit: "/com.engineer/workFlowLog/edit",
        queryById: "/com.engineer/workFlowLog/queryById"
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
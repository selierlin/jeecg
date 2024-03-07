<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="报表类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="inspectionType">
              <j-dict-select-tag type="list" v-model="model.inspectionType" dictCode="inspection_type"
                                 placeholder="请选择报表类型"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报验内容" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="reportContent">
              <a-textarea v-model="model.reportContent" rows="4" placeholder="请输入报验内容"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报验人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="reportor">
              <j-dict-select-tag type="list" v-model="model.reportor" dictCode="contractor" placeholder="请选择报验人"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报验时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="reportTime">
              <j-date placeholder="请选择报验时间" v-model="model.reportTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="验收时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="acceptTime">
              <j-date placeholder="请选择验收时间" v-model="model.acceptTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="验收结果" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="acceptResult">
              <j-dict-select-tag type="list" v-model="model.acceptResult" dictCode="check_result"
                                 placeholder="请选择验收结果"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-textarea v-model="model.remark" rows="4" placeholder="请输入备注"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报表文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fileSource">
              <j-upload v-model="model.fileSource"></j-upload>
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
  name: 'InspectionRecordsForm',
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
        add: "/engineer/inspectionRecords/add",
        edit: "/engineer/inspectionRecords/edit",
        queryById: "/engineer/inspectionRecords/queryById"
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
<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="报表类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approvalType">
              <j-dict-select-tag type="list" disabled v-model="model.approvalType" dictCode="approval_type" placeholder="请选择报表类型" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="主要内容" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="content">
              <a-textarea v-model="model.content" disabled rows="4" placeholder="请输入主要内容" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="发件单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="unitName">
              <a-input v-model="model.unitName" disabled placeholder="请输入发件单位"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="收件时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="receiptTime">
              <j-date placeholder="请选择收件时间"  disabled v-model="model.receiptTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="返回时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="returnTime">
              <j-date placeholder="请选择返回时间" disabled  v-model="model.returnTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="来源" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="source">
              <a-input v-model="model.source" disabled placeholder="请输入来源"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审批意见" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approvalOpinion">
              <a-textarea v-model="model.approvalOpinion" rows="4" placeholder="请输入审批意见" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, putAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'ApprovalRecordsForm1',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/engineer/approvalRecords/add",
          edit: "/engineer/approvalRecords/edit",
          audit: "/engineer/approvalRecords/audit",
          queryById: "/engineer/approvalRecords/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      audit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm (isPass) {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            httpurl+=this.url.audit;
            putAction(httpurl,{id:this.model.id,pass:isPass,approvalOpinion:this.model.approvalOpinion}).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
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
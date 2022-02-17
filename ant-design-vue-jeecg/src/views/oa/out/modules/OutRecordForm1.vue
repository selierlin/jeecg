<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="文件标题1" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="title">
              <a-input disabled v-model="model.title" placeholder="请输入文件标题"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="主要内容" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="content">
              <a-textarea disabled v-model="model.content" rows="4" placeholder="请输入主要内容" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="发件人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sender">
              <j-dict-select-tag disabled type="list" v-model="model.sender" dictCode="sender" placeholder="请选择发件人" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="文号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="uid">
              <a-input disabled v-model="model.uid" placeholder="请输入文号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="收件时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="receiptTime">
              <j-date disabled placeholder="请选择收件时间" v-model="model.receiptTime"  style="width: 100%" />
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
    name: 'OutRecordForm1',
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
          add: "/engineer/outRecord/add",
          edit: "/engineer/outRecord/edit",
          audit: "/engineer/outRecord/audit",
          queryById: "/engineer/outRecord/queryById"
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
      audit (record) {
        console.log("abc");
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      edit (record) {
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
<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="工点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="worksite">
              <a-input disabled v-model="model.worksite" placeholder="请输入工点"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="巡查记录表名" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="sideName">
              <a-input disabled v-model="model.sideName" placeholder="请输入巡查记录表名"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="startTime">
              <j-date disabled placeholder="请选择开始时间"  v-model="model.startTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="endTime">
              <j-date disabled placeholder="请选择结束时间"  v-model="model.endTime" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="有无异常" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="abnormal">
              <j-dict-select-tag disabled type="list" v-model="model.abnormal" dictCode="abnormal" placeholder="请选择有无异常" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="巡查人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="checkUser">
              <a-input disabled v-model="model.checkUser" placeholder="请输入巡查人"  ></a-input>
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
    name: 'CheckRecordForm1',
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
          add: "/engineer/checkRecord/add",
          edit: "/engineer/checkRecord/edit",
          audit: "/engineer/sideRecord/audit",
          queryById: "/engineer/checkRecord/queryById"
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
        console.log("abc");
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
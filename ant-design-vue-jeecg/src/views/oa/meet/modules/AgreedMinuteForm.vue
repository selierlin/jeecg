<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="报表文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fileSource">
              <j-upload v-model="model.fileSource"   ></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审批意见" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="approvalOpinion">
              <a-input v-model="model.approvalOpinion" placeholder="请输入审批意见"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="工程名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectName">
              <a-input v-model="model.projectName" placeholder="请输入工程名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="密级" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="level">
              <a-input v-model="model.level" placeholder="请输入密级"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="文档名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fileName">
              <a-input v-model="model.fileName" placeholder="请输入文档名称"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="保密管理人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="manaName">
              <a-input v-model="model.manaName" placeholder="请输入保密管理人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="签发时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="signTime">
              <j-date placeholder="请选择签发时间" v-model="model.signTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="页数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pageNums">
              <a-input-number v-model="model.pageNums" placeholder="请输入页数" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="传阅范围" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="readScope">
              <a-input v-model="model.readScope" placeholder="请输入传阅范围"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="签收人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="signUser">
              <a-input v-model="model.signUser" placeholder="请输入签收人"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="传阅后处理" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="readDeal">
              <a-input v-model="model.readDeal" placeholder="请输入传阅后处理"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="处理时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dealTime">
              <j-date placeholder="请选择处理时间" v-model="model.dealTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="会议地点" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="meetAddr">
              <a-input v-model="model.meetAddr" placeholder="请输入会议地点"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="会议时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="meetTime">
              <j-date placeholder="请选择会议时间" v-model="model.meetTime"  style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="建设单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="buildName">
              <a-input v-model="model.buildName" placeholder="请输入建设单位"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="承建单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="contractName">
              <a-input v-model="model.contractName" placeholder="请输入承建单位"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="监理单位" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="superName">
              <a-input v-model="model.superName" placeholder="请输入监理单位"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="content">
              <a-textarea v-model="model.content" rows="4" placeholder="请输入内容" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="决议" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="resolution">
              <a-textarea v-model="model.resolution" rows="4" placeholder="请输入决议" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="回执文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="backFile">
              <j-upload v-model="model.backFile"   ></j-upload>
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'AgreedMinuteForm',
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
          add: "/engineer/agreedMinute/add",
          edit: "/engineer/agreedMinute/edit",
          queryById: "/engineer/agreedMinute/queryById"
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
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
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
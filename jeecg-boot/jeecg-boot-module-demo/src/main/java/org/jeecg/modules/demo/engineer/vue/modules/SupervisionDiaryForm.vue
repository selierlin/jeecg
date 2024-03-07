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
            <a-form-model-item label="日记起始日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="startTime">
              <j-date placeholder="请选择日记起始日期" v-model="model.startTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="日记结束日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="endTime">
              <j-date placeholder="请选择日记结束日期" v-model="model.endTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="报表文件" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fileSource">
              <j-upload v-model="model.fileSource"></j-upload>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="工程名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="projectName">
              <a-input v-model="model.projectName" placeholder="请输入工程名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="密级" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="level">
              <a-input v-model="model.level" placeholder="请输入密级"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="文档名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="fileName">
              <a-input v-model="model.fileName" placeholder="请输入文档名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="保密管理人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="manaName">
              <a-input v-model="model.manaName" placeholder="请输入保密管理人"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="签发时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="signTime">
              <j-date placeholder="请选择签发时间" v-model="model.signTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="页数" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="pageNum">
              <a-input-number v-model="model.pageNum" placeholder="请输入页数" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="传阅范围" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="readScope">
              <a-input v-model="model.readScope" placeholder="请输入传阅范围"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="签收人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="signUser">
              <a-input v-model="model.signUser" placeholder="请输入签收人"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="传阅后处理" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="readDeal">
              <a-input v-model="model.readDeal" placeholder="请输入传阅后处理"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="处理时间" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dealTime">
              <j-date placeholder="请选择处理时间" v-model="model.dealTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="监理单位名称" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="superName">
              <a-input v-model="model.superName" placeholder="请输入监理单位名称"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="superTime">
              <j-date placeholder="请选择日期" v-model="model.superTime" style="width: 100%"/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="监理人员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="superUser">
              <a-input v-model="model.superUser" placeholder="请输入监理人员"></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="内容" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="content">
              <a-textarea v-model="model.content" rows="4" placeholder="请输入内容"/>
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
  name: 'SupervisionDiaryForm',
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
        add: "/engineer/supervisionDiary/add",
        edit: "/engineer/supervisionDiary/edit",
        queryById: "/engineer/supervisionDiary/queryById"
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
<template>
  <j-modal
    :title="title"
    :width="width"
    :visible="visible"
    switchFullscreen
    @cancel="handleCancel"
    >
    <template slot="footer">
        <a-button key="back" @click="handleReject">
          拒绝
        </a-button>
        <a-button key="submit" type="primary" @click="handleOk">
          通过
        </a-button>
      </template>
      <notice-record-form1 ref="realForm1" :disabled="disableSubmit"></notice-record-form1>
  </j-modal>
</template>

<script>

  import NoticeRecordForm1 from './NoticeRecordForm1'
  export default {
    name: 'NoticeRecordModal1',
    components: {
      NoticeRecordForm1
    },
    data () {
      return {
        title:'',
        width:800,
        visible: false,
        disableSubmit: false
      }
    },
    methods: {
      add () {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.add();
        })
      },
      edit (record) {
        this.visible=true
        this.$nextTick(()=>{
          this.$refs.realForm.edit(record);
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        this.$refs.realForm1.submitForm(1);
        this.submitCallback();
      },
      handleReject () {
        this.$refs.realForm1.submitForm(2);
        this.submitCallback();
      },
      submitCallback(){
        this.$emit('ok');
        this.visible = false;
      },
      audit (record) {
        this.visible=true
        console.log("111");
        this.$nextTick(()=>{
          this.$refs.realForm1.audit(record);
        })
      },
      handleCancel () {
        this.close()
      }
    }
  }
</script>
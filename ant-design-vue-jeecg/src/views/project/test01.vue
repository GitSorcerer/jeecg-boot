<template>
  <div>
    <a-button type="primary">Primary</a-button>
    <div style="margin-top: 10px">
      <a-input placeholder="Basic usage" v-model="testData"/>
    </div>
    <div style="margin-top: 10px">
      {{ testData }}
    </div>
    <div style="margin-top: 10px">
      {{ msg }}
    </div>
  </div>
</template>

<script>
  import {getAction} from '@/api/manage'
  export default {
    data () {
      return {
        msg: "",
        testData: ''
      }
    },
    methods: {
      hello () {
        var param = { id: '王红'}
        var url = "/test/jeecgDemo/hello"
        getAction(url, param).then((res) => {
          if (res.success) {
            this.msg = res.result
          }
        })
      }
    },
    created() {
      this.hello();
    }
  }
</script>
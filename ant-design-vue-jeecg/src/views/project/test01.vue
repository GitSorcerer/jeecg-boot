<template>

  <div>
    <Button type="primary" shape="circle" icon="search" />
    {{ msg }}

  </div>
</template>

<script>
  import {getAction} from '@/api/manage'
  export default {
    data () {
      return {
        msg: ""
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
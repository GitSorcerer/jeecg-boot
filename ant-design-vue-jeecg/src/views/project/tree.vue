<template>
  <a-tree
    checkable
    :treeData="treeTest"
    :defaultExpandedKeys="['2']"
    :defaultSelectedKeys="['2' ]"
    :defaultCheckedKeys="['2' ]"
    @select="this.onSelect"
    @check="this.onCheck"
  >
    <span slot="title0010" style="color: #1890ff">sss</span>
  </a-tree>
</template>
<script>
  import {getAction} from '@/api/manage'
  const treeData = [{
    title: '湖北',
    key: 'hb',
    children: [{
      title: '武汉',
      key: 'wh',
      disabled: true,
      children: [
        { title: '武昌', key: 'wc', disableCheckbox: true },
        { title: '汉口', key: 'hank' },
      ],
    }, {
      title: '孝感',
      key: 'xiaog',
      children: [
        { key: 'xiaoc', slots: { title: '孝昌' }},
      ],
    }],
  }]

  export default {
    data () {
      return {
        treeData,
        treeTest: []
      }
    },
    methods: {
      loadTree(){
        var param = { id: '王红'}
        var url = "/test/treeTest"
        // var url = "/test/treeTest/hello"
        getAction(url, param).then((res) => {
          if (res.success) {
            console.log('data:')
            console.log(res.result)
            this.treeTest = res.result
          }
        })
      },
      onSelect (selectedKeys, info) {
        console.log('selected', selectedKeys, info)
      },
      onCheck (checkedKeys, info) {
        console.log('onCheck', checkedKeys, info)
      },
    },created(){
     this.loadTree()
    }
  }

</script>

<style scoped>

</style>
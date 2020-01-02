<template>
  <div>
    <div>
      <a-select
        mode="tags"
        style="width: 50%"
        placeholder="Tags Mode">
        <a-select-option v-for="i in 25" :key="(i + 9).toString(36) + i">{{(i + 9).toString(36) + i}}</a-select-option>
      </a-select>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'Select',
    method:{
      handleChange(value) {
        console.log(`selected ${value}`);
      }
    }
  }
</script>

<style scoped>

</style>
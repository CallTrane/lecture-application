<template>
  <div>
    <show-info></show-info>

    <!-- 人才培养计划 -->
    <el-card :body-style="{ padding: '10px 20px' }" class="myPlan">
      <h3>人才培养计划</h3>
    </el-card>

  </div>
</template>

<script>
import ShowInfo from './ShowInfo'

export default {
  name: 'Stuinfo',
  components: {
    ShowInfo
  },
  data() {
    return {}
  },
  created() {},
  methods: {}
}
</script>

<style scoped>
.myPlan {
  height: 300px;
  margin-top: 50px;
}
</style>
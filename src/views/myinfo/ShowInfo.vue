<template>
  <div>
    <el-card>
      <el-descriptions title="个人信息" :column="3" border>
        <el-descriptions-item label="姓名">{{myInfo.studentDO.name}}</el-descriptions-item>
        <el-descriptions-item label="学号">{{myInfo.studentDO.stuId}}</el-descriptions-item>

        <el-descriptions-item label="学院">{{myInfo.collegeMajorDO.collegeName}}</el-descriptions-item>
        <el-descriptions-item label="专业">{{myInfo.collegeMajorDO.majorName}}</el-descriptions-item>
        <el-descriptions-item label="年级">{{myInfo.studentDO.enrollment}}</el-descriptions-item>
        <el-descriptions-item label="毕业年份">{{myInfo.studentDO.graduation}}</el-descriptions-item>

        <el-descriptions-item label="校区">{{campusName}}</el-descriptions-item>
        <el-descriptions-item label="账号">{{myInfo.userDO.account}}</el-descriptions-item>
        <el-descriptions-item label="手机">{{myInfo.userDO.phone}}</el-descriptions-item>
        <el-descriptions-item label="邮箱">{{myInfo.userDO.email}}</el-descriptions-item>

        <!-- <el-descriptions-item label="备注">
          <el-tag size="small">学校</el-tag>
        </el-descriptions-item> -->

      </el-descriptions>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'showInfo',

  data() {
    return {
      myInfo: {}
    }
  },

  computed: {
    campusName() {
      switch (this.myInfo.studentDO.campusId) {
        case 1:
          return '广州校区'
          break
        case 2:
          return '佛山校区'
          break
        default:
          return ''
          break
      }
    }
  },
  created() {
    this.getStuInfo()
    console.log(this.myInfo)
  },
  methods: {
    getStuInfo() {
      console.log(this.$store.state.myInfo)
      console.log('类型', typeof this.$store.state.myInfo)

      if (typeof this.$store.state.myInfo == 'object') {
        this.myInfo = this.$store.state.myInfo
      } else {
        console.log('json parse')
        this.myInfo = JSON.parse(this.$store.state.myInfo)
      }
    }
  }
}
</script>

<style scoped>
</style>
<template>
  <div class="selection-contain">

    <!-- 搜索框 -->
    <el-card :body-style="{ padding: '10px 20px' }" class="search-card">
      <!-- 搜索 -->
      <el-row>

        <el-col :span="6">
          校&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;区：
          <el-select v-model="searchRequestBody.campusId" placeholder="请选择校区">
            <el-option label="全部" value=""></el-option>
            <el-option label="广州校区" value="1"></el-option>
            <el-option label="佛山校区" value="2"></el-option>
          </el-select>
        </el-col>

        <!-- 选修必修 -->
        <el-col :span="6">
          必修/选修：
          <el-select v-model="searchRequestBody.required" placeholder="请选择">
            <el-option label="全部" value=""></el-option>
            <el-option label="必修" value="1"></el-option>
            <el-option label="选修" value="0"></el-option>
          </el-select>
        </el-col>

        <!-- 星期 -->
        <el-col :span="6">
          星期：
          <el-select v-model="searchRequestBody.weekday" placeholder="请选择" @change="getLessonList(1)">
            <el-option label="全部" value=""></el-option>
            <el-option label="星期一" value="1"></el-option>
            <el-option label="星期二" value="2"></el-option>
            <el-option label="星期三" value="3"></el-option>
            <el-option label="星期四" value="4"></el-option>
            <el-option label="星期五" value="5"></el-option>
          </el-select>
        </el-col>

      </el-row>

      <el-row type="flex" justify="start">
        <!-- 课程名称 -->
        <el-col :span="6">
          课程名称：
          <el-input v-model="searchRequestBody.lessonName" placeholder="请输入内容" clearable></el-input>
        </el-col>

        <!-- 老师名称 -->
        <el-col :span="6">
          教师姓名：
          <el-input @blur="getLessonList(1)" v-model="searchRequestBody.teacherName" placeholder="请输入内容" clearable></el-input>
        </el-col>
      </el-row>

      <el-row type="flex" justify="start">

        <!-- 过滤已满 -->
        <el-col :span="6">
          <div class="switch">
            过滤已满：
            <el-switch @change="filterFull" v-model="isFilter" :width="50" active-color="#06b799"></el-switch>
          </div>
        </el-col>

        <!-- 只看专业课 -->
        <el-col :span="6">
          <div class="switch">
            只看专业课：
            <el-switch @change="onlyMajor" v-model="isMajor" :width="50" active-color="#06b799"></el-switch>
          </div>
        </el-col>

        <!-- 查询按钮 -->
        <el-col :span="6">
          <el-button @click="getLessonList()" type="primary" size="medium">查询</el-button>
        </el-col>

      </el-row>
    </el-card>

    <!-- 课程数据视图区域 -->

    <el-card class="lessons-card">

      <!-- 课程数据 -->
      <el-table :data="lessonData" stripe>
        <el-table-column label="#" type="index" align="center"></el-table-column>

        <!-- 课程ID -->
        <el-table-column prop="lid" label="课程ID" align="center"></el-table-column>

        <!-- 课程名称 -->
        <el-table-column prop="name" label="课程名称" align="center"></el-table-column>

        <!-- 校区 -->
        <el-table-column label="校区" align="center">
          <template slot-scope="scope">
            <span>{{scope.row.campusId===1?'广州校区':'佛山校区'}}</span>
          </template>
        </el-table-column>

        <!-- 学分 -->
        <el-table-column prop="credit" label="学分" align="center" width="50"></el-table-column>

        <el-table-column prop="teacherName" label="教师名称" align="center"></el-table-column>

        <el-table-column label="上课时间" align="center">
          <template slot-scope="scope">
            <span>{{formatWeekday(scope.row.weekday,scope.row.classes)}}</span>
          </template>
        </el-table-column>

        <el-table-column label="必修/选修" align="center">
          <template slot-scope="scope">
            <span>{{scope.row.required?'必修':'选修'}}</span>
          </template>
        </el-table-column>

        <el-table-column label="剩余可选" align="center">
          <template slot-scope="scope">
            <span>{{scope.row.remainPeople}} / {{scope.row.totalPeople}}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button @click="selectClick(scope.row.lid)" :disabled="isDisabled(scope.row.lid)" :plain="isDisabled(scope.row.lid)" :type="isDisabled(scope.row.lid)?'info':'primary'" size="small">{{isDisabled(scope.row.lid)?'已选':'选课'}}</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :page-sizes="[5, 10, 20]" :page-size="searchRequestBody.pageSize" :current-page="searchRequestBody.pageIndex" :total="lessonTotal" layout="total, sizes, prev, pager, next, jumper" background>
      </el-pagination>

    </el-card>

  </div>
</template>

<script>
import {
  getLessonList,
  selectLesson,
  getLessonCount,
  getLessonSelected
} from 'network/selection'

export default {
  inject: ['reload'],
  name: 'Selection',
  components: {},
  data() {
    return {
      // 是否过滤已满
      isFilter: false,
      // 是否只看专业课
      isMajor: false,
      // 选课弹窗是否可见
      selectDialogVisible: false,
      // 课程数据
      lessonData: [
        // 测试数据
        {
          name: 'java',
          weekday: 1,
          classes: 1,
          collegeId: 101,
          majorId: 1011,
          teacherName: '老师A',
          period: 1,
          credit: 2,
          remainPeople: 0,
          totalPeople: 30,
          required: 1,
          lid: 140001
        },
        {
          name: '计算机网络',
          weekday: 6,
          classes: 2,
          collegeId: 101,
          majorId: 1011,
          teacherName: '老师D',
          period: 1,
          credit: 4,
          remainPeople: 5,
          totalPeople: 15,
          required: 0,
          lid: 140002
        }
      ],

      searchRequestBody: {
        // 专业课Id
        majorId: '',
        // 当前页码
        pageIndex: 1,
        // 每页数据量
        pageSize: 10,
        // 是否过滤已满
        filter: 0,
        campusId: '',
        required: '',
        weekday: '',
        lessonName: '',
        teacherName: ''
      },

      // 课程总数
      lessonTotal: 0,
      // 学生已选课程
      lessonSelected: []
    }
  },

  // 计算属性
  computed: {
    // 专业id
    majorId() {
      return this.$store.state.myInfo.collegeMajorDO.majorId
    }
  },
  created() {
    // 获取课程列表信息
    this.getLessonList()
    // 获取课程总数
    this.getLessonCount()
    // 获取学生已选课程
    this.getLessonSelected()
  },

  // activated() {},
  // destroyed() {
  //   this.reload()
  // },
  deactivated() {
    // this.reload()
  },

  methods: {
    // 查询课程 /  获取课程列表信息
    async getLessonList(pageIndex = 1) {
      console.log('pageIndex', pageIndex)
      // 查询后回到第一页
      this.searchRequestBody.pageIndex = pageIndex
      const { data: res } = await getLessonList(this.searchRequestBody)
      // 错误
      if (res.status === 500) {
        return this.$message.error(res.message)
      }
      console.log('查询课程结果', res)
      this.lessonData = res.data
      this.lessonTotal = res.pageCount
      console.log('getLessonList', this.lessonData)
    },

    // 获取当前学生已选课程
    async getLessonSelected() {
      // 获取学生id
      const stuId = this.$store.state.myInfo.studentDO.stuId
      const { data: res } = await getLessonSelected(stuId)
      this.lessonSelected = res
      // console.log('学生已选课程', res)
    },

    // 过滤已满课程
    filterFull(isFilter) {
      if (isFilter) {
        this.searchRequestBody.filter = 1
        // 重新获取列表
        this.getLessonList()
      } else {
        this.searchRequestBody.filter = 0
        this.getLessonList()
      }

      // if (isFilter) {
      //   this.lessonData = this.lessonData.filter(value => {
      //     return value.remainPeople !== 0
      //   })
      //   console.log(this.lessonData)
      // } else {
      //   this.searchClick()
      // }
    },

    // 是否只看专业课
    onlyMajor(isMajor) {
      // console.log(this.majorId)
      // 只看专业课
      if (isMajor) {
        this.searchRequestBody.majorId = this.majorId
        this.getLessonList()
      } else {
        this.searchRequestBody.majorId = ''
        this.getLessonList()
      }
    },

    // 传入星期几，第几节
    formatWeekday(weekday, classes) {
      switch (weekday) {
        case 1:
          return '星期一，' + '第' + classes + '节'
          break
        case 2:
          return '星期二，' + '第' + classes + '节'
          break
        case 3:
          return '星期三，' + '第' + classes + '节'
          break
        case 4:
          return '星期四，' + '第' + classes + '节'
          break
        case 5:
          return '星期五，' + '第' + classes + '节'
          break
        default:
          break
      }
    },

    // 选课按钮
    async selectClick(lid) {
      // 弹框提示用户
      const confirmResult = await this.$confirm('确定选这节课?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).catch(err => err)

      // 如果确认，则返回字符串 “confirm”
      // 如果取消，则返回字符串 cancel
      console.log(confirmResult)

      if (confirmResult !== 'confirm') {
        return this.$message.info('已取消选课')
      }

      // 获取学生id
      const stuId = this.$store.state.myInfo.studentDO.stuId
      // 选课请求
      const res = await selectLesson(stuId, lid)
      console.log(res)

      if (res.status === 200) {
        this.$message.success('选课成功')
        this.getLessonList(this.searchRequestBody.pageIndex)
        this.getLessonSelected()
        // this.reload()
      } else {
        return this.$message.error(res.data.message)
      }
    },

    //获取课程总数
    async getLessonCount() {
      const { data: res } = await getLessonCount()
      this.lessonTotal = res
      // console.log('课程总数', this.lessonTotal)
    },

    // 监听一页显示数的改变
    handleSizeChange(newSize) {
      this.searchRequestBody.pageSize = newSize
      this.getLessonList()
    },

    // 监听当前页码的改变
    handleCurrentChange(newPage) {
      // this.searchRequestBody.pageIndex = newPage
      this.getLessonList(newPage)
    },

    // 计算是否禁用选课按钮
    isDisabled(lid) {
      let res = this.lessonSelected.some(item => {
        // console.log(item.lid)
        return item.lid === lid
      })
      // console.log('是否禁用', res)
      return res
    }
  }
}
</script>

<style scoped>
.selection-contain {
  overflow: hidden;
  width: 100%;
}

/* 搜索区域 */
/* .search-card {
  position: fixed;
  width: 100%;
  z-index: 9;
} */

/* 课程展示区域 */
/* .lessons-card {
  overflow: hidden;
  margin-top: 130px;
} */

/* 下拉长度 */
.el-select {
  width: 120px;
  font-size: 13px;
}

/* 输入框长度 */
.el-input {
  width: 180px;
}

.el-row {
  margin-bottom: 20px;
}

/* 按钮样式 */
.el-button {
  background-color: #06b799;
}

.el-table-column {
  text-align: center;
}

.switch {
  display: flex;
  align-items: center;
  height: 40px;
}
</style>
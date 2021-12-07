<template>
  <div>
    <div class="userInfo">
      <div class="avatar">
        <img src="~assets/img/avatar/avatar.jpg" alt="" />
      </div>
      <div class="Info">
        <span>用户: {{ userInfo.studentDO.name }}</span>
        <span>学号: {{ userInfo.studentDO.stuId }}</span>
        <span>学院: {{ userInfo.collegeMajorDO.collegeName }}</span>
        <span>专业: {{ userInfo.collegeMajorDO.majorName }}</span>
      </div>
    </div>
    <div class="table">
      <table class="tableContent" border="1px" cellpadding="0" cellspacing="0">
        <tr class="column">
          <th></th>
          <th>星期一</th>
          <th>星期二</th>
          <th>星期三</th>
          <th>星期四</th>
          <th>星期五</th>
        </tr>
        <tr>
          <th class="column">第一节</th>
          <th>{{ class11.name }}<br />{{ class11.teacherName }}</th>
          <th>{{ class21.name }}<br />{{ class21.teacherName }}</th>
          <th>{{ class31.name }}<br />{{ class31.teacherName }}</th>
          <th>{{ class41.name }}<br />{{ class41.teacherName }}</th>
          <th>{{ class51.name }}<br />{{ class51.teacherName }}</th>
        </tr>
        <tr>
          <th class="column">第二节</th>
          <th>{{ class12.name }}<br />{{ class12.teacherName }}</th>
          <th>{{ class22.name }}<br />{{ class22.teacherName }}</th>
          <th>{{ class32.name }}<br />{{ class32.teacherName }}</th>
          <th>{{ class42.name }}<br />{{ class42.teacherName }}</th>
          <th>{{ class52.name }}<br />{{ class52.teacherName }}</th>
        </tr>
        <tr>
          <th class="column">第三节</th>
          <th>{{ class13.name }}<br />{{ class13.teacherName }}</th>
          <th>{{ class23.name }}<br />{{ class23.teacherName }}</th>
          <th>{{ class33.name }}<br />{{ class33.teacherName }}</th>
          <th>{{ class43.name }}<br />{{ class43.teacherName }}</th>
          <th>{{ class53.name }}<br />{{ class53.teacherName }}</th>
        </tr>
        <tr>
          <th class="column">第四节</th>
          <th>{{ class14.name }}<br />{{ class14.teacherName }}</th>
          <th>{{ class24.name }}<br />{{ class24.teacherName }}</th>
          <th>{{ class34.name }}<br />{{ class34.teacherName }}</th>
          <th>{{ class44.name }}<br />{{ class44.teacherName }}</th>
          <th>{{ class54.name }}<br />{{ class54.teacherName }}</th>
        </tr>
        <tr>
          <th class="column">第五节</th>
          <th>{{ class15.name }}<br />{{ class15.teacherName }}</th>
          <th>{{ class25.name }}<br />{{ class25.teacherName }}</th>
          <th>{{ class35.name }}<br />{{ class35.teacherName }}</th>
          <th>{{ class45.name }}<br />{{ class45.teacherName }}</th>
          <th>{{ class55.name }}<br />{{ class55.teacherName }}</th>
        </tr>
        <tr>
          <th class="column">第六节</th>
          <th>{{ class16.name }}<br />{{ class16.teacherName }}</th>
          <th>{{ class26.name }}<br />{{ class26.teacherName }}</th>
          <th>{{ class36.name }}<br />{{ class36.teacherName }}</th>
          <th>{{ class46.name }}<br />{{ class46.teacherName }}</th>
          <th>{{ class56.name }}<br />{{ class56.teacherName }}</th>
        </tr>
        <tr>
          <th class="column">第七节</th>
          <th>{{ class17.name }}<br />{{ class17.teacherName }}</th>
          <th>{{ class27.name }}<br />{{ class27.teacherName }}</th>
          <th>{{ class37.name }}<br />{{ class37.teacherName }}</th>
          <th>{{ class47.name }}<br />{{ class47.teacherName }}</th>
          <th>{{ class57.name }}<br />{{ class57.teacherName }}</th>
        </tr>
        <tr>
          <th class="column">第八节</th>
          <th>{{ class18.name }}<br />{{ class18.teacherName }}</th>
          <th>{{ class28.name }}<br />{{ class28.teacherName }}</th>
          <th>{{ class38.name }}<br />{{ class38.teacherName }}</th>
          <th>{{ class48.name }}<br />{{ class48.teacherName }}</th>
          <th>{{ class58.name }}<br />{{ class58.teacherName }}</th>
        </tr>
      </table>
    </div>
  </div>
</template>

<script>
import { getPersonTable } from 'network/timeTable'
export default {
  name: 'Timetable',
  data() {
    return {
      userInfo: {},
      stuId: '',
      classData: [],
      class11: {},
      class21: {},
      class31: {},
      class41: {},
      class51: {},
      class12: {},
      class22: {},
      class32: {},
      class42: {},
      class52: {},
      class13: {},
      class23: {},
      class33: {},
      class43: {},
      class53: {},
      class14: {},
      class24: {},
      class34: {},
      class44: {},
      class54: {},
      class15: {},
      class25: {},
      class35: {},
      class45: {},
      class55: {},
      class16: {},
      class26: {},
      class36: {},
      class46: {},
      class56: {},
      class17: {},
      class27: {},
      class37: {},
      class47: {},
      class57: {},
      class18: {},
      class28: {},
      class38: {},
      class48: {},
      class58: {}
    }
  },

  created() {
    this.getUserInfo()
    this.getClassTable(this.stuId)
  },
  methods: {
    // 获取用户信息
    getUserInfo() {
      this.userInfo = JSON.parse(sessionStorage.getItem('myInfo'))
      console.log(this.userInfo)
      this.stuId = this.userInfo.studentDO.stuId
    },
    // 获取用户课表
    getClassTable(stuId) {
      getPersonTable(stuId).then(res => {
        console.log('class', res)
        this.classData = [...res.data]
        this.classData.map(item => {
          console.log('item', item)
          if (item.weekday === 1 && item.classes === 1) {
            this.class11 = item
          } else if (item.weekday === 2 && item.classes === 1) {
            this.class21 = item
          } else if (item.weekday === 3 && item.classes === 1) {
            this.class31 = item
          } else if (item.weekday === 4 && item.classes === 1) {
            this.class41 = item
          } else if (item.weekday === 5 && item.classes === 1) {
            this.class51 = item
          } else if (item.weekday === 1 && item.classes === 2) {
            this.class12 = item
          } else if (item.weekday === 2 && item.classes === 2) {
            this.class22 = item
          } else if (item.weekday === 3 && item.classes === 2) {
            this.class32 = item
          } else if (item.weekday === 4 && item.classes === 2) {
            this.class42 = item
          } else if (item.weekday === 5 && item.classes === 2) {
            this.class52 = item
          } else if (item.weekday === 1 && item.classes === 3) {
            this.class13 = item
          } else if (item.weekday === 2 && item.classes === 3) {
            this.class23 = item
          } else if (item.weekday === 3 && item.classes === 3) {
            this.class33 = item
          } else if (item.weekday === 4 && item.classes === 3) {
            this.class43 = item
          } else if (item.weekday === 5 && item.classes === 3) {
            this.class53 = item
          } else if (item.weekday === 1 && item.classes === 4) {
            this.class14 = item
          } else if (item.weekday === 2 && item.classes === 4) {
            this.class24 = item
          } else if (item.weekday === 3 && item.classes === 4) {
            this.class34 = item
          } else if (item.weekday === 4 && item.classes === 4) {
            this.class44 = item
          } else if (item.weekday === 5 && item.classes === 4) {
            this.class54 = item
          } else if (item.weekday === 1 && item.classes === 5) {
            this.class15 = item
          } else if (item.weekday === 2 && item.classes === 5) {
            this.class25 = item
          } else if (item.weekday === 3 && item.classes === 5) {
            this.class35 = item
          } else if (item.weekday === 4 && item.classes === 5) {
            this.class45 = item
          } else if (item.weekday === 5 && item.classes === 5) {
            this.class55 = item
          } else if (item.weekday === 1 && item.classes === 6) {
            this.class16 = item
          } else if (item.weekday === 2 && item.classes === 6) {
            this.class26 = item
          } else if (item.weekday === 3 && item.classes === 6) {
            this.class36 = item
          } else if (item.weekday === 4 && item.classes === 6) {
            this.class46 = item
          } else if (item.weekday === 5 && item.classes === 6) {
            this.class56 = item
          } else if (item.weekday === 1 && item.classes === 7) {
            this.class17 = item
          } else if (item.weekday === 2 && item.classes === 7) {
            this.class27 = item
          } else if (item.weekday === 3 && item.classes === 7) {
            this.class37 = item
          } else if (item.weekday === 4 && item.classes === 7) {
            this.class47 = item
          } else if (item.weekday === 5 && item.classes === 7) {
            this.class57 = item
          } else if (item.weekday === 1 && item.classes === 8) {
            this.class18 = item
          } else if (item.weekday === 2 && item.classes === 8) {
            this.class28 = item
          } else if (item.weekday === 3 && item.classes === 8) {
            this.class38 = item
          } else if (item.weekday === 4 && item.classes === 8) {
            this.class48 = item
          } else if (item.weekday === 5 && item.classes === 8) {
            this.class58 = item
          }
        })
        //   this.classData.map(item => {
        //     if (item.weekday === 1 && item.classes === 1) {
        //       this.class11 = item
        //     } else if (item.weekday === 1 && item.classes === 2) {}
        //   })
        // })
      })
    }
  }
}
</script>

<style scoped>
.userInfo {
  display: flex;
  flex-direction: row;
  width: 500px;
  margin-left: 100px;
}
.userInfo .Info {
  display: flex;
  flex-wrap: wrap;
  font-size: 16px;
}
.Info {
  margin-left: 50px;
}

.Info span {
  margin: 10px;
}

.Info span:nth-child(even) {
  margin-left: 25px;
}

.avatar img {
  /* margin-top: 10px; */
  width: 100px;
  height: 80px;
  border-radius: 5%;
}
.table {
  font-size: 16px;
  margin: 10px 10px;
}
.tableContent {
  margin: 25px 50px;
  width: 90%;
}
.tableContent .column {
  background-color: #c7e1f5;
}
.tableContent th {
  padding: 20px;
  line-height: 25px;
}
.column {
  white-space: nowrap;
}
</style>
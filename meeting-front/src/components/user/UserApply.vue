<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li>
          <router-link to="/user">会议室列表</router-link>
        </li>
        <li><a href="#">会议室申请</a></li>
      </ol>
      <hr>
      <div class="alert alert-danger" role="alert">
        <strong>注意：</strong>所有会议室申请时间至少<strong>提前2小时</strong>，如有紧急情况请立即电话联系管理员！———&nbsp;&nbsp;<span
          class="glyphicon glyphicon-phone"></span>(+86)13889366982</div>
      <div class="row">
        <div class="col-md-8">
          <div>
            <div class="form-group">
              <label for="meetingItem">会议主题</label>
              <input type="text" class="form-control" id="meetingItem" placeholder="请输入会议主题" v-model="meetingTheme">
              <label for="department">申请部门</label>
              <input type="text" class="form-control" id="department" placeholder="请输入申请部门" readonly="readonly"
                v-model="depName">
              <label for="number">参加会议人数</label>
              <input type="text" class="form-control" id="number" placeholder="请如实填写参加会议总人数" v-model="personCount">
            </div>
            <div class="form-group">
              <label>请选择会议时间段&nbsp;&nbsp;&nbsp;</label>
              <date-picker :date="startTime" :option="multiOption" :limit="limit1"></date-picker>
              ---------
              <date-picker :date="endTime" :option="multiOption" :limit="limit2"></date-picker>
            </div>
            <div class="form-group">
              <label for="meetingItem">会议内容</label>
              <textarea class="form-control" rows="4" placeholder="请输入会议内容" v-model="meetingDigest"></textarea>
            </div>
            <button type="submit" class="btn btn-default" @click="insertRecord()">提交申请</button>
          </div>
        </div>
        <div class="col-md-4">
          <div class="panel panel-default">
            <div class="panel-heading">会议室详情</div>
            <div class="panel-body">
              <ul class="list-group">
                <li class="list-group-item">
                  <span class="badge">{{roomNo}}</span>
                  会议室门牌号
                </li>
                <li class="list-group-item">
                  <span class="badge">{{roomFloor}}</span>
                  会议室楼层
                </li>
                <li class="list-group-item">
                  <span class="badge">{{roomType}}</span>
                  会议室类型
                </li>
                <li class="list-group-item">
                  <span class="badge">{{roomSize}}</span>
                  会议室容纳人数
                </li>
                <li class="list-group-item">
                  <span class="badge">{{roomStatus}}</span>
                  会议室状态
                </li>
                <li class="list-group-item">
                  <span class="badge">{{applyCount}}</span>
                  会议室被申请次数
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="alert alert-danger" role="alert">
            <strong>注意：</strong>以下是该会议室已被申请的时间，请选好自己申请的时间，不要冲突。
          </div>
        </div>
        <div class="col-md-6">
          <div class="alert alert-info" role="alert">
            <strong>注意：</strong>以下是该部门已经申请的会议时间，请选择好新的会议时间，不要冲突。
          </div>
        </div>

      </div>
      <div class="row">
        <div class="col-md-6">
          <div class="table-responsive">
            <table class="table table-hover table-striped table-bordered">
              <thead>
                <tr>
                  <th>申请部门</th>
                  <th>会议日期</th>
                  <th>开始时间</th>
                  <th>结束时间</th>
                </tr>
              </thead>
              <tbody v-for="(item, index) in getRoomListRecord" :key="index">
                <tr>
                  <td>{{item.depName}}</td>
                  <td>{{item.meetingDate}}</td>
                  <td>{{item.startTime}}</td>
                  <td>{{item.endTime}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="col-md-6">
          <div class="table-responsive">
            <table class="table table-hover table-striped table-bordered">
              <thead>
                <tr>
                  <th>会议室门牌号</th>
                  <th>会议日期</th>
                  <th>开始时间</th>
                  <th>结束时间</th>
                </tr>
              </thead>
              <tbody v-for="(item, index) in getDepListRecord" :key="index">
                <tr>
                  <td>{{item.roomNo}}</td>
                  <td>{{item.meetingDate}}</td>
                  <td>{{item.startTime}}</td>
                  <td>{{item.endTime}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </div>
  </div>
</template>

<script>
  import qs from 'qs'
  import myDatepicker from 'vue-datepicker/vue-datepicker-es6.vue'
  import Cookies from 'js-cookie';

  export default {
    components: {
      'date-picker': myDatepicker
    },
    data() {
      return {
        startTime: { // 开始时间
          time: ''
        },
        endTime: { // 结束时间
          time: ''
        },
        timeoption: {
          type: 'min', // day , multi-day
          week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
          month: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
          format: 'YYYY-M-D HH:mm', // YYYY-MM-DD 日期
          inputStyle: { // input 样式
            'display': 'inline-block',
            'padding': '6px',
            'line-height': '22px',
            'width': '160px',
            'font-size': '16px',
            'border': '2px solid #fff',
            'box-shadow': '0 1px 3px 0 rgba(0, 0, 0, 0.2)',
            'border-radius': '2px',
            'color': '#5F5F5F',
            'margin': '0'
          },
          color: { // 字体颜色
            header: '#35acff', // 头部
            headerText: '#fff', // 头部文案 
          },
          buttons: { // button 文案
            ok: '确定',
            cancel: '取消'
          },
          overlayOpacity: 0.5, // 遮罩透明度
          placeholder: '请选时间', // 提示日期
          dismissible: true // 默认true  待定
        },
        multiOption: {
          type: 'min',
          week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
          month: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
          format: "YYYY-M-D HH:mm",
          inputStyle: {
            'display': 'inline-block',
            'padding': '6px',
            'line-height': '22px',
            'width': '160px',
            'font-size': '16px',
            'border': '2px solid #fff',
            'box-shadow': '0 1px 3px 0 rgba(0, 0, 0, 0.2)',
            'border-radius': '2px',
            'color': '#5F5F5F',
            'margin': '0'
          },
          color: { // 字体颜色
            header: '#35acff', // 头部
            headerText: '#fff', // 头部文案 
          },
          buttons: { // button 文案
            ok: '确定',
            cancel: '取消'
          },

          placeholder: '请选时间',
          dismissible: true
        },
        limit1: [{
            type: 'weekday',
            available: [1, 2, 3, 4, 5, 6, 0]
          },
          {
            type: 'fromto',
            from: new Date((new Date()).getTime() - 24 * 60 * 60 * 1000),
            to: '2050-02-20'
          }
        ],
        limit2: [{
            type: 'weekday',
            available: [1, 2, 3, 4, 5, 6, 0]
          },
          {
            type: 'fromto',
            from: new Date((new Date()).getTime() - 24 * 60 * 60 * 1000),
            to: '2050-02-20'
          }
        ],

        roomNo: '',
        roomFloor: '',
        roomType: '',
        roomSize: '',
        roomStatus: '',

        depId: '',
        meetingTheme: '',
        depName: '',
        personCount: '',
        meetingDigest: '',

        resultNum: '',

        applyCount: ''


      }
    },
    computed: {
      // 从vuex中取出存储的当前会议室从现在开始的日期往后，被申请的记录
      getRoomListRecord() {
        return this.$store.state.roomListRecordItems
      },

      // 从vuex中取出存储的当前部门从现在开始的日期往后，申请的会议室时间段
      getDepListRecord() {
        return this.$store.state.depListRecordItems
      },
    },
    created() {
      this.getApplyId()
      this.showDepName()
      scroll(0, 0)
    },
    mounted() {
      this.listRecordFromNow()
      this.intervalid1 = setInterval(this.listRecordFromNow, 1000);
    },
    beforeDestroy() {
      clearInterval(this.intervalid1)
    },
    methods: {
      // 根据传过来的id获取会议室信息
      getApplyId() {
        // console.log("time"+this.startTime.time)
        let that = this
        let id = this.$route.params.id
        let data = qs.stringify({
          "id": id
        })
        this.$axios.get('/room/getById?' + data)
          .then(res => {
            // this.$store.commit("setApplyRooms", res.data)  --------------此思想暂时失败，以后再试

            // 赋值当前会议室的信息
            that.roomNo = res.data.roomNo
            that.roomFloor = res.data.roomFloor
            that.roomType = res.data.roomType
            that.roomSize = res.data.roomSize
            that.roomStatus = res.data.roomStatus

            // 判断roomStatus的值 0-维修 1-可用
            if (that.roomStatus == 1) {
              that.roomStatus = '可用'
            } else if (that.roomStatus == 0) {
              that.roomStatus = '维修'
            }

          })

        // 根据roomid查询该会议室被申请的记录次数
        this.$axios.get('/apply/listRecordByRoomId?roomId=' + this.$route.params.id)
          .then(res => {
            this.applyCount = res.data.length
          })
      },
      // 回显并获取部门名称
      showDepName() {
        // 获取Cookie中存储的部门编号
        this.userNo = Cookies.get('username')
        // 根据部门编号查询部门名称,并赋值给data中定义的username
        let data = {
          "depNo": this.userNo
        }
        let that = this
        this.$axios.post('/department/listByCondition', data)
          .then(res => {
            that.depId = res.data[0].depId
            that.depName = res.data[0].depName
          })

      },
      // 添加用户申请记录
      insertRecord() {
        // 日期格式化方法
        Date.prototype.pattern = function (fmt) {
          var o = {
            "M+": this.getMonth() + 1, //月份         
            "d+": this.getDate(), //日         
            "h+": this.getHours() % 12 == 0 ? 12 : this.getHours() % 12, //小时         
            "H+": this.getHours(), //小时         
            "m+": this.getMinutes(), //分         
            "s+": this.getSeconds(), //秒         
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度         
            "S": this.getMilliseconds() //毫秒         
          };
          var week = {
            "0": "/u65e5",
            "1": "/u4e00",
            "2": "/u4e8c",
            "3": "/u4e09",
            "4": "/u56db",
            "5": "/u4e94",
            "6": "/u516d"
          };
          if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
          }
          if (/(E+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "/u661f/u671f" :
              "/u5468") : "") + week[this.getDay() + ""]);
          }
          for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
              fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k])
                .length)));
            }
          }
          return fmt;
        }
        // 发起提交申请的请求
        let depData = {
          "applicationRecord": {
            "depId": this.depId + '',
            "roomId": parseInt(this.$route.params.id),
            "applyTime": new Date().pattern("yyyy-MM-dd hh:mm:ss")
          },
          "meetingRecord": {
            "meetingTheme": this.meetingTheme,
            "personCount": parseInt(this.personCount),
            "startTime": this.startTime.time + ":00",
            "endTime": this.endTime.time + ":00",
            "meetingDigest": this.meetingDigest,
          }
        }
        // 判断用户选择的会议室时间是否有冲突
        let timeData = {
          "startTime": this.startTime.time + ":00",
          "endTime": this.endTime.time + ":00",
          "roomId": parseInt(this.$route.params.id)
        }
        let that = this
        this.$axios.post('/apply/listTimeConflict', timeData)
          .then(res => {
            that.resultNum = res.data.length
            // 判断用户输入是否为空
            let ex = /^\d+$/; // 正整数正则表达式
            if (that.resultNum > 0) {
              alert('对不起，您选择的会议时间与已存在的会议时间冲突，请修改您的会议时间！')
            } else if (depData.meetingRecord.meetingTheme.length <= 1) {
              alert("请输入会议主题！")
            } else if (depData.meetingRecord.personCount == "") {
              alert("请输入会议总人数！")
            } else if (depData.meetingRecord.personCount == 0 || !ex.test(depData // 判断输入的会议人数是否为正整数
                .meetingRecord.personCount)) {
              alert("请正确输入会议总人数！")
            } else if (depData.meetingRecord.meetingDigest.length <= 1) {
              alert('请输入会议内容')
            } else {
              // 判断输入的参加会议人数是否远远超出会议室容量
              let id = this.$route.params.id
              let countData = qs.stringify({
                "id": id
              })
              this.$axios.get('/room/getById?' + countData)
                .then(res => {
                  if (depData.meetingRecord.personCount > res.data.roomSize + 10) {
                    alert('对不起，您输入的会议人数已远超会议室容纳人数，请选择其他适合的会议室！')
                  } else {
                    // 满足以上需求，提交申请请求
                    this.$axios.post('/apply/insert', depData)
                      .then(res => {
                        if (res.data == true) {
                          alert('提交申请成功！请注意查看申请记录，确认申请是否通过！')
                          this.$router.push('/user/record')
                        } else if (res == false) {
                          alert('申请失败！')
                          console.log(res)
                        }
                      })
                  }
                })
            }
          })

      },
      // 根据会议室id查询从此时刻开始已经被申请的记录 以及 根据部门id查询从此时刻已经申请的会议室记录
      listRecordFromNow() {
        // 获取Cookie中存储的部门编号
        this.userNo = Cookies.get('username')
        // 根据部门编号查询部门Id
        let data = {
          "depNo": this.userNo
        }
        let that = this
        this.$axios.post('/department/listByCondition', data)
          .then(res => {
            let data = qs.stringify({
              "roomId": parseInt(this.$route.params.id),
              "depId": res.data[0].depId
            })
            // console.log(data)
            this.$axios.post('/apply/listRecordFromNow', data)
              .then(res => {
                // console.log(res.data.roomsRecordsFromNow)
                // console.log(res.data.depRecordsFromNow)
                this.$store.commit("setRoomListRecord", res.data.roomsRecordsFromNow)
                this.$store.commit("setDepListRecord", res.data.depRecordsFromNow)
              })
          })

      },

    }
  }
</script>

<style scoped>
  h2 {
    margin-left: 40%;
  }

  hr {
    color: #0099ff;
  }

  .row {
    margin-top: 20px;
  }
</style>
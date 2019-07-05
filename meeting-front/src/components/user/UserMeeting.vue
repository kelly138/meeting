<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li><a href="#">会议室列表</a></li>
      </ol>
      <hr>
      <div class="row">
        <div class="form-inline">
          <div class="form-group">
            <select class="form-control" v-model="meetingCount">
              <option disabled>--请选择会议室容纳人数--</option>
              <option>不限</option>
              <option>20</option>
              <option>40</option>
              <option>60</option>
              <option>100</option>
              <option>1000</option>
            </select>
          </div>
          <div class="form-group">
            <select class="form-control" v-model="meetingFloor">
              <option disabled>--请选择会议室楼层--</option>
              <option>不限</option>
              <option>一层</option>
              <option>二层</option>
              <option>三层</option>
              <option>四层</option>
              <option>五层</option>
              <option>六层</option>
              <option>七层</option>
              <option>八层</option>
            </select>
          </div>
          <div class="form-group">
            <select class="form-control" v-model="meetingType">
              <option disabled>--请选择会议室类型--</option>
              <option>不限</option>
              <option>多媒体小型会议室</option>
              <option>多媒体中型会议室</option>
              <option>多媒体大型会议室</option>
              <option>多媒体超大型会议室</option>
              <option>多媒体特大型会议室</option>
            </select>
          </div>
          <div class="form-group">
            <select class="form-control" v-model="meetingStatus">
              <option disabled>--请选择会议室状态--</option>
              <option>不限</option>
              <option>可用</option>
              <option>维修</option>
            </select>
          </div>
          <div class="form-group">
            <div class="form-group">
              <label class="sr-only" for="number">门牌号</label>
              <input type="text" class="form-control" id="number" placeholder="请输入会议室门牌号" v-model="meetingNum">
              <button type="submit" class="btn btn-default" @click="showOneMeeting()">查询</button>
            </div>
          </div>
          <div class="form-group">
            <div class="form-group">
              <button type="submit" class="btn btn-primary" @click="showAllMeetings()">显示全部会议室</button>
            </div>
          </div>

        </div>
      </div>
      <div class="row">
        <div class="table-responsive">
          <table class="table table-hover table-striped table-bordered">
            <thead>
              <tr>
                <th>会议室门牌号</th>
                <th>会议室楼层</th>
                <th>会议室类型</th>
                <th>会议室容纳人数</th>
                <th>会议室状态</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody v-for="(item) in getRooms" :key="item.roomId">
              <tr>
                <td>{{item.roomNo}}</td>
                <td>{{item.roomFloor}}</td>
                <td>{{item.roomType}}</td>
                <td>{{item.roomSize}}</td>
                <td v-if="item.roomStatus == 1">可用</td>
                <td v-if="item.roomStatus == 0">维修</td>
                <td v-if="item.roomStatus == 1">
                  <router-link :to="'/user/apply/' + item.roomId">
                    点击申请
                  </router-link>
                </td>
                <td v-if="item.roomStatus == 0">
                  <a href="#" disabled:true style="pointer-events:none; color:#A0A0A0;">不可申请</a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import qs from 'qs'

  export default {
    data() {
      return {
        meetingCount: '--请选择会议室容纳人数--',
        meetingFloor: '--请选择会议室楼层--',
        meetingType: '--请选择会议室类型--',
        meetingStatus: '--请选择会议室状态--',
        meetingNum: '',
        // applyCount:''
      }
    },
    // 页面初始化完成后即显示会议室列表数据
    created() {
      this.showAllMeetings()
    },
    computed: {
      getRooms() {
        // 在vux中获取数据
        return this.$store.state.roomItems
      }
    },
    methods: {
      // 显示全部会议室
      showAllMeetings() {
        this.$axios.get('/room/listAll')
          .then(res => {
            // console.log(res.status)
            if (res.status == 200) {
              this.$store.commit("setRooms", res.data)
            } else {
              alert('会议室列表显示失败！')
            }
          })
      },
      // 按条件显示会议室
      showOneMeeting() {
        if (/^[0-9]+.?[0-9]*/.test(this.meetingNum) || this.meetingNum == '') {
          let data = {
            "roomSize": this.meetingCount,
            "roomFloor": this.meetingFloor,
            "roomType": this.meetingType,
            "roomStatus": this.meetingStatus,
            "roomNo": this.meetingNum,
          }

          // 规范data参数
          if ((data.roomSize === '--请选择会议室容纳人数--') || (data.roomSize === '不限')) {
            delete data['roomSize']
          }

          if ((data.roomFloor === '--请选择会议室楼层--') || (data.roomFloor === '不限')) {
            delete data['roomFloor']
          }

          if ((data.roomType === '--请选择会议室类型--') || (data.roomType === '不限')) {
            delete data['roomType']
          }

          if ((data.roomStatus === '--请选择会议室状态--') || (data.roomStatus === '不限')) {
            delete data['roomStatus']
          }
          if ((data.roomStatus === '可用')) {
            data.roomStatus = 1
          }
          if ((data.roomStatus === '维修')) {
            data.roomStatus = 0
          }
          if (data.roomNo === '') {
            delete data['roomNo']
          }

          this.$axios.post('/room/listByCondition', data)
            .then(res => {
              if (res.status == 200) {
                this.$store.commit("setRooms", res.data)
              } else {
                alert('会议室列表显示失败！')
              }
              this.$store.commit("setRooms", res.data)
            })
        } else {
          alert('请输入正确的会议室门牌号！')
        }

      },

      //  // 根据roomid查询该会议室被申请的记录次数
      //  showCount(id){
      //    this.$axios.get('/apply/listRecordByRoomId?roomId=' + id)
      //     .then(res => {
      //       this.applyCount = res.data.length
      //     })
      //  }
        
      
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
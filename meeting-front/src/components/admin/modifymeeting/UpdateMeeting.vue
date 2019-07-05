<template>
  <div class="container">
    <ol class="breadcrumb">
      <li>当前位置</li>
      <li>
        <router-link to="/admin/meetingmanage">会议室信息管理</router-link>
      </li>
      <li><a href="#">修改会议室信息</a></li>
    </ol>
    <hr>
    <div class="row">
      <div class="col-md-2"></div>
      <div class="col-md-8">
        <div class="form-horizontal">
          <div class="form-group">
            <label for="id" class="col-sm-2 control-label">会议室门牌号</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="id" placeholder="请输入会议室门牌号" v-model="roomNo"
                readonly="readonly">
            </div>
          </div>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">会议室楼层</label>
            <div class="col-sm-10">
              <select class="form-control" v-model="roomFloor">
                <option disabled>--请选择会议室楼层--</option>
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
          </div>
          <div class="form-group">
            <label for="count" class="col-sm-2 control-label">会议室类型</label>
            <div class="col-sm-10">
              <select class="form-control" v-model="roomType">
                <option disabled>--请选择会议室类型--</option>
                <option>多媒体小型会议室</option>
                <option>多媒体中型会议室</option>
                <option>多媒体大型会议室</option>
                <option>多媒体超大型会议室</option>
                <option>多媒体特大型会议室</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="number" class="col-sm-2 control-label">会议室容纳人数</label>
            <div class="col-sm-10">
              <select class="form-control" v-model="roomSize">
                <option disabled>--请选择会议室容纳人数--</option>
                <option>20</option>
                <option>40</option>
                <option>60</option>
                <option>100</option>
                <option>1000</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-sm-2 control-label">会议室状态</label>
            <div class="col-sm-10">
              <select class="form-control" v-model="roomStatus">
                <option>可用</option>
                <option>维修</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-primary" @click="updateRoom()">提交修改</button>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-2"></div>
    </div>
  </div>

</template>

<script>
  import qs from 'qs'

  export default {
    data() {
      return {
        roomNo: '',
        roomFloor: '',
        roomType: '',
        roomSize: '',
        roomStatus: '',
        roomId: ''
      }
    },
    created() {
      this.getRoomId(),
        scroll(0, 0)
    },
    methods: {
      // 根据传过来的id获取会议室信息
      getRoomId() {
        let that = this
        let id = this.$route.params.id
        let data = qs.stringify({
          "id": id
        })
        // 回显要修改的会议室的信息
        this.$axios.get('/room/getById?' + data)
          .then(res => {
            // 赋值当前会议室的信息
            that.roomNo = res.data.roomNo
            that.roomFloor = res.data.roomFloor
            that.roomType = res.data.roomType
            that.roomSize = res.data.roomSize
            that.roomStatus = res.data.roomStatus

            // 判断roomstatus的值 0-维修 1-可用
            if (that.roomStatus == 1) {
              that.roomStatus = '可用'
            } else if (that.roomStatus == 0) {
              that.roomStatus = '维修'
            }
          })
      },

      // 更新会议室信息
      updateRoom() {
        let data = {
          "roomId": this.$route.params.id,
          "roomNo": this.roomNo,
          "roomFloor": this.roomFloor,
          "roomType": this.roomType,
          "roomSize": this.roomSize,
          "roomStatus": this.roomStatus,
        }
        if (data.roomStatus == '可用') {
          data.roomStatus = 1
        }
        if (data.roomStatus == '维修') {
          data.roomStatus = 0
        }
        this.$axios.post('/room/update', data)
          .then(res => {
            alert('会议室信息修改成功！')
            this.$router.push('/admin/meetingmanage')
          })
      }
    }

  }
</script>

<style scoped>

</style>
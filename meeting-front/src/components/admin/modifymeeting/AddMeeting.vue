<template>
  <div class="container">
    <ol class="breadcrumb">
      <li>当前位置</li>
      <li>
        <router-link to="/admin/meetingmanage">会议室信息管理</router-link>
      </li>
      <li><a href="#">添加新会议室</a></li>
    </ol>
    <hr>
    <div class="row">
      <div class="col-md-2"></div>
      <div class="col-md-8">
        <div class="form-horizontal">
          <div class="form-group">
            <label for="id" class="col-sm-2 control-label">会议室门牌号</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="id" placeholder="请输入会议室门牌号" v-model="meetingNum">
            </div>
          </div>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">会议室楼层</label>
            <div class="col-sm-10">
              <select class="form-control" v-model="meetingFloor">
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
              <select class="form-control" v-model="meetingType">
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
              <select class="form-control" v-model="meetingCount">
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
              <select class="form-control" v-model="meetingStatus">
                <option disabled>--请选择会议室状态--</option>
                <option>可用</option>
                <option>维修</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-primary" @click="addMeeting()">添加会议室</button>
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
        meetingCount: '--请选择会议室容纳人数--',
        meetingFloor: '--请选择会议室楼层--',
        meetingType: '--请选择会议室类型--',
        meetingStatus: '--请选择会议室状态--',
        meetingNum: '',
      }
    },
    created() {
      scroll(0, 0)
    },
    methods: {
      addMeeting() {
        // 会议室门牌号判空
        if (this.meetingNum == '') { // 会议室门牌号为空
          alert('请输入会议室门牌号！')
        } else {
          let numData = {
            "roomNo": this.meetingNum,
          }
          // 会议室门牌号是否重复
          this.$axios.post('/room/listByCondition', numData)
            .then(response => {
              // 会议室门牌号不重复
              if (response.data == '') {
                // 规范data参数---判空以及规范会议室门牌号是否为正整数
                let data = {
                  "roomSize": this.meetingCount,
                  "roomFloor": this.meetingFloor,
                  "roomType": this.meetingType,
                  "roomStatus": this.meetingStatus,
                  "roomNo": this.meetingNum,
                }
                if (data.roomNo === '' || !(/^\d+$/.test(data.roomNo))) {
                  alert('请输入正确的会议室门牌号')
                } else if ((data.roomFloor === '--请选择会议室楼层--')) {
                  alert('请选择会议室楼层')
                } else if ((data.roomType === '--请选择会议室类型--')) {
                  alert('请选择会议室类型')
                } else if ((data.roomSize === '--请选择会议室容纳人数--')) {
                  alert('请选择会议室容纳人数')
                } else if ((data.roomStatus === '--请选择会议室状态--')) {
                  alert('请选择会议室状态')
                } else {
                  // 转换会议室状态
                  if ((data.roomStatus === '可用')) {
                    data.roomStatus = 1
                  } else if ((data.roomStatus === '维修')) {
                    data.roomStatus = 0
                  }

                  // 提交添加会议室请求
                  this.$axios.post('/room/insert', data)
                    .then(res => {
                      if (res.data == true) {
                        alert('添加成功！')
                        this.$router.push('/admin/meetingmanage')
                      } else {
                        alert('添加失败')
                        console.log(res)
                      }
                    })
                }
              } else { // 会议室门牌号重复
                alert('此会议室信息已经存在！')
              }
            })
        }
      }
    }
  }
</script>

<style scoped>

</style>
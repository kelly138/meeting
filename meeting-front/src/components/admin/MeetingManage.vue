<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li><a href="#">会议室信息管理</a></li>
      </ol>
      <hr>
      <div class="row">
        <div class="form-inline">
          <div class="form-group">
            <div class="form-group">
              <label class="sr-only" for="number">部门编号</label>
              <input type="email" class="form-control" id="number" placeholder="请输入会议室门牌号" v-model="roomNo">
              <button type="submit" class="btn btn-default" @click="showOneRoom()">查询</button>
            </div>
          </div>
          <div class="form-group">
            <div class="form-group">
              <button type="submit" class="btn btn-primary" @click="showAllRooms()">显示全部会议室</button>
            </div>
          </div>
          <div class="form-group">
            <div class="form-group">
              <router-link to="/admin/meetingmanage/add">
                <button type="submit" class="btn btn-primary">添加新会议室</button>
              </router-link>
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
                <td>
                  <router-link :to="'/admin/meetingmanage/update/' + item.roomId">
                    修改
                  </router-link>
                  /
                  <a href="#" @click.prevent="deleteRoom(item.roomId)">删除</a>
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
        roomNo: ''
      }
    },
    created() {
      this.showAllRooms(),
      scroll(0, 0)
    },
    computed: {
      getRooms() {
        // 在vux中获取数据
        return this.$store.state.roomItems
      }
    },
    methods: {
      // 显示全部会议室信息
      showAllRooms() {
        this.roomNo = ''
        this.$axios.get('/room/listAll')
          .then(res => {
            this.$store.commit("setRooms", res.data)
          })


      },
      // 按条件显示会议室信息
      showOneRoom() {
        if (/^\d+$/.test(this.roomNo)) {
          let data = {
            "roomNo": this.roomNo,
          }
          this.$axios.post('/room/listByCondition', data)
            .then(res => {
              console.log(res)
              this.$store.commit("setRooms", res.data)
            })
        } else {
          alert('请输入正确的会议室门牌号！')
        }
        // this.roomNo = ''
      },
      // 按照roomId删除会议室
      deleteRoom(id) {
        if (window.confirm('您确定要删除此会议室信息吗？')) {
          let data = qs.stringify({
            "roomId": id,
          })
          this.$axios.get('/room/delete?' + data)
            .then(res => {
              alert('删除成功！')
              window.location.reload()
            })
        } else {
          // alert("取消");
          // return false;
        }

      }
    }
  }
</script>

<style scoped>
  .row {
    margin-top: 20px;
  }
</style>
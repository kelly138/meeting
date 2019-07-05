<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li><a href="#">已审核用户申请</a></li>
      </ol>
      <hr>
      <div class="row">
        <div class="form-inline">
          <div class="form-group">
            <input type="text" class="form-control" placeholder="请输入部门编号" v-model="depNo">
          </div>
          <div class="form-group">
            <select class="form-control" v-model="auditStatus">
              <option disabled>--请选择是否通过审核--</option>
              <option>通过审核</option>
              <option>未通过审核</option>
              <option>不限</option>
            </select>
          </div>
          <button type="submit" class="btn btn-primary" @click="showOneReviewed()">查询</button>
          <button type="submit" class="btn btn-danger" @click="showAllReviewed()">显示全部</button>
        </div>
      </div>
      <div class="row">
        <ul class="list-group">
          <li class="list-group-item" v-for="(item) in getReviewed" :key="item.applyId">
            <div class="panel panel-info">
              <div class="panel-heading">部门编号： {{item.depNo}}</div>
              <div class="panel-body">
                <ul class="list-group">
                  <li class="list-group-item">会议时间段————{{item.startTime}} ——— {{item.endTime}}</li>
                  <li class="list-group-item">申请时间————{{item.applyTime}}</li>
                  <li class="list-group-item">申请部门————{{item.depName}}</li>
                  <li class="list-group-item">参加会议人数————{{item.personCount}}</li>
                  <li class="list-group-item">会议室门牌号————{{item.roomNo}}</li>
                  <li class="list-group-item">会议室类型————{{item.roomType}}</li>
                  <li class="list-group-item">会议室容纳人数————{{item.roomSize}}</li>
                  <li class="list-group-item">会议主题————{{item.meetingTheme}}</li>
                  <li class="list-group-item">
                    会议内容
                    <div class="well">
                      {{item.meetingDigest}}
                    </div>
                  </li>
                  <li class="list-group-item">申请审核情况：
                    <span class="label label-danger" v-if="item.auditStatus==1? true:false">通过审核</span>
                    <span class="label label-danger" v-if="item.auditStatus==2? true:false">未通过审核</span>
                    <br v-if="item.auditStatus==2? true:false"><br v-if="item.auditStatus==2? true:false">
                    <p v-if="item.auditStatus==2? true:false">未通过理由：{{item.rejectReason}}</p>
                  </li>
                </ul>
              </div>
            </div>
          </li>
        </ul>
      </div>

      <!-- 分页 -->
      <zpagenav v-bind:page="page" v-bind:page-size="pageSize" v-bind:total="total" v-bind:max-page="maxPage"
        v-on:pagehandler="pageHandler">
      </zpagenav>


    </div>
  </div>
</template>

<script>
  import qs from 'qs'

  export default {
    data() {
      return {
        depNo: '',
        auditStatus: '--请选择是否通过审核--',
        // 分页变量
        page: 1, //当前页数
        pageSize: 2, // 每一页显示的数据条数
        total: 0, // 记录总数
        maxPage: 20 // 最大页数
      }
    },
    computed: {
      // 存储已经审核的申请
      getReviewed() {
        return this.$store.state.reviewedItems
      }
    },
    created() {
      this.showAllReviewed(),
        this.pageHandler(1)
    },
    methods: {
      // 分页回调函数
      pageHandler(page) {
        scrollTo(0, 0)
        this.page = page
        // 查询审核通过的申请
        let data = {
          "auditStatus": this.auditStatus,
          "pageNo": page,
          "pageSize": this.pageSize,
          "depNo": this.depNo,
          "auditTime": null
        }
        // 判断depNo
        if (data.depNo == '') {
          data.depNo = null
        } else {
          data.depNo = data.depNo
        }
        // 判断auditStatus
        if (data.auditStatus == '--请选择是否通过审核--' || data.auditStatus == '不限') {
          data.auditStatus = [1, 2]
        } else if (data.auditStatus == '通过审核') {
          data.auditStatus = [1]
        } else if (data.auditStatus == '未通过审核') {
          data.auditStatus = [2]
        }
        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            // console.log(res.data.rows)
            this.$store.commit("setReviewed", res.data.rows)
          })
        // this.auditStatus = '--请选择是否通过审核--'

      },
      // 按条件查询已审核用户申请记录
      showOneReviewed() {
        // console.log(this.auditStatus)
        if (/^\d+$/.test(this.depNo) || this.depNo == '') {
          let data = {
            "auditStatus": this.auditStatus,
            "pageNo": 1,
            "pageSize": this.pageSize,
            "depNo": this.depNo,
            // "auditTime": null
          }
          if (data.depNo == '') {
            data.depNo = null
          }
          if (data.auditStatus == '--请选择是否通过审核--' || data.auditStatus == '不限') {
            data.auditStatus = [1, 2]
          } else if (data.auditStatus == '通过审核') {
            data.auditStatus = [1]
          } else if (data.auditStatus == '未通过审核') {
            data.auditStatus = [2]
          }
          // console.log(data)

          this.$axios.post('/audit/listRecordByAuditStatus', data)
            .then(res => {
              // console.log(data)
              // console.log(res.data.rows)
              this.$store.commit("setReviewed", res.data.rows)
              this.total = res.data.total
            })
        } else {
          alert('请输入正确的部门编号！')
        }

      },
      // 显示全部已审核用户申请记录
      showAllReviewed() {
        this.depNo = ''
        this.auditStatus = '--请选择是否通过审核--'
        // 查询审核通过的申请
        let data = {
          "auditStatus": [1, 2],
          "pageNo": 1,
          "pageSize": this.pageSize,
          "depNo": null,
          "auditTime": null
        }
        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            // console.log(res.data.rows)
            this.$store.commit("setReviewed", res.data.rows)
            this.total = res.data.total
          })
      }
    }
  }
</script>

<style scoped>
  .row {
    margin-top: 20px;
  }

  .label {
    font-size: 15px;
  }
</style>
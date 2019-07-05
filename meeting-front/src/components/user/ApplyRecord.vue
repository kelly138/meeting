<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li><a href="#">会议室申请记录</a></li>
      </ol>
      <hr>
      <div class="row">
        <div class="form-inline">
          <div class="form-group">
            <select class="form-control" v-model="auditStatus">
              <option disabled>--请选择审核状态--</option>
              <option>审核中</option>
              <option>未通过审核</option>
              <option>通过审核</option>
              <option>不限</option>
            </select>
          </div>
          <button type="submit" class="btn btn-primary" @click="listOneRecord()">查询</button>
          <button type="submit" class="btn btn-danger" @click="listAllRecords()">显示全部</button>
        </div>
      </div>
      <div class="row">
        <ul class="list-group">
          <li class="list-group-item" v-for="(item, index) in getDepApplyRecords" :key="index">
            <div class="panel panel-info">
              <div class="panel-heading">&nbsp;</div>
              <div class="panel-body">
                <ul class="list-group">
                  <li class="list-group-item">开始时间————{{item.startTime}}</li>
                  <li class="list-group-item">结束时间————{{item.endTime}}</li>
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
                  <li class="list-group-item" v-if="item.auditStatus==0? false:true">审核日期————{{item.auditTime}}</li>
                  <li class="list-group-item">申请部门————{{item.depName}}</li>
                  <li class="list-group-item">申请审核情况：
                    <span class="label label-danger" v-if="item.auditStatus==0? true:false">审核中</span>
                    <span class="label label-danger" v-if="item.auditStatus==1? true:false">通过审核</span>
                    <span class="label label-danger" v-if="item.auditStatus==2? true:false">未通过审核</span>
                    <br v-if="item.auditStatus==2? true:false">
                    <br v-if="item.auditStatus==2? true:false">
                    <p v-if="item.auditStatus==2? true:false">未通过理由：{{item.rejectReason}}</p>
                  </li>
                  <li class="list-group-item" v-if="item.auditStatus==0? true:false">
                    <button type="submit" class="btn btn-primary" @click="withdrawApply(item.applyId)">撤回申请</button>
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
  import Cookies from 'js-cookie';
  import qs from 'qs'

  export default {
    data() {
      return {
        auditStatus: '--请选择审核状态--',

        // 分页变量
        page: 1, //当前页数
        pageSize: 2, // 每一页显示的数据条数
        total: 0, // 记录总数
        maxPage: 20 // 最大页数
      }

    },
    created() {
      this.listAllRecords()
      this.pageHandler(1)
    },
    computed: {
      getDepApplyRecords() {
        return this.$store.state.DepApplyRecordsItems
      }
    },
    // watch: {
    //   auditStatus: {
    //     page = 1
    //   }
    // },
    methods: {
      // 分页回调函数
      pageHandler(page) {
        scrollTo(0, 0)
        this.page = page
        let data = {
          "auditStatus": this.auditStatus,
          "pageNo": page, // 当前页码
          "pageSize": this.pageSize, // 每页显示多少项
          "depNo": Cookies.get('username'),
          "auditTime": null
        }
        // 判断审核状态 0-未审核 1-通过审核 2-未通过审核
        if (data.auditStatus == "审核中") {
          data.auditStatus = [0]
        } else if (data.auditStatus == "未通过审核") {
          data.auditStatus = [2]
        } else if (data.auditStatus == "通过审核") {
          data.auditStatus = [1]
        } else if (data.auditStatus == "不限" || data.auditStatus == "--请选择审核状态--") {
          data.auditStatus = [0, 1, 2]
        }
        // // 判断用户是否选择了审核时间
        // if (data.auditTime == '') {
        //   delete data['auditTime']
        // }
        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            // console.log(res.data.rows)
            this.$store.commit("setDepApplyRecords", res.data.rows)
            // console.log(this.total)
          })

      },
      // 按条件查询部门申请记录
      listOneRecord() {
        // this.pageNo = 1
        let data = {
          "auditStatus": this.auditStatus,
          "pageNo": 1, // 当前页码
          "pageSize": this.pageSize, // 每页显示多少项
          "depNo": Cookies.get('username'),
          "auditTime": null
        }
        // 判断审核状态 0-未审核 1-通过审核 2-未通过审核
        if (data.auditStatus == "审核中") {
          data.auditStatus = [0]
        } else if (data.auditStatus == "未通过审核") {
          data.auditStatus = [2]
        } else if (data.auditStatus == "通过审核") {
          data.auditStatus = [1]
        } else if (data.auditStatus == "不限" || data.auditStatus == "--请选择审核状态--") {
          data.auditStatus = [0, 1, 2]
        }
        // // 判断用户是否选择了审核时间
        // if (data.auditTime == '') {
        //   delete data['auditTime']
        // }
        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            // console.log(res.data.rows)
            this.$store.commit("setDepApplyRecords", res.data.rows)
            // console.log(res.data.total)      
            this.total = res.data.total
          })

      },
      // 查询所有部门申请记录
      listAllRecords() {
        this.auditStatus = '--请选择审核状态--'
        // this.pageNo = 1
        let data = {
          "auditStatus": [0, 1, 2],
          "pageNo": 1, // 当前页码
          "pageSize": this.pageSize, // 每页显示多少项
          "depNo": Cookies.get('username'),
          "auditTime": null
        }
        console.log("this.pageNo" + this.pageNo)
        console.log('data.pageNo' + data.pageNo)

        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            // console.log(res.data.rows)
            this.$store.commit("setDepApplyRecords", res.data.rows)
            this.total = res.data.total
            // console.log(res.data.total)        
          })
      },
      // 撤回申请
      withdrawApply(applyId) {
        let data = qs.stringify({
          "applyId": applyId
        })
        console.log(data)
        this.$axios.get('/apply/delete?' + data)
          .then(res => {
            if (res.data == true) {
              // console.log(res)
              alert('撤回成功！')
              this.listAllRecords()
              // let data = {
              //   "auditStatus": [0],
              //   "pageNo": 0,
              //   "pageSize": 10,
              //   "depNo": Cookies.get('username'),
              //   "auditTime": null
              // }
              // this.$axios.post('/audit/listRecordByAuditStatus', data)
              //   .then(res => {
              //     // console.log(res.data.rows)
              //     this.$store.commit("setDepApplyRecords", res.data.rows)
              //   })
              // window.location.reload()
            } else if (res.data == false) {
              alert('撤回失败！')
              // console.log(res)
            }
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
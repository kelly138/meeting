<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li><a href="#">未审核用户申请</a></li>
      </ol>
      <hr>
      <div class="row">
        <div class="form-inline">
          <div class="form-group">
            <input type="text" class="form-control" placeholder="请输入部门编号" v-model="depNo">
          </div>
          <button type="submit" class="btn btn-primary" @click="showOneWillReview()">查询</button>
          <button type="submit" class="btn btn-danger" @click="showAllWillReview()">显示全部</button>
        </div>
      </div>
      <div class="row">
        <ul class="list-group">
          <li class="list-group-item" v-for="(item) in getReviewItems" :key="item.applyId">
            <div class="panel panel-info">
              <div class="panel-heading">部门编号： {{item.depNo}}</div>
              <div class="panel-body">
                <ul class="list-group">
                  <li class="list-group-item">会议时间段————{{item.startTime}}&nbsp;&nbsp;&nbsp;———
                    &nbsp;&nbsp;&nbsp;{{item.endTime}}</li>
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
                  <li class="list-group-item">
                    <div class="form-inline">
                      <div class="form-group">
                        <input class="btn btn-primary" type="button" value="通过审核" @click="passed(item.applyId)">
                        <input class="btn btn-danger" type="button" value="驳回申请" @click="unPassed(item.applyId)">
                        <textarea class="form-control" rows="2" cols="100" placeholder="请填写驳回理由后，再点击驳回申请。"
                          v-model="rejectReason"></textarea>
                      </div>
                    </div>
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
        rejectReason: '',
        // 分页变量
        page: 1, //当前页数
        pageSize: 2, // 每一页显示的数据条数
        total: 0, // 记录总数
        maxPage: 20 // 最大页数
      }
    },
    computed: {
      getReviewItems() {
        return this.$store.state.willReviewItems
      }
    },
    created() {
      this.showAllWillReview(),
        this.pageHandler(1)
    },
    methods: {
      // 分页回调函数
      pageHandler(page) {
        scrollTo(0, 0)
        this.page = page
        let data = {
          "auditStatus": [0],
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
        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            // console.log(res.data.rows)
            this.$store.commit("setWillReview", res.data.rows)
          })

      },
      // 按条件显示会议室申请记录
      showOneWillReview() {
        if (/^\d+$/.test(this.depNo)) {
          let data = {
            "auditStatus": [0],
            "pageNo": 1,
            "pageSize": this.pageSize,
            "depNo": this.depNo,
            "auditTime": null
          }
          this.$axios.post('/audit/listRecordByAuditStatus', data)
            .then(res => {
              // console.log(res)
              this.$store.commit("setWillReview", res.data.rows)
              this.total = res.data.total
            })
          // this.depNo = ''
        } else {
          alert('请输入正确的部门编号！')
        }
      },

      // 显示全部会议室申请记录
      showAllWillReview() {
        this.depNo = ''
        let data = {
          "auditStatus": [0],
          "pageNo": 1,
          "pageSize": this.pageSize,
          "depNo": null,
          "auditTime": null
        }
        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            // console.log(res.data.rows)
            this.$store.commit("setWillReview", res.data.rows)
            this.total = res.data.total
          })
      },
      // 通过审核
      passed(applyId) {
        let data = {
          "auditStatus": 1,
          "auditTime": new Date(),
          "rejectReason": '',
          "applyId": applyId

        }
        this.$axios.post('/audit/update', data)
          .then(res => {
            // console.log(data)
            // console.log(res)
            if (res.data == true) {
              alert('提交成功！审核结果——通过审核')
              window.location.reload()
            } else if (res.data == false) {
              alert('提交失败！')
            }
          })
      },
      // 未通过审核
      unPassed(applyId) {
        if (this.rejectReason == "") {
          alert('请填写驳回理由！')
        } else {
          let data = {
            "auditStatus": 2,
            "auditTime": new Date(),
            "rejectReason": this.rejectReason,
            "applyId": applyId
          }
          this.$axios.post('/audit/update', data)
            .then(res => {
              // console.log(data)
              // console.log(res)
              if (res.data == true) {
                alert('提交成功！审核结果——未通过审核')
                window.location.reload()
              } else if (res.data == false) {
                alert('提交失败！')
              }
            })

        }


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
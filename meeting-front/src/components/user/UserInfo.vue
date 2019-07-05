<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li><a href="#">部门信息查看</a></li>
      </ol>
      <hr>
      <div class="row">
        <div class="col-md-7">
          <div class="jumbotron">
            <h1>Hello, {{depName}}!</h1>
            <p>会议室管理系统，企业得力小助手</p>
            <p>欢迎使用本系统，任何问题请联系管理员</p>
            <p><span class="badge">管理员</span>&nbsp;&nbsp;<span class="glyphicon glyphicon-phone"></span>(+86)13889366982
            </p>
          </div>
        </div>
        <div class="col-md-5">
          <div class="panel panel-primary">
            <div class="panel-heading">部门信息</div>
            <div class="panel-body">
              <ul class="list-group">
                <li class="list-group-item">
                  <span class="badge">{{depNo}}</span>
                  部门编号
                </li>
                <li class="list-group-item">
                  <span class="badge">{{depName}}</span>
                  部门名称
                </li>
                <li class="list-group-item">
                  <span class="badge">{{depPersonCount}}</span>
                  部门人数
                </li>
                <li class="list-group-item">
                  <span class="badge">{{depPhone}}</span>
                  部门联系电话
                </li>
                <li class="list-group-item">
                  <span class="badge">{{depMobile}}</span>
                  部门移动电话
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div class="alert alert-danger" role="alert">
        <strong>注意：</strong>所有会议室申请时间至少<strong>提前2小时</strong>，如有紧急情况请立即电话联系管理员！———&nbsp;&nbsp;<span
          class="glyphicon glyphicon-phone"></span>(+86)13889366982</div>


    </div>
  </div>
</template>

<script>
  import Cookies from 'js-cookie';

  export default {
    data() {
      return {
        depNo: '',
        depName: '',
        depPersonCount: '',
        depPassword: '',
        depPhone: '',
        depMobile: ''
      }
    },
    created() {
      this.showDepInfo()
    },
    methods: {
      showDepInfo() {
        // 获取Cookie中存储的部门编号,根据部门编号查询部门名称
        let data = {
          "depNo": Cookies.get('username')
        }
        let that = this
        this.$axios.post('/department/listByCondition', data)
          .then(res => {
            that.depNo = res.data[0].depNo
            that.depName = res.data[0].depName
            that.depPersonCount = res.data[0].depPersonCount
            that.depPassword = res.data[0].depPassword
            that.depPhone = res.data[0].depPhone
            that.depMobile = res.data[0].depMobile
          })
      }
    }
  }
</script>

<style scoped>

</style>
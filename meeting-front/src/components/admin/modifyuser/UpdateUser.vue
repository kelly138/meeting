<template>
  <div class="container">
    <ol class="breadcrumb">
      <li>当前位置</li>
      <li>
        <router-link to="/admin/modify">部门信息管理</router-link>
      </li>
      <li><a href="#">修改部门信息</a></li>
    </ol>
    <hr>
    <div class="row">
      <div class="col-md-2"></div>
      <div class="col-md-8">
        <div class="form-horizontal">
          <div class="form-group">
            <label for="id" class="col-sm-2 control-label">部门编号</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="id" placeholder="请输入用户编号" v-model="depNo" readonly="readonly">
            </div>
          </div>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">部门名称</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="name" placeholder="请输入部门名称" v-model="depName"
                readonly="readonly">
            </div>
          </div>
          <div class="form-group">
            <label for="count" class="col-sm-2 control-label">部门人数</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="count" placeholder="请输入部门人数" v-model="depPersonCount">
            </div>
          </div>
          <div class="form-group">
            <label for="password1" class="col-sm-2 control-label">请输入部门原密码</label>
            <div class="col-sm-10">
              <input type="password" class="form-control" id="password1" placeholder="请输入部门原密码,若不改密码，则无需输入这项"
                v-model="depPassword1">
            </div>
          </div>
          <div class="form-group">
            <label for="password2" class="col-sm-2 control-label">请输入部门新密码</label>
            <div class="col-sm-10">
              <input type="password" class="form-control" id="password2" placeholder="请输入部门新密码,若不改密码，则无需输入这项"
                v-model="depPassword2">
            </div>
          </div>
          <div class="form-group">
            <label for="phone" class="col-sm-2 control-label">部门联系电话</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="phone" placeholder="请输入部门联系电话" v-model="depPhone">
            </div>
          </div>
          <div class="form-group">
            <label for="mobile" class="col-sm-2 control-label">部门移动电话</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="mobile" placeholder="请输入部门移动电话" v-model="depMobile">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-primary" @click="updateDep()">提交修改</button>
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
        depNo: '',
        depMobile: '',
        depName: '',
        depPassword1: '',
        depPassword2: '',
        depPersonCount: '',
        depPhone: ''
      }
    },
    created() {
      this.getDepId(),
        scroll(0, 0)
    },
    methods: {
      // 根据传过来的id获取会部门信息
      getDepId() {
        let id = this.$route.params.id
        let data = qs.stringify({
          "depId": id
        })
        // 回显要修改的部门的信息
        this.$axios.get('/department/getById?' + data)
          .then(res => {
            // 赋值当前部门的信息
            // console.log(res)
            this.depNo = res.data.depNo,
              this.depMobile = res.data.depMobile,
              this.depName = res.data.depName,
              this.depPersonCount = res.data.depPersonCount,
              this.depPhone = res.data.depPhone
          })
      },
      // 更新部门信息
      updateDep() {
        // 检验用户输入是否有空缺
        if (this.depNo == '') {
          alert('请输入部门编号！')
        } else if (this.depName == '') {
          alert('请输入部门名称')
        } else if (this.depPersonCount == '') {
          alert('请输入部门人数！')
        } else if (this.depPhone == '' || !/0\d{2,3}-\d{7,8}/.test(this.depPhone)) {
          alert('请输入正确的部门联系电话！')
        } else if (this.depMobile == '' || !/^[1][3,4,5,7,8][0-9]{9}$/.test(this.depMobile)) {
          alert('请输入正确的部门移动电话！')
        } else if (this.depPassword1 != '' && this.depPassword2 == '') {
          alert('请输入部门新密码！')
        } else if (this.depPassword1 == '' && this.depPassword2 != '') {
          alert('请输入部门原密码！')
        } else {
          // 判空完毕

          // 未修改密码  新密码旧密码为空  提交修改请求
          if (this.depPassword1 == '' && this.depPassword2 == '') {
            let depData = {
              "depId": parseInt(this.$route.params.id),
              "depNo": this.depNo,
              "depName": this.depName,
              "depPersonCount": parseInt(this.depPersonCount),
              "depPhone": this.depPhone,
              "depMobile": this.depMobile,
              // "depPassword": res.data.depPassword
            }
            this.$axios.post('/department/update', depData)
              .then(res => {
                // console.log(depData)
                // console.log(res)
                alert('修改部门信息成功！')
                this.$router.push('/admin/modify')
              })
          }
          // 修改了密码 新密码旧密码不为空  提交修改请求
          else if (this.depPassword1 != '' && this.depPassword2 != '') {
            // 校验真正的原始密码与管理员输入的原始密码是否一致
            this.$axios.get('/department/validatePassword', {
                params: {
                  "rawPassword": this.depPassword1,
                  "depId": parseInt(this.$route.params.id)
                }
              })
              .then(res => {
                // console.log(params)
                // console.log(res)
                if (res.data == true) {
                  let depData = {
                    "depId": parseInt(this.$route.params.id),
                    "depNo": this.depNo,
                    "depName": this.depName,
                    "depPersonCount": parseInt(this.depPersonCount),
                    "depPhone": this.depPhone,
                    "depMobile": this.depMobile,
                    "depPassword": this.depPassword2
                  }
                  this.$axios.post('/department/update', depData)
                    .then(res => {
                      // console.log(depData)
                      // console.log(res)
                      alert('修改部门信息成功！')
                      this.$router.push('/admin/modify')
                    })
                } else {
                  alert('部门原始密码输入错误！')
                }


              })

          }
        }


      }
    }
  }
</script>

<style scoped>

</style>
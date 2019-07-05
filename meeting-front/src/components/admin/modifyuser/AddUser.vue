<template>
  <div class="container">
    <ol class="breadcrumb">
      <li>当前位置</li>
      <li>
        <router-link to="/admin/modify">部门信息管理</router-link>
      </li>
      <li><a href="#">添加新部门</a></li>
    </ol>
    <hr>
    <div class="row">
      <div class="col-md-2"></div>
      <div class="col-md-8">
        <div class="form-horizontal">
          <div class="form-group">
            <label for="id" class="col-sm-2 control-label">部门编号</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="id" placeholder="请输入部门编号" v-model="depNo">
            </div>
          </div>
          <div class="form-group">
            <label for="name" class="col-sm-2 control-label">部门名称</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="name" placeholder="请输入部门名称" v-model="depName">
            </div>
          </div>
          <div class="form-group">
            <label for="count" class="col-sm-2 control-label">部门人数</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="count" placeholder="请输入部门人数" v-model="depPersonCount">
            </div>
          </div>
          <div class="form-group">
            <label for="password" class="col-sm-2 control-label">部门密码</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="password" placeholder="请输入部门密码" v-model="depPassword">
            </div>
          </div>
          <div class="form-group">
            <label for="phone" class="col-sm-2 control-label">部门联系电话</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="phone" placeholder="请输入部门联系电话----请输入正确11位座机号码"
                v-model="depPhone">
            </div>
          </div>
          <div class="form-group">
            <label for="mobile" class="col-sm-2 control-label">部门移动电话</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="mobile" placeholder="请输入部门移动电话----请输入正确11位手机号码"
                v-model="depMobile">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
              <button type="submit" class="btn btn-primary" @click="addDep()">添加用户</button>
            </div>
          </div>
        </div>
      </div>
      <div class="col-md-2"></div>
    </div>
  </div>

</template>

<script>
  export default {
    data() {
      return {
        depNo: '',
        depName: '',
        depPassword: '',
        depPhone: '',
        depMobile: '',
        depPersonCount: ''
      }
    },
    created() {
      scroll(0, 0)
    },
    methods: {
      addDep() {
        // 判断输入的部门编号和名称是否为空 和 部门编号是否为正整数
        if (this.depNo == '' || !(/^\d+$/.test(this.depNo))) {
          alert('请输入正确的部门编号！')
        } else if (this.depName == '') {
          alert('请输入部门名称！')
        } else {
          // 查询部门编号和名称是否有重复
          let data = {
            "depNo": this.depNo
          }
          this.$axios.post('/department/listByCondition', data)
            .then(res => {
              // console.log(res.data.length)
              if (res.data.length > 0) {
                alert('部门编号重复！')
              } else {
                let data = {
                  "depName": this.depName
                }
                this.$axios.post('/department/listByCondition', data)
                  .then(res => {
                    // console.log(res.data.length)
                    if (res.data.length > 0) {
                      alert('部门名称重复！')
                    } else {
                      // 判断用户其他输入是否为空
                      if (this.depPersonCount == '' || !(/^\d+$/.test(this.depPersonCount))) {
                        alert('请输入正确的部门人数！')
                      } else if (this.depPassword == '') {
                        alert('请输入部门密码！')
                      } else if (this.depPhone == '') {
                        alert('请输入部门联系电话！')
                      } else if (!/^(0\d{2,3}-\d{7,8})$/.test(this.depPhone)) {  // /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/
                        alert('请输入正确11位座机号码！')
                      } else if (this.depMobile == '') {
                        alert('请输入部门移动电话！')
                      } else if (!/^[1][3,4,5,7,8][0-9]{9}$/.test(this.depMobile)) {
                        alert('请输入正确11位手机号码！')
                      } else {
                        // 提交添加部门请求
                        let data = {
                          // "depId": 4,
                          "depNo": this.depNo,
                          "depName": this.depName,
                          "depPassword": this.depPassword,
                          "depPhone": this.depPhone,
                          "depMobile": this.depMobile,
                          "depPersonCount": parseInt(this.depPersonCount)
                        }
                        this.$axios.post('/department/insert', data)
                          .then(res => {
                            // console.log(res)
                            // console.log(data)
                            if (res.data == true) {
                              alert('添加部门成功！')
                              this.$router.push('/admin/modify')
                            } else {
                              alert('添加部门失败！')
                            }
                          })
                      }

                    }
                  })
              }
            })

        }
      }
    }
  }
</script>

<style scoped>

</style>
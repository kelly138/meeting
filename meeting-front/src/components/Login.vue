<template>
  <div class="bigContainer">
    <div class="container">
      <div class="row" style="height:100px;"></div>
      <div class="row">
        <div class="col-md-3"></div>
        <div class="col-md-4">
          <div class="panel panel-primary">
            <div class="panel-heading">系统使用注意事项</div>
            <div class="panel-body">
              <p>1、部门的登录账号统一为部门编号</p>
              <p>2、任何会议室使用申请请<b>至少提前2小时提交，</b>若有紧急情况请电话联系管理员。</p>
              <p>3、请<b>合理安排会议室使用时间和会议室使用容量，</b>做到最大化的有效利用。</p>
              <p>4、管理员联系电话：（+86）13889366982</p>
            </div>
          </div>
        </div>
        <div class="col-md-5">
          <div class="panel panel-primary">
            <div class="panel-heading">企业会议室管理系统</div>
            <div class="panel-body">
              <form>
                <div class="form-group">
                  <label for="InputName">登录账号</label>
                  <input type="text" class="form-control" id="InputName" placeholder="请输入登录账号" v-model="username"
                    @keyup.enter="login()" autofocus="autofocus">
                </div>
                <div class="form-group">
                  <label for="InputPassword">密码</label>
                  <input type="password" class="form-control" id="InputPassword" placeholder="请输入密码" v-model="password"
                    @keyup.enter="login()">
                </div>
              </form>
              <button type="submit" class="btn btn-primary" @click="login()">登录</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 底部 footer 区域 -->
    <div class="footerContainer">

      <footer>
        <div class="container">
          <div class="row">
            <div class="col-md-12">
              <p id="footerP">
                Copyright&nbsp;©&nbsp;2019-2050&nbsp;&nbsp;www.kelly996.com&nbsp;&nbsp;沈阳理工毕业设计-1503050308
              </p>
            </div>
          </div>
        </div>
      </footer>
    </div>

    <!-- 底部 footer 区域-END -->
  </div>
</template>

<script>
  import qs from 'qs'
  import Cookies from 'js-cookie';


  export default {
    data() {
      return {
        username: '',
        password: '',
      }
    },
    methods: {
      login() {
        let data = qs.stringify({
          "username": this.username,
          "password": this.password
        })

        this.$axios.post('/login/name', data)
          .then(res => {
            let authority = res.data[0].authority
            if (authority === 'ROLE_ADMIN') {
              this.$router.push('/admin/updatepsw')
              // console.log(qs.parse(data).username)
              // 获取管理员登录时写的管理员编号，存到Cookie中
              Cookies.set('adminname', qs.parse(data).username)

            } else if (authority === 'ROLE_USER') {
              this.$router.push('/user/info')
              // 获取用户登录时写的部门编号，存到Cookie中
              Cookies.set('username', qs.parse(data).username)

            } else {
              alert('用户名或密码错误！')
              this.username = '',
                this.password = ''
            }
          })

      }
    }
  }
</script>

<style scoped>
  .panel {
    background-color: rgba(252, 252, 252, .7);
  }

  .bigContainer {
    position: fixed;
    width: 100%;
    height: 100%;
    background-image: url('../assets/meetingroom.jpg');
    background-position: center center;
    background-size: cover;

  }

  .footerContainer {
    position: absolute;
    margin-top: 50px;
    left: 50%;
  }

  #footerP {
    background-color: rgba(51, 122, 183, .2)
  }
</style>
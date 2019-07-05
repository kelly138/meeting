<template>
  <div>
    <!-- 顶部 Header 区域 -->
    <nav class="navbar navbar-default">
      <div class="container">
        <!--小屏幕导航按钮和logo-->
        <div class="navbar-header">
          <button class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <span href="#" class="navbar-brand">Meeting Room</span>
        </div>
        <!--小屏幕导航按钮和logo-->
        <!--导航-->
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li>
              <router-link to="/user/info">
                <span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;个人信息查看
              </router-link>
            </li>
            <li>
              <router-link to="/user">
                <span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;会议室列表
              </router-link>
            </li>
            <li>
              <router-link to="/user/record">
                <span class="glyphicon glyphicon-align-justify"></span>&nbsp;&nbsp;会议室申请记录
              </router-link>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li>
              <a>
                欢迎登录，<b>{{userName}}</b>&nbsp;&nbsp;部门编号：<b>{{userNo}}</b>
              </a>
            </li>
            <li><a href="/"><span class="glyphicon glyphicon-off"></span>&nbsp;&nbsp;安全退出</a></li>
          </ul>
        </div>
        <!--导航-->

      </div>
    </nav>
    <!-- 顶部 Header 区域-END -->
    <router-view></router-view>
    <my-footer></my-footer>
  </div>
</template>

<script>
  import Footer from './Footer'
  import Cookies from 'js-cookie';

  export default {
    data() {
      return {
        userName: '',
        userNo: ''
      }
    },
    components: {
      'my-footer': Footer
    },
    created() {
      this.showName()
    },
    methods: {
      showName() {
        // 获取Cookie中存储的部门编号
        this.userNo = Cookies.get('username')
        // 根据部门编号查询部门名称,并赋值给data中定义的username
        let data = {
          "depNo": this.userNo
        }
        let that = this
        this.$axios.post('/department/listByCondition', data)
          .then(res => {
            that.userName = res.data[0].depName
          })
      }
    }
  }
</script>

<style scoped>
  body {
    font-family: 'Microsoft YaHei', sans-serif;
  }

  .navbar-default {
    background-color: #337ab7;
  }

  .navbar-default .navbar-nav>li>a {
    color: #fcfcfc;
  }

  .navbar-default .navbar-brand {
    color: #fcfcfc;
  }

  /*������*/
  .progress {
    margin-bottom: 15px;
  }

  /*ͷ��*/
  .wh64 {
    width: 64px;
    height: 64px;
    border-radius: 50%;
  }

  /*�߾�*/
  .mar_t15 {
    margin-top: 15px;
  }

  /*padding*/
  .pad0 {
    padding: 0;
  }

  /*page-header*/
  .page-header {
    margin-top: 0;
  }

  .page-header h1 {
    margin: 0;
    font-size: 16px;
  }

  /*uesr_search*/
  .uesr_search {
    padding: 10px;
    border: solid 1px #ddd;
    border-top: none;
  }

  /*tag*/
  .taglist {
    padding-top: 15px;
  }

  .taglist .alert {
    margin: 0 15px 15px 0;
  }

  .navbar-default .navbar-nav>li.active>a {
    color: #555555;
  }

  .navbar-default .navbar-nav>li>a:hover {
    color: #555555;
  }

  .nav-active {
    color: #007aff;
  }
</style>
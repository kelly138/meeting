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
              <router-link to="/admin/updatepsw">
                <span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;注意事项
              </router-link>
            </li>
            <li>
              <router-link to="/admin">
                <span class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;会议室列表
              </router-link>
            </li>
            <li>
              <router-link to="/admin/willreview">
                <span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;未审核部门申请&nbsp;<span
                  class="badge">{{showNum}}</span>
              </router-link>
            </li>
            <li>
              <router-link to="/admin/reviewed">
                <span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;已审核部门申请
              </router-link>
            </li>
            <li>
              <router-link to="/admin/meetingmanage">
                <span class="glyphicon glyphicon-list-alt"></span>&nbsp;&nbsp;会议室信息管理
              </router-link>
            </li>
            <li>
              <router-link to="/admin/modify">
                <span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;部门信息管理
              </router-link>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li>
              <a href="#">
                欢迎登录，<b>{{adminName}}</b>
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
        adminName: '',
        adminNo: '',
        showNum: this.$store.state.WillReviewItems
      }
    },
    components: {
      'my-footer': Footer
    },
    created() {
      this.showName()
    },
    mounted() {
      this.getWillReviewNum()
      this.intervalid1 = setInterval(this.getWillReviewNum, 1000);
    },
    beforeDestroy() {
      clearInterval(this.intervalid1)
    },
    methods: {
      showName() {
        // 获取Cookie中存储的管理员编号
        this.adminNo = Cookies.get('adminname')
        if (this.adminNo == 'admin') {
          this.adminName = 'Admin'
        }
      },
      // 获取未审核申请的数量
      getWillReviewNum() {
        let data = {
          "auditStatus": [0],
          "pageNo": 0,
          "pageSize": 10,
          "depNo": null,
          "auditTime": null
        }
        this.$axios.post('/audit/listRecordByAuditStatus', data)
          .then(res => {
            this.$store.commit("setWillReviewNum", res.data.rows.length)
            this.showNum = this.$store.state.WillReviewItems
            // console.log(this.$store.state.WillReviewItems)
            // console.log(this.showNum)
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

  .container {
    width: 85%;
  }

  .badge {
    background-color: #cf3f51;
    color: #d5d5d5;
    font-size: 15px;
  }
</style>
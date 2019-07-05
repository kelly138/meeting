<template>
  <div>
    <div class="container">
      <ol class="breadcrumb">
        <li>当前位置</li>
        <li><a href="#">部门信息管理</a></li>
      </ol>
      <hr>
      <div class="row">
        <div class="form-inline">
          <div class="form-group">
            <div class="form-group">
              <label class="sr-only" for="number">部门编号</label>
              <input type="email" class="form-control" id="number" placeholder="请输入部门编号" v-model="depNo">
              <button type="submit" class="btn btn-default" @click="showOneDep()">查询</button>
            </div>
          </div>
          <div class="form-group">
            <div class="form-group">
              <button type="submit" class="btn btn-primary" @click="showAllDeps()">显示全部部门</button>
            </div>
          </div>
          <div class="form-group">
            <div class="form-group">
              <router-link to="/admin/modify/add">
                <button type="submit" class="btn btn-primary">添加新部门</button>
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
                <th>部门编号</th>
                <th>部门名称</th>
                <th>部门人数</th>
                <!-- <th>部门密码</th> -->
                <th>部门联系电话</th>
                <th>部门移动电话</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody v-for="(item, index) in getDeps" :key="index">
              <tr>
                <td>{{item.depNo}}</td>
                <td>{{item.depName}}</td>
                <td>{{item.depPersonCount}}</td>
                <!-- <td>{{item.depPassword}}</td> -->
                <td>{{item.depPhone}}</td>
                <td>{{item.depMobile}}</td>
                <td>
                  <router-link :to="'/admin/modify/update/'+item.depId">
                    修改
                  </router-link>
                  /
                  <a href="#" @click.prevent="deleteDep(item.depId)">删除</a>
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
        depNo: '',
        depName: '',
        depPersonCount: '',
        depPassword: '',
        depPhone: '',
        depMobile: '',
        // getDeps: {}
      }
    },
    computed: {
      getDeps() {
        // 在vux中获取数据
        return this.$store.state.depItems
      }
    },
    // 页面初始化完成后即显示会议室列表数据
    created() {
      this.showAllDeps()
    },

    methods: {
      // 查询全部用户
      showAllDeps() {
        this.depNo = ''
        // 将请求下来的数据存储到vuex中
        this.$axios.get('/department/listAll')
          .then(res => {
            this.$store.commit("setDeps", res.data)
          })
      },

      // 按条件查询部门
      showOneDep() {
        // 判断输入的部门编号是否为正整数
        if (/^\d+$/.test(this.depNo)) {
          let data = {
            "depNo": this.depNo
          }
          this.$axios.post('/department/listByCondition', data)
            .then(res => {
              // console.log(res.data)
              this.$store.commit("setDeps", res.data)
            })
        } else {
          alert('请输入正确的部门编号！')
        }

      },

      // 删除部门
      deleteDep(depId) {
        if (window.confirm('您确定要删除此部门信息吗？')) {
          let data = qs.stringify({
            "depId": depId
          })
          this.$axios.get('/department/delete?' + data)
            .then(res => {
              // console.log(data)
              // console.log(res)
              if (res.data = true) {
                alert('删除成功！')
                window.location.reload()
              }
              else{
                alert('删除失败!')
              }
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
  h2 {
    margin-left: 40%;
  }

  hr {
    color: #0099ff;
  }

  .row {
    margin-top: 20px;
  }
</style>
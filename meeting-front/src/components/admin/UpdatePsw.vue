<template>
  <div class="container">
    <ol class="breadcrumb">
      <li>当前位置</li>
      <li><a href="#">注意事项</a></li>
    </ol>
    <hr>
    <div class="row">
      <div class="col-md-7">
        <div class="jumbotron">
          <h1>Welcome, 管理员!</h1>
          <p>您的细心，会换来企业办公效率大幅提高！</p>
          <p>请在审核时仔细校对时间，及时通知部门。</p>
          <p style="color:red;">请在安排会议室时，以紧急会议优先安排！</p>
        </div>
      </div>
      <div class="col-md-5">
        <div class="alert alert-danger" role="alert">管理员请<b>做好保密工作，</b>勿将管理员及部门密码随意透露给他人</div>
        <div class="alert alert-danger" role="alert">管理员请时刻注意导航栏中的<b>未审核部门申请</b>，它会时刻提示您是否有新的部门申请</div>
        <div class="alert alert-danger" role="alert">管理员在为部门进行会议室申请时，请先查询该部门<b>是否有其他会议时间段，</b>以免发生时间冲突</div>
        <div class="alert alert-danger" role="alert">管理员在修改或添加部门信息时，请<b>尽量使用规律性的密码，</b>并牢记部门密码，避免再次修改部门信息时，忘记部门原始密码的尴尬</div>
      </div>
    </div>
  </div>

</template>

<script>

</script>

<style scoped>
  .jumbotron {
    background-color: rgba(51, 122, 183, .1);
  }
</style>
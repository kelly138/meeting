// 导入一级路由
import LoginComponent from './components/Login.vue'
import UserHeader from './components/UserHeader.vue'
import AdminHeader from './components/AdminHeader.vue'

// 导入二级路由
// 用户
import ApplyRecord from './components/user/ApplyRecord.vue'
import UserApply from './components/user/UserApply.vue'
import UserInfo from './components/user/UserInfo.vue'
import UserMeeting from './components/user/UserMeeting.vue'
// 管理员
import AdminApply from './components/admin/AdminApply.vue'
import AdminMeeting from './components/admin/AdminMeeting.vue'
import MeetingManage from './components/admin/MeetingManage.vue'
import ModifyUser from './components/admin/ModifyUser.vue'
import Reviewed from './components/admin/Reviewed.vue'
import UpdatePsw from './components/admin/UpdatePsw.vue'
import WillReview from './components/admin/WillReview.vue'

// 导入三极路由
import AddMeeting from './components/admin/modifymeeting/AddMeeting.vue'
import UpdateMeeting from './components/admin/modifymeeting/UpdateMeeting.vue'
import AddUser from './components/admin/modifyuser/AddUser.vue'
import UpdateUser from './components/admin/modifyuser/UpdateUser.vue'



export const routes = [{
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    component: LoginComponent,
  },
  {
    path: '/user',
    component: UserHeader,
    redirect: '/user/meeting',
    children: [{
        path: '/user/meeting',
        component: UserMeeting
      },
      {
        path: '/user/apply/:id',
        component: UserApply,
      },
      {
        path: '/user/record',
        component: ApplyRecord,
      },
      {
        path: '/user/info',
        component: UserInfo,
      },
    ]
  },
  {
    path: '/admin',
    component: AdminHeader,
    redirect:'/admin/meeting',
    children: [{
        path: '/admin/meeting',
        component: AdminMeeting,
      },
      {
        path: '/admin/apply/:id',
        component: AdminApply,
      },
      {
        path: '/admin/modify',
        component: ModifyUser,
      },
      {
        path: '/admin/modify/update/:id',
        component: UpdateUser,
      },
      {
        path: '/admin/modify/add',
        component: AddUser,
      },
      {
        path: '/admin/willreview',
        component: WillReview,
      },
      {
        path: '/admin/reviewed',
        component: Reviewed,
      },
      {
        path: '/admin/meetingmanage',
        component: MeetingManage,
      },
      {
        path: '/admin/meetingmanage/update/:id',
        component: UpdateMeeting,
      },
      {
        path: '/admin/meetingmanage/add',
        component: AddMeeting,
      },
      {
        path: '/admin/updatepsw',
        component: UpdatePsw,
      },
    ]
  },
  {
    path: '*', //用于检测路径，如果以上路径都没有检测到，自动重定位到 / - Home组件上去（防止用户写错路径）
    redirect: '/'
  },


]
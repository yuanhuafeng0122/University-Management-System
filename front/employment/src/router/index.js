import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    redirect:"/login",
    meta: {
      title: '毕业生就业登录页'
    }
  },

  {
    path: "/select",
    component: () => import('../components/admin/SelectPCA')
  },

  {
    path: "/login",
    name: "login",
    component: () => import('../views/Login'),
    meta: {
      title: '毕业生就业登录页'
    }
  },
  {
    path: "/home",
    name: 'home',
    component: () => import('../views/Home'),
    meta: {
      title: '毕业生就业主页'
    }
  },
  {
    path:"/registerStudent",
    name:"registerStudent",
    component: () =>import('../views/RegisterStudent'),
    meta: {
      title: '毕业生注册页'
    }
  },
  {
    path:"/registerTeacher",
    name:"registerTeacher",
    component: () =>import('../views/RegisterTeacher'),
    meta: {
      title: '辅导员注册页'
    }
  },

  {
    path: "/adminLogin",
    name: "adminLogin",
    component: () =>import('../views/AdminLogin'),
    meta: {
      title: '管理员登录页'
    }
  },

  {
    path: "/teacherHome",
    name: "teacherHome",
    component: () =>import('../views/TeacherHome'),
    redirect: {name:'addRecruit'},
    meta: {
      title: '辅导员主页'
    },
    children:
    [
      {
        path: "/TeaClass",
        name:"TeaClass",
        component: () => import('../views/TeaClass'),
        meta: {
          title: '我的班级'
        }
      },
      {
        path: "/myStudent",
        name: "myStudent",
        component: () => import ('../views/MyStudent'),
        meta: {
          title: '我的毕业生页'
        }
      },
      {
        path:"/addRecruit",
        name:"addRecruit",
        component: () =>import('../views/AddRecruit'),
        meta: {
          title: '发布岗位页'
        }
      },
      {
        path:"/showEmployInfo",
        name:"showEmployInfo",
        component: () =>import('../views/ShowEmployInfo'),
        meta: {
          title: '毕业生就业信息页'
        }
      },
      {
        path:"/showStudentInfo",
        name:"showStudentInfo",
        component: () =>import('../views/ShowStudentInfo'),
        meta: {
          title: '毕业生详细信息主页'
        }
      },
      {
        path: "/MyPost",
        name:"MyPost",
        component: () => import('../views/MyPost'),
        meta: {
          title: '我的岗位'
        }
      },
      {
        path: "/News",
        name:"News",
        component: () => import('../views/News'),
        meta: {
          title: '我的通知'
        }
      },
      {
        path: "/SumChart",
        name:"SumChart",
        component: () => import('../views/SumChart'),
        meta: {
          title: '班级就业率'
        }
      },
      {
        path: "/Statistics",
        name:"Statistics",
        component: () => import('../views/Statistics'),
        meta: {
          title: '汇总图'
        }
      }
    ]

  },


  {
    path: "/adminHome",
    name: "adminHome",
    component: () =>import('../views/AdminHome'),
    redirect: {name:'student'},
    meta: {
      title: '管理员主页'
    },
    children:
    [
      {
        path: "/Classes",
        name: "Classes",
        component: () =>import('../views/admin/Classes'),
        meta: {
          title: '班级管理'
        }
      },
      {
        path: "/student",
        name: "student",
        component: () =>import('../views/admin/Student'),
        meta: {
          title: '所有毕业生'
        }
      },
      {
        path: "/teacher",
        name: "teacher",
        component: () =>import('../views/admin/Teacher'),
        meta: {
          title: '所有辅导员'
        }
      },
      {
        path: "/recruit",
        name: "recruit",
        component: () =>import('../views/admin/Recruit'),
        meta: {
          title: '所有岗位'
        }
      },
      {
        path: "/EmpInfo",
        name: "EmpInfo",
        component: () =>import('../views/admin/EmpInfo'),
        meta: {
          title: '所有学生就业信息'
        }
      },
      {
        path: "/NewsAll",
        name:"NewsAll",
        component: () => import('../views/admin/NewsAll'),
        meta: {
          title: '所有通知'
        }
      }
    ]
  },

]

const router = new VueRouter({
  routes
})

export default router

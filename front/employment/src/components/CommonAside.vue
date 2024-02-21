<template>
    <el-menu  class="el-menu-vertical-demo"
              :default-active="path"
              @open="handleOpen"
              @close="handleClose"
              :collapse="isCollapse"
              background-color="#545c65"
              text-color="#fff"
              active-text-color="#ffdo4b" router>
        <h3 style="margin-left: 50px;color: white" v-show="!isCollapse">就业系统</h3>
        <h3 v-show="isCollapse">管理系统</h3>
        <el-menu-item index="/TeaClass">
            <template slot="title">
                <i class="el-icon-s-data"></i>
                <span slot="title">我的班级</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/myStudent">
            <template slot="title">
                <i class="el-icon-user-solid"></i>
                <span slot="title">我的学生</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/addRecruit">
            <template slot="title">
                <i class="el-icon-circle-plus"></i>
                <span slot="title">发布岗位信息</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/MyPost">
            <template slot="title">
                <i class="el-icon-s-help"></i>
                <span slot="title">我的岗位</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/News">
            <template slot="title">
                <i class="el-icon-s-goods"></i>
                <span slot="title">我的通知</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/showEmployInfo">
            <template slot="title">
                <i class="el-icon-s-platform"></i>
                <span slot="title">学生就业情况</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/showStudentInfo">
            <template slot="title">
                <i class="el-icon-zoom-in"></i>
                <span slot="title">查看学生信息</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/SumChart">
            <template slot="title">
                <i class="el-icon-info"></i>
                <span slot="title">班级就业率</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/Statistics">
            <template slot="title">
                <i class="el-icon-s-marketing"></i>
                <span slot="title">汇总图</span>
            </template>
        </el-menu-item>
    </el-menu>



</template>

<script>
    export default {
        name: "",
        data () {
            return {
                user: {},
                path: this.$route.path //设置默认高亮的菜单
            }
        },
        methods: {
            handleOpen(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(key, keyPath) {
                console.log(key, keyPath);
            }
        },
        computed: {
            isCollapse() {
                return this.$store.state.tab.isCollapse;
            }
        },
        created: function () {
            // let userStr = sessionStorage.getItem("user") || {}
            // this.user = JSON.parse(userStr)
            //
            // //请求服务器获取当前登陆的用户信息
            // request.get("/user/" + this.user.id).then(res => {
            //     if(res.code === '0'){
            //         this.user = res.data
            //     }
            // })
        }
    }
</script>

<style scoped>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        height: 100vh;
    }
</style>

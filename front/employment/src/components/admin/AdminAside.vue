<template>
    <el-menu  class="el-menu-vertical-demo"
              :default-active="this.$route.path"
              @open="handleOpen"
              @close="handleClose"
              :collapse="isCollapse"
              background-color="#545c65"
              text-color="#fff"
              active-text-color="#ffdo4b" router>
        <h3 v-show="!isCollapse" style="color: white;margin-left: 25px">就业系统后台管理</h3>
        <h3 v-show="isCollapse">管理系统</h3>
        <el-menu-item index="/Classes">
            <template slot="title">
                <i class="el-icon-help"></i>
                <span slot="title">班级管理</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/student">
            <template slot="title">
                <i class="el-icon-user-solid"></i>
                <span slot="title">学生管理</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/teacher">
            <template slot="title">
            <i class="el-icon-user"></i>
            <span slot="title">老师管理</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/recruit">
            <template slot="title">
                <i class="el-icon-s-platform"></i>
                <span slot="title">岗位信息</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/newsAll">
            <template slot="title">
                <i class="el-icon-s-platform"></i>
                <span slot="title">所有通知</span>
            </template>
        </el-menu-item>
        <el-menu-item index="/EmpInfo">
            <template slot="title">
                <i class="el-icon-s-order"></i>
                <span slot="title">就业信息</span>
            </template>
        </el-menu-item>
    </el-menu>
</template>

<script>
    export default {
        data() {
            return {

            };
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
        }
    }
</script>

<style scoped>
    .el-menu-vertical-demo:not(.el-menu--collapse) {
        width: 200px;
        height: 100vh;
    }
</style>

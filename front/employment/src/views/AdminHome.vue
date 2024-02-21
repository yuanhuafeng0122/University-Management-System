<template>
    <el-container style="height: 100%">
        <el-aside width="auto"><AdminAside></AdminAside></el-aside>
        <el-container>
            <el-header><AdminHead></AdminHead></el-header>
            <el-main>
                <router-view v-wechat-title="$route.meta.title"></router-view>
            </el-main>
        </el-container>
    </el-container>
</template>

<script>
    import AdminAside from "../components/admin/AdminAside";
    import AdminHead from "../components/admin/AdminHead";
    export default {H
        name: "",
        components:{AdminAside,AdminHead}
    }
</script>

<style scoped>
    .el-header{
        background-color: #545c65;
    }
    .el-main{
        padding-top: 0;
    }
</style>

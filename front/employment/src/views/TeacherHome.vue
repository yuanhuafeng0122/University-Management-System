<template>
    <el-container style="height: 100%">
        <el-aside width="auto"><CommonAside></CommonAside></el-aside>
        <el-container>
            <el-header><CommonHeader></CommonHeader></el-header>
            <el-main>
                <router-view v-wechat-title="$route.meta.title"></router-view>
            </el-main>
        </el-container>
    </el-container>

</template>

<script>
    import CommonAside from "../components/CommonAside";
    import CommonHeader from "../components/CommonHeader";
    export default {

        name: "",
        components:{CommonAside,CommonHeader},
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

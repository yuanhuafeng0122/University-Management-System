<template>
    <header>
        <div class="l-content">
<!--            <el-button @click="handleMenu()" class="el-button" plain icon="el-icon-menu" size="mini"></el-button>-->
            <h3 style="color: #ffffff;margin-left: 490px">管理员页面</h3>
        </div>
        <div class="r-content">
            <el-dropdown trigger="click" size="mini">
               <span class="el-dropdown-link">
                   <el-avatar :src="userImg"></el-avatar>
                   <span>
                       <el-dropdown-menu slot="dropdown">

                        <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
                      </el-dropdown-menu>
                   </span>
               </span>
            </el-dropdown>
        </div>
    </header>
</template>

<script>
    export default {
        data() {
            return {
                userImg: require("../../assets/picture/admin.jpg")
            }
        },
        methods:{
            handleMenu() {
                this.$store.commit('collapseMenu')
            },
            logout(){
                this.$axios.get("/user/logout").then(res => {
                    if (res.data.code === 200){
                        sessionStorage.removeItem("user");//删除名称为“user”的信息。
                        this.$message.success("退出成功")
                        this.$router.push({name:"login"})
                    }else {
                        this.$message.error("系统异常，请检查网络是否连接")
                    }
                })
            }
        }
    }
</script>

<style scoped>
    header{
        display: flex;
        height: 100%;
        align-items: center;
        justify-content: space-between;
    }

    .l-content{
        display: flex;
        align-items: center;
    }
    .el-button{
        margin-right: 20px;
    }
    .user{
        width: 40px;
        height: 40px;
        border-radius: 50%;
    }
    .r-content{

    }
</style>

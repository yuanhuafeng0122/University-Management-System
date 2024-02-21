<template>
    <div>


    <header>
        <div class="l-content">
            <h3 style="color: #ffffff;align-content: center;margin-left: 500px">教师页面</h3>
        </div>
        <div class="r-content">
            <el-dropdown trigger="click" size="mini">
               <span class="el-dropdown-link">
                   <el-avatar :src="userImg"></el-avatar>
                   <span>
                       <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item @click.native="openUserInfo">个人中心</el-dropdown-item>
                        <el-dropdown-item @click.native="logout">退出</el-dropdown-item>
                      </el-dropdown-menu>
                   </span>
               </span>
            </el-dropdown>
        </div>
    </header>


        <el-drawer
                :visible.sync="if_show"
                :show-close="false"
                :size=300>
            <h3 class="title">个人中心</h3>
            <div class="card">
                <div class="ava">
                    <el-avatar class="ava" :src="userImg"></el-avatar>
                </div>
                <div class="info">
                    <el-row>
                        <el-col>
                            <span>姓名:&emsp;&emsp;&emsp;{{teacher.name}}</span>
                        </el-col>

                        <el-col >
                            <span>学院:&emsp;&emsp;&emsp;{{teacher.collegeName}}</span>
                        </el-col>

                        <el-col>
                            <span>办公室:&emsp;&emsp;&emsp;{{teacher.workRoom}}</span>
                        </el-col>
                        <el-col>
                            <span>联系电话:&emsp;{{teacher.phone}}</span>
                        </el-col>
                        <el-col>
                            <span>邮箱:&emsp;{{teacher.email}}</span>
                        </el-col>
                        <el-col>
                            <el-button type="primary" round size="mini" @click="modifyInfo">个人信息</el-button>
                            <el-button type="primary" round size="mini" @click="modifyPassword">修改密码</el-button>
                        </el-col>
                    </el-row>
                </div>

            </div>

        </el-drawer>

        <ModifyPassword v-model="openModifyPassword"></ModifyPassword>
        <ModifyTeaInfo v-model="openModifyTeaInfo"></ModifyTeaInfo>

    </div>
</template>

<script>
    import ModifyPassword from "./ModifyPassword";
    import ModifyTeaInfo from "./ModifyTeaInfo";
    export default {
        components:{ModifyPassword,ModifyTeaInfo},
        data() {
            return {
                openModifyTeaInfo:false,
                openModifyPassword:false,
                if_show:false,
                handleClose:false,
                userImg: '',
                user: {
                    id:'',
                    account:'',
                    password:'',
                    phone:'',
                    roleId:''
                },
                teacher:{
                    id:'',
                    name:'',
                    phone:'',
                    workRoom:'',
                    email:'',
                    collegeName:''
                }
            }
        },
        methods:{

            modifyPassword(){
                this.openModifyPassword = true
            },

            modifyInfo(){
                this.openModifyTeaInfo = true
            },

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
            },
            openUserInfo(){
                this.if_show = true
            }
        },
        mounted() {
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.$axios.post("teacher/findTeaByPhone/"+this.user.account).then(res => {
                if (res.data.code === 200){
                    this.teacher = res.data.data
                    this.userImg = this.teacher.cover
                }else {
                    this.$message.error(res.data.msg)
                }
            })
        }

    }

</script>

<style scoped  lang="less">
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

    .title{
        margin-left: 100px;
    }
    .ava{
        margin-left: 50px;
        width: 80px;
        height: 80px;
    }
    .info{
        margin-left: 50px;
    }
    /deep/.el-avatar>img {
        display: block;
        height: 100%;
        width: 100%;
        vertical-align: middle;}
</style>

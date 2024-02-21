<template>
    <div
        <div class="head">
            <div><el-image class="image" :src="imgUrl1"></el-image></div>
            <div style="margin-left: 50px;margin-top: 13px">
                <el-button @click="showNews = true" round>通知</el-button>
                <el-button @click="add" round>填写就业信息</el-button>
                <el-button @click="modify" round>查看就业信息</el-button>
                <el-button @click="goinformation" round>个人中心</el-button>
                <el-button @click="logout" round>退出登录</el-button>
            </div>
        </div>
        <div class="recruit">
        <RecruitInfo></RecruitInfo>
        </div>
        <AddEmployInfo :number="number" v-model="openAdd" ></AddEmployInfo>
        <ModifyEmployInfo :number="number"  v-model="openModify"></ModifyEmployInfo>
        <el-drawer :visible.sync="showNews" direction="ltr" :show-close="false" :size=300 :before-close="handleClose">
            <h3 class="title">通知</h3>
            <div class="block">
                <el-carousel height="200px" >
                    <el-carousel-item v-for="item in newss" :key="item" @click.native="imgDetail(item,index)">
                       <el-image :src="item.cover" style="height: 200px"/>
                    </el-carousel-item>
                </el-carousel>
            </div>
            <el-card class="box-card" v-for="item in newss2"  :key="item" style="margin-top: 10px">
                <div slot="header" class="clearfix">
                    <span>{{item.title}}</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="detailInfoNews(item)">详情</el-button>
                </div>
                <el-image v-if="item.cover !== ''" :src="item.cover" style="height: 50px; width: 50px; "/>
                <textarea v-model="item.contentH" :rows="3"  style="resize: none; margin-left: 10px; border: none" class="text item"  >
                </textarea>
            </el-card>
        </el-drawer>
        <el-drawer :visible.sync="if_show" :direction="direction" :show-close="false" :size=300 :before-close="handleClose">
            <h3 class="title">个人中心</h3>
            <div class="card">
                <div class="ava">
                    <el-avatar :src="imgUrl" shape="square"></el-avatar>
                </div>
                <div class="info">
                    <el-row>
                        <el-col>
                            <span>学号:&emsp;&emsp;&emsp;{{student.number}}</span>
                        </el-col>
                        <el-col>
                            <span>姓名:&emsp;&emsp;&emsp;{{student.name}}</span>
                        </el-col>
                        <el-col >
                            <span>学院:&emsp;&emsp;&emsp;{{student.collegeName}}</span>
                        </el-col>
                        <el-col >
                            <span>专业:&emsp;&emsp;&emsp;{{student.major}}</span>
                        </el-col>
                        <el-col>
                            <span>班级:&emsp;&emsp;&emsp;{{student.className}}</span>
                        </el-col>
                        <el-col>
                            <span>联系电话:&emsp;{{student.phone}}</span>
                        </el-col>
                        <el-col>
                            <el-button type="primary" round size="mini" @click="modifyInfo">修改信息</el-button>
                            <el-button type="primary" round size="mini" @click="modifyPassword">修改密码</el-button>
                        </el-col>
                    </el-row>
                    <ModifyStuInfo v-model="openModifyInfo"></ModifyStuInfo>
                    <ModifyPassword v-model="openModifyPassword"></ModifyPassword>
                </div>
            </div>
        </el-drawer>


        <el-dialog title="通知详情" style="min-height: 100px; margin: 5px"  :visible.sync="vis" width="50%" >
            <el-card style="display: flex">
                <div style="font-size: 24px; font-weight: bold">标题：</div>
                <div>{{detail.title}}</div>
            </el-card>

            <el-card v-if="detail.cover" style="margin-top: 10px">
                <img  :src="detail.cover" class="avatar" style="width: 300px;height: 200px">
            </el-card>

            <el-card style="margin-top: 10px">
                <div style="font-size: 24px; font-weight: bold">内容：</div>

                <div v-html="detail.content"> </div>
            </el-card>
        </el-dialog>

    </div>

</template>

<script>
    import CardMe from "../components/CardMe";
    import RecruitInfo from "../components/RecruitInfo";
    import AddEmployInfo from "../components/AddEmployInfo";
    import ModifyEmployInfo from "../components/ModifyEmployInfo";
    import ModifyStuInfo from "../components/ModifyStuInfo";
    import ModifyPassword from "../components/ModifyPassword";

    export default {
        name: "",
        components: {CardMe,RecruitInfo,AddEmployInfo,ModifyEmployInfo,ModifyStuInfo,ModifyPassword},
        data() {
            return {
                detail:{},
                newss2:[],
                newss:[],
                showNews: false,
                direction: 'rtl',
                className:'',
                search:'',
                openModifyInfo:false,
                openModifyPassword:false,
                imgUrl1: require("../assets/picture/aaa.png"),
                imgUrl: "",
                currentDate: new Date(),
                openAdd: false,
                openModify: false,
                number:'',
                teacher:{},
                student:{
                    id:'',
                    number:'',
                    name:'',
                    idNumber:'',
                    gender: '',
                    dormitory:'',
                    address: '',
                    phone:'',
                    teacherId:'',
                    major:'',
                    className:'',
                    collegeName:'',
                    cover:''
                },
                user: {
                    id:'',
                    account:'',
                    password:'',
                    phone:'',
                    roleId:''
                },
                if_show:false,
                vis:false
            }
        },
        methods: {
            detailInfoNews(news){
                this.detail = news
                this.vis = true
            },
            imgDetail(item,index){
                this.detail = item
                this.vis = true
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                        done();
                    })
                    .catch(_ => {});
            },
            add() {
                if(this.user === null){
                    this.$message.error("未登录，无法打开")
                }else {
                    this.$axios.get("/employInfo/findEmpByNum/"+this.number).then(res => {
                        if (res.data.code === 200){
                            this.$message.error("您已经填写过，如需修改请点击查看就业信息")
                            return
                        }else {
                            this.openAdd = true;
                        }
                    })
                }

            },
            modify(){
                if(this.user === null){
                    this.$message.error("未登录，无法打开")
                }else {
                    //要是有信息就打开，没有提交就不打开
                    this.$axios.get("/employInfo/findEmpByNum/"+this.number).then(res => {
                        if (res.data.code === 200){
                            this.openModify = true;
                        }else {
                            this.$message.error("请先填写就业信息")
                        }
                    })


                }
            },
            goinformation(){
                if(this.user === null){
                    this.$message.error("未登录，无法打开")
                }else {
                    this.if_show=true
                }
            },
            // getClass(claId){
            //     this.$axios.get("/class/findClassById/"+claId).then(res => {
            //         if (res.data.code === 200){
            //             this.className = res.data.data.className
            //         }else {
            //             this.$message.error("系统异常 ")
            //         }
            //     })
            // },
            getStu(){
                this.$axios.get("/student/getStudentInfo/"+this.user.account).then(res => {
                    if (res.data.code === 200){
                        this.student = res.data.data
                        this.imgUrl = this.student.cover
                        this.getTea()
                    }else {
                        this.$message.error("系统异常 ")
                    }
                })
            },
            getTea(){
                this.$axios.get("/teacher/findTeaById/"+this.student.teacherId).then(res =>{
                    if(res.data.code === 200){
                        this.teacher = res.data.data
                        this.getNews()
                        this.getNews2()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getNews2(){
                this.$axios.get("/news/findNesAllByTeaId/"+this.teacher.id).then(res =>{
                    if(res.data.code === 200){
                        this.newss2 = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getNews(){
                this.$axios.get("/news/findNesByTeaId/"+this.teacher.id).then(res =>{
                    if(res.data.code === 200){
                        this.newss = res.data.data
                        console.log(this.newss)
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            modifyInfo(){
                this.openModifyInfo = true;
            },
            modifyPassword(){
                this.openModifyPassword = true;
            },
            logout(){
                if (this.user === null){
                    this.$message.error("未登录，请先登录")
                }else {
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

        },
        created() {
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.number = this.user.account;
            this.getStu()
        },

    }
</script>

<style scoped lang="less">
    .head{
        display: flex;

    }
    .recruit{

    }

    .title{
        margin-left: 100px;
    }
    .el-avatar{
        margin-left: 95px;
        width: 80px;
        height: 80px;
    }
    .info{
        margin-left: 50px;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 18px;
        opacity: 0.75;
        line-height: 300px;
        margin: 0;
    }

    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 150px;
        margin: 0;
    }

    .el-carousel__item:nth-child(2n) {
        background-color: #99a9bf;
    }

    .el-carousel__item:nth-child(2n+1) {
        background-color: #d3dce6;
    }
    /deep/.el-drawer__title{
        margin-bottom: 0px;
        padding: 0px 0px 0;
    }
    .text {
        font-size: 14px;
    }

/deep/ .el-drawer__header{
    margin-bottom: 0px;
    padding: 0px 0px 0;
}

    /deep/.el-avatar>img {
      display: block;
      height: 100%;
      width: 100%;
      vertical-align: middle;
    }
</style>

<template>
    <div style="width: 100%; height: 100vh;  overflow: hidden" >
        <div style="width: 400px; margin: 10px auto">
            <div style="color: #2b4b6b; font-size: 30px; text-align: center; padding: 30px 0">欢迎辅导员注册</div>
            <el-form ref="form" :model="teacher" size="normal" :rules="rules">

                <el-form-item label="学院" prop="college">
                    <el-select v-model="teacher.collegeName"  placeholder="请选择">
                        <el-option @click.native="getCollege(item.name)" v-for="item in colleges" :key="item.id" :label="item.name" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item prop="name" label="姓名：">
                    <el-input  v-model="teacher.name" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="上传头像">
                    <el-input v-model="teacher.cover" v-if="false"></el-input>
                    <el-upload
                            class="avatar-uploader"
                            action="http://192.168.43.190:8000/files/upload"
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="filesUploadSuccess">
                        <img v-if="teacher.cover" :src="teacher.cover" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>
                <el-form-item prop="phone" label="账号：">
                    <el-input   v-model="teacher.phone" placeholder="请输入手机号（账号）"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="密码：">
                    <el-input   v-model="user.password" placeholder="请输入密码"></el-input>
                </el-form-item>
                <el-form-item prop="password" label="确认密码：">
                    <el-input   v-model="confirmPassword" placeholder="请输入确认密码"></el-input>
                </el-form-item>
                <el-form-item prop="email" label="邮箱：">
                    <el-input   v-model="user.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>
                <el-form-item prop="workRoom" label="工作地点：">
                    <el-input   v-model="teacher.workRoom" placeholder="请输入办公室"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%;" type="primary" @click="register">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script>
    import SelectCollege from "../components/SelectCollege";
    export default {
        name: "",
        components: {
            SelectCollege
        },
        data() {
            return{
                colleges:[],
                confirmPassword:'',
                collegeId: '',
                teacher: {
                    name: '',
                    phone:'',
                    collegeName: '',
                    workRoom: '',
                    email:'',
                    cover:''
                },
                user: {
                    account: '',
                    password:'',
                    phone: '',
                    email: '',
                    roleId: ''
                },
                rules: {
                    username: [
                        {required: true, message: '请输入用户名', trigger: 'blur'},
                        {min: 9, max: 15, message: '长度在 3 到 8 个字符', trigger: 'blur'}
                    ],
                    password: [
                        {required: true, message: '请输入密码', trigger: 'blur'},
                        {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}
                    ],
                    confirmPassword: [
                        {required: true, message: '请输入确认密码', trigger: 'blur'},
                        {min: 6, max: 12, message: '长度在 6 到 12 个字符', trigger: 'blur'}
                    ],
                }
            }
        },
        mounted() {
          this.getCol()
        },
        methods: {
            filesUploadSuccess(res) {
                this.teacher.cover = res.data
            },
            beforeUpload(file){
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            },
            getCollege(collegeName){
                this.teacher.collegeName = collegeName
            },
            getCol(){
                this.$axios.get("/college/allCollege").then(res => {
                    if (res.data.code === 200){
                        this.colleges = res.data.data;
                    }else {
                        this.$message.error("获取学院数据错误！")
                    }
                })
            },
            register() {
                if (this.user.password !== this.confirmPassword){
                    this.$message({
                        type: "error",
                        message: "两次密码输入不一致"
                    })
                    return;
                }
                this.teacher.collegeId = this.collegeId
                this.user.account = this.teacher.phone
                this.user.phone = this.teacher.phone
                this.teacher.email = this.user.email
                this.user.roleId = 2
                this.$axios.post("/user/regUserAndTea",{user:this.user,teacher:this.teacher}).then(res =>{
                    if (res.data.code === 200){
                        this.$message.success("注册成功")
                        //传递两个参数 账号和密码还有选中是谁登录
                        this.$router.push({
                            name:"login",
                            params:{
                                account:this.user.account,
                                password:this.user.password,
                                roleId: this.user.roleId
                            }
                        })
                    }else if (res.data.code === 10006){
                        this.$message.error("手机号已经存在请重新确认或联系管理员")
                    } else {
                        this.$message.error("注册失败")
                    }
                })

            },
            fun(collegeName){
                this.teacher.collegeName = collegeName
            }
        }
    }
</script>

<style scoped lang="less">
    .el-form-item{
        margin-bottom: 10px !important;
    }
    .el-form-item__content{
        display: flex;
    }
    .el-form>div{
      display:flex;
      justify-content: space-between;
    }

    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      background: #DEE1E6;
      font-size: 28px;
      color: #8c939d;
      width: 80px;
      height: 80px;
      line-height: 80px;
      text-align: center;
    }
    .avatar {
      width: 80px;
      height: 80px;
      display: block;
    }
</style>

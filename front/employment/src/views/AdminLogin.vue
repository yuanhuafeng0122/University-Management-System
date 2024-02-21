<template>
    <div>
        <div class="login_container">
            <div>
                <img :src="src" width="1000px" height="100px">
            </div>
            <div class="background">
                <img :src="backGroundImg" width="100%" height="100%">
            </div>



            <div class="login_box">
                <div class="avatar_box">

                    <el-form ref="form" :model="form" label-width="80px" class="login_form" :rules="rules">
                        <el-form-item label="账号" prop="account">
                            <el-input v-model="form.account"></el-input>
                        </el-form-item>
                        <el-form-item label="密码" prop="password">
                            <el-input v-model="form.password" show-password></el-input>
                        </el-form-item>
                        <el-form-item class="btns">
                            <el-button type="primary" @click="submitForm('form')">登录</el-button>
                            <el-button @click="resetForm('form')">重置</el-button>
                            <el-button type="text"  @click="gotoLogin">返回用户登录</el-button>
                        </el-form-item>

                    </el-form>
                </div>

            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "",
        data(){
            return {
                backGroundImg: require("../assets/picture/bc.jpeg"),
                src: require("../assets/picture/img.png"),
                form: {
                    account: '',
                    password: '',
                    roleId: 3,
                },
                rules: {
                    account: [
                        { required: true, message: '请输入账号', trigger: 'blur' },
                        { min: 3, max: 16, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 3, max: 16, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                    ],
                },

            }
        },
        methods: {
            gotoLogin(){
                this.$router.push({name:'login'})
            },
            resetForm(form) {
                this.$refs[form].resetFields();
            },
            submitForm(form) {

                this.$refs[form].validate((valid) => {
                    if (valid) {
                        //请求后端的数据，返回一个管理员信息

                        this.$axios.post("user/login",this.form).then(res =>{
                            if (res.data.code === 200){
                                this.user = res.data.data
                                sessionStorage.setItem("user", JSON.stringify(res.data.data))
                                this.$router.push({name:'adminHome'})
                            }else {
                                this.$message.error("登录失败请联系开发人员")
                            }
                        })

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
        }
    }
</script>

<style scoped>
    .login_container{
        background-color: #2b4b6b;
        height: 100%;
    }
    .login_box{
        width: 450px;
        height: 300px;
        background-color: #fff;
        border-radius: 3px;
        position: absolute;
        left: 50%;
        top: 50%;
        transform: translate(-50%,-50%);
    }
    .btns{
        display: flex;
        justify-content: flex-end;
    }
    .login_form{
        position: absolute;
        bottom: 0%;
        width: 100%;
        padding: 0 10px;
        box-sizing: border-box;
    }
</style>

<template>
    <div class="login_container">
        <div class="background">
            <img :src="backGroundImg" width="100%" height="100%">
        </div>

        <div>
            <img :src="src" width="1000px" height="100px">
        </div>

        <div class="login_box">
            <div class="avatar_box">
                <el-form ref="form" :model="form" label-width="80px" class="login_form" :rules="rules">

                    <div style="margin-left: 150px;padding: 25px;">毕业生就业管理系统</div>

                    <el-tooltip  placement="top">
                        <div slot="content">学生的账号为学号<br/>老师的账号为手机号码</div>
                        <el-form-item label="账号" prop="account">
                            <el-input v-model="form.account">{{this.$route.params.account}}</el-input>
                        </el-form-item>
                    </el-tooltip>

                    <el-form-item label="密码" prop="password">
                        <el-input show-password v-model="form.password">{{this.$route.params.password}}</el-input>
                    </el-form-item>
                    <div class="gender">
                        <el-radio-group v-model="form.roleId">
                            <el-radio :label="1" value="1"  >用户</el-radio>
                            <el-radio :label="2" value="2"  >教师</el-radio>
                        </el-radio-group>
                    </div>
                    <el-form-item class="btns">
                        <el-button type="primary" @click="submitForm('form')">登录</el-button>
                        <el-button @click="resetForm('form')">重置</el-button>
                        <el-button type="text"  @click="registerDialogVisible = true">注册</el-button>
                        <el-button type="text"  @click="gotoAdmin">管理员登录</el-button>
                    </el-form-item>

                </el-form>
            </div>

        </div>
        <el-dialog
                :visible.sync="registerDialogVisible"
                width="30%"
                center>
            <h2>请选择身份</h2>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="registerStudent">学生</el-button>
                <el-button type="primary" @click="registerTeacher">辅导员</el-button>
            </span>
        </el-dialog>
    </div>

</template>

<script>
    export default {
        data() {
            return {
                backGroundImg: require("../assets/picture/bc.jpeg"),
                radio: '',
                src:require("../assets/picture/img.png"),
                registerDialogVisible: false,
                form: {
                    account: '',
                    password: '',
                    roleId: 1,
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
                }
            }
        },
        mounted() {
          if (this.$route.params.roleId === '1'){
            this.form.roleId = 1;
          }else if (this.$route.params.roleId === '2'){
              this.form.roleId = 2 ;
          }
        },
        methods: {
            resetForm(form) {
                this.$refs[form].resetFields();
            },
            submitForm(form) {

                //设置单选框必须填写
                if (this.form.roleId==null){
                    this.$message.error("请选择登录角色！")
                    return
                }

                this.$refs[form].validate((valid) => {
                    if (valid) {
                        //请求后端的数据，返回一个用户信息然后判断user.roleId=1还是2还是3，进入不同的页面

                        this.$axios.post("user/login",this.form).then(res =>{


                            if (res.data.data == null){
                                if (res.data.code === 10005){
                                    //用户不存在
                                    this.$message.error(res.data.msg)
                                    return
                                }
                            }else {
                                let roleId = res.data.data.roleId
                                if(roleId === 1) {
                                    sessionStorage.setItem("user", JSON.stringify(res.data.data))
                                    this.$message.success("登录成功")
                                    this.$router.push({name:'home'})
                                }else if (roleId === 2){
                                    sessionStorage.setItem("user", JSON.stringify(res.data.data))
                                    this.$message.success("登录成功")
                                    this.$router.push({name:'teacherHome'})
                                }
                            }

                        })

                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            registerStudent(){
                //跳转到学生注册
                this.registerDialogVisible=false
                this.$router.push({name:'registerStudent'})
            },
            registerTeacher(){
                //跳转到老师注册
                this.registerDialogVisible=false
                this.$router.push({name:'registerTeacher'})
            },
            gotoAdmin(){
              //跳转到管理员登录
                this.$router.push({name:'adminLogin'})
            },
        }
    }
</script>

<style lang="less" scoped>
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
    .gender{
      margin-left: 80px;
    }
    .background{
      width:100%;
      height:100%;  /**宽高100%是为了图片铺满屏幕 */
      z-index:-1;
      position: absolute;
    }

</style>

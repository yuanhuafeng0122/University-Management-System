<template>
    <div style="width: 100%; height: 100vh;  overflow: hidden" >
        <div style="width: 400px; margin: 10px auto">
            <div style="color: #0a0a0a; font-size: 30px; text-align: center; padding: 5px 0">欢迎学生注册</div>
            <el-form ref="student" :inline="true" :model="student" size="normal" :rules="rules">
                <el-form-item label="学院" prop="college">
                    <el-select v-model="student.collegeName"  placeholder="请选择">
                        <el-option @click.native="getMajor(item.id,item.name)" v-for="item in colleges" :key="item.id" :label="item.name" :value="item.name"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="专业" prop="major">
                    <el-select v-model="student.major" placeholder="请选择" >
                        <el-option @click.native="getClasses(item.name)" v-for="item in majors" :key="item.id" :label="item.name" :value="item.name"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="班级" prop="myClass">
                    <el-select v-model="myClass" placeholder="请选择" >
                        <el-option @click.native="getTea(item.className,item.id)" v-for="item in classes" :key="item.id" :label="item.className" :value="item.className"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="辅导员">

                    <el-input  v-model="teacherName" disabled placeholder="请选择班级"></el-input>

<!--                 <el-select v-model="student.teacherName" placeholder="请选择" >-->
<!--                 <el-option @click.native="getClasses(item.id)" v-for="item in teacher" :key="item.id" :label="item.name" :value="item.name" ></el-option>-->
<!--                 </el-select>-->
                </el-form-item>
                <el-form-item label="姓名" prop="name">
                    <el-input  v-model="student.name" placeholder="姓名"></el-input>
                </el-form-item>


                <el-form-item label="上传头像">
                    <el-input v-model="student.cover" v-if="false"></el-input>
                    <el-upload
                            class="avatar-uploader"
                            action="http://192.168.43.190:8000/files/upload"
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="filesUploadSuccess">
                        <img v-if="student.cover" :src="student.cover" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

                <el-form-item label="性别">
                    <el-radio-group  v-model="student.gender">
                        <el-radio :label="1" value="男"  >男</el-radio>
                        <el-radio :label="2" value="女"  >女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="学号(账号)" prop="number">
                    <el-input   v-model="student.number"  placeholder="学号（账号）"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input  show-password v-model="user.password"  placeholder="密码"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="confirmPassword">
                    <el-input show-password   v-model="confirmPassword"  placeholder="确认密码"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input    v-model="user.email"  placeholder="邮箱"></el-input>
                </el-form-item>

                <el-form-item label="身份证号码" prop="idNumber">
                    <el-input   v-model="student.idNumber" placeholder="身份证号"></el-input>
                </el-form-item>
                <el-form-item label="宿舍号" prop="dormitory">
                    <el-input   v-model="student.dormitory" placeholder="宿舍号"></el-input>
                </el-form-item>
                <el-form-item label="家庭地址" prop="address">
                    <el-input   v-model="student.address" placeholder="家庭地址"></el-input>
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-input   v-model="student.phone" placeholder="联系电话"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button style="width: 100%;" type="primary" @click="register('student')">注册</el-button>
                </el-form-item>
            </el-form>

        </div>
    </div>
</template>

<script>
    export default {
        name: "",
        data() {
            return{
                cover:'',
                myClass:'',
                teacherName:'',
                classes:[],
                colleges:[],
                majors:[],
                college:'',
                number:'',
                confirmPassword: '',
                teacher: {
                    id: '',
                    name:'',
                    phone:'',
                    collegeId:'',
                    workRoom:'',
                    email:''
                },
                student: {
                    name:'',
                    number:'',
                    major: '',
                    className:'',
                    gender: 1,
                    idNumber:'',
                    dormitory:'',
                    address:'',
                    phone:'',
                    collegeName:'',
                    teacherId:'',
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
                    idNumber: [
                        {required: true, message: '请输入身份证号码', trigger: 'blur'},
                        {min: 18, max: 18, message: '长度在 18 个字符', trigger: 'blur'}
                    ],
                    name: [
                        {required: true, message: '请输入姓名', trigger: 'blur'},
                        {min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur'}
                    ],
                    number: [
                        {required: true, message: '请输入学号', trigger: 'blur'},
                        {min: 12, max: 12, message: '长度在 12 个字符', trigger: 'blur'}
                    ],
                }
            }
        },
        watch: {
            'student.college': function (newValue, oldValue) {
                this.student.major = null
            }
        },
        mounted() {
            this.getCollege();
            this.url = window.location.host
        },
        methods: {
            filesUploadSuccess(res) {
                this.student.cover = res.data
                console.log(this.student.cover)
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
            getTea(className,classId){
                this.student.className = className
                //通过班级找到老师Id
                this.$axios.get("/class/findTeaByClassName/"+className).then(res =>{
                    if(res.data.code === 200){
                        this.student.teacherId = res.data.data.id
                        this.student.classId = classId
                        this.myClass = className
                        this.teacherName = res.data.data.name
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getClasses(majorName){
                this.$axios.get("/class/findClassByMajor/"+majorName).then(res =>{
                    if(res.data.code === 200){
                        this.classes = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getCollege(){
                let data = {};
                this.$axios.get("/college/allCollege",data).then(res => {
                    if (res.data.code === 200){
                        this.colleges = res.data.data;
                    }else {
                        this.$message.error("获取学院数据错误！")
                    }

                })
            },
            getMajor(collegeId,collegeName){
                this.$axios.get("/major/findMajorByCollege/"+collegeId).then(res =>{
                    if (res.data.code === 200){
                        this.majors = res.data.data;
                    }else {
                        this.$message.error("获取专业数据错误！")
                    }
                })

                this.$axios.post("/teacher/findAllTeaByCollegeName/"+collegeName).then(res => {
                    if (res.data.code === 200){
                        this.teacher = res.data.data
                    }else {
                        this.$message.error("获取老师信息错误")
                    }
                })


            },
            register(student){
                if (this.user.password !== this.confirmPassword){
                    this.$message({
                        type: "error",
                        message: "两次密码输入不一致"
                    })
                    return;
                }

                if (this.student.gender === 1){
                    this.student.gender = "男"
                }else if (this.student.gender === 2){
                    this.student.gender = "女"
                }else {
                    this.$message.error("请选择性别")
                    return
                }

                this.user.account = this.student.number
                this.user.roleId = 1
                this.user.phone = this.student.phone



                this.$refs[student].validate((valid) =>{
                    if (valid){
                        console.log(this.student)
                        this.$axios.post("/user/regUserAndStu",{user:this.user,student:this.student}).then(res => {
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
                            }else if (res.data.code === 10004){
                                this.$message.error("学号（账号）已经存在请重新确认或联系管理员")
                            } else {
                                this.$message.error("注册失败")
                            }
                        })

                    }else {
                        console.log('error submit!!');
                        return false;
                    }
                });

            },
        }
    }
</script>

<style scoped>
    .el-form-item{
        margin-bottom: 1px !important;
    }
    .el-form-item{
        display: flex;
        justify-content: space-between !important;
    }
    .el-form-item::after, .el-form-item::before{
        content: none;
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

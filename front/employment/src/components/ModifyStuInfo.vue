<template>
    <div>
        <el-dialog :modal="false" center title="个人信息" :visible="showMask" :showClose="false" :close-on-click-modal="close_modal">

            <div>
                <el-form :model="student" label-width="100px">
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

                    <el-form-item label="姓名">
                        <el-input v-model="student.name" ></el-input>
                    </el-form-item>
                    <el-form-item label="学号">
                        <el-input v-model="student.number" disabled ></el-input>
                    </el-form-item>

                    <el-form-item label="学院" prop="college">
                        <el-select v-model="student.collegeName"  placeholder="请选择">
                            <el-option @click.native="getMajor(item.id,item.name)" v-for="item in colleges" :key="item.id" :label="item.name" :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="专业" prop="major">
                        <el-select v-model="student.major" placeholder="请选择" >
                            <el-option @click.native="getClasses(item.name)" v-for="item in majors" :key="item.id" :label="item.name" :value="item.name"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="班级" prop="classes">
                        <el-select v-model="student.className" placeholder="请选择" >
                            <el-option @click.native="getTea(item.className,item.id)" v-for="item in classes" :key="item.id" :label="item.className" :value="item.className"></el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="辅导员" prop="teacherName">
                        <el-input  v-model="teacherName" disabled placeholder="请先选择班级"></el-input>
                    </el-form-item>
                    <div class="gender">

                        <el-radio-group v-model="student.gender">
                            <el-radio label="男" value="男"  >男</el-radio>
                            <el-radio label="女" value="女"  >女</el-radio>
                        </el-radio-group>
                    </div>

                    <el-form-item label="身份证号" prop="idNumber">
                        <el-input   v-model="student.idNumber" placeholder="身份证号"></el-input>
                    </el-form-item>
                    <el-form-item label="宿舍" prop="dormitory">
                        <el-input   v-model="student.dormitory" placeholder="宿舍号"></el-input>
                    </el-form-item>
                    <el-form-item label="家庭地址" prop="address">
                        <el-input   v-model="student.address" placeholder="家庭地址"></el-input>
                    </el-form-item>
                    <el-form-item label="联系电话" prop="phone">
                        <el-input   v-model="student.phone" placeholder="联系电话"></el-input>
                    </el-form-item>

                </el-form>
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button type="success" @click="closeMask">关闭</el-button>
                <el-popover
                        placement="top"
                        width="160"
                        v-model="visible">
                    <p>请检查信息是否无误，确定是否修改？</p>
                    <div style="text-align: right; margin: 0">
                        <el-button size="mini" type="text" @click="visible = false">取消</el-button>
                        <el-button type="primary" size="mini" @click="modifyInfo">确定</el-button>
                    </div>
                    <el-button type="warning" slot="reference">修改</el-button>
                </el-popover>
            </div>
        </el-dialog>
    </div>

</template>

<script>
    import SelectCollege from "./SelectCollege";
    export default {
        name: "",
        computed:{SelectCollege},
        props: {
            value:{} ,// 注意此处获取的value对应的就是组件标签中的v-model
        },
        data(){
            return {
                teacherName:'',
                class:{},
                className:'',
                classes:[],
                visible: false,
                teachers:[],
                majors:[],
                colleges:[],
                showMask: false,
                close_modal:false,
                oldClass:'',
                user: {
                    id:'',
                    account: '',
                    password:'',
                    phone: '',
                    email: '',
                    roleId: ''
                },
                student: {
                    name:'',
                    number:'',
                    major: '',
                    className:'',
                    gender: '',
                    idNumber:'',
                    dormitory:'',
                    address:'',
                    phone:'',
                    collegeName: '',
                    teacherId:'',
                    cover:''
                },
            }
        },
        methods:{
            filesUploadSuccess(res) {
                this.student.cover = res.data
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
                        this.teachers = res.data.data
                    }else {
                        this.$message.error("获取老师信息错误")
                    }
                })


            },
            closeMask(){
                this.showMask = false;
            },
            modifyInfo(){
                this.visible = false;
                //修改个人信息
                this.$axios.put("student/modifyStu/"+this.oldClass,this.student).then(res => {
                    if (res.data.code === 200){
                        console.log(this.student)
                        this.student = res.data.data
                        this.$message.success("更新成功")
                        setTimeout(function (){
                            location.reload()
                        },1000)

                        this.showMask = false;
                        this.getStu()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })


            },
            getClass(claId){
                // this.$axios.get("/class/findClassById/"+claId).then(res => {
                //     if (res.data.code === 200){
                //         this.class = res.data.data
                //         this.className = res.data.data.className
                //         this.getTea1(this.class.teaId)
                //     }else {
                //         this.$message.error("系统异常 ")
                //     }
                // })
            },
            getStu(){
                this.$axios.get("/student/getStudentInfo/"+this.user.account).then(res => {
                    if (res.data.code === 200){
                        this.student = res.data.data
                        this.oldClass = this.student.className
                        this.getTea1(this.student.teacherId)
                        this.getClass(this.student.classId)
                    }else {
                        this.$message.error("系统异常 ")
                    }
                })
            },
            getTea1(id){
                this.$axios.get("/teacher/findTeaById1/"+id).then(res => {
                    if (res.data.code === 200){
                        console.log(123456458)
                        this.teacher = res.data.data
                        this.teacherName = res.data.data.name
                        console.log(this.teacherName+"1111111111")
                    }else {
                        this.$message.error("系统异常 ")
                    }
                })
            },
            getTea(className,classId){
                //通过班级找到老师Id
                this.$axios.get("/class/findTeaByClassName/"+className).then(res =>{
                    if(res.data.code === 200){
                        this.teacher = res.data.data
                        this.teacherName = res.data.data.name
                        this.student.teacherId = this.teacher.id
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
        },
        created() {
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.getStu()
        },
        mounted(){
            this.showMask = this.value;    // 在生命周期中，把获取的value值获取给showMash
            this.getCollege();
        },
        watch:{
            value(newVal, oldVal){
                this.showMask = newVal;// 监测value的变化，并赋值。
            },
            showMask(val) {
                this.$emit('input', val);    // 此处监测showMask目的为关闭弹窗时，重新更换value值，注意emit的事件一定要为input。
            }
        },

    }
</script>

<style scoped>
    .el-form-item{
        margin-bottom: 1px !important;
        width: 60%;
    }
    .gender{
        margin-left: 120px;
    }
    .dialog-footer{

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

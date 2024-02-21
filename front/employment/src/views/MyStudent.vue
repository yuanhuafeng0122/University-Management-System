<template>
    <div>
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入学生姓名或学号"  style="width: 20%;" clearable></el-input>
            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
            <el-button type="primary" @click="handleDownload">导出</el-button>
            <el-button type="primary" @click="printAll">打印</el-button>
            <div style="display: flex ; margin: 10px 10px 10px 10px">
                <select-major @getMajorName="getMajorName" :majors="majors"></select-major>
                <select-class2 @getClassName="getClassName" :majors="majors"></select-class2>
            </div>

        </div>
        <el-table
                :data="students.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                border
                style="width: 100%">
            <el-table-column
                    prop="number"
                    label="学号"
                    width="120">
            </el-table-column>
            <el-table-column
                    fixed
                    prop="name"
                    label="姓名"
                    width="80">
            </el-table-column>
            <el-table-column  label="头像" width="110">
                <template #default="scope">
                    <el-image
                            style="width: 80px; height: 80px; margin-right: 50px"
                            :src="scope.row.cover"
                            :preview-src-list="[scope.row.cover]"></el-image>
                </template>
            </el-table-column>
            <el-table-column
                    prop="gender"
                    label="性别"
                    width="50">
            </el-table-column>
            <el-table-column
                    prop="idNumber"
                    label="身份证号"
                    width="170">
            </el-table-column>
            <el-table-column
                    prop="collegeName"
                    label="学院"
                    width="110">
            </el-table-column>
            <el-table-column
                    prop="major"
                    label="专业"
                    width="150">
            </el-table-column>
            <el-table-column
                    prop="className"
                    label="班级"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="dormitory"
                    label="寝室号"
                    width="80">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    label="联系方式"
                    width="120">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="家庭住址"
                    width="300">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    width="170">
                <template slot-scope="scope">

                    <el-button @click="handleClick(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>

                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            @confirm = "deleteUser(scope.row)"
                            :title="'删除学生'+scope.row.name+'所有数据?'">
                        <el-button slot="reference" type="danger" icon="el-icon-delete" circle></el-button>
                    </el-popconfirm>

                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[5]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="students.length">
            </el-pagination>
        </div>

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
                        <el-input v-model="student.number"  ></el-input>
                    </el-form-item>

                    <el-form-item label="学院" prop="college">
                        <el-select v-model="student.collegeName"  placeholder="请选择">
                            <el-option @click.native="getMajor(item.id,item.name)" v-for="item in colleges" :key="item.id" :label="item.name" :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="专业" prop="major">
                        <el-select v-model="student.major" placeholder="请选择">
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
                <el-button type="success" @click="showMask = false">关闭</el-button>
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
    import printJS from "print-js";
    import ModifyStuInfo from "../components/ModifyStuInfo";
    import SelectMajor from "../components/SelectMajor";
    import SelectClass2 from "../components/SelectClass2";
    export default {
        components: {ModifyStuInfo,SelectMajor,SelectClass2},
        data() {
            return {
                account:'',
                teacherName:'',
                oldClassName:'',
                classes:[],
                majors:[],
                colleges:[],
                close_modal:false,
                showMask:false,
                openDel:false,
                currentPage: 1,
                pageSize: 5,
                students:[],
                student: {
                    id:'',
                    name:'',
                    number:'',
                    major: '',
                    className:'',
                    gender: '',
                    idNumber:'',
                    dormitory:'',
                    address:'',
                    phone:'',
                    collegeName:'',
                    teacherId:'',
                    cover:''
                },
                user: {
                    id:'',
                    account: '',
                    password:'',
                    phone: '',
                    email: '',
                    roleId: ''
                },
                search:'',
                visible:false
            }
        },
        methods: {
            getMajorName(major){
                console.log(major)
                this.$axios.get("/student/findStuByMajorAndTeaId/"+major+'/'+this.teacher.id).then(res => {
                    if (res.data.code === 200){
                        this.students = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getClassName(className){
                console.log(className)
                this.$axios.get("/student/findStuByClassNameAndTeaId/"+className+'/'+this.teacher.id).then(res => {
                    if (res.data.code === 200){
                        this.students = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            modifyInfo(){
                this.visible = false;
                this.$axios.put("student/modiFyStu/"+this.account+'/'+this.oldClassName,this.student).then(res => {
                    if (res.data.code === 200){
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
            async printAll(){

                let data = []
                for (let i = 0 ; i < this.students.length; i++){
                    data.push({
                        "name":this.students[i].name,
                        "number":this.students[i].number,
                        "gender":this.students[i].gender,
                        "idNumber":this.students[i].idNumber,
                        "collegeName":this.students[i].collegeName,
                        "major":this.students[i].major,
                        "className":this.students[i].className,
                        "dormitory":this.students[i].dormitory,
                        "phone":this.students[i].phone,
                        "address":this.students[i].address
                    })
                }

                printJS({
                    printable: data,
                    properties:[
                        {
                            field: 'number',
                            displayName: '学号',
                            columnSize: 1
                        },
                        {
                            field: 'name',
                            displayName: '姓名',
                            columnSize: 1
                        },
                        {
                            field: 'gender',
                            displayName: '性别',
                            columnSize: 1
                        },
                        {
                            field: 'idNumber',
                            displayName: '身份证',
                            columnSize: 1
                        },
                        {
                            field: 'collegeName',
                            displayName: '学院',
                            columnSize: 1
                        },
                        {
                            field: 'major',
                            displayName: '专业',
                            columnSize: 1
                        },
                        {
                            field: 'className',
                            displayName: '班级',
                            columnSize: 1
                        },
                        {
                            field: 'dormitory',
                            displayName: '寝室',
                            columnSize: 1
                        },
                        {
                            field: 'phone',
                            displayName: '联系电话',
                            columnSize: 1
                        },
                        {
                            field: 'address',
                            displayName: '住址',
                            columnSize: 1
                        }
                    ],
                    type: 'json',
                    header: '学生信息详情表',
                    //样式设置
                    gridStyle: 'border: 2px solid #3971A5',
                    gridHeaderStyle:'color: red; border: 2px solid #3971A5'
                })

            },
            handleDownload() {
                require.ensure([], () => {
                    const { export_json_to_excel } = require('../vendor/Export2Excel')
                    const tHeader = ['学号', '姓名', '性别','身份证号','学院','专业','班级','寝室号','联系方式','家庭住址']
                    const filterVal = ['number', 'name', 'gender','idNumber','collegeName','major','className','dormitory','phone','address']
                    const list = this.students
                    const data = this.formatJson(filterVal, list)
                    export_json_to_excel(tHeader, data, '我的学生信息表excel')

                })
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },
            handleClick(row) {
                this.oldClassName = row.className
                this.account = row.number
                this.student = row
                this.showMask = true;
                this.getTea(this.student.className,null)
                this.getCollege()
                console.log(row);
            },
            getTea(className,classId){
                //通过班级找到老师Id
                this.$axios.get("/class/findTeaByClassName/"+className).then(res =>{
                    if(res.data.code === 200){
                        this.teacher = res.data.data
                        this.student.teacherId = this.teacher.id
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
            },
            getStu(){

                //先查询到老师在找学生
                this.user = JSON.parse(window.sessionStorage.getItem("user"))

                this.$axios.post("/teacher/findTeaByPhone/"+this.user.account).then(res => {
                    if (res.data.code === 200){
                        this.teacher = res.data.data
                        this.$axios.get("/student/findStuByTeaId/"+this.teacher.id).then(res => {
                            if (res.data.code === 200){
                                this.students = res.data.data
                                this.majors = this.students
                            }else {
                                this.$message.error(res.data.msg)
                            }
                        })
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })





            },
            load(){
                if (this.search === ''){
                    this.getStu()
                }
                this.$axios.get("/student/findStuByName/"+this.search+"/"+this.teacher.id).then(res => {
                    if (res.data.code === 200){
                        this.students = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            handleSizeChange(pageSize) {//改变当前每页的个数触发
                this.pageSize = pageSize

            },
            handleCurrentChange(pageNum) {//改变当前页码
                this.currentPage = pageNum

            },
            deleteUser(stu){
                this.$axios.delete("student/deleteStuByNum/"+stu.number).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("删除成功")
                        this.openDel = false
                        location.reload()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            }
        },


        created() {
            this.getStu()
        }
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

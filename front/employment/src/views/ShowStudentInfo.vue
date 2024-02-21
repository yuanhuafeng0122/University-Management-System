<template>
    <div>
        <div style="margin: 10px 0; " >
            <el-input  v-model="search" placeholder="请输入姓名或者学号"  style="width: 20%;" clearable></el-input>
            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
            <el-button type="primary" @click="handleDownload">导出</el-button>
            <el-button type="primary" @click="printAll">打印</el-button>
            <div style="display: flex ; margin: 10px 10px 10px 10px">
                <select-major @getMajorName="getMajorName" :majors="majors"></select-major>
                <select-class2 @getClassName="getClassName" :majors="majors"></select-class2>
            </div>
        </div>

        <el-table :data="students.slice((currentPage-1)*pageSize,currentPage*pageSize)" stripe style="width: 100%;" >
            <el-table-column prop="index" label="#" type="index" width="70" ></el-table-column>
            <el-table-column prop="number" label="学号" > </el-table-column>
            <el-table-column prop="name" label="姓名" > </el-table-column>
            <el-table-column  label="头像">
                <template #default="scope">
                    <el-image
                            style="width: 100px; height: 80px"
                            :src="scope.row.cover"
                            :preview-src-list="[scope.row.cover]"></el-image>
                </template>
            </el-table-column>
            <el-table-column prop="idNumber" label="身份证" > </el-table-column>
            <el-table-column prop="dormitory" label="宿舍号" > </el-table-column>
            <el-table-column prop="isWork" label="是否就业" >
                <template  slot-scope="scope">
                    {{scope.row.isWork == 1 ? "是":"否"}}
                </template>
            </el-table-column>
            <el-table-column prop="phone" label="联系方式" > </el-table-column>
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
    </div>
</template>

<script>
    import printJS from "print-js";
    import SelectMajor from "../components/SelectMajor";
    import SelectClass2 from "../components/SelectClass2";
    import ModifyStuInfo from "../components/ModifyStuInfo";
    export default {
        name: "",
        components: {ModifyStuInfo,SelectMajor,SelectClass2},
        data(){
            return {
                majors:[],
                search:'',
                currentPage: 1,
                pageSize: 5,
                students:[],
                teacher:{},
                student:{
                    id:'',
                    number:'',
                    name:'',
                    isWork:'',
                    idNumber:'',
                    gender: '',
                    dormitory:'',
                    address: '',
                    phone:'',
                    major:'',
                    classes:'',
                    collegeName:'',
                    teacherName:''
                }
            }
        },
        methods: {

            async printAll(){

                let data = []

                let isWorks = [

                ];


                for (let i = 0 ; i < this.students.length; i++){

                    if (this.students[i].isWork === true){
                        isWorks[i] = '是'
                    }else {
                        isWorks[i] = '否'
                    }

                    data.push({
                        "name":this.students[i].name,
                        "number":this.students[i].number,
                        "dormitory":this.students[i].dormitory,
                        "idNumber":this.students[i].idNumber,
                        "phone":this.students[i].phone,
                        "isWork":isWorks[i],
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
                            field: 'idNumber',
                            displayName: '身份证',
                            columnSize: 1
                        },
                        {
                            field: 'dormitory',
                            displayName: '寝室',
                            columnSize: 1
                        },
                        {
                            field: 'isWork',
                            displayName: '是否就业',
                            columnSize: 1
                        },
                        {
                            field: 'phone',
                            displayName: '联系电话',
                            columnSize: 1
                        },

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
                    const tHeader = ['学号', '姓名', '身份证','宿舍号','是否就业','联系方式']
                    const filterVal = ['number', 'studentName', 'idNumber','dormitory','isWork','phone']
                    const list = this.students
                    const data = this.formatJson(filterVal, list)
                    export_json_to_excel(tHeader, data, '学生是否就业表excel')

                })
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },
            getStu() {

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
            handleSizeChange(pageSize) {//改变当前每页的个数触发
                this.pageSize = pageSize

            },
            handleCurrentChange(pageNum) {//改变当前页码
                this.currentPage = pageNum

            },
            load(){
                if (this.search === ''){
                    this.$message.error("请输入姓名")
                    return
                }
                this.$axios.get("/student/findStuByName/"+this.search).then(res => {
                    if (res.data.code === 200){
                        this.students = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                        this.getStu()
                    }
                })
            },
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


        },
        created() {
            this.getStu()
        },
    }
</script>

<style scoped>

</style>

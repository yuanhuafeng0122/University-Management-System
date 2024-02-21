<template>
    <div>
        <div class="head">

            <!--详细数据-->
            <el-input  v-model="search" placeholder="请输入姓名或者学号"  style="width: 20%;" clearable></el-input>
            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
            <el-button type="primary" @click="handleDownload">导出</el-button>
            <el-button type="primary" @click="printAll">打印</el-button>

            <div style="display: flex;margin: 10px">
                <select-major @getMajorName="getMajorName" :majors="majors"></select-major>
                <select-class @getClassName="getClassName" :majors="majors"></select-class>
                <select-area @getArea="getArea" @getProvince = "getProvince" @getCity="getCity"></select-area>
            </div>


        </div>

        <el-table id="table" :data="employInfos.slice((currentPage-1)*pageSize,currentPage*pageSize)" stripe style="width: 100%;" >
            <el-table-column prop="index" label="#" type="index" width="60" ></el-table-column>
            <el-table-column prop="number" label="学号" ></el-table-column>
            <el-table-column prop="studentName" label="姓名" > </el-table-column>
            <el-table-column prop="major" label="专业" > </el-table-column>
            <el-table-column prop="classes" label="班级" > </el-table-column>
            <el-table-column prop="graduationGoto" label="毕业去向"> </el-table-column>
            <el-table-column prop="companyName" label="单位名称"> </el-table-column>
            <el-table-column prop="post" label="岗位"> </el-table-column>
            <el-table-column prop="province" label="省"> </el-table-column>
            <el-table-column prop="city" label="市"> </el-table-column>
            <el-table-column prop="area" label="县"> </el-table-column>
            <el-table-column prop="dtaAddress" label="详细地址"> </el-table-column>
        </el-table>
        <div style="margin: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[7]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="employInfos.length">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    import printJS from 'print-js'
    import SelectMajor from "../../components/SelectMajor";
    import SelectClass from "../../components/SelectClass";
    import SelectArea from "../../components/SelectArea";
    export default {
        name: "",
        components:{SelectMajor,SelectClass,SelectArea},
        data() {
            return {
                majors:[],
                search:'',
                print:{
                    id:"table",
                    popTitle:"学生就业信息"
                },
                currentPage: 1,
                pageSize: 7,
                total: 0,
                employInfos: [],
                user: {
                    id:'',
                    account:'',
                    password:'',
                    phone:'',
                    roleId:''
                },
            }
        },
        mounted() {

        },
        created() {
            this.getEmp()
        },
        methods: {
            handleDownload() {
                require.ensure([], () => {
                    const { export_json_to_excel } = require('../../vendor/Export2Excel')
                    const tHeader = ['学号', '姓名', '专业','毕业去向','单位名称']
                    const filterVal = ['number', 'studentName', 'major','graduationGoto','companyName']
                    const list = this.employInfos
                    const data = this.formatJson(filterVal, list)
                    export_json_to_excel(tHeader, data, '学生就业信息表excel')

                })
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },
            handleSizeChange(pageSize) {//改变当前每页的个数触发
                this.pageSize = pageSize
            },
            handleCurrentChange(pageNum) {//改变当前页码
                this.currentPage = pageNum
            },
            getEmp(){
                this.$axios.get("employInfo/findAllEmp").then(res => {
                    if (res.data.code === 200){
                        this.employInfos = res.data.data
                        this.majors = this.employInfos
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            async printAll(){
                let data = []
                for (let i = 0 ; i < this.employInfos.length; i++){
                    data.push({
                        "number":this.employInfos[i].number,
                        "studentName":this.employInfos[i].studentName,
                        "major":this.employInfos[i].major,
                        "graduationGoto":this.employInfos[i].graduationGoto,
                        "companyName":this.employInfos[i].companyName,
                        "dtaAddress":this.employInfos[i].dtaAddress
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
                            field: 'studentName',
                            displayName: '姓名',
                            columnSize: 1
                        },
                        {
                            field: 'major',
                            displayName: '专业',
                            columnSize: 1
                        },
                        {
                            field: 'graduationGoto',
                            displayName: '去向',
                            columnSize: 1
                        },
                        {
                            field: 'companyName',
                            displayName: '单位名称',
                            columnSize: 1
                        },
                        {
                            field: 'dtaAddress',
                            displayName: '详细地址',
                            columnSize: 1
                        }
                    ],
                    type: 'json',
                    header: '学生就业信息详情表',
                    //样式设置
                    gridStyle: 'border: 2px solid #3971A5',
                    gridHeaderStyle:'color: red; border: 2px solid #3971A5'
                })

            },
            load(){
                if (this.search === ''){
                    this.getEmp()
                }
                this.$axios.post("employInfo/findEmpByName/"+this.search).then(res => {
                    if (res.data.code === 200){
                        this.employInfos = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getMajorName(major){
                this.$axios.get("employInfo/findEmpByMajor/"+major).then(res => {
                    if (res.data.code === 200){
                        this.employInfos = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getClassName(className){
                this.$axios.get("employInfo/findEmpByClassName/"+className).then(res => {
                    if (res.data.code === 200){
                        this.employInfos = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getArea(area){
                console.log(area)
                this.$axios.get("/employInfo/findEmpByArea/"+area).then(res => {
                    if (res.data.code === 200){
                        this.employInfos = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getCity(city){
                console.log(city)
                this.$axios.get("/employInfo/findEmpByCity/"+city).then(res => {
                    if (res.data.code === 200){
                        this.employInfos = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getProvince(province){
                console.log(province)
                this.$axios.get("/employInfo/findEmpByProvince/"+province).then(res => {
                    if (res.data.code === 200){
                        this.employInfos = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .head{
        margin: 10px 0;
    }
</style>

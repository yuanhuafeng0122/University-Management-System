<template>
    <div>
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入岗位"  style="width: 20%;" clearable></el-input>
            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
            <el-button type="primary" @click="handleDownload">导出</el-button>
            <el-button type="primary" @click="printAll">打印</el-button>
        </div>
        <el-table :data="recruits.slice((currentPage-1)*pageSize,currentPage*pageSize)" border style="width: 100%">
            <el-table-column prop="post"
                    label="岗位">
            </el-table-column>
            <el-table-column
                    prop="companyName"
                    label="公司名称">
            </el-table-column>
            <el-table-column
                    prop="salary"
                    label="薪水">
            </el-table-column>
            <el-table-column
                    prop="education"
                    label="学历要求">
            </el-table-column>
            <el-table-column
                    prop="companyPhone"
                    label="联系方式">
            </el-table-column>
            <el-table-column
                    prop="dtaAddress"
                    label="地址">
            </el-table-column>
            <el-table-column
                    prop="jobRequirements"
                    label="主要要求">
            </el-table-column>
            <el-table-column
                    prop="releaseTime"
                    label="发布时间">
            </el-table-column>
            <el-table-column
                    label="操作"
                    width="170">
                <template slot-scope="scope">
                    <el-button @click="openModifyRec(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            @confirm = "deleteRec(scope.row)"
                            :title="'删除'+scope.row.post+'岗位？'">
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
                    :page-sizes="[7]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="recruits.length">
            </el-pagination>
        </div>


        <el-dialog center :title="'修改推荐的职位岗位'+recruit.post+'信息'" :visible="openModify" :showClose="false"
                   :modal="false"
                   :close-on-click-modal="close_modal">
            <el-form :model="recruit"    label-width="100px"style="width: 500px;">
                <el-form-item label="就业岗位" prop="post">
                    <el-input v-model="recruit.post"></el-input>
                </el-form-item>
                <el-form-item label="就业薪水" prop="salary">
                    <el-input v-model="recruit.salary"></el-input>
                </el-form-item>
                <el-form-item label="公司名称" prop="companyName">
                    <el-input v-model="recruit.companyName"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="companyPhone">
                    <el-input v-model="recruit.companyPhone"></el-input>
                </el-form-item>
                <el-form-item label="学历要求" prop="education">
                    <el-input v-model="recruit.education"></el-input>
                </el-form-item>
                <el-form-item label="职位要求" prop="jobRequirements">
                    <el-input v-model="recruit.jobRequirements"></el-input>
                </el-form-item>

                <el-form-item label="发布时间">
                    <el-date-picker
                            v-model="recruit.releaseTime"
                            type="date"
                            validate-event="YYYY-MM-DD"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>

                <GetProvinces @getCity="getCity" :provinceName="recruit.province"></GetProvinces>

                <GetCities @getArea="getArea" :province-id="provinceId" :cityName="recruit.city"></GetCities>

                <GetAreas @getAreaName="getAreaName" :city-id="cityId" :areaName="recruit.area"></GetAreas>
                <el-form-item label="详细地址" prop="dtaAddress">
                    <el-input v-model="recruit.dtaAddress"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="openModify = false">关闭</el-button>
                <el-button type="primary" @click="modifyInfo(recruit)">修改</el-button>
            </div>
        </el-dialog>



    </div>
</template>

<script>
    import printJS from "print-js";
    import GetProvinces from "../../components/GetProvinces";
    import GetAreas from "../../components/GetAreas";
    import GetCities from "../../components/GetCities";

    export default {
        name: "",
        components:{GetCities,GetAreas,GetProvinces},
        data() {
            return {
                provinceId:'',
                cityId:'',
                openModify:false,
                close_modal: false,
                search:'',
                currentPage: 1,
                pageSize: 7,
                size:'',
                recruits:[],
                recruit: {
                    id:'',
                    companyName:'',
                    companyPhone:'',
                    post:'',
                    salary:'',
                    education:'',
                    jobRequirements:'',
                    releaseTime:'',
                    province:'',
                    city:'',
                    area:'',
                    dtaAddress:''
                }
            }
        },
        methods: {
            getCity(provinceId,province){
                this.recruit.province = province
                this.provinceId = provinceId
            },
            getArea(cityId,city){
                this.cityId = cityId
                this.recruit.city = city
            },
            getAreaName(area){
                this.recruit.area = area
            },

            modifyInfo(recruit){
                this.$axios.put("recruit/modifyRec",recruit).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("修改成功")
                        location.reload()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },

            openModifyRec(recruit){
                this.openModify = true
                this.recruit = recruit
            },

            async printAll(){

                let data = []
                for (let i = 0 ; i < this.recruits.length; i++){
                    data.push({
                        "companyName":this.recruits[i].companyName,
                        "companyPhone":this.recruits[i].companyPhone,
                        "post":this.recruits[i].post,
                        "salary":this.recruits[i].salary,
                        "education":this.recruits[i].education,
                        "jobRequirements":this.recruits[i].jobRequirements,
                        "releaseTime":this.recruits[i].releaseTime,
                        "dtaAddress":this.recruits[i].dtaAddress,
                    })
                }

                printJS({
                    printable: data,
                    properties:[
                        {
                            field: 'post',
                            displayName: '岗位',
                            columnSize: 1
                        },
                        {
                            field: 'companyName',
                            displayName: '公司',
                            columnSize: 1
                        },
                        {
                            field: 'salary',
                            displayName: '薪水',
                            columnSize: 1
                        },
                        {
                            field: 'education',
                            displayName: '学历要求',
                            columnSize: 1
                        },
                        {
                            field: 'companyPhone',
                            displayName: '联系方式',
                            columnSize: 1
                        },
                        {
                            field: 'dtaAddress',
                            displayName: '地址',
                            columnSize: 1
                        },
                        {
                            field: 'jobRequirements',
                            displayName: '主要要求',
                            columnSize: 1
                        },
                        {
                            field: 'releaseTime',
                            displayName: '发布时间',
                            columnSize: 1
                        },

                    ],
                    type: 'json',
                    header: '所有招聘信息详情',
                    //样式设置
                    gridStyle: 'border: 2px solid #3971A5',
                    gridHeaderStyle:'color: red; border: 2px solid #3971A5'
                })

            },


            handleDownload() {
                require.ensure([], () => {
                    const { export_json_to_excel } = require('../../vendor/Export2Excel')
                    const tHeader = ['岗位', '公司名称', '薪水','学历要求','联系方式','地址','主要要求','发布时间']
                    const filterVal = ['post', 'companyName', 'salary','education','companyPhone','dtaAddress','jobRequirements','releaseTime']
                    const list = this.recruits
                    const data = this.formatJson(filterVal, list)
                    export_json_to_excel(tHeader, data, '所有招聘信息表excel')

                })
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },

            getAllRec(){
                this.$axios.get("/recruit/findAllRec").then(res => {
                    if (res.data.code === 200){
                        this.recruits = res.data.data
                    }else {
                        this.$message.error("系统异常，检查服务器是否在运行）")
                    }
                })
            },
            load(){

                if(this.search===''){
                    this.getAllRec()
                }

                this.$axios.post("/recruit/findRecByPost/"+this.search).then(res => {
                    if (res.data.code === 200){
                        this.recruits = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                        this.getAllRec()
                    }
                })
            },
            deleteRec(recruit){
                this.$axios.delete("recruit/deleteRecById/"+recruit.id).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("删除成功")
                        location.reload()
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
        },
        created() {
            this.getAllRec()
        },
    }
</script>

<style scoped>
    .el-form-item{
        margin-bottom: 5px !important;
    }
</style>

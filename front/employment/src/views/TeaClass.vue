<template>
    <div>
        <div style="margin: 10px 0">
            <el-button type="primary" @click="openAdd">增加</el-button>
        </div>

<!--            <el-input v-model="search" placeholder="请输入学生姓名"  style="width: 20%;" clearable></el-input>-->
<!--            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>-->
<!--            <el-button type="primary" @click="handleDownload">导出</el-button>-->
<!--            <el-button type="primary" @click="printAll">打印</el-button>-->


        <el-table
                :data="classes.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                border
                style="width: 100%"  stripe>
            <el-table-column
                    prop="className"
                    label="班级">
            </el-table-column>
            <el-table-column
                    prop="classMajor"
                    label="专业">
            </el-table-column>
            <el-table-column
                    prop="trueStu"
                    label="注册人数">
            </el-table-column>
            <el-table-column
                    prop="notTrueStu"
                    label="未注册人数">
            </el-table-column>
            <el-table-column
                    prop="fillStu"
                    label="已填写人数">
            </el-table-column>
            <el-table-column
                    prop="resNotFill"
                    label="已注册未填写">
            </el-table-column>
            <el-table-column
                    prop="notFillStu"
                    label="未填写人数">
            </el-table-column>
            <el-table-column
                    prop="fullStu"
                    label="班级总人数">
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作">
                <template slot-scope="scope">

                    <el-button @click="openModifyCla(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>

                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            @confirm = "deleteClass(scope.row)"
                            :title="'删除班级'+scope.row.className+'所有数据?'">
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
                    :total="classes.length">
            </el-pagination>
        </div>


        <div>
            <el-dialog title="新增班级" :visible.sync="showAdd" width="30%">
                <el-form :model="classForm" label-width="120px">
                    <el-form-item label="名称">
                        <el-input v-model="classForm.className" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="专业">
                        <el-select v-model="classForm.classMajor" placeholder="请选择专业" >
                            <el-option @click.native="setClo(item.collegeId)" v-for="item in majors" :key="item.id" :label="item.name" :value="item.name"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="班级总人数">
                        <el-input v-model="classForm.fullStu" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="注册人数">
                        <el-input v-model="classForm.trueStu" disabled style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="填写人数">
                        <el-input v-model="classForm.fillStu" disabled style="width: 80%"></el-input>
                    </el-form-item>


                </el-form>
                <span slot="footer" class="dialog-footer" style="text-align: center;display:block;">
                    <el-button @click="showAdd = false">取 消</el-button>
                    <el-button type="primary" @click="add">确定</el-button>
                </span>
            </el-dialog>
        </div>



        <el-dialog center :title="'修改'+modifyClassForm.className+'信息'" :visible="openModify" :showClose="false"
                   :modal="false"
                   :close-on-click-modal="close_modal">
            <el-form :model="modifyClassForm"    label-width="100px"style="width: 500px;">
                <el-form-item label="班级" prop="className">
                    <el-input v-model="modifyClassForm.className"></el-input>
                </el-form-item>
                <el-form-item label="专业" prop="classMajor">
                    <el-select v-model="modifyClassForm.classMajor" placeholder="请选择专业" >
                        <el-option v-for="item in majors" @click.native="setClo(item.id)" :key="item.id" :label="item.name" :value="item.name"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="班级人数" prop="fullStu">
                    <el-input v-model="modifyClassForm.fullStu"></el-input>
                </el-form-item>

                <el-form-item label="注册人数" prop="trueStu">
                    <el-input v-model="modifyClassForm.trueStu" disabled></el-input>
                </el-form-item>
                <el-form-item label="未注册人数" prop="notTrueStu">
                    <el-input v-model="modifyClassForm.notTrueStu" disabled></el-input>
                </el-form-item>
                <el-form-item label="已填写人数" prop="fillStu">
                    <el-input v-model="modifyClassForm.fillStu" disabled></el-input>
                </el-form-item>
                <el-form-item label="已注册未填写" prop="resNotFill">
                    <el-input v-model="modifyClassForm.resNotFill" disabled></el-input>
                </el-form-item>
                <el-form-item label="未填写人数" prop="notFillStu">
                    <el-input v-model="modifyClassForm.notFillStu" disabled></el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="openModify = false">关闭</el-button>
                <el-button type="primary" @click="modifyClass(modifyClassForm)">修改</el-button>
            </div>
        </el-dialog>


    </div>

</template>

<script>
    export default {
        name: "",
        data(){
            return {
                oldClass:'',
                modifyClassForm:{},
                close_modal: false,
                showAdd:false,
                openModify:false,
                classes:[],
                majors:[],
                currentPage: 1,
                pageSize: 7,
                size:'',
                teacher:{},
                classForm:{
                    id:'',
                    className:'',
                    classMajor:'',
                    collegeName:'',
                    teaId:'',
                    fullStu:'',
                    trueStu: '0',
                    fillStu: '0',
                    notTrueStu: '0',
                    notFillStu: '',
                    resNotFill:'0'
                }
            }
        },
        methods:{
            setClo(id){
                console.log(id)
                this.$axios.get("/college/findCollegeById/"+id).then(res => {
                    if (res.data.code === 200){
                        this.classForm.collegeName = res.data.data.name;
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            modifyClass(myClass){
                this.$axios.put("/class/modifyClass/"+this.oldClass,myClass).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("修改成功")
                        setTimeout(function() {
                            location.reload()
                        },1000)
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            openModifyCla(cla){
                this.oldClass = cla.className
                this.openModify = true
                this.modifyClassForm = cla
            },
            deleteClass(cla){
                this.$axios.delete("/class/deleteClassById/"+cla.id).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("删除成功")
                        setTimeout(function() {
                            location.reload()
                            },1000)
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            openAdd(){
              this.showAdd = true
            },
            add(){
                console.log(this.classForm.className)
                this.$axios.get("/class/findClassByClassName/"+this.classForm.className).then(res=>{
                    if(res.data.code === 200){
                        this.$axios.post("/class/addClass",this.classForm).then(res =>{
                            if(res.data.code === 200){
                                this.$message.success("添加成功")
                                this.classes = res.data.data
                                location.reload();
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
            getMajor(collegeName){
                this.$axios.get("/major/findMajorByCollegeName/"+collegeName).then(res =>{
                    if(res.data.code === 200){
                        this.majors = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getTea(){
                this.$axios.post("/teacher/findTeaByPhone/"+this.user.account).then(res =>{
                    if(res.data.code === 200){
                        this.teacher = res.data.data
                        this.classForm.teaId = this.teacher.id
                        this.getMajor(this.teacher.collegeName)
                        this.getClasses(this.teacher.id)
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getClasses(teaId){
                this.$axios.post("/class/findClassesByTeaId/"+teaId).then(res =>{
                    if(res.data.code === 200){
                        this.classes = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            }
        },
        mounted() {
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.getTea()
        }
    }
</script>

<style scoped>

</style>

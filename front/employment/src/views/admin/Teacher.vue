<template>
    <div>
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入老师姓名"  style="width: 20%;" clearable></el-input>
            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
            <el-button type="primary" @click="handleDownload">导出</el-button>
            <el-button type="primary" @click="printAll">打印</el-button>
        </div>

        <el-table
                :data="teachers"
                stripe
                style="width: 100%">
            <el-table-column
                    prop="collegeName"
                    label="学院">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="姓名">
            </el-table-column>
            <el-table-column
                    prop="=cover"
                    label="头像"
                    width="180">
                <template #default="scope">
                    <el-image
                            style="width: 100px; height: 80px"
                            :src="scope.row.cover"
                            :preview-src-list="[scope.row.cover]"></el-image>
                </template>
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="邮箱"
                    >
            </el-table-column>
            <el-table-column
                    prop="phone"
                    label="联系电话"
                   >
            </el-table-column>
            <el-table-column
                    prop="workRoom"
                    label="办公室"
                    >
            </el-table-column>
            <el-table-column
                    fixed="right"
                    label="操作"
                    >
                <template slot-scope="scope">
                    <el-button @click="openModifyTea(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>

                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            @confirm = "deleteUser(scope.row)"
                            :title="'删除老师'+scope.row.name+'所有数据?'">
                        <el-button slot="reference" type="danger" icon="el-icon-delete" circle></el-button>
                    </el-popconfirm>

                </template>
            </el-table-column>
        </el-table>



        <el-dialog center :title="'修改'+teacher.name+'信息'" :visible="openModify" :showClose="false"
                    :modal="false"
                    :close-on-click-modal="close_modal">
            <el-form :model="teacher" label-width="100px">

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

                <SelectCollege @fun="fun" :collegeName="teacher.collegeName"></SelectCollege>

                <el-form-item label="姓名:" prop="name">
                    <el-input  v-model="teacher.name" placeholder="辅导员姓名"></el-input>
                </el-form-item>
                <el-form-item label="手机号:" prop="phone">
                    <el-input   v-model="teacher.phone" placeholder="手机号（账号）"></el-input>
                </el-form-item>
                <el-form-item label="邮箱:" prop="email">
                    <el-input   v-model="teacher.email" placeholder="邮箱"></el-input>
                </el-form-item>
                <el-form-item label="办公室:" prop="workRoom">
                    <el-input   v-model="teacher.workRoom" placeholder="办公室"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="openModify = false">关闭</el-button>
                <el-button type="primary" @click="modifyInfo(teacher)">修改</el-button>
            </div>
        </el-dialog>





        <div style="margin: 10px 0">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[5]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="teachers.length">
            </el-pagination>
        </div>
    </div>
</template>

<script>

    import printJS from "print-js";
    import SelectCollege from "../../components/SelectCollege";

    export default {
        components:{SelectCollege},
        data() {
            return {
                close_modal:false,
                openModify: false,
                currentPage: 1,
                pageSize: 5,
                teachers:[],
                teacher: {
                    id:'',
                    name:'',
                    phone:'',
                    collegeName:'',
                    workRoom:'',
                    email:'',
                },
                search:'',
            }
        },
        methods: {

            fun(collegeName){
                this.teacher.collegeName = collegeName
            },

            modifyInfo(teacher){
                this.$axios.put("teacher/modifyTea",teacher).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("修改成功")
                        location.reload()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })

            },
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
            openModifyTea(teacher){
                this.openModify = true
                this.teacher = teacher
            },

            async printAll(){
                let data = []
                for (let i = 0 ; i < this.teachers.length; i++){
                    data.push({
                        "name":this.teachers[i].name,
                        "phone":this.teachers[i].phone,
                        "collegeName":this.teachers[i].collegeName,
                        "workRoom":this.teachers[i].workRoom,
                        "email":this.teachers[i].email,
                    })
                }
                printJS({
                    printable: data,
                    properties:[
                        {
                            field: 'collegeName',
                            displayName: '学院',
                            columnSize: 1
                        },
                        {
                            field: 'name',
                            displayName: '姓名',
                            columnSize: 1
                        },
                        {
                            field: 'email',
                            displayName: '邮箱',
                            columnSize: 1
                        },
                        {
                            field: 'phone',
                            displayName: '联系电话',
                            columnSize: 1
                        },
                        {
                            field: 'workRoom',
                            displayName: '办公室',
                            columnSize: 1
                        },

                    ],
                    type: 'json',
                    header: '所有辅导员信息详情',
                    //样式设置
                    gridStyle: 'border: 2px solid #3971A5',
                    gridHeaderStyle:'color: red; border: 2px solid #3971A5'
                })

            },


            handleDownload() {
                require.ensure([], () => {
                    const { export_json_to_excel } = require('../../vendor/Export2Excel')
                    const tHeader = ['学院', '姓名', '邮箱','联系电话','办公室']
                    const filterVal = ['collegeName', 'name', 'email','phone','workRoom']
                    const list = this.teachers
                    const data = this.formatJson(filterVal, list)
                    export_json_to_excel(tHeader, data, '所有辅导员信息表excel')

                })
            },
            formatJson(filterVal, jsonData) {
                return jsonData.map(v => filterVal.map(j => v[j]))
            },


            handleClick(row) {
                console.log(row);
            },
            getTea(){
                this.$axios.get("teacher/findAllTea").then(res => {
                    if (res.data.code === 200){
                        this.teachers = res.data.data
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            load(){
                this.$axios.get(""+this.search).then(res => {
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
            deleteUser(tea){
                this.$axios.delete("teacher/delTeaByAccount/"+tea.phone).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("删除成功")
                        this.openDel = false
                        location.reload()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })

                this.openDel = false
            }
        },


        created() {
            this.getTea()
        }
    }

</script>

<style scoped>
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

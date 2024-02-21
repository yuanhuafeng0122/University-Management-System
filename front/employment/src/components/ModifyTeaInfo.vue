<template>
    <div>
        <el-dialog :modal="false" center title="个人信息" :visible="showMask" :showClose="false" :close-on-click-modal="close_modal">

            <div>

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
                    <el-form-item label="姓名">
                        <el-input v-model="teacher.name"></el-input>
                    </el-form-item>

                    <el-form-item label="学院" prop="college">
                        <el-select v-model="teacher.collegeName"  placeholder="请选择">
                            <el-option @click.native="getCollegeName(item.name)" v-for="item in colleges" :key="item.id" :label="item.name" :value="item.name">
                            </el-option>
                        </el-select>
                    </el-form-item>

                    <el-form-item label="办公室" prop="idNumber">
                        <el-input   v-model="teacher.workRoom" placeholder="办公室"></el-input>
                    </el-form-item>

                    <el-form-item label="联系电话" prop="phone">
                        <el-input  v-model="teacher.phone" placeholder="联系电话"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" prop="dormitory">
                        <el-input   v-model="teacher.email" placeholder="邮箱"></el-input>
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
    export default {
        name: "",
        props: {
            value:{} ,// 注意此处获取的value对应的就是组件标签中的v-model
        },
        data(){
            return {
                phone:'',
                visible: false,
                colleges:[],
                showMask: false,
                close_modal:false,
                user: {
                    id:'',
                    account: '',
                    password:'',
                    phone: '',
                    email: '',
                    roleId: ''
                },
                teacher:{
                    id:'',
                    name:'',
                    phone:'',
                    workRoom:'',
                    email:'',
                    collegeName:'',
                    cover:''
                }
            }
        },
        methods:{
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
            getCollegeName(collegeName){
                this.teacher.collegeName = collegeName
            },
            closeMask(){
                this.showMask = false;
            },
            modifyInfo(){
                this.visible = false;
                //修改个人信息
                this.$axios.put("/teacher/modifyTeaByPhone/"+this.phone,this.teacher).then(res => {
                    if (res.data.code === 200){
                        this.teacher = res.data.data
                        console.log(res.data)
                        this.$message.success("更新成功")
                        this.showMask = false;
                        sessionStorage.removeItem("user")
                        let user = JSON.stringify(res.data.data2)
                        sessionStorage.setItem("user",user)
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })


            },

            getTea(){
                this.user = JSON.parse(window.sessionStorage.getItem("user"))
                this.$axios.post("teacher/findTeaByPhone/"+this.user.account).then(res => {
                    if (res.data.code === 200){
                        this.teacher = res.data.data
                        this.phone =this.teacher.phone
                    }else {
                        this.$message.error("系统异常 ")
                    }
                })
            },

        },
        created() {
            this.getTea()
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

<template>
    <div>
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="请输入通知"  style="width: 20%;" clearable></el-input>
            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
            <el-button type="primary" @click="add">新增</el-button>

        </div>

        <el-table
                :data="newss.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                border
                style="width: 100%"  stripe>
            <el-table-column
                    prop="title"
                    label="标题">
            </el-table-column>
            <el-table-column
                    prop="cover"
                    label="图片">
                <template #default="scope">
                    <el-image v-if="scope.row.cover!==''"
                            style="width: 100px; height: 80px"
                            :src="scope.row.cover"
                            :preview-src-list="[scope.row.cover]"></el-image>
                    <div v-else >未添加图片</div>
                </template>
            </el-table-column>

            <el-table-column
                    prop="contentH"
                    label="内容">
                <template  #default="scope">
                    <textarea
                           :rows="3"
                            v-model="scope.row.contentH" style="border: none;resize: none;" disabled>
                    </textarea>
                </template>

            </el-table-column>
            <el-table-column
                    prop="time"
                    label="时间">
            </el-table-column>
            <el-table-column

                    label="操作"
                    width="240">
                <template #default="scope">

                    <el-button @click="details(scope.row)" type="primary" icon="el-icon-edit" circle></el-button>
                    <el-button v-if="scope.row.teaId != 11"  @click="handleEdit(scope.row)"  type="warning" icon="el-icon-edit" circle>编辑</el-button>

                    <el-popconfirm
                            confirm-button-text='确定'
                            cancel-button-text='取消'
                            icon="el-icon-info"
                            icon-color="red"
                            @confirm = "deleteNews(scope.row)"
                            :title="'删除'+scope.row.title+'通知？'">
                        <el-button v-if="scope.row.teaId != 11" slot="reference" type="danger" icon="el-icon-delete" circle style="margin-left: 10px"></el-button>
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
                    :total="newss.length">
            </el-pagination>
        </div>




        <el-dialog title="提示" :visible.sync="dialogVisible" width="50%" @close="close">
            <el-form :model="news" label-width="120px">
                <el-form-item label="标题">
                    <el-input v-model="news.title" style="width: 80%"></el-input>
                </el-form-item>

                <el-form-item label="图片">
                    <el-upload
                            class="avatar-uploader"
                            action="http://192.168.43.190:8000/files/upload"
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="filesUploadSuccess"
                            ref="upload">
                        <img v-if="news.cover" :src="news.cover" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                    </el-upload>
                </el-form-item>

                <div id="div1">
                </div>
            </el-form>
            <span slot="footer" class="dialog-footer" style="text-align: center;display:block;">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
            </span>
        </el-dialog>

        <el-dialog title="通知详情" style="min-height: 100px; margin: 5px"  :visible.sync="vis" width="50%" @close="close">
            <el-card style="display: flex">
                <div style="font-size: 24px; font-weight: bold">标题：</div>
                <div>{{detail.title}}</div>
            </el-card>

            <el-card v-if="detail.cover" style="margin-top: 10px">
                <img  :src="detail.cover" class="avatar" style="width: 300px;height: 200px">
            </el-card>

            <el-card style="margin-top: 10px">
                <div style="font-size: 24px; font-weight: bold">内容：</div>

                <div v-html="detail.content"> </div>
            </el-card>
        </el-dialog>


    </div>
</template>

<script>
    import E from "wangeditor";
    let editor
    export default {
        name: "",
        data(){
            return {
                search:'',
                vis: false,
                dialogVisible:false,
                news:{
                    id:'',
                    title:'',
                    content:'',
                    contentH:'',
                    teaId:'',
                    time:'',
                    cover:''
                },
                currentPage: 1,
                pageSize: 5,
                size:'',
                newss:[],
                user:{},
                teacher:{},
                teaId:'',
                detail:{}
            }

        },
        methods:{
            close(){
                editor.txt.clear()//清空
                this.news.cover = ''
                this.news.title = ''
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
            filesUploadSuccess(res) {
                this.news.cover = res.data
                console.log(this.news.cover)
            },
            load(){
                if (this.search === ''){
                    console.log(111)
                    this.getNews()
                }else {
                    this.$axios.get("/news/findNews/"+this.search).then(res => {
                        if (res.data.code === 200){
                            this.newss = res.data.data
                        }else {
                            this.$message.error(res.data.msg)
                        }
                    })
                }

            },
            handleSizeChange(pageSize) {//改变当前每页的个数触发
                this.pageSize = pageSize

            },
            handleCurrentChange(pageNum) {//改变当前页码
                this.currentPage = pageNum

            },
            details(row) {
                this.detail = row
                this.vis = true
            },
            deleteNews(row){
                this.$axios.delete("/news/deleteNewsById/"+row.id).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("删除成功")
                        setTimeout(function (){
                            location.reload()
                        },1000)
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            creatDom(){
                //关联弹窗里面的div，new一个 editor对象
                editor = new E('#div1')
                // 配置 server 接口地址
                editor.config.uploadImgServer = 'http://192.168.43.190:8000/files/editor/upload'
                editor.config.uploadFileName = "file"
                editor.create()
            },
            add(){
                this.dialogVisible = true

                this.$nextTick(() => {
                    if (editor==null){
                        this.creatDom()
                    }else {
                        editor.destroy()
                        this.creatDom()
                    }

                })
            },
            save() {
                this.news.content = editor.txt.html() //获取编辑器的值给news表单
                this.news.contentH = editor.txt.text()
                if (this.news.id){
                    console.log(1111)
                    this.$axios.put("/news/modifyNews",this.news).then(res => {
                        if(res.data.code === 200){
                            this.$message.success("更新成功")
                            this.dialogVisible = false
                            setTimeout(function (){
                                location.reload()
                            },1000)
                        }else {
                            this.$message.error(res.data.msg)
                        }


                    })
                }else {
                    this.$axios.post("/news/addNews",this.news).then(res => {
                        if(res.data.code === 200){
                            this.$message.success("新增成功")
                            this.dialogVisible = false
                            setTimeout(function (){
                                location.reload()
                            },1000)

                        }else {
                            this.$message.error(res.data.msg)
                        }

                    })
                }



            },
            getTea(){
                this.$axios.post("/teacher/findTeaByPhone/"+this.user.account).then(res =>{
                    if(res.data.code === 200){
                        this.teacher = res.data.data
                        this.news.teaId = this.teacher.id
                        this.teaId = this.teacher.id
                        console.log(this.teaId)
                        this.getNews()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getNews(){
                this.$axios.get("/news/findNesAllByTeaId/"+this.teaId).then(res =>{
                    if(res.data.code === 200){
                        this.newss = res.data.data
                        console.log(this.newss)
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            handleEdit(row) {
                this.news = JSON.parse(JSON.stringify(row))
                this.dialogVisible = true
                this.$nextTick(() => {
                    if (editor == null){
                        this.creatDom()
                        editor.txt.html(this.news.content)
                    }else {
                        editor.destroy();
                        this.creatDom();
                        editor.txt.html(this.news.content)
                    }
                })
            },
        },
        mounted() {
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.getTea()
        },

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
        margin-left: 200px;
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

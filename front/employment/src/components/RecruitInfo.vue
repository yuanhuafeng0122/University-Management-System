<template>
    <div>
        <div class="recruit_info_serch">
            <el-input v-model="search" placeholder="岗位"  style="width: 20%;" clearable></el-input>
            <el-button type="primary" @click="load" style="margin-left: 5px">查询</el-button>
        </div>

    <div class="recruit">
            <div v-for="(o,index) in recruits.slice((currentPage-1)*pageSize,currentPage*pageSize)" :key="o.index" :span="5" :offset="index > 0 ? 1 : 0" class="card-panel-col">
                <el-card :body-style="{padding:'10px 20px'}">

                    <div class="div1">
                        <span class="post">{{o.post}}</span>
                        <span class="salary">{{o.salary}}</span>
                    </div>
                    <div class="div2">
                        <span>{{o.city}}</span>
                        <span>{{o.education}}</span>
                        <span>{{o.releaseTime}}</span>
                    </div>
                    <div class="div3">
                        <span>{{o.companyName}}</span>
                        <el-popover  placement="right"
                                     width="600"
                                     trigger="click">
                            <el-descriptions class="margin-top" title="就业详细信息" :column="2" :size="size" border>
                                <el-descriptions-item>
                                    <template slot="label">
                                        <i class="el-icon-tickets"></i>
                                        公司名称
                                    </template>
                                    {{o.companyName}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                    <template slot="label">
                                        <i class="el-icon-user"></i>
                                        岗位
                                    </template>
                                    {{o.post}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                    <template slot="label">
                                        <i class="el-icon-mobile-phone"></i>
                                        手机号
                                    </template>
                                    {{o.companyPhone}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                    <template slot="label">
                                        <i class="el-icon-location-outline"></i>
                                        就业地点
                                    </template>
                                    {{o.city}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                    <template slot="label">
                                        <i class="el-icon-office-building"></i>
                                        要求
                                    </template>
                                    {{o.jobRequirements}}
                                </el-descriptions-item>
                                <el-descriptions-item>
                                    <template slot="label">
                                        <i class="el-icon-office-building"></i>
                                        联系地址
                                    </template>
                                    {{o.dtaAddress}}
                                </el-descriptions-item>
                            </el-descriptions>
                            <el-button type="text"  slot="reference" round onclick="openDetail">详细信息</el-button>
                        </el-popover>

                    </div>
                </el-card>
            </div>
    </div>
        <div class="recruit_fenye">
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="currentPage"
                    :page-sizes="[9]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="recruits.length">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        name: "",
        data() {
            return {
                search:'',
                currentPage: 1,
                pageSize: 9,
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
              this.$axios.post("/recruit/findRecByPost/"+this.search).then(res => {
                  if (res.data.code === 200){
                      this.recruits = res.data.data
                  }else {
                      this.$message.error(res.data.msg)
                      this.getAllRec()
                  }
              })
            },
            openDetail(){

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


<style lang="scss" scoped>

.recruit_info_serch{
  width: 1000px;
  margin: 5px auto;
  padding-left: 40%;
}
.recruit_fenye{
  width: 100px;
  margin-top: 80px;
  margin-left: 35%;
}
    .companyName{
      display: flex;
      align-content: center;
      margin-bottom: 9px;
    }


    .salary{
      color: red;
      font-size: 14px;
      text-align: justify;
      line-height: 24px;
      overflow: hidden;
      text-overflow: ellipsis;
      display: -webkit-box;
    }

    .post{
      font-size: 18px;
    }

    .recruit{
      width: 1000px;
      height: 500px;
      //background: #ff4056;
      margin: 10px auto;
      display: grid;
      grid-template-columns: repeat(3,1fr);
      grid-template-rows: repeat(3,1fr);
      grid-row-gap:20px;
      grid-column-gap: 30px ;
    }

    .div1,.div2,.div3{
      width: 100%;
      height: 50px;
      display: flex;
      justify-content: space-between;
    }




</style>

<template>
    <div>
       <div class="add_Rec">
           <el-form :model="recruit" :rules="rules" ref="recruit"   label-width="100px" class="demo-ruleForm" style="width: 500px;">
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
<!--                   <el-input v-model="recruit.education"></el-input>-->

                   <el-select v-model="recruit.education" placeholder="请选择">
                       <el-option @click.native="setEducation(item.label)"
                               v-for="item in options"
                               :key="item.value"
                               :label="item.label"
                               :value="item.value">
                       </el-option>
                   </el-select>

               </el-form-item>
               <el-form-item label="职位要求" prop="jobRequirements">
                   <el-input v-model="recruit.jobRequirements" type="textarea"
                             autosize></el-input>
               </el-form-item>
               <el-form-item label="发布时间">
                   <el-input v-model="recruit.releaseTime"  value-format="yyyy-MM-dd"      disabled>{{recruit.releaseTime}}</el-input>
               </el-form-item>
               <GetProvinces @getCity="getCity" :provinceName="recruit.province"></GetProvinces>

               <GetCities @getArea="getArea" :province-id="provinceId" :cityName="recruit.city"></GetCities>

               <GetAreas @getAreaName="getAreaName" :city-id="cityId" :areaName="recruit.area"></GetAreas>
               <el-form-item label="详细地址" prop="dtaAddress">
                   <el-input v-model="recruit.dtaAddress"></el-input>
               </el-form-item>
               <el-form-item>
                   <el-button type="primary" @click="saveRecruit('recruit')">发布岗位 </el-button>
                   <el-button @click="resetForm('recruit')">重置</el-button>
               </el-form-item>
           </el-form>
       </div>

    </div>
</template>

<script>
    import GetProvinces from "../components/GetProvinces";
    import GetCities from "../components/GetCities";
    import GetAreas from "../components/GetAreas";
    export default {
        inject:['reload'],
        components:{
          GetProvinces,GetCities,GetAreas
        },
        data() {
            return {
                options: [{
                    value: '专科',
                    label: '专科'
                }, {
                    value: '本科',
                    label: '本科'
                }, {
                    value: '研究生',
                    label: '研究生'
                }, {
                    value: '硕士',
                    label: '硕士'
                }, {
                    value: '博士',
                    label: '博士'
                }],
                provinces: [],
                provinceId: '',
                cityId:'',
                teacher:{},
                recruit: {
                    companyName: '',
                    companyPhone: '',
                    post: '',
                    salary: '',
                    education: '',
                    jobRequirements: '',
                    releaseTime: this.getDate(),
                    province:'',
                    city:'',
                    area:'',
                    dtaAddress:'',
                    pubId:''
                },
                value: '',
                rules: {
                    post:[
                        { require: true, message: '请输入岗位信息',trigger:'blur'},
                    ],
                    salary:[
                        { require: true, message: '请输入就业薪水',trigger:'blur'}
                    ],
                    companyName:[
                        { require: true, message: '请输入公司名称',trigger:'blur'}
                    ],
                    companyPhone:[
                        { require: true, message: '请输入公司联系方式',trigger:'blur'}
                    ],
                    education:[
                        { require: true, message: '请输入学历要求',trigger:'blur'}
                    ],
                    jobRequirements:[
                        { require: true, message: '请输入职位要求',trigger:'blur'}
                    ],

                }
            }
        },
        methods: {
            setEducation(education){
              this.recruit.education =   education
            },
            saveRecruit(recruit) {
                this.$refs[recruit].validate((valid) => {
                    if (valid) {
                        console.log(this.recruit)
                        this.$axios.post("/recruit/addRecruit", this.recruit).then(res => {
                            if (res.data.code === 200) {
                                this.$message.success("添加岗位信息成功")
                                location.reload();
                            } else {
                                this.$message.error("系统错误")
                            }

                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            resetForm(recruit) {
                this.$refs[recruit].resetFields();
            },
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
            getDate() {
                //获取日期
                const date = new Date();
                let year = date.getFullYear();
                let month = date.getMonth() + 1;
                let day = date.getDate();
                month = month < 10 ? "0" + month : month; //月小于10，加0
                day = day < 10 ? "0" + day : day; //day小于10，加0
                return `${year}-${month}-${day}`;
            },
            getTea(){
                this.$axios.post("/teacher/findTeaByPhone/"+this.user.account).then(res =>{
                    if(res.data.code === 200){
                        this.teacher = res.data.data
                        this.recruit.pubId = this.teacher.id
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
    .el-form-item{
        margin-bottom: 5px !important;
    }
</style>

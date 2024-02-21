<template>
    <div class="modify_information">
        <el-dialog title="修改就业信息" :visible="showMask" width="30%" :close-on-click-modal="close_modal"
                   :showClose="false">
            <el-form :model="employInfo" label-width="120px">
                <el-form-item label="学号">
                    <el-input v-model="employInfo.number" disabled style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="姓名">
                    <el-input v-model="employInfo.studentName" disabled style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="专业">
                    <el-input v-model="employInfo.major" disabled style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="辅导员姓名">
                    <el-input v-model="employInfo.teacherName" disabled style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="班级">
                    <el-input v-model="employInfo.classes" disabled style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="毕业去向">
                    <el-input v-model="employInfo.graduationGoto" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="单位名称">
                    <el-input v-model="employInfo.companyName" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="岗位">
                    <el-input v-model="employInfo.post" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="联系人">
                    <el-input v-model="employInfo.empPerson" style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="联系人电话">
                    <el-input v-model="employInfo.empPhone" style="width: 80%"></el-input>
                </el-form-item>
                <GetProvinces @getCity="getCity" :provinceName="employInfo.province"></GetProvinces>

                <GetCities @getArea="getArea" :province-id="provinceId" :cityName="employInfo.city"></GetCities>

                <GetAreas @getAreaName="getAreaName" :city-id="cityId" :areaName="employInfo.area"></GetAreas>

                <el-form-item label="详细地址" prop="name">
                    <el-input v-model="employInfo.dtaAddress" style="width: 80%"></el-input>
                </el-form-item>

                <el-form-item label="填写时间">
                    <el-input v-model="employInfo.fillInTime"  disabled style="width: 80%"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer" style="text-align: center;display:block;">
                    <el-button @click="closeMask">取 消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                </span>
        </el-dialog>
    </div>
</template>

<script>
    import GetCities from "./GetCities";
    import GetProvinces from "./GetProvinces";
    import GetAreas from "./GetAreas";
    export default {
        name: "",
        components:{
            GetProvinces,GetCities,GetAreas
        },
        props: {
            value:{} ,// 注意此处获取的value对应的就是组件标签中的v-model
            number:'', //
        },

        data (){
            return {
                provinceId:'',
                cityId:'',
                employInfo: {
                    id:'',
                    number:'',
                    studentName:'',
                    teacherName:'',
                    major:'',
                    classes:'',
                    graduationGoto:'',
                    companyName:'',
                    post:'',
                    province:'',
                    city:'',
                    area:'',
                    fillInTime: this.getDate(),
                    dtaAddress:''
                },
                showMask: false,
                close_modal:false,
                user: {
                    id:'',
                    account:'',
                    password:'',
                    phone:'',
                    roleId:''
                },
            }
        },
        methods: {
            save(){
                this.showMask = false;
                let dtaAddress = this.employInfo.dtaAddress
                this.employInfo.dtaAddress = ''
                let address = this.employInfo.province+this.employInfo.city+this.employInfo.area+dtaAddress
                console.log(address)
                this.employInfo.dtaAddress = address

                //更新信息
                this.$axios.put("/employInfo/updateEmp",this.employInfo).then(res => {
                    if (res.data.code === 200){
                        this.$message.success("更新成功")
                    }
                })

            },
            closeMask(){
                this.showMask = false;
            },
            getCity(provinceId,province){
                this.employInfo.province = province
                this.provinceId = provinceId
            },
            getArea(cityId,city){
                this.cityId = cityId
                this.employInfo.city = city
            },
            getAreaName(area){
                this.employInfo.area = area
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
            getEmp(){
                this.$axios.get("/employInfo/findEmpByNum/"+this.number).then(res => {
                    if (res.data.code === 200){
                        this.employInfo = res.data.data
                    }
                })
            },
        },
        mounted(){
            this.showMask = this.value;    // 在生命周期中，把获取的value值获取给showMash
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.employInfo.number = this.user.account

        },
        watch:{
            value(newVal, oldVal){
                this.showMask = newVal;     // 监测value的变化，并赋值。
            },
            showMask(val) {
                this.$emit('input', val);    // 此处监测showMask目的为关闭弹窗时，重新更换value值，注意emit的事件一定要为input。
                this.getEmp()
            }
        },

    }
</script>

<style>
    .modify_information .el-dialog__header{
        /*height: 50px !important;*/
        padding:0px 150px 5px !important;
    }
    .modify_information .el-dialog__body{
        padding: 0 22px !important;
    }
    .modify_information .el-form-item{
        margin-bottom: 2px;
    }

</style>
<style scoped>

</style>

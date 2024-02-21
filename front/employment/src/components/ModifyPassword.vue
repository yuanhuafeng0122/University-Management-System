<template>
    <div>
        <el-dialog center title="修改密码" :visible="showMask" :showClose="false"
                   :modal="false"
                   :close-on-click-modal="close_modal">

            <el-form :model="passForm" label-width="120px">
                <el-form-item label="原始密码">
                    <el-input v-model="passForm.oldPassword"  style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="新密码">
                    <el-input v-model="passForm.newPassword"  style="width: 80%"></el-input>
                </el-form-item>
                <el-form-item label="再输入一次">
                    <el-input v-model="ValidationPassword" style="width: 80%"></el-input>
                </el-form-item>
            </el-form>

            <div slot="footer" class="dialog-footer">
                <el-button @click="closeMask">关闭</el-button>
                <el-button type="primary" @click="modifyInfo">修改</el-button>
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

                user: {
                    id:'',
                    account: '',
                    password:'',
                    phone: '',
                    email: '',
                    roleId: ''
                },

                passForm: {
                    userId: '',
                    oldPassword:'', //现在的密码
                    newPassword:'',  //修改的密码
                },

                ValidationPassword:'',//需要验证的密码

                showMask: false,
                close_modal:false,
            }
        },
        methods:{
            closeMask(){
                this.showMask = false;
            },
            modifyInfo(){

                if (this.passForm.newPassword !== this.ValidationPassword){
                    this.$message({
                        type: "error",
                        message: "两次新密码输入不一致"
                    })
                    return;
                }
                this.$axios.post("/user/modifyPassword",this.passForm).then(res => {
                    if (res.data.code === 200){
                        sessionStorage.removeItem('user');
                        this.user = res.data.data
                        sessionStorage.setItem("user", JSON.stringify(res.data.data))
                        this.showMask = false;
                        this.$router.push({name:'login'})
                        this.$message.success("修改成功")
                    }else if (res.data.code === 10007){
                        this.$message.error(res.data.msg)
                    }
                })


            }
        },
        created() {
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.passForm.userId = this.user.id
        },
        mounted(){
            this.showMask = this.value;    // 在生命周期中，把获取的value值获取给showMash
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

</style>

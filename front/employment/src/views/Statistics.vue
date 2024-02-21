<template>




</template>

<script>
    //通过this.$echarts来使用
    export default {

        data(){
            return{
                user:{},
                teacher:{},
                classes:[],
            }
        },
        mounted(){
            // 在通过mounted调用即可
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.getTea()

        },
        methods: {

            getTea(){
                this.$axios.post("/teacher/findTeaByPhone/"+this.user.account).then(res =>{
                    if(res.data.code === 200){
                        this.teacher = res.data.data
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
            },
        },

    }
</script>

<style></style>

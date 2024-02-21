<template>
    <div>
      <div>
          <div>
              <el-avatar :src="imgUrl"></el-avatar>
          </div>
        <div>
          <span>&emsp;&emsp;&emsp;&emsp;<i class="el-icon-location-outline"></i>湖南&长沙</span>
        </div>
        <div>
            <el-row>
                <el-col>
                    <span>学号:&emsp;&emsp;&emsp;{{student.number}}</span>
                </el-col>
                <el-col>
                    <span>姓名:&emsp;&emsp;&emsp;{{student.name}}</span>
                </el-col>
                <el-col >
                    <span>学院:&emsp;&emsp;&emsp;{{student.collegeName}}</span>
                </el-col>
                <el-col >
                    <span>专业:&emsp;&emsp;&emsp;{{student.major}}</span>
                </el-col>
                <el-col>
                    <span>班级:&emsp;&emsp;&emsp;{{student.classes}}</span>
                </el-col>
                <el-col>
                    <span>联系电话:&emsp;{{student.phone}}</span>
                </el-col>
                <el-col>
                    <el-button type="primary" round size="mini" @click="modifyInfo">个人信息</el-button>
                    <el-button type="primary" round size="mini" @click="modifyPassword">修改密码</el-button>
                </el-col>
            </el-row>

        </div>

      </div>

        <ModifyStuInfo v-model="openModifyInfo"></ModifyStuInfo>
        <ModifyPassword v-model="openModifyPassword"></ModifyPassword>
    </div>




</template>

<script>
    import ModifyStuInfo from "./ModifyStuInfo";
    import ModifyPassword from "./ModifyPassword";
  export default {
    name: 'CardMe',
      components:{ ModifyStuInfo,ModifyPassword },
    data() {
      return {
          openModifyInfo:false,
          openModifyPassword:false,
          imgUrl: require("../assets/picture/default_avatar.jpg"),
          student:{
              id:'',
              number:'',
              name:'',
              idNumber:'',
              gender: '',
              dormitory:'',
              address: '',
              phone:'',
              major:'',
              classes:'',
              collegeName:'',
          }

      }
    },
      methods: {
          modifyInfo(){
              this.openModifyInfo = true;
          },
          modifyPassword(){
              this.openModifyPassword = true;
          }

    },
      created() {
          this.user = JSON.parse(window.sessionStorage.getItem("user"))
          this.$axios.get("/student/getStudentInfo/"+this.user.account).then(res => {
                if (res.data.code === 200){
                    this.student = res.data.data
                }else {
                    this.$message.error("系统异常 ")
                }
          })
      }
  }
</script>

<style scoped>

</style>

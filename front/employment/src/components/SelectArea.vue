<template>
    <div class="block">
        <el-cascader placeholder="请选择地区"
                v-model="value"
                :options="list"
                :props="{ checkStrictly: true }"
                @change="handleChange"
                ></el-cascader>
    </div>
</template>

<script>
    export default {
        name: "",
        data(){
            return {
                value: [],
                provinces:[],
                citys:[],
                areas:[],
                list:[],
                linshi:[]
            }
        },
        methods:{
            handleChange(e) {
                console.log(e)
                // console.log(e.length)
                if (e.length === 1 ){
                    this.$emit("getProvince",this.list[e[0]-1].label)
                }else if (e.length === 2){
                    this.$emit("getCity",this.list[e[0]-1].children[e[1]].label)
                } else if (e.length === 3){
                    this.$emit("getArea",this.list[e[0]-1].children[e[1]].children[e[2]].label)
                }
                // console.log(this.list[e[0]+1].value);
                // console.log(this.list[e[0]-1].label);
                // console.log(this.list[e[0]-1].children[e[1]].label);
                // console.log(this.list[e[0]-1].children[e[1]].children[e[2]].label);

               // this.$emit("getArea",this.list[e[0]-1].children[e[1]].children[e[2]].label)

            },

            getProvince(){
                this.$axios.get("/provinces/findAllPro").then(res =>{
                    if(res.data.code === 200){
                        this.provinces = res.data.data
                        this.getCity()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getCity(){
                this.$axios.get("/cities/findAllCity1").then(res =>{
                    if(res.data.code === 200){
                        this.citys = res.data.data
                        this.getArea()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getArea(){
                this.$axios.get("/areas/findAllArea").then(res =>{
                    if(res.data.code === 200){
                        this.areas = res.data.data
                        this.getPCA()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
            getPCA(){
                this.provinces.forEach((item,index)=>{
                    this.list.push({value:index+1,id:item.provinceId,label:item.province})
                })

                this.list.forEach((it,i)=>{
                    let x=0
                    this.citys.forEach((item,index)=>{
                        if(it.id == item.provinceId){
                            this.linshi.push({value:x++,label:item.city,id:item.cityId})
                        }
                    })
                    it.children=this.linshi
                    this.linshi=[]
                })

                let y=0;
                this.list.forEach((item,index)=>{
                    item.children.forEach((it,i)=>{
                        this.areas.forEach((ins,j)=>{
                            if(it.id == ins.cityId){
                                this.linshi.push({value:y++,label:ins.area})
                            }
                        })
                        it.children=this.linshi
                        y=0;
                        this.linshi=[]
                    })

                })


            }
        },
        mounted() {
            this.getProvince()
        }
    }
</script>

<style scoped>

</style>

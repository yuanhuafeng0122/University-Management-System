<template>

<!--    <div v-for="(item,index) in classes" :key="index">-->
        <div   style="width: auto;height: 400px; align-content: center;margin-top: 60px;margin-left: 30px" id="main" class="main">
        </div>
<!--    </div>-->



</template>

<script>
    //通过this.$echarts来使用
    export default {
        name: "page",
        data(){
          return{
              user:{},
              teacher:{},
              classes:[],
              serviceData:[],
              colors:[],
              //就业率
              Xdata1:[],
              //班级名字
              Sdata2:[],
              //总人数
               Xdata3:[],
              //填写人数
              Sdata4:[],
              option:[]
          }
        },
        mounted(){
            // 在通过mounted调用即可
            this.user = JSON.parse(window.sessionStorage.getItem("user"))
            this.getTea()

        },
        methods: {
            getColor(){
                //构成颜色的字符元素
                let cArray=["0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"];
                for(let i=0;i<this.classes.length;i++){
                    let color = "#";
                    for(let j=0;j<6;j++){
                        let  cIndex = Math.round(Math.random()*15);
                        color+=cArray[cIndex];
                    }
                    this.colors[i] = color;
                }
                this.echartsInit()
            },
            //初始化echarts
            echartsInit() {
                let chartDom = document.getElementById('main');
                let myChart = this.$echarts.init(chartDom);
                let col = this.colors
                this.option = {
                    title: {
                        text: '所有班级就业率图'
                    },
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {
                            type: 'shadow'
                        }
                    },
                    legend: {},
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    xAxis: {
                        show:false
                    },
                    yAxis: [
                        {
                            type: 'category',
                            inverse:true,
                            data: [],
                            axisLine:{
                                show:false
                            },
                            axisTick:{
                                show:false
                            },
                            axisLabel:{
                                color:""
                            }
                        },
                        {
                            type: 'category',
                            data: [],
                            axisLine:{
                                show:false
                            },
                            axisTick:{
                                show:false
                            },
                            axisLabel:{
                                color:""
                            }
                        },
                    ],
                    series: [
                        {
                            type: 'bar',
                            data: [],
                            yAxisIndex: 0,
                            itemStyle:{
                                barBorderRadius: 20,
                                color: function (params){
                                    return  col[params.dataIndex]
                                }
                            },
                            barCategoryGap: 50,
                            barWidth:30,
                            label:{
                                show:true,
                                position: 'inside',
                                formatter: "{c}%"
                            }
                        },
                        {
                            type: 'bar',
                            data: [],
                            barCategoryGap: 50,
                            barWidth: 30,
                            yAxisIndex: 1,
                            itemStyle:{
                                color:"none",
                                borderWidth: 3,
                                barBorderRadius: 15
                            }

                        }
                    ]
                };


                //总人数/填写人数 = 就业率
                for (let i = 0 ; i < this.classes.length;i++){
                    let myClass = this.classes[i]
                    this.Xdata1.push(
                        Math.round(myClass.fillStu / myClass.fullStu * 10000) / 100.00
                    )
                    this.Sdata2.push(myClass.className)
                    this.Xdata3.push(myClass.fullStu)
                    this.Sdata4.push(myClass.fillStu)
                }
                this.option.yAxis[0].data = this.Sdata2
                this.option.yAxis[1].data = this.Xdata3

                this.option.series[0].data = this.Xdata1
                console.log(this.option.series[0].data)
                this.option.series[1].data = this.Sdata4
                console.log(this.option.series[0].data)

                this.option && myChart.setOption(this.option);
            },
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
                        this.getColor()
                    }else {
                        this.$message.error(res.data.msg)
                    }
                })
            },
        },

    }
</script>

<style></style>

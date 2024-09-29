<template>
    <div>
        <img :src="imageSrc" alt="平台广告" width="1100" height="180" style="align-items: center;" />
        <el-row :gutter="20" style="margin-top: 10px;,margin-bottom: 20px;">
            <el-col :span="6">
                <el-card>
                    <div class="statistic-title">商品总数</div>
                    <div class="statistic-value">
                        <i class="el-icon-goods" style="color: blue;"></i>
                        {{ commodityNum }}件
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="statistic-title">总销售额</div>
                    <div class="statistic-value1">
                        <i class="el-icon-money" style="color: green;"></i>
                        ¥{{ moneyAll }}万
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="statistic-title">订单总数</div>
                    <div class="statistic-value">
                        <i class="el-icon-document" style="color: orange;"></i>
                        {{ orderNum }}个
                    </div>
                </el-card>
            </el-col>
            <el-col :span="6">
                <el-card>
                    <div class="statistic-title">用户数量</div>
                    <div class="statistic-value">
                        <i class="el-icon-user" style="color: red;"></i>
                        {{ userNum }}位
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <div id="charts-container" :style="{ marginTop: '30px' }">
            <div id="myChart" :style="{ width: '860px', height: '860px' }"></div>
            <div id="myChart2" :style="{ width: '800px', height: '800px' }"></div>
        </div>
        <div id="charts-container" :style="{ marginTop: '30px' }">
            <div id="myChart3" :style="{ width: '1100px', height: '450px' }"></div>
            <div id="myChart4" :style="{ width: '1100px', height: '450px' }"></div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'dataShow',
    mounted() {
        this.axios.get("/order/shopMoney").then((res) => {
            var shopNum = res.data.data
            var data = []
            for (var i = 0; i < shopNum.length; i++) {
                var d = { name: '', value: 0 }
                d.name = shopNum[i].name
                d.value = shopNum[i].num
                data.push(d)
            }
            data.sort((a, b) => b.value - a.value)
            this.data1 = data
            this.Draw()
        }).catch((err) => {
            console.log(err)
            alert('请求失败！请检查后端接口！！！！')
        }),
            this.axios.get("/order/shopOrderNum").then((res) => {
                var shopNum = res.data.data
                var data = []
                for (var i = 0; i < shopNum.length; i++) {
                    var d = { name: '', value: 0 }
                    d.name = shopNum[i].name
                    d.value = shopNum[i].num
                    data.push(d)
                }
                data.sort((a, b) => b.value - a.value)
                this.data2 = data
                this.Draw2()
            }).catch((err) => {
                console.log(err)
                alert('请求失败！请检查后端接口！！！！')
            }),
            this.Draw3(),
            this.Draw4()
    },
    data() {
        return {
            imageSrc: require('@/assets/image/商品描述广告.png'),
            commodityNum: 99999,
            moneyAll: 999.9999,
            orderNum: 999999,
            userNum: 9999,
            data1: [],
            data2: [],
        };
    },
    created() {
        this.axios.get("/commodity/commodityNum").then(response => {
            if (response.data.code == 0) {
                this.commodityNum = response.data.data;
            }
        });
        this.axios.get("/order/Money").then(response => {
            if (response.data.code == 0) {
                this.moneyAll = response.data.data / 10000;
            }
        });
        this.axios.get("/order/orderNum").then(response => {
            if (response.data.code == 0) {
                this.orderNum = response.data.data;
            }
        });
        this.axios.get("/user/Num").then(response => {
            if (response.data.code == 0) {
                this.userNum = response.data.data;
            }
        });

    },
    methods: {
        change() {
            if (this.yaer == 2023) {
                this.year = 2024
            } else {
                this.year = 2023
            }
        },
        Draw() {
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById('myChart'))

            // 绘制图表
            myChart.setOption({
                color: [
                    '#FF6F61', // Coral
                    '#6B5B95', // Purple
                    '#88B04B', // Green
                    '#F7CAC9', // Light Pink
                    '#92A8D1', // Light Blue
                    '#F7A072', // Orange
                    '#3B3A30', // Dark Grey
                    '#F0E5CF', // Beige
                    '#B9D5CE', // Teal
                    '#E4B7A0'  // Peach
                ],
                title: {
                    text: '店铺收入统计图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: '50%',
                        data: this.data1,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]

            })
        },
        Draw2() {
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById('myChart2'))

            // 绘制图表
            myChart.setOption({
                title: {
                    text: '店铺订单统计图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    top: '5%',
                    left: 'center'
                },
                series: [
                    {
                        name: 'Access From',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2
                        },
                        label: {
                            show: false,
                            position: 'center'
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: 40,
                                fontWeight: 'bold'
                            }
                        },
                        labelLine: {
                            show: false
                        },
                        data: this.data2
                    }
                ]

            })
        },
        Draw3() {
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById('myChart3'))

            // 绘制图表
            myChart.setOption({
                title: {
                    text: '2023年度销售统计图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    top: '5%',
                    left: '10%',

                    data: ['sj小卖部', '十月结晶官方店', '国服韩信的小店', '小米之家', '张子超的小店', '德福的小店', '明煌生鲜食品专营店', '李宁专卖店', '益丰大药房官方店', '舒肤佳官方旗舰店']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
                },
                yAxis: {
                    type: 'value',
                    name: 'Sales (元)'
                },
                series: [
                    {
                        name: 'sj小卖部',
                        type: 'line',

                        data: [19486, 21198, 19598, 13057, 18069, 13397, 17943, 7436, 17528, 24808, 11241, 15934]
                    },
                    {
                        name: '十月结晶官方店',
                        type: 'line',
    
                        data: [12177, 17775, 18401, 9134, 11452, 17298, 17539, 12581, 12798, 17693, 14897, 11959]
                    },
                    {
                        name: '国服韩信的小店',
                        type: 'line',
          
                        data: [21569, 18576, 17838, 17298, 10076, 17238, 17187, 17556, 15011, 15860, 14981, 11956]
                    },
                    {
                        name: '小米之家',
                        type: 'line',
               
                        data: [43672, 39614, 38520, 50608, 38817, 40224, 39728, 35972, 41284, 39841, 47348, 42037]
                    },
                    {
                        name: '张子超的小店',
                        type: 'line',
                
                        data: [2694, 3732, 9760, 2947, 7293, 3939, 3236, 9814, 5388, 4387, 5494, 4099]
                    },
                    {
                        name: '德福的小店',
                        type: 'line',
                
                        data: [1564, 8876, 5079, 6392, 6451, 5648, 4531, 9849, 4941, 5156, 2409, 6258]
                    },
                    {
                        name: '明煌生鲜食品专营店',
                        type: 'line',
                 
                        data: [20631, 17107, 23623, 17433, 11695, 17105, 18669, 17865, 17248, 23995, 18444, 16170]
                    },
                    {
                        name: '李宁专卖店',
                        type: 'line',
             
                        data: [16845, 11823, 12272, 13968, 17075, 17069, 22632, 15986, 16157, 15530, 17005, 17435]
                    },
                    {
                        name: '益丰大药房官方店',
                        type: 'line',
                
                        data: [8801, 17602, 17285, 19595, 27057, 17306, 11716, 19064, 15851, 11298, 16327, 8265]
                    },
                    {
                        name: '舒肤佳官方旗舰店',
                        type: 'line',
                 
                        data: [16564, 23241, 15783, 8466, 17165, 17110, 21466, 18279, 14805, 11917, 10005, 11607]
                    }
                ]

            })
        },
        Draw4() {
            // 基于准备好的dom，初始化echarts实例
            let myChart = this.$echarts.init(document.getElementById('myChart4'))

            // 绘制图表
            myChart.setOption({
                title: {
                    text: '2024年度销售统计图',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    top: '5%',
                    left: '10%',
                    data: ['sj小卖部', '十月结晶官方店', '国服韩信的小店', '小米之家', '张子超的小店', '德福的小店', '明煌生鲜食品专营店', '李宁专卖店', '益丰大药房官方店', '舒肤佳官方旗舰店']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug']
                },
                yAxis: {
                    type: 'value',
                    name: 'Sales ($)'
                },
                series: [
                    {
                        name: 'sj小卖部',
                        type: 'line',
          
                        data: [15877, 5698, 11864, 11807, 16437, 15547, 21233, 15328],
                    },
                    {
                        name: '十月结晶官方店',
                        type: 'line',
             
                        data: [28263, 23539, 22221, 14433, 26909, 25015, 14030, 13232.9],

                    },
                    {
                        name: '国服韩信的小店',
                        type: 'line',
      
                        data: [12256, 16176, 13526, 12830, 17980, 15361, 22820, 18872],

                    },
                    {
                        name: '小米之家',
                        type: 'line',
            
                        data: [41737, 45340, 44284, 38023, 46581, 44195, 38074, 107350],

                    },
                    {
                        name: '张子超的小店',
                        type: 'line',
            
                        data: [3605, 3470, 7730, 10040, 4722, 9037, 2200, 9136.3],

                    },
                    {
                        name: '德福的小店',
                        type: 'line',
                
                        data: [6413, 7621, 6553, 5677, 5687, 3255, 10277, 6409],

                    },
                    {
                        name: '明煌生鲜食品专营店',
                        type: 'line',
           
                        data: [13278, 16991, 14721, 9765, 11498, 14728, 16115, 13635],

                    },
                    {
                        name: '李宁专卖店',
                        type: 'line',
          
                        data: [20192, 18609, 9952, 19618, 11735, 15017, 13069, 17486],

                    },
                    {
                        name: '益丰大药房官方店',
                        type: 'line',
       
                        data: [12006, 15990, 17265, 18722, 22567, 17064, 8977, 8785],

                    },
                    {
                        name: '舒肤佳官方旗舰店',
                        type: 'line',
         
                        data: [15799, 18765, 14380, 13444, 23542, 21500, 22151, 19493.47]

                    }
                ]

            })
        },
    }

};
</script>

<style scoped>
.statistic-title {
    font-size: 16px;
    margin-bottom: 10px;
    margin-right: 150px;
    font-weight: bold;
}

.statistic-value {
    font-size: 28px;
    display: flex;
    align-items: center;
    margin-left: 60px;
}

.statistic-value1 {
    font-size: 28px;
    display: flex;
    align-items: center;
    margin-left: 25px;
}
</style>

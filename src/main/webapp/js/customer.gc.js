$(function(){
    $.ajax({
        type:"post",
        url:ctx+"/customer/queryCustomerGc",
        dataType:"json",
        success:function (data) {
            var myChart = echarts.init(document.getElementById('main'));
            if(data.code==200){
                var option = {
                    title: {
                        text: '客户构成分析'
                    },
                    tooltip: {},
                    legend: {
                        data:['人数']
                    },
                    xAxis: {
                        data:data.levels
                    },
                    yAxis: {},
                    series: [{
                        name: '人数',
                        type: 'bar',
                        data: data.total
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        }
    });
});
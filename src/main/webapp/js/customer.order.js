function formatterState(val) {
    if(val==0){
        return "未支付";
    }
    if(val=1){
        return "已支付";
    }
}
function formatterOp() {
    var href = "javascript:openOrderDetailById()";
    return "<a href=" + href +">查看详情</a>";
}
function openOrderDetailById() {
    var selections=$("#dg").datagrid("getSelections");
    $.ajax({
        type:"post",
        url:ctx+"/customer_order/queryOrderInfoById",
        dataType:"json",
        data:"id="+selections[0].id,
        success:function(data){
            if(data.state==1){
                data.state="已支付";
            }else{
                data.state="未支付";
            }
            data.orderData=new Date(data.orderData).format("yyyy-MM-dd hh:mm:ss");
            $("#fm").form("load",data);
        }
    });
    $("#dg2").datagrid("load",{
        orderId:selections[0].id
    });
    $("#dlg").dialog("open");
}
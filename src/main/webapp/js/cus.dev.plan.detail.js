$(function () {
    var devresult = $("#devResult").val();
    if(devresult==2||devresult==3) {
        $("#toolbar").remove();
    }
    $('#dg').edatagrid({
        url:ctx+"/cus_dev_plan/queryCusDevPlan?saleChanceId="+$("#saleChanceId").val(),
        saveUrl: ctx+"/cus_dev_plan/addCusDevPlan?saleChanceId="+$("#saleChanceId").val(),
        updateUrl:ctx+"/cus_dev_plan/updateCusDevPlan?saleChanceId="+$("#saleChanceId").val(),
    });
});
function saveCusDevPlan() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}
function updateCusDevPlan() {
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}
function delCusDevPlan() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length==0){
        $.messager.confirm("删除开发计划","未选中数据!","warning");
        return;
    }
    $.messager.confirm("删除开发计划","确定要删除数据吗?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/cus_dev_plan/deleteCusDevPlan",
                data:"id="+selection[0].id,
                dataType:"json",
                success:function (data) {
                    if (data.code == 200) {
                        $.messager.alert("来自crm系统","删除开发计划成功!", "info");
                        $("#dg").edatagrid("load");
                    }else{
                        $.messager.alert("来自crm系统","删除开发计划失败!", "error");
                    }
                }
            })
        }
    });
}
function updateSaleChanceDevResult(devResult) {
    $.messager.confirm("修改开发进程","确定要执行此操作!",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/cus_dev_plan/updateDevResult",
                data:"devResult="+devResult + "&saleChanceId=" + $("#saleChanceId").val(),
                dataType:"json",
                success:function (data) {
                    $.messager.alert("来自crm",data.msg,"info");
                    if (data.code == 200) {
                        $("#toolbar").remove();
                    }
                }
            });
        }
    })
}
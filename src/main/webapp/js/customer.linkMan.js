$(function () {
    $('#dg').edatagrid({
        url:ctx+"/customer_linkman/queryCusLinkman?cusId="+$("#cusId").val(),
        saveUrl: ctx+"/customer_linkman/addCusLinkman?cusId="+$("#cusId").val(),
        updateUrl:ctx+"/customer_linkman/updateCusLinkman?id="+$("#dg").edatagrid("options").id,
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
        $.messager.confirm("删除联系人","未选中数据!","warning");
        return;
    }
    $.messager.confirm("删除联系人","确定要删除数据吗?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/customer_linkman/deleteCusLinkman",
                data:"id="+selection[0].id,
                dataType:"json",
                success:function (data) {
                    if (data.code == 200) {
                        $.messager.alert("来自crm系统","删除联系人成功!", "info");
                        $("#dg").edatagrid("load");
                    }else{
                        $.messager.alert("来自crm系统","删除联系人失败!", "error");
                    }
                }
            })
        }
    });
}
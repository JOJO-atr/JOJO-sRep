$(function () {
    var lossId = $("#lossId").val();
    $("#dg").edatagrid({
        url: ctx + "/customer_repri/customerReprieveByLossId?lossId=" + lossId,
        saveUrl: ctx + "/customer_repri/insertReprive?lossId=" + lossId,
        updateUrl: ctx + "/customer_repri/updateReprive?lossId=" + lossId
    })
    var state = $("#state").val();
    if (state == 1) {
        $("#toolbar").remove();
        $("#dg").edatagrid("disableEditing");
    }
});
function saveCustomerRepri(){
    $("#dg").edatagrid("saveRow");
    $("#dg").edatagrid("load");
}
function delCustomerRepri(){
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length==0){
        $.messager.confirm("删除暂缓措施","未选中数据!","warning");
        return;
    }
    $.messager.confirm("删除暂缓措施", "确定要删除所选项?", function (r) {
        if (r) {
            $.ajax({
                type: "post",
                url: ctx + "/customer_repri/deleteReprive",
                data: "id=" + selection[0].id,
                dataType: "json",
                success: function (data) {
                    $.messager.alert("删除暂缓措施", data.msg, "info");
                    if (data.code == 200) {
                        $("#dg").edatagrid("load");
                    }
                }
            })
        }
    })
}
function updateCustomerLossState() {
    $.messager.confirm("确认流失","确定该客户已流失?",function (r) {
        if(r){
            $.messager.prompt("确认流失","请输入流失原因",function (msg) {
                if(msg){
                    $.ajax({
                        type:"post",
                        url:ctx + "/customer_loss/updateCustomerLossState",
                        data: "lossId=" + $("#lossId").val() + "&lossReason=" + msg,
                        dataType: "json",
                        success: function (data) {
                            $.messager.alert("确认流失", data.msg, "info");
                            if (data.code == 200) {
                                $("#toolbar").remove();
                            }
                        }
                    });
                }else{
                    $.messager.confirm("确认流失","流失原因不能为空!","warning");
                }
            });
        }
    });
}
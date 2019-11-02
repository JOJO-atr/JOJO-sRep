function openAssignDlg() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("分配","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("分配","只能选中一条!","warning");
        return;
    }
    $("#fm").form("load",selection[0]);
    $("#dlg").dialog("open");
}
function closeCustomerServeDialog() {
    $("#dlg").dialog("close");
}
function addCustomerServeAssign() {
    $("#fm").form("submit",{
        url:ctx+"/customer_serve/update",
        onSubmit:function (data) {
            data.state=2;
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            $.messager.alert("crm",data.msg,"info")
            if(data.code==200){
                closeCustomerServeDialog();
                $("#dg").datagrid("load");
            }
        }
    })
}
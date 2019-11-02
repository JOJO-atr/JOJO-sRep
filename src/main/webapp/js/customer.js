function searchCustomer(){
    $("#dg").datagrid("load",{
        khno:$("#s_khno").val(),
        name:$("#s_name").val()
    });
}
function openCustomerAddDialog(){
    $("#dlg").dialog({"title":"添加客户信息"});
    $("#fm")[0].reset();
    $("#dlg").dialog("open");
}
function closeCustomerDialog() {
    $("#dlg").dialog("close");
}
function saveOrUpdateCustomer() {
    var id=$("#id").val();
    url=ctx+"/customer/updateCustomer";
    if(isEmpty(id)){
        url=ctx+"/customer/addCustomer";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function(params){
            return $("#fm").form("validate");
        },
        success:function (data) {
           data=JSON.parse(data);
            if(data.code==200){
                closeCustomerDialog();
                $.messager.confirm("添加客户信息",data.msg,"info");
                searchCustomer();
            }else{
                $.messager.confirm("添加客户信息",data.msg,"error");
            }
        }
    });
}
function openCustomerModifyDialog() {
    var selections=$("#dg").datagrid("getSelections");
    if(selections.length<1){
        $.messager.confirm("修改客户信息","您未选中数据!","warning");
        return;
    }
    if(selections.length>1){
        $.messager.confirm("修改客户信息","只能选中一条数据!!","warning");
        return;
    }
    $("#fm").form("load",selections[0]);
    $("#dlg").dialog({"title":"修改客户信息"});
    $("#dlg").dialog("open");
}
function deleteCustomer() {
    var selections=$("#dg").datagrid("getSelections");
    if(selections.length<1){
        $.messager.confirm("修改客户信息","您未选中数据!","warning");
        return;
    }
    var str="id=";
    for(var i=0;i<selections.length;i++){
        if(i<selections.length-1){
            str+=selections[i].id+"&id=";
        }else{
            str+=selections[i].id;
        }
    }
    $.messager.confirm("删除客户信息","你确定要删除选中数据吗?",function (r) {
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/customer/deleteCustomer",
                data:str,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        closeCustomerDialog();
                        $.messager.confirm("删除客户信息","删除成功!","info");
                        searchCustomer();
                    }else {
                        $.messager.confirm("删除客户信息", data.msg, "error");
                    }
                }
            });
        }
    })
}
function openCustomerOtherInfo(title,type){
    var selections=$("#dg").datagrid("getSelections");
    if(selections.length<1){
        $.messager.confirm("修改客户信息","您未选中数据!","warning");
        return;
    }
    if(selections.length>1){
        $.messager.confirm("修改客户信息","只能选中一条数据!!","warning");
        return;
    }
    window.parent.openTab(title,ctx+"/customer/openCustomerOtherInfo/"+type+"/"+selections[0].id);
}
function openCustomerOtherInfo(title,type) {
    var selections=$("#dg").datagrid("getSelections");
    if(selections.length<1){
        $.messager.confirm("修改客户信息","您未选中数据!","warning");
        return;
    }
    if(selections.length>1){
        $.messager.confirm("修改客户信息","只能选中一条数据!!","warning");
        return;
    }
    window.parent.openTab(title,ctx+"/customer/openCustomerOtherInfo/"+type+"/"+selections[0].id);

}
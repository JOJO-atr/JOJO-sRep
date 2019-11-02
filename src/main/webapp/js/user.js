function searchUsers() {
    $("#dg").datagrid("load",{
        userName:$("#userName").val(),
        trueName:$("#trueName").val(),
        phone:$("#phone").val(),
        email:$("#email").val()
    });
}
function openAddUserDialog() {
    $("#dlg").dialog("setTitle","添加用户记录")
    $("#fm")[0].reset();
    $("#dlg").dialog("open");
}
function closeDialog() {
    $("#dlg").dialog("close");
}
function saveOrUpdateUser(){
    var id=$("#id").val();
    url=ctx+"/user/updateUser";
    if(isEmpty(id)){
      url=ctx+"/user/addUser";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function (data) {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            $.messager.confirm("添加用户",data.msg,"info");
            if(data.code==200){
                closeDialog();
                $("#dg").datagrid("load");
            }
        }
    });
}
function openModifyUserDialog() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("修改用户信息","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("修改用户信息","只能选中一条数据!","warning");
        return;
    }
    $("#fm").form("load",selection[0]);
    $("#dlg").dialog("setTitle","修改用户记录")
    $("#dlg").dialog("open");
}
function deleteUser() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("删除用户信息","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("删除用户信息","只能选中一条数据!","warning");
        return;
    }
    $.messager.confirm("来自crm","确定删除选中的"+selection.length+"条记录?",function(r){
        if(r){
            $.ajax({
                type:"post",
                url:ctx+"/user/deleteUser",
                data:"id=" + selection[0].id,
                dataType:"json",
                success:function(data){
                    $.messager.alert("来自crm",data.msg,"info");
                    if(data.code==200){
                        closeDialog();
                        searchUsers();
                    }
                }
            });
        }
    });
}
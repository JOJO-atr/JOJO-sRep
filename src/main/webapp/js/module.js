function formatterGrade(val){
    if(val=="0"){
        return "根级";
    }
    if(val=="1"){
        return "一级";
    }
    if(val=="2"){
        return "二级";
    }
}
function searchModules(){
    $("#dg").datagrid("load",{
        moduleName:$("#moduleName").val(),
        optValue:$("#optValue").val(),
        parentModuleName:$("#parentModuleName").val()
    });
}
function openAddModuleDialog(){
    $("#fm")[0].reset();
    $("#dlg").dialog("setTitle","添加模块").dialog("open");

}
function closeDialog() {
    $("#dlg").dialog("close");
}
$(function(){
    $("#parentMenu").hide();
    $("#grade").combobox({
        onChange:function (val) {
            if (val == 1 || val == 2) {
                $("#parentMenu").show();
            } else {
                $("#parentMenu").hide();
            }
            $("#parentId").combobox("clear");
            $('#parentId').combobox({
                url:ctx+"/module/queryModuleByGrade?grade="+(val-1),
                valueField:'id',
                textField:'moduleName'
            });
        }
    });
})
function saveOrUpdateModule(){
    var id=$("#id").val();
    url=ctx+"/module/updateModule";
    if(isEmpty(id)){
        url=ctx+"/module/addModule";
    }
    $("#fm").form("submit",{
        url:url,
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            $.messager.confirm("添加模块",data.msg,"info");
            if(data.code==200){
                closeDialog();
                $("#dg").datagrid("load");
            }
        }
    })
}
function openModifyModuleDialog(){
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("修改模块","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("修改模块","只能选中一条数据!","warning");
        return;
    }
    $("#fm").form("load",selection[0]);
    $("#dlg").dialog("setTitle","修改模块").dialog("open");
}
function deleteModule() {
    var selection=$("#dg").datagrid("getSelections");
    if(selection.length<1){
        $.messager.confirm("删除模块","未选中数据!","warning");
        return;
    }
    if(selection.length>1){
        $.messager.confirm("删除模块","只能选中一条数据!","warning");
        return;
    }
   $.messager.confirm("删除模块","确定删除选中的数据吗?",function (r) {
       if(r){
           $.ajax({
               type:"post",
               url:ctx+"/module/deleteModule",
               data:"id="+selection[0].id,
               dataType:"json",
               success:function(data){
                   $.messager.confirm("删除模块",data.msg,"info");
                   if(data.code==200){
                       $("#dg").datagrid("load");
                   }
               }
           });
       }
   })
}
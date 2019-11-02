function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}
function logout(){
    $.messager.confirm("退出","确定要退出吗？",function (r){
        if(r){
            $.removeCookie("userName");
            $.removeCookie("trueName");
            $.removeCookie("userId");
            window.location.href="index";
        }
    });
}
function openPasswordModifyDialog() {
    $("#dlg").dialog("open");
}
function closePasswordModifyDialog(){
    $("#dlg").dialog("close");
}
function modifyPassword(){
   $("#fm").form('submit',{
        url:ctx+"/user/updatePwd",
        onSubmit:function () {
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);//将返回值转换成为json串
            if(data.code==200){
                $.messager.alert("修改密码","修改成功,重新登录!","info");
                $.removeCookie("userName");
                $.removeCookie("trueName");
                $.removeCookie("userId");
                window.location.href="index";
            }else{
                $.messager.alert("修改密码",data.msg,"warning");
            }
        }
   });


}

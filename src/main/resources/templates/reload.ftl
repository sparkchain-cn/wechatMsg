<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>重载配置</title>
</head>

<body>
<div class="box" style="text-align: center;left: 50%">
    <div>
        <p>配置token</p>
    </div>
    <div>
        <span>tokenCode</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="tokenCode" value=${tokenCode}>
    </div>
    <div>
        <span>appcode</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="appcode" value=${appcode}>
    </div>
    <div>
        <span>appname</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="appname" value=${appname}>
    </div>
    <div>
        <span>用户钱包充值个数</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="accountWalletPushNum" onkeyup="value=value.replace(/[^\d]/g,'')" value=${accountWalletPushNum}>
    </div>
    <div>
        <span>app钱包充值个数</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="appWalletPushNum" onkeyup="value=value.replace(/[^\d]/g,'')" value=${appWalletPushNum}>
    </div>
    <div>
        <span>红包最小金额</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="redPacketMin" onkeyup="value=value.replace(/[^\d]/g,'')" value=${redPacketMin}>
    </div>
    <div>
        <span>红包最大金额</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="redPacketMax" onkeyup="value=value.replace(/[^\d]/g,'')" value=${redPacketMax}>
    </div>
    <div>
        <span>发行方地址</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="IssuerAddress" value=${IssuerAddress}>
    </div>
    <div>
        <span>发行方私钥</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="IssuerSecret" value=${IssuerSecret}>
    </div>
    <div>
        <span>链上浏览器地址</span>
        <input type="text" style="outline:none;width: 300px;margin-top: 15px" id="chianUrl" value=${chianUrl}>
    </div>


    <input type="button" style="margin: 20px" value="提交" onclick="reloadSubmit()">

</div>
<script src="./js/jquery.js"></script>
</body>
<script>
    function reloadSubmit(){
        var obj = {};
        obj.tokenCode = $('#tokenCode').val();
        obj.appAccount = $('#appAccount').val();
        obj.appSecret = $('#appSecret').val();
        obj.appcode = $('#appcode').val();
        obj.appname = $('#appname').val();
        obj.accountWalletPushNum = $('#accountWalletPushNum').val();
        obj.appWalletPushNum = $('#appWalletPushNum').val();
        obj.redPacketMin = $('#redPacketMin').val();
        obj.redPacketMax = $('#redPacketMax').val();
        obj.IssuerAddress = $('#IssuerAddress').val();
        obj.IssuerSecret = $('#IssuerSecret').val();
        obj.chianUrl = $('#chianUrl').val();
        $.ajax({
            type: "post",
            data: obj,
            url: "/reload/init"}).done(function (data) {
                alert(data);

        });

    }

</script>

</html>
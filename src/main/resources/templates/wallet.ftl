<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>钱包</title>
    <link rel="stylesheet" href="./css/submit.css">
</head>


<body>
    <div class="box">
        <div class="message">
            <p>留言板</p>
        </div>
        <div class="publickey">
            <span>公钥</span>
            <input type="text" style="outline:none;" class="publickeyvalue" readonly value=${wallet.account}>
        </div>
        <div class="privatekey">
            <span>私钥</span>
            <input type="text" style="outline:none;" class="privatekeyvalue" readonly value=${wallet.secret}>
        </div>
        <div class="balance">
                <span>余额</span>
                <input type="text" style="outline:none;" class="balancevalue" readonly value=${wallet.balance}>
            </div>
        <div class="submit"><a href="http://47.75.144.55:3000/home">公链查看详情</a></div>

    </div>


    <script src="./js/flexible.js"></script>
    <script src="./js/jquery.js"></script>
</body>

</html>
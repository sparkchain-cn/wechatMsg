<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>留言板</title>
    <link rel="stylesheet" href="./css/index.css">
</head>

<body>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Document</title>
    </head>

    <body>
        <div class="box">
            <div class="message">
                <p>留言板</p>
            </div>
            <div class="content">
                <textarea id="message" maxlength="240" class="rectangle" placeholder="请输入您的留言" onchange="this.value=this.value.substring(0, 240)" onkeydown="this.value=this.value.substring(0, 240)"
                onkeyup="this.value=this.value.substring(0, 240)"></textarea>
                <div class="absolute"><span id="text-count2" value="">0</span>/240</div>
            </div>
            <div class="rectangle-22">
                <span>手机号码</span>
                <input type="text" class="iphone" id="phone" maxlength="11" style="outline:none;" placeholder="请输入">
                <input id="openId" type="hidden" value="${openId}"/>
            </div>
            <div class="submit" id="send" onclick="indexSubmit()">提交</div>
        </div>
    </body>

    </html>
    <script src="./js/flexible.js"></script>
    <script src="./js/jquery.js"></script>
    <script src="./js/index.js"></script>
    <script src="./js/message.js"></script>
</body>

</html>
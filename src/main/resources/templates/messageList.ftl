<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>留言列表</title>
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
	</head>

	<body>
		<ul>
		<#list messageList as message>
			<li>
				<span>${message.openId}</span>
				<span>${message.memo}</span>
				<span>${message.hash}</span>
			</li>
		</#list>
		</ul>
	</body>
</html>
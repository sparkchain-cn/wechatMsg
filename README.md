#进入http://liuyan.sparkchain.cn/reload 页面进行配置。
#新的appcode,appname会生成一组新的企业钱包，发行方会向该企业钱包充值一定代币，用户登陆火花小站会给用户生成一个用户钱包并发给用户一定数量代笔作为手续费，用户提交留言成功后，企业钱包会向用户钱包充值一个红包。

#进入火花小站-选择测试留言板-提交留言和填写手机号后，可以得到自己的钱包地址，私钥和余额
#注：余额由于链上返回时效问题可能不会在转账时做到实时准确。

#项目启动页
微信登录后进入liuyan.sparkchain.cn/init 获取用户code，再调用回调接口liuyan.sparkchain.cn，获取openId,根据openId创建用户钱包（已有钱包则直接跳到钱包页），创建钱包完毕后进入留言页

#提交留言
提交留言接口liuyan.sparkchain.cn/message/send。用户钱包会向链上提交文本留言（留言需要GAS费），企业钱包会向该用户钱包发一个红包。这些操作完成后，跳到余额页

#IndexController类 主程序所有方法都在此类
#ReloadController类 配置页所有方法都在此类

#resources/templates
message.ftl 留言板页面
wallet.ftl 钱包页面
reload.ftl 配置页面


##todo

主要放架构设计文档，API设计文档，数据设计文档及PDM

用户在接入平台中创建钱包(wallet),包含address、查询用password和转账用payPassword
对应在不同的区块链上，会开设不同的账号(account),包括address和secret。
平台不直接把secret传给用户，会在平台加密保存，用户需用自己的payPassword获取自己账号的secret。
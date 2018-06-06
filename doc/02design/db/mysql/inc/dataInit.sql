
----创建区块链数据
insert sc_block_chain VALUES
(1, 'MOAC', 'MOAC',NULL, NULL, NULL, NULL,NULL,1,NULL, 0, 1, 0, NULL, NULL, NULL);

insert sc_block_chain VALUES
(2, 'JINGTUM', 'JINGTUM',NULL, NULL, NULL, NULL,NULL,1,NULL, 0, 1, 0, NULL, NULL, NULL);

insert sc_block_chain VALUES
(3, 'SPARKCHAIN', 'SPARKCHAIN',NULL, NULL, NULL, NULL,NULL,1,NULL, 0, 1, 0, NULL, NULL, NULL);


----创建默认可用Token
insert sc_token VALUES
(1, 2, 'JINGTUM', 'SWT', 'SWTC', 'SWT', 6, NULL, 'SWT', NULL, NULL, NULL, 1, 1, 1, NULL, NULL);

insert sc_token VALUES
(2, 1, 'MOAC', 'MOAC', 'MOAC', 'MOAC', 6, NULL, 'MOAC', NULL, NULL, NULL, 1, 1, 1, NULL, NULL);

insert sc_token VALUES
(3, 2, 'SPP', 'SPP', 'SPP', 'SPP', 6, NULL, 'SPP', NULL, NULL, NULL, 1, 2, 1, NULL, NULL);

----设置区块链GASTOKEN种类
UPDATE sc_block_chain SET gas_token_id=2 WHERE id=1;
UPDATE sc_block_chain SET gas_token_id=1 WHERE id=2;
UPDATE sc_block_chain SET gas_token_id=1 WHERE id=3;
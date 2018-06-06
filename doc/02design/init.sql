INSERT INTO `sc_block_chain` (`id`, `code`, `name`, `apis`, `gas_token_id`, `gas_token_code`, `gas_price_unit`, `gas_limit`, `typeval`, `typecode`, `main_chain_id`, `active_count`, `active`, `active_token_code`, `privated`, `origined`, `child_created`, `contract_support`, `createtime`, `updatetime`) 
VALUES ('10001', 'jingtum', '井通', NULL, '1001', 'SWT', '0.01', '1', NULL, NULL, '1001', '25', '0', 'SWT', '0', '1', '0', '0', NULL, NULL);
INSERT INTO `sc_block_chain` (`id`, `code`, `name`, `apis`, `gas_token_id`, `gas_token_code`, `gas_price_unit`, `gas_limit`, `typeval`, `typecode`, `main_chain_id`, `active_count`, `active`, `active_token_code`, `privated`, `origined`, `child_created`, `contract_support`, `createtime`, `updatetime`) 
VALUES ('10002', 'moac', '墨客', NULL, '1002', 'MOAC', '25000000000', '40000', NULL, NULL, '1002', '0', '0', 'MOAC', '0', '1', '1', '1', NULL, NULL);


INSERT INTO `sc_token` (`id`, `chain_id`, `chain_code`, `code`, `name`, `units`, `precisions`, `issuers`, `currency`, `contract_addr`, `contract_contents`, `contract_abi`, `state`, `type`, `orig_token_code`, `origined`, `createtime`, `updatetime`) 
VALUES ('1001', '10001', 'jingtum', 'SWT', 'SWTC', 'SWT', '6', NULL, 'SWT', NULL, NULL, NULL, '1', '1', 'SWT', '1', NULL, NULL);
INSERT INTO `sc_token` (`id`, `chain_id`, `chain_code`, `code`, `name`, `units`, `precisions`, `issuers`, `currency`, `contract_addr`, `contract_contents`, `contract_abi`, `state`, `type`, `orig_token_code`, `origined`, `createtime`, `updatetime`)
VALUES ('1002', '10002', 'moac', 'MOAC', 'MOAC', 'MOAC', '2', NULL, 'MOAC', NULL, NULL, NULL, '1', '1', 'MOAC', '1', NULL, NULL);

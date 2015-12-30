--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: org/kuali/ole/2.1/db.changelog-20151123.xml
--  *********************************************************************

--  Lock Database
--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::OLE_LOAD_KRCR_PARM_T_mysql::ole
SET sql_mode='NO_BACKSLASH_ESCAPES'
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID) VALUES ('OLE-DESC', 'Describe', 'HOLDINGS_CALL_NUMBER_TYPE', '5A689075D35B7AEBE0404F8189D81922', '1', 'CONFG', 'LCC', 'To set Holdings Default value in editor', 'A', 'KUALI')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID) VALUES ('OLE-DESC', 'Describe', 'ITEM_CALL_NUMBER_TYPE', '5A689075D35B7AEBE0404F8189D81923', '1', 'CONFG', 'LCC', 'To set Item Default value in editor', 'A', 'KUALI')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID) VALUES ('OLE-DESC', 'Describe', 'E-HOLDINGS_CALL_NUMBER_TYPE', '5A689075D35B7AEBE0404F8189D81924', '1', 'CONFG', 'LCC', 'To set Holdings Default value in editor', 'A', 'KUALI')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID) VALUES ('OLE-DLVR', 'Deliver', 'RECEIPT_PRINTER_PAGE_SIZE', 'OLE7048', '1', 'CONFG', 'B7', 'This parameter value is used for configuring the page size for receipt printer.', 'A', 'OLE')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID) VALUES ('OLE-DLVR', 'Deliver', 'RECEIPT_PRINTER_FONT_SIZE', 'OLE7049', '1', 'CONFG', '13', 'This parameter value is used for configuring the font size for receipt printer.', 'A', 'OLE')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID) VALUES ('OLE-DLVR', 'Deliver', 'REGULAR_PRINTER_PAGE_SIZE', 'OLE7050', '1', 'CONFG', 'A4', 'This parameter value is used for configuring the page size for regular printer.', 'A', 'OLE')
/

INSERT INTO KRCR_PARM_T (NMSPC_CD, CMPNT_CD, PARM_NM, OBJ_ID, VER_NBR, PARM_TYP_CD, VAL, PARM_DESC_TXT, EVAL_OPRTR_CD, APPL_ID) VALUES ('OLE-DLVR', 'Deliver', 'REGULAR_PRINTER_FONT_SIZE', 'OLE7051', '1', 'CONFG', '10', 'This parameter value is used for configuring the font size for regular printer.', 'A', 'OLE')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_LOAD_KRCR_PARM_T_mysql', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 1, '7:0112a05952269a2b79f850f0d9fc8023', 'sql, loadData', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::OLE_KRCR_PARM_T::ole
UPDATE KRCR_PARM_T SET VAL = 'PO + 0 $' WHERE PARM_NM = 'REENCUMBER_RECURRING_ORDERS'
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_KRCR_PARM_T', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 2, '7:4b8bac07b079429600f713e4b495a922', 'update', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::OLE_DS_ITEM_T_ADD::ole
ALTER TABLE OLE_DS_ITEM_T ADD DESC_OF_PIECES VARCHAR(400) NULL
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_DS_ITEM_T_ADD', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 3, '7:9195e2f46e6a719bf93100823a3f6e31', 'addColumn', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::ITEM_AUDIT_T::ole
CREATE TABLE ITEM_AUDIT_T (AUDIT_ID INT(10) NOT NULL, FOREIGN_KEY_REF INT(40) NULL, ACTOR VARCHAR(40) NULL, UPDATE_DATE TIMESTAMP NULL, COLUMN_UPDATED VARCHAR(40) NULL, COLUMN_VALUE BLOB NULL, OBJ_ID VARCHAR(36) NULL, VER_NBR DECIMAL(8) NULL, CONSTRAINT PK_ITEM_AUDIT_T PRIMARY KEY (AUDIT_ID))
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('ITEM_AUDIT_T', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 4, '7:c6426909901d1d50e0f7f4476e615658', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::ITEM_AUDIT_S::ole
CREATE TABLE ITEM_AUDIT_S (ID BIGINT(19) NOT NULL, CONSTRAINT PK_ITEM_AUDIT_S PRIMARY KEY (ID))
/

ALTER TABLE ITEM_AUDIT_S MODIFY ID BIGINT(19) AUTO_INCREMENT
/

ALTER TABLE ITEM_AUDIT_S AUTO_INCREMENT=1
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('ITEM_AUDIT_S', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 5, '7:9bcdc626e01c62324d3215a7ff3188af', 'createTable, addAutoIncrement', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::BIB_AUDIT_T::ole
CREATE TABLE BIB_AUDIT_T (AUDIT_ID INT(10) NOT NULL, FOREIGN_KEY_REF INT(40) NULL, ACTOR VARCHAR(40) NULL, UPDATE_DATE TIMESTAMP NULL, COLUMN_UPDATED VARCHAR(40) NULL, COLUMN_VALUE BLOB NULL, OBJ_ID VARCHAR(36) NULL, VER_NBR DECIMAL(8) NULL, CONSTRAINT PK_BIB_AUDIT_T PRIMARY KEY (AUDIT_ID))
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('BIB_AUDIT_T', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 6, '7:1f3e9c6b844c26d85a4bf5577e4e7efa', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::BIB_AUDIT_S::ole
CREATE TABLE BIB_AUDIT_S (ID BIGINT(19) NOT NULL, CONSTRAINT PK_BIB_AUDIT_S PRIMARY KEY (ID))
/

ALTER TABLE BIB_AUDIT_S MODIFY ID BIGINT(19) AUTO_INCREMENT
/

ALTER TABLE BIB_AUDIT_S AUTO_INCREMENT=1
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('BIB_AUDIT_S', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 7, '7:f05c53d8c16e2e14f32fa0ceb3e6a522', 'createTable, addAutoIncrement', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::HOLDINGS_AUDIT_T::ole
CREATE TABLE HOLDINGS_AUDIT_T (AUDIT_ID INT(10) NOT NULL, FOREIGN_KEY_REF INT(40) NULL, ACTOR VARCHAR(40) NULL, UPDATE_DATE TIMESTAMP NULL, COLUMN_UPDATED VARCHAR(40) NULL, COLUMN_VALUE BLOB NULL, OBJ_ID VARCHAR(36) NULL, VER_NBR DECIMAL(8) NULL, CONSTRAINT PK_HOLDINGS_AUDIT_T PRIMARY KEY (AUDIT_ID))
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('HOLDINGS_AUDIT_T', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 8, '7:458ea09629d0eb2d0ffe8b80da20c278', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::HOLDINGS_AUDIT_S::ole
CREATE TABLE HOLDINGS_AUDIT_S (ID BIGINT(19) NOT NULL, CONSTRAINT PK_HOLDINGS_AUDIT_S PRIMARY KEY (ID))
/

ALTER TABLE HOLDINGS_AUDIT_S MODIFY ID BIGINT(19) AUTO_INCREMENT
/

ALTER TABLE HOLDINGS_AUDIT_S AUTO_INCREMENT=1
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('HOLDINGS_AUDIT_S', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 9, '7:2e0e5c4d5c6511f403f1f7b82f239462', 'createTable, addAutoIncrement', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::OLE_BAT_PRCS_PRFLE_CNST_T_ATT_VAL_MYSQL::ole
ALTER TABLE OLE_BAT_PRCS_PRFLE_CNST_T MODIFY ATT_VAL varchar(500)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_BAT_PRCS_PRFLE_CNST_T_ATT_VAL_MYSQL', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 10, '7:1493697f04debf4577b4234340847e0a', 'sql', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::OLE_DS_HOLDINGS_T_ADD_CANCELLATION_CANDIDATE::ole
ALTER TABLE OLE_DS_HOLDINGS_T ADD CANCELLATION_CANDIDATE CHAR(1) NULL
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_DS_HOLDINGS_T_ADD_CANCELLATION_CANDIDATE', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 11, '7:6d78e0988ed6e5e10cf1735cd9ce8885', 'addColumn', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::RQST_NOTE_ADD::ole
ALTER TABLE OLE_DLVR_RQST_T ADD RQST_NOTE VARCHAR(4000) NULL
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('RQST_NOTE_ADD', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 12, '7:ce622f267ae14d2658c07d9acdb6e78b', 'addColumn', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::OLE_RETURN_HISTORY_T::ole
ALTER TABLE OLE_LOAN_INTRANSIT_HISTORY_T RENAME OLE_RETURN_HISTORY_T
/

ALTER TABLE OLE_RETURN_HISTORY_T ADD RETURNED_ITEM_STATUS VARCHAR(200) NULL
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_RETURN_HISTORY_T', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 13, '7:a37b0219bda47584d5ae2ac2ce763ecc', 'renameTable, addColumn', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::OLE_RETURN_HISTORY_S_MYSQL::ole
ALTER TABLE OLE_LOAN_INTRANSIT_HISTORY_S RENAME OLE_RETURN_HISTORY_S
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_RETURN_HISTORY_S_MYSQL', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 14, '7:06a0fb708c903b0b1e1b29a0b2becbeb', 'renameTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.1/db.changelog-20151123.xml::ADD_RCP_ROLE_GROUP::ole
ALTER TABLE OLE_E_RES_REC_T ADD E_RES_RCP_ROLE_ID VARCHAR(40) NULL
/

ALTER TABLE OLE_E_RES_REC_T ADD E_RES_RCP_GROUP_ID VARCHAR(40) NULL
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('ADD_RCP_ROLE_GROUP', 'ole', 'org/kuali/ole/2.1/db.changelog-20151123.xml', NOW(), 15, '7:56484a78acc4bb73efd16413743bd059', 'addColumn (x2)', '', 'EXECUTED', '3.2.0')
/

--  Release Database Lock
--  Release Database Lock

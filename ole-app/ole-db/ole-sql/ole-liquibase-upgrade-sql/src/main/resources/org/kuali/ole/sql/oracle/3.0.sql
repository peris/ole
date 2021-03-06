-- *********************************************************************
-- Update Database Script
-- *********************************************************************
-- Change Log: org/kuali/ole/3.0/db.changelog-20160208.xml
-- *********************************************************************

-- Lock Database
-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::OLE_KRIM_PERM_T_oracle::ole
INSERT INTO KRIM_PERM_T (PERM_ID, OBJ_ID, VER_NBR, PERM_TMPL_ID, NMSPC_CD, NM, DESC_TXT, ACTV_IND) VALUES ('OLE10502', 'OLE10502', '1', '1', 'OLE-CAT', 'MARC_EDITOR_COPY_BIB', 'user to Copy a bibliographic record will be created', 'Y')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_KRIM_PERM_T_oracle', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 1, '7:53c438fb3ea44da885b942abc5876443', 'loadData', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::OLE_KRIM_ROLE_PERM_T_oracle::ole
INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND) VALUES ('OLE12506', 'OLE12506', 'OLE10077', 'OLE10502', 'Y')
/

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND) VALUES ('OLE12507', 'OLE12507', 'OLE10078', 'OLE10502', 'Y')
/

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND) VALUES ('OLE12508', 'OLE12508', 'OLE10079', 'OLE10502', 'Y')
/

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND) VALUES ('OLE12509', 'OLE12509', 'OLE10080', 'OLE10502', 'Y')
/

INSERT INTO KRIM_ROLE_PERM_T (ROLE_PERM_ID, OBJ_ID, ROLE_ID, PERM_ID, ACTV_IND) VALUES ('OLE12510', 'OLE12510', 'OLE10082', 'OLE10502', 'Y')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_KRIM_ROLE_PERM_T_oracle', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 2, '7:a0c10fd381684b8bd3340fdb93eedc8b', 'loadData', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::OLE_CAT_SHVLG_SCHM_T_UPDATE::ole
UPDATE OLE_CAT_SHVLG_SCHM_T SET SHVLG_SCHM_CD = 'EIGHT', SHVLG_SCHM_ID = '10' WHERE SHVLG_SCHM_ID = 8
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_CAT_SHVLG_SCHM_T_UPDATE', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 3, '7:07bcfea29f570366d060d68cbb549c8f', 'update', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::OLE_CAT_SHVLG_SCHM_T_INSERT::ole
INSERT INTO OLE_CAT_SHVLG_SCHM_T (OBJ_ID,ROW_ACT_IND,SHVLG_SCHM_CD,SHVLG_SCHM_ID,SHVLG_SCHM_NM,SRC,SRC_DT,VER_NBR) VALUES ('fb8119a1-eafb-4150-ad72-0f9c67e7d7df','Y','SIX',8,'Shelved separately','MFHD 852 1st Indicator: http://www.loc.gov/marc/holdings/hd852.html',TO_DATE( '20120322000000', 'YYYYMMDDHH24MISS' ),1)
/

INSERT INTO OLE_CAT_SHVLG_SCHM_T (OBJ_ID,ROW_ACT_IND,SHVLG_SCHM_CD,SHVLG_SCHM_ID,SHVLG_SCHM_NM,SRC,SRC_DT,VER_NBR) VALUES ('fb8119a1-eafb-4150-ad72-0f9c67e7d7dg','Y','SEVEN',9,'Source specified in subfield $2','MFHD 852 1st Indicator: http://www.loc.gov/marc/holdings/hd852.html',TO_DATE( '20120322000000', 'YYYYMMDDHH24MISS' ),1)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_CAT_SHVLG_SCHM_T_INSERT', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 4, '7:89c9e831825ee25366aadf60c3e2ab7e', 'sql (x2)', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::PUR_REQS_T_BILL_PHN_NBR_NULL::ole
ALTER TABLE PUR_REQS_T MODIFY BILL_PHN_NBR NULL
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('PUR_REQS_T_BILL_PHN_NBR_NULL', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 5, '7:0c66f770f71c621ab63c8ba4c67c8929', 'dropNotNullConstraint', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::ALERT_OLE_PUR_POBA_T_ORACLE::ole
ALTER TABLE OLE_PUR_POBA_T MODIFY UPLD_FILE_NM varchar2(120)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('ALERT_OLE_PUR_POBA_T_ORACLE', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 6, '7:1ed9edd50adf36556f6b8346b1053e79', 'sql', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::OLE_DS_HOLDINGS_T_ADD::ole
ALTER TABLE OLE_DS_HOLDINGS_T ADD MATERIALS_SPECIFIED VARCHAR2(100)
/

ALTER TABLE OLE_DS_HOLDINGS_T ADD FIRST_INDICATOR VARCHAR2(20)
/

ALTER TABLE OLE_DS_HOLDINGS_T ADD SECOND_INDICATOR VARCHAR2(20)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_DS_HOLDINGS_T_ADD', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 7, '7:177bc16fdd76c1e15e186a9d2978d739', 'addColumn', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::PUR_PO_ITM_TOT_ENC_V_ALTER::ole
CREATE OR REPLACE VIEW ole.PUR_PO_ITM_TOT_ENC_V AS (SELECT A.FDOC_NBR AS FDOC_NBR,
            SUM(A.ITM_OSTND_ENC_AMT) AS TOTAL_ENCUMBRANCE,
            SUM(A.ITM_INV_TOT_QTY) AS INVOICED_QUANTITY
            FROM PUR_PO_ITM_T A group by A.FDOC_NBR)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('PUR_PO_ITM_TOT_ENC_V_ALTER', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 8, '7:837fbcb817bc5be9220ce4d924bbec3d', 'createView', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::PUR_PO_V_ALTER::ole
CREATE OR REPLACE VIEW ole.PUR_PO_V AS (SELECT P.AP_PUR_DOC_LNK_ID,
            P.PO_ID,
            P.PO_CUR_IND,
            P.FDOC_NBR,
            P.OBJ_ID,
            D.APP_DOC_STAT,
            P.RECUR_PMT_TYP_CD,
            P.PO_VNDR_CHC_CD,
            P.PO_END_DT,
            E.TOTAL_ENCUMBRANCE,
            SUMMATION.TOTAL_AMOUNT,
            P.PO_CRTE_DT AS PO_CRTE_DT,
            E.INVOICED_QUANTITY AS INVOICED_QUANTITY
            FROM
            PUR_PO_T P,
            KREW_DOC_HDR_T D,
            PUR_PO_ITM_TOT_ENC_V E,
            PUR_PO_ITM_TOT_AMT_V SUMMATION
            WHERE
            P.FDOC_NBR = E.FDOC_NBR
            AND
            P.FDOC_NBR=D.DOC_HDR_ID
            AND P.FDOC_NBR = SUMMATION.FDOC_NBR)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('PUR_PO_V_ALTER', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 9, '7:595eb36ec9c259f43ed267e820a89ff3', 'createView', '', 'EXECUTED', '3.2.0')
/

-- Changeset org/kuali/ole/3.0/db.changelog-20160208.xml::ADD_LOCN_POPUP::ole
ALTER TABLE OLE_CRCL_DSK_LOCN_T ADD LOCN_POPUP VARCHAR2(1)
/

ALTER TABLE OLE_CRCL_DSK_LOCN_T ADD LOCN_POPUP_MSG VARCHAR2(4000)
/

ALTER TABLE OLE_CRCL_DSK_LOCN_T MODIFY LOCN_POPUP DEFAULT 'N'
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('ADD_LOCN_POPUP', 'ole', 'org/kuali/ole/3.0/db.changelog-20160208.xml', SYSTIMESTAMP, 10, '7:abc8f571e596b27bddbb33333e833f37', 'addColumn, addDefaultValue', '', 'EXECUTED', '3.2.0')
/

-- Release Database Lock
-- Release Database Lock

<%--
 Copyright 2006 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.opensource.org/licenses/ecl2.php
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/jsp/sys/kfsTldHeader.jsp"%>

<c:set var="advanceDepositAttributes"
	value="${DataDictionary['AdvanceDepositDocument'].attributes}" />
<c:set var="readOnly"
	value="${!KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />

<kul:documentPage showDocumentInfo="true"
	htmlFormAction="financialAdvanceDeposit"
	documentTypeName="AdvanceDepositDocument" renderMultipart="true"
	showTabButtons="true">
	<sys:hiddenDocumentFields />
	<sys:documentOverview editingMode="${KualiForm.editingMode}" />
	<SCRIPT type="text/javascript">
	    <!--
	        function submitForm() {
	            document.forms[0].submit();
	        }
	    //-->
	</SCRIPT>
	
	<fp:advanceDeposits editingMode="${KualiForm.editingMode}" />
	<kul:tab tabTitle="Accounting Lines" defaultOpen="true" tabErrorKey="${OLEConstants.ACCOUNTING_LINE_ERRORS},newSourceLine*">
		<sys-java:accountingLines>
			<sys-java:accountingLineGroup newLinePropertyName="newSourceLine" collectionPropertyName="document.sourceAccountingLines" collectionItemPropertyName="document.sourceAccountingLine" attributeGroupName="source" />
		</sys-java:accountingLines>
	</kul:tab>
	
	<c:set var="readOnly" value="${!KualiForm.documentActions[Constants.KUALI_ACTION_CAN_EDIT]}" />
  	<fp:capitalAccountingLines readOnly="${readOnly}"/>
  	
	<c:if test="${KualiForm.capitalAccountingLine.canCreateAsset}">
		<fp:capitalAssetCreateTab readOnly="${readOnly}"/>
	</c:if>
			
	<fp:capitalAssetModifyTab readOnly="${readOnly}"/>  
			
	<gl:generalLedgerPendingEntries />
	<kul:notes />
	<kul:adHocRecipients />
	<kul:routeLog />
	<kul:panelFooter />
	<sys:documentControls
		transactionalDocument="${documentEntry.transactionalDocument}" />
</kul:documentPage>

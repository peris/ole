getMaintenanceDataForFieldType = function (title, fieldType, $scope, $http) {
    if ((fieldType == 'Call Number Type' || fieldType == 'Holdings Call Number Type') && $scope.callNumberTypeValues == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_CALLNUMBER_TYPES).success(function (data) {
            $scope.callNumberTypeValues = data;
        });
    } else if (fieldType == 'Item Type' && $scope.itemTypeValues == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_ITEM_TYPES).success(function (data) {
            $scope.itemTypeValues = data;
        });
    } else if (fieldType == 'Item Status' && $scope.itemStatusValues == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_ITEM_STATUS).success(function (data) {
            $scope.itemStatusValues = data;
        });
    } else if (fieldType == 'Donor Code' && $scope.donorCodeValues == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_DONOR_CODES).success(function (data) {
            $scope.donorCodes = data;
        });
    } else if ((fieldType == 'Location Level1' || fieldType == 'Holdings Location Level1') && $scope.locationLevel1Values == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_LOCATIONS, {params: {"levelId": 1}}).success(function (data) {
            $scope.locationLevel1Values = data;
        });
    } else if ((fieldType == 'Location Level2' || fieldType == 'Holdings Location Level2') && $scope.locationLevel2Values == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_LOCATIONS, {params: {"levelId": 2}}).success(function (data) {
            $scope.locationLevel2Values = data;
        });
    } else if ((fieldType == 'Location Level3' || fieldType == 'Holdings Location Level3') && $scope.locationLevel3Values == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_LOCATIONS, {params: {"levelId": 3}}).success(function (data) {
            $scope.locationLevel3Values = data;
        });
    } else if ((fieldType == 'Location Level4' || fieldType == 'Holdings Location Level4') && $scope.locationLevel4Values == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_LOCATIONS, {params: {"levelId": 4}}).success(function (data) {
            $scope.locationLevel4Values = data;
        });
    } else if ((fieldType == 'Location Level5' || fieldType == 'Holdings Location Level5') && $scope.locationLevel5Values == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_LOCATIONS, {params: {"levelId": 5}}).success(function (data) {
            $scope.locationLevel5Values = data;
        });
    } else if (fieldType == 'Bib Status' && $scope.bibStatuses == undefined) {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_BIB_STATUS).success(function (data) {
            $scope.bibStatuses = data;
        });
    } else if (fieldType == 'Chart Code'
        || fieldType == 'Org Code'
        || fieldType == 'Item Chart Code'
        || fieldType == 'Contract Manager'
        || fieldType == 'Order Type'
        || fieldType == 'Funding Source'
        || fieldType == 'Delivery Campus Code'
        || fieldType == 'Building Code'
        || fieldType == 'Building Room Number'
        || fieldType == 'Vendor Choice'
        || fieldType == 'Cost Source'
        || fieldType == 'Default Location'
        || fieldType == 'Vendor Number'
        || fieldType == 'Vendor Alias Name'
        || fieldType == 'Acquisition Unit\'s Vendor account / Vendor Info Customer #'
        || fieldType == 'Requestor Name'
        || fieldType == 'Account Number'
        || fieldType == 'Object Code'
        || fieldType == 'Request Source'
        || fieldType == 'Assign To User'
        || fieldType == 'Method Of PO Transmission'
        || fieldType == 'Recurring Payment Type'
        || fieldType == 'Fund Code') {
        $http.get(OLENG_CONSTANTS.PROFILE_GET_ORDER_FIELD_VALUES, {params: {"fieldName": fieldType}}).success(function (data) {
            if (title == 'Constants And Defaults') {
                $scope.constantAndDefaultFieldValues = data;
            } else if (title == 'Data Mappings') {
                $scope.dataMappingFieldValues = data;
            }
        });
    }
};
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="buildingListURL" value="/admin/building-list"/>
<html>
<head>
    <title>Danh sách toà nhà</title>
</head>
<body>

<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
            </script>

            <ul class="breadcrumb">
                <li>
                    <i class="ace-icon fa fa-home home-icon"></i>
                    <a href="#">Home</a>
                </li>
                <li class="active">Dashboard</li>
            </ul><!-- /.breadcrumb -->
        </div>

        <div class="page-content">
            <div class="row">
                <div class="col-xs-12">
                    <div class="widget-box">
                        <div class="widget-header">
                            <h4 class="widget-title">Tìm kiếm</h4>

                            <div class="widget-toolbar">
                                <a href="#" data-action="collapse">
                                    <i class="ace-icon fa fa-chevron-up"></i>
                                </a>

                                <a href="#" data-action="close">
                                    <i class="ace-icon fa fa-times"></i>
                                </a>
                            </div>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main" style="padding-bottom: 50px">
                                <form action="" id="formSearchBuilding">
                                <div class="form-horizontal">
                                    <div class="form-group">
                                        <!-- PAGE CONTENT BEGINS -->
                                        <div class= "col-sm-6">
                                            <div>
                                                <label for="name"><b>Tên tòa nhà</b></label>
                                                <input name="name" type="text" id="name" value="${model.name}" class="form-control" />
                                            </div>
                                        </div>

                                        <div class= "col-sm-6">
                                            <div>
                                                <label for="floorArea"><b>Diện tích sàn</b></label>
                                                <input name="floorArea" type="number" id="floorArea" value="${model.floorArea}" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="district"><b>Quận hiện có</b></label>
                                                </br>
                                                <select class="col-sm-6 form-control" id="district" name="district">
                                                    <option value="">--Chọn quận--</option>
                                                    <c:forEach items="${districtEnums}" var="item">
                                                        <option value="${item.key}" ${item.key == modelSearch.district ? 'selected' : '' }>
                                                                ${item.value}
                                                        </option>
                                                    </c:forEach>

                                                </select>
                                            </div>
                                        </div>
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="ward"><b>Phường</b></label>
                                                <input type="text" name="ward" id="ward" value="${model.ward}" class="form-control" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="street"><b>Đường</b></label>
                                                <input type="text" name="street" value="${model.street}" id="street" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="numberOfBasement"><b>Số tầng hầm</b></label>
                                                <input type="number" name="numberOfBasement" value="${model.numberOfBasement}" id="numberOfBasement" class="form-control" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="direction"><b>Hướng</b></label>
                                                <input type="text" id="direction" name="direction" value="${model.direction}" class="form-control" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="level"><b>Hạng</b></label>
                                                <input type="text" name="level" id="level" value="${model.level}" class="form-control" />
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class= "col-sm-3">
                                            <div>
                                                <label for="areaFrom"><b>Diện tích từ</b></label>
                                                <input name="areaFrom" type="number" id="areaFrom" value="${model.areaFrom}" class="form-control" name="areaRentFrom" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-3">
                                            <div>
                                                <label for="areaTo"><b>Diện tích đến</b></label>
                                                <input name="areaTo" type="number" id="areaTo" class="form-control" value="${model.areaTo}" name="areaRentTo" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-3">
                                            <div>
                                                <label for="rentPriceFrom"><b>Giá thuê từ</b></label>
                                                <input type="number" name="rentPriceFrom" id="rentPriceFrom" value="${model.rentPriceFrom}" class="form-control" name="costRentFrom" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-3">
                                            <div>
                                                <label for="rentPriceTo"><b>Giá thuê đến</b></label>
                                                <input type="number" id="rentPriceTo" name="rentPriceTo" value="${model.rentPriceTo}" class="form-control" name="costRentTo"/>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="managerName"><b>Tên quản lý</b></label>
                                                <input type="text" id="managerName" name="managerName" value="${model.managerName}" class="form-control" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="managerPhone"><b>Điện thoại quản lý</b></label>
                                                <input type="number" id="managerPhone" name="managerPhone" value="${model.managerPhone}" class="form-control" />
                                            </div>
                                        </div>
                                        <div class= "col-sm-4">
                                            <div>
                                                <label for="staffId"><b>Chọn nhân viên phụ trách</b></label>
                                                </br>
                                                <select id="staffId" class="col-sm-8" id="form-field-select-1">
                                                    <option value="" selected>--Chọn nhân viên phụ trách--</option>
                                                    <c:forEach var="item" items="${staffs}">
                                                        <option value="${item.id}" ${item.id == model.staffId ? 'selected' : '' } >
                                                                ${item.fullName}
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>


                                    </div>
                                </form>
                                    <div class="form-group ">
                                        <div class="col-sm-8">
                                            <c:forEach var="item" items="${rentTypesEnums}">

                                                    <label class="checkbox-inline"> ${item.value}
                                                    </label>
                                                    <input type="checkbox" name="rentTypes"
                                                           value="${item.key}" ${fn:contains(fn:join(model.rentTypes,','),item.key) ? 'checked' : '' } />
                                                    <!-- PAGE CONTENT ENDS -->

                                            </c:forEach>
                                        </div>
                                    </div>
                                    <!-- PAGE CONTENT ENDS -->
                                    <div class="form-group">
                                        <div class="pull-left col-sm-4 ">
                                            <button class="btn btn-info btn-white btn-bold" id="btnSearchBuilding">
                                                <i class="fa fa-search" area-hidden="true"></i>
                                                Tìm kiếm
                                            </button>
                                        </div>
                                    </div><!-- /.col -->
                                </div><!-- /.row -->
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- /.row -->
            </br>
            </br>
            <div class="row">
                <div class="col-xs-12">
                    <div class="pull-right">
                        <a title="Thêm tòa nhà" class="btn btn-white btn-info btn-bold"
                           data-toggle="tooltip"
                           href="<c:url value="/admin/building-save"/>"> <i
                                class="fa fa-plus-circle"></i>
                        </a>
                        <button class="btn btn-white btn-info btn-bold"
                                data-toggle="tooltip"
                                title="Xóa tòa nhà" id="btnDeleteBuilding">
                            <i class=" fa fa-trash"></i>
                        </button>
                    </div>
                </div>
            </div><!-- /.row -->

            <div class = "row">
                <div class="col-xs-12">
                    <table id="buildingList"
                           class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>Số Lượng</th>
                            <th>Tên Tòa Nhà</th>
                            <th>Địa chỉ</th>
                            <th>Tên quản lý</th>
                            <th>Số điện thoại quản lý</th>
                            <th>Diện tích sàn</th>
                            <th>Diện tích trống</th>
                            <th>Giá thuê</th>
                            <th>Diện tích thuê</th>
                            <th>Phí dịch vụ</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${searchBuilding}">
                            <tr>
                                <td><input type="checkbox" value="${item.id}" id="checkbox_${item.id}"></td>
                                <td>${item.name}</td>
                                <td>${item.street}</td>
                                <td>${item.managerName}</td>
                                <td>${item.managerPhone}</td>
                                <td>${item.floorArea}</td>
                                <td>${item.rentPrice}</td>
                                <td>${item.waterFee}</td>
                                <td>${item.image}</td>
                                <td>${item.serviceFee}</td>

                                <td>
                                    <button class="btn btn-xs btn-info" data-toggle="tooltip"
                                            title="Giao tòa nhà"
                                            onclick="assignmentBuilding(${item.id})">
                                        <i class="fa fa-eye" aria-hidden="true" id="button"></i>
                                    </button>
                                    <a  href="<c:url value="/admin/building-edit-${item.id}"/>" class="btn btn-xs btn-info"
                                        data-toggle="tooltip" title="Cập nhật tòa nhà"> <i
                                            class="fa fa-pencil" aria-hidden="true"></i>
                                    </a>


                                </td>

                            </tr>
                        </c:forEach>
                        </tbody>

                    </table>
                    <nav aria-label="Page navigation" style="margin-left: 340px;">
                        <ul class="pagination" id="pagination"></ul>
                    </nav>


                </div>
            </div>
            </div>
        </div><!-- /.page-content -->
    </div>
</div><!-- /.main-content -->
<script>
    function assignmentBuilding(buildingId) {
        openModalAssignmentBuilding ();
        $('#buildingId').val(buildingId);
        console.log($('#buildingId').val());
    }
    function openModalAssignmentBuilding(){
        $('#assignmentBuildingModal').modal();
    }
    $('#btnAssignBuilding').click(function (e) {
        e.preventDefault();
        var data = {};
        var staffs = [];
        data['buildingId'] = $('#buildingId').val();
        var staffs = $('#staffList').find('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        data['staffs'] = staffs;
        assignStaff(data);
    });
    function assignStaff(data){
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/api-user-assignment",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (response) {
                console.log('Success')
            },
            error: function (response) {
                console.log('Failed'),
                    console.log(response)
            }
        });
    }
    $('#btnSearch').click(function (e) {
        e.preventDefault();
        $('#listForm').submit();
    });
    $('#btnDeleteBuilding').click(function (e) {
        e.preventDefault();
        var data = {};
        data['buildingId'] = $('#buildingId').val();
        var buildingIds = $('#buildingList').find('tbody input[type=checkbox]:checked').map(function () {
            return $(this).val();
        }).get();
        data['buildingIds'] = buildingIds;
        deleteBuilding(data);
    });
    function deleteBuilding(data){
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8080/api-building",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (response) {
                console.log('Success')
            },
            error: function (response) {
                console.log('Failed'),
                    console.log(response)
            }
        });
    }
    $("#btnSearchBuilding").click(function (e) {
        e.preventDefault();
        $("#formSearchBuilding").submit();
    });
</script>


</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="buildingListURL" value="/admin/building-list"/>
<c:url var="buildingApiUrl" value="/api/building"/>
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
                                                    <label for="areaRentFrom"><b>Diện tích từ</b></label>
                                                    <input name="areaRentFrom" type="number" id="areaRentFrom" value="${model.areaRentFrom}" class="form-control"  />
                                                </div>
                                            </div>
                                            <div class= "col-sm-3">
                                                <div>
                                                    <label for="areaRentTo"><b>Diện tích đến</b></label>
                                                    <input name="areaRentTo" type="number" id="areaRentTo" class="form-control" value="${model.areaRentTo}"  />
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
                                            <div class="col-sm-4">
                                                <div>
                                                    <label for="staffId"><b>Chọn nhân viên phụ trách</b></label>
                                                    </br>
                                                    <select class="col-sm-8" id="staffId" name="staffId">
                                                        <option value="">-- Chọn nhân viên phụ trách --</option>
                                                        <c:forEach items="${staffmaps}" var="item">
                                                            <option value="${item.key}">
                                                                    ${item.value}
                                                            </option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>


                                    </div>

                                <div class="form-group ">
                                    <div class="col-sm-8">
                                        <c:forEach var="item" items="${rentTypesEnums}">
                                            <div class="col-xs-12 col-sm-2">
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" value="${item.key}" id="rentTypes"
                                                           name="rentTypes"> <b> ${item.value} </b>
                                                </label>
                                            </div>
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
                                </form>
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
                        <th>CheckBox</th>
                        <th>Số Lượng</th>
                        <th>Tên Tòa Nhà</th>
                        <th>Địa chỉ</th>
                        <th>Tên quản lý</th>
                        <th>Số điện thoại quản lý</th>
                        <th>Diện tích sàn</th>
                        <th>Diện tích trống</th>
                        <th>Hướng</th>
                        <th>Giao Dịch</th>
                        <

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

                            <td>${item.direction}</td>


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
<div class="modal fade" id="assignmentBuildingModal" role="dialog">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Danh Sách Nhân Viên</h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered" id="staffList">
                    <thead>
                    <tr>
                        <th>Chọn nhân viên</th>
                        <th>Tên nhân viên</th>
                    </tr>
                    </thead>
                    <tbody>

                    </tbody>
                </table>
                <input type="hidden" id="buildingId" name="buildingId" value="">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="btnAssignBuilding">Giao
                    tòa nhà
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Đóng</button>
            </div>
        </div>
    </div>
</div>
<script>

    function assignmentBuilding(buildingId) {
        openModalAssignmentBuilding ();
        $('#buildingId').val(buildingId);
        showStaffAssignment(buildingId);// truyen id vao #buildingId
    }
    function openModalAssignmentBuilding(){
        $('#assignmentBuildingModal').modal();
    }
    function showStaffAssignment(id) {
        $.ajax({
            type: "GET",
            url: "${buildingApiUrl}/" + id + "/staffs",
            dataType: "json",
            success: function (response) {
                var html = '';
                $.each(response, function (index, staffOutput) {
                    html += '<tr>';
                    html += '<td><input type="checkbox" value="' + staffOutput.id + '" ' + staffOutput.checked + '></td>';
                    html += '<td>' + staffOutput.userName + '</td>';
                    html += '</tr>';
                });
                $('#staffList tbody').html(html);
            },
            error: function (response) {
                console.log(response);
            }
        });
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
            url: "${buildingApiUrl}/assignBuilding",
            data: JSON.stringify(data),
            contentType: "application/json",
            traditional: true,
            success: function (response) {
                if (response == "success") {
                    console.log("Thành công", "Sản phẩm đã được lưa", "success");
                }
            },
            error: function (response) {
                console.log(response);
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
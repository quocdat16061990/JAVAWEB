<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="buildingApiUrl" value="/api/buildings"/>

<c:url var="buildingEditURL" value="/admin/building-edit"/>
<html>
<head>
    <title>Chỉnh sửa toàn nhà</title>
</head>
<body>
<div class="main-content">
    <div class="main-content-inner">
        <div class="breadcrumbs" id="breadcrumbs">
            <script type="text/javascript">
                try {
                    ace.settings.check('breadcrumbs', 'fixed')
                } catch (e) {
                }
            </script>

            <ul class="breadcrumb">
                <li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">Trang Chủ </a>
                </li>
                <li class="active">Danh Sách Tòa Nhà</li>
            </ul>
            <!-- /.breadcrumb -->


        </div>

        <div class="page-content">
            <div class="ace-settings-container" id="ace-settings-container">


                <div class="ace-settings-box clearfix" id="ace-settings-box">
                    <div class="pull-left width-50">
                        <div class="ace-settings-item">
                            <div class="pull-left">
                                <select id="skin-colorpicker" class="hide">
                                    <option data-skin="no-skin" value="#438EB9">#438EB9</option>
                                    <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                                    <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                                    <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                                </select>
                            </div>
                            <span>&nbsp; Choose Skin</span>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-navbar"/> <label class="lbl"
                                                                     for="ace-settings-navbar"> Fixed Navbar</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-sidebar"/> <label class="lbl"
                                                                      for="ace-settings-sidebar"> Fixed Sidebar</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-breadcrumbs"/> <label class="lbl"
                                                                          for="ace-settings-breadcrumbs"> Fixed
                            Breadcrumbs</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-rtl"/> <label class="lbl"
                                                                  for="ace-settings-rtl"> Right To Left (rtl)</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-add-container"/> <label class="lbl"
                                                                            for="ace-settings-add-container"> Inside <b>.container</b>
                        </label>
                        </div>
                    </div>
                    <!-- /.pull-left -->

                    <div class="pull-left width-50">
                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-hover"/> <label class="lbl"
                                                                    for="ace-settings-hover"> Submenu on Hover</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-compact"/> <label class="lbl"
                                                                      for="ace-settings-compact"> Compact
                            Sidebar</label>
                        </div>

                        <div class="ace-settings-item">
                            <input type="checkbox" class="ace ace-checkbox-2"
                                   id="ace-settings-highlight"/> <label class="lbl"
                                                                        for="ace-settings-highlight"> Alt. Active
                            Item</label>
                        </div>
                    </div>
                    <!-- /.pull-left -->
                </div>
                <!-- /.ace-settings-box -->
            </div>
            <!-- /.ace-settings-container -->

            <div class="page-header">
                <h1>
                    Danh Sách Tòa Nhà <small> <i
                        class="ace-icon fa fa-angle-double-right"></i> Edit Building
                </small>
                </h1>
            </div>
            <!-- /.page-header -->

            <div class="row">

                <form class="form-horizontal" role="form" id="formEdit">
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="name">Tên
                            tòa nhà </label>
                        <div class="col-sm-9">
                            <input type="text" id="name" name="name" class="form-control"
                                   value="${building.name}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="district">Quận</label>
                        <div class="col-sm-9">
                            <select id="district" name="district" class="form-control">
                                <option value="" selected>Choose...</option>
                                <c:forEach var="item" items="${districtsEnums}">
                                    <option value="${item.key}" ${item.key == building.district ? 'selected' : '' }> ${item.value}</option>
                                </c:forEach>

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="numberOfBasement"> Số tầng hầm </label>
                        <div class="col-sm-9">
                            <input type="number" id="numberOfBasement"
                                   name="numberOfBasement" class="form-control"
                                   value="${building.numberOfBasement}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="floorArea"> Diện tích sàn </label>
                        <div class="col-sm-9">
                            <input type="number" id="floorArea" name="floorArea"
                                   class="form-control" value="${building.floorArea}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="rentPrice"> Giá thuê </label>

                        <div class="col-sm-9">
                            <input type="number" id="rentPrice" name="rentPrice"
                                   class="form-control" value="${building.rentPrice}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right" for="ward">
                            Phường </label>

                        <div class="col-sm-9">
                            <input type="text" id="ward" name="ward" class="form-control"
                                   value="${building.ward}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="street">Đường</label>

                        <div class="col-sm-9">
                            <input type="text" id="street" name="street" class="form-control"
                                   value="${building.street}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="areaRent"> Diện tích </label>

                        <div class="col-sm-9">
                            <input type="text" id="areaRent" name="areaRent" placeholder="VD: 100,200,300"
                                   class="form-control" value="${building.areaRent}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="managerName"> Tên quản lý </label>

                        <div class="col-sm-9">
                            <input type="text" id="managerName" name="managerName"
                                   class="form-control" value="${building.managerName}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="managerPhone"> Điện thoại quản lý </label>

                        <div class="col-sm-9">
                            <input type="number" id="managerPhone" name="managerPhone"
                                   class="form-control" value="${building.managerPhone}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="structure"> Cấu trúc tòa nhà </label>

                        <div class="col-sm-9">
                            <input type="text" id="structure" name="structure"
                                   class="form-control" value="${building.structure}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="direction"> Hướng </label>

                        <div class="col-sm-9">
                            <input type="text" id="direction" name="direction"
                                   class="form-control" value="${building.direction}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="level"> Level </label>

                        <div class="col-sm-9">
                            <input type="text" id="level" name="level"
                                   class="form-control" value="${building.level}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="rentPriceDescription"> Mô tả giá thuê </label>

                        <div class="col-sm-9">
                            <input type="text" id="rentPriceDescription" name="rentPriceDescription"
                                   class="form-control" value="${building.rentPriceDescription}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="serviceFee"> Phí phục vụ </label>

                        <div class="col-sm-9">
                            <input type="text" id="serviceFee" name="serviceFee"
                                   class="form-control" value="${building.serviceFee}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="carFee"> Phí xe hơi </label>

                        <div class="col-sm-9">
                            <input type="text" id="carFee" name="carFee"
                                   class="form-control" value="${building.carFee}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="motoFee"> Phí xe máy</label>

                        <div class="col-sm-9">
                            <input type="text" id="motoFee" name="motoFee"
                                   class="form-control" value="${building.motoFee}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="overTimeFee"> Phí ngoài giờ </label>

                        <div class="col-sm-9">
                            <input type="text" id="overTimeFee" name="overTimeFee"
                                   class="form-control" value="${building.overTimeFee}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="waterFee"> Phí nước </label>

                        <div class="col-sm-9">
                            <input type="text" id="waterFee" name="waterFee"
                                   class="form-control" value="${building.waterFee}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="electricityFee"> Phí điện </label>

                        <div class="col-sm-9">
                            <input type="text" id="electricityFee" name="electricityFee"
                                   class="form-control" value="${building.electricityFee}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="deposit"> Tiền đặt trước </label>

                        <div class="col-sm-9">
                            <input type="text" id="deposit" name="deposit"
                                   class="form-control" value="${building.deposit}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="payment"> Trả trước </label>

                        <div class="col-sm-9">
                            <input type="text" id="payment" name="payment"
                                   class="form-control" value="${building.payment}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="rentTime"> Thời gian thuê </label>

                        <div class="col-sm-9">
                            <input type="text" id="rentTime" name="rentTime"
                                   class="form-control" value="${building.rentTime}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="decorationTime"> Thời gian trang trí </label>

                        <div class="col-sm-9">
                            <input type="text" id="decorationTime" name="decorationTime"
                                   class="form-control" value="${building.decorationTime}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="brokerAgeFee"> Phí môi trường </label>

                        <div class="col-sm-9">
                            <input type="text" id="brokerAgeFee" name="brokerAgeFee"
                                   class="form-control" value="${building.brokerAgeFee}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="note"> Ghi chú </label>

                        <div class="col-sm-9">
                            <input type="text" id="note" name="note"
                                   class="form-control" value="${building.note}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="linkOfBuilding"> Link </label>

                        <div class="col-sm-9">
                            <input type="text" id="linkOfBuilding" name="linkOfBuilding"
                                   class="form-control" value="${building.linkOfBuilding}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="map">Map </label>

                        <div class="col-sm-9">
                            <input type="text" id="map" name="map"
                                   class="form-control" value="${building.map}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"
                               for="image"> Image </label>

                        <div class="col-sm-9">
                            <input type="file" id="image" name="image"
                                   class="form-control" value=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"> Loại tòa nhà </label>
                        <div class="col-sm-9">
                            <c:forEach var="rents" items="${rentTypesEnums}">
                                <label class="checkbox-inline" for="rentTypes">${rents.value}</label>
                                <input type="checkbox" name="rentTypes" id="rentTypes"
                                       value="${rents.key}" ${fn:contains(building.type,rents.key) ? 'checked' : '' }>
                            </c:forEach>

                        </div>
                    </div>
                    <input type="hidden" value="${building.id}" name="id">

                    <div class="clearfix form-actions">
                        <div class="col-md-offset-3 col-md-9">
                            <c:if test="${building.id != null}">
                                <button class="btn btn-info" type="submit" id="btnAddBuilding">
                                    <i class="ace-icon fa fa-check bigger-110"></i> Cập Nhật
                                </button>
                                <a href="<c:url value="/admin/building-list?page=1&limit=5" />">

                                    <button class="btn" type="button">
                                        <i class="ace-icon fa fa-undo bigger-110"></i> Thoát
                                    </button>
                                </a>
                            </c:if>
                            <c:if test="${building.id == null}">
                                <button class="btn btn-info" type="submit" id="btnAddBuilding">
                                    <i class="ace-icon fa fa-check bigger-110"></i> Thêm
                                </button>
                                <button class="btn" type="reset">
                                    <i class="ace-icon fa fa-undo bigger-110"></i> Reset
                                </button>
                            </c:if>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- /.page-content -->
    </div>
</div>

<div class="footer">
    <div class="footer-inner">
        <div class="footer-content">
						<span class="bigger-120">
							<span class="blue bolder">Ace</span>
							Application &copy; 2013-2014
						</span>

            &nbsp; &nbsp;
            <span class="action-buttons">
							<a href="#">
								<i class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
							</a>

							<a href="#">
								<i class="ace-icon fa fa-rss-square orange bigger-150"></i>
							</a>
						</span>
        </div>
    </div>
</div>
<script>
    $("#btnAddBuilding").click(function (event) {
        event.preventDefault();
        var rentTypes = [];
        var formData = $("#formEdit").serializeArray();
        $.each(formData, function (i, v) {
            if (v.name == 'rentTypes') {
                rentTypes.push(v.value)
            } else {
                data["" + v.name + ""] = v.value;
            }

        });
        data['rentTypes'] = rentTypes;
        if (data['id'] != '' || data['id'] > 0) {
            editBuilding(data);
        } else {
            addBuilding(data);
        }
    });
    function addBuilding(data) {
        $.ajax({
            url: '${buildingApiUrl}',
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                swal("Thành công", "Sản phẩm đã được lưa", "success");
            },
            error: function (res) {
                swal("Chưa Thực Hiện Xóa", "Dữ liệu chưa được lưu", "error");
            }
        });
    }

    function editBuilding(data) {
        $.ajax({
            url: '${buildingApiUrl}',
            type: 'PUT',
            dataType: 'json',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (res) {
                swal("Thành công", "Sản phẩm đã được cập nhật", "success");
            },
            error: function (res) {
                swal("Chưa Thực Hiện Xóa", "Dữ liệu chưa được cập nhật", "error");
            }
        });
    }
</script>
</body>
</html>

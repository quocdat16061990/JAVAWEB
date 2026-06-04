<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/taglib.jsp" %>
<c:url var="buildingAPI" value="/api/building"/>
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
                    <form class="form-horizontal" role="form" id="formEdit">
                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="name"> Tên toà nhà</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="name" name="name"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="district">Quận: </label>
                            <div class="col-sm-9">
                                <select name="district" id="district" class="custom-select mb-3">
                                    <option selected>--- Chọn quận ---</option>
                                    <option value="QUAN_1">Quận 1</option>
                                    <option value="QUAN_2">Quận 2</option>
                                    <option value="QUAN_3">Quận 3</option>
                                    <option value="QUAN_4">Quận 4</option>
                                    <option value="QUAN_5">Quận 5</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="ward"> Phường</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="ward" name="ward"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="street"> Đường</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="street" name="street"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="structure"> Kết cấu</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="structure" name="structure"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="numberOfBasement"> Số tầng
                                hầm</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="number" id="numberOfBasement"
                                       name="numberOfBasement" value=""/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="floorArea"> Diện tích
                                sàn</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="number" id="floorArea" name="floorArea"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="direction"> Hướng</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="direction" name="direction"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="level"> Hạng</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="level" name="level"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="rentArea"> Diện tích
                                thuê</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="rentArea" name="rentArea"
                                       placeholder="100,200,300,..."/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="rentAreaDescription"> Mô tả diện
                                tích</label>
                            <div class="col-sm-9">
                            <textarea class="form-control" rows="5" id="rentAreaDescription"
                                      name="rentAreaDescription"></textarea>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="rentCost"> Giá thuê</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="number" id="rentCost" name="rentCost"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="costDescription"> Mô tả
                                giá</label>
                            <div class="col-sm-9">
                            <textarea class="form-control" rows="5" id="costDescription"
                                      name="costDescription"></textarea>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="serviceCost"> Phí dịch
                                vụ</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="serviceCost"
                                       name="serviceCost"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="carCost"> Phí ô tô</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="carCost" name="carCost"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="motorCost"> Phí mô tô</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="motorCost" name="motorCost"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="overtimeCost"> Phí ngoài
                                giờ</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="overtimeCost" name="overtimeCost"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="electricBill"> Tiền điện</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="electricBill" name="electricBill"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="deposit"> Đặt cọc</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="deposit" name="deposit"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="payment"> Thanh toán</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="payment" name="payment"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="timeRent"> Thời hạn thuể</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="timeRent" name="timeRent"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="timeDecorator"> Thời gian trang
                                trí </label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="timeDecorator" name="timeDecorator"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="managerName"> Tên quản
                                lý</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="managerName" name="managerName"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="managerPhone"> SĐT quản
                                lý</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="text" id="managerPhone" name="managerPhone"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="brokeragetee"> Phí môi
                                giới</label>
                            <div class="col-sm-9">
                                <input class="form-control" type="number" id="brokeragetee" name="brokeragetee"/>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right"> Loại toà nhà</label>
                            <div class="col-sm-9">
                                <div class="form-check">
                                    <label class="form-check-label">
                                        <input type="checkbox" class="form-check-input" name="buildingTypes"
                                               value="TANG_TRET">Tầng trệt
                                    </label>
                                </div>
                                <div class="form-check">
                                    <label class="form-check-label">
                                        <input type="checkbox" class="form-check-input" name="buildingTypes"
                                               value="NGUYEN_CAN">Nguyên căn
                                    </label>
                                </div>
                                <div class="form-check">
                                    <label class="form-check-label">
                                        <input type="checkbox" class="form-check-input" name="buildingTypes"
                                               value="NOI_THAT">Nội thất
                                    </label>
                                </div>
                            </div>
                        </div>

                        <div class="form-group col-sm-12">
                            <label class="col-sm-3 control-label no-padding-right" for="note">Ghi chú</label>
                            <div class="col-sm-9">
                                <textarea class="form-control" rows="5" id="note" name="note"></textarea>
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-9" style="text-align: center;">
                                <button type="button" class="btn btn-primary" id="btnAddbuilding">Thêm toà
                                    nhà
                                </button>
                                <button type="button" class="btn btn-primary" id="">Huỷ</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Assignment Building Modal -->
<div id="assignmentBuildingModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Danh sách nhân viên</h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Chọn nhân viên</th>
                        <th>Tên nhân viên</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><input type="checkbox" value="2" id="checkbox_2"></td>
                        <td>Nguyễn Văn B</td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" value="3" id="checkbox_3"></td>
                        <td>Nguyễn Văn C</td>
                    </tr>
                    <tr>
                        <td><input type="checkbox" value="4" id="checkbox_4"></td>
                        <td>Nguyễn Văn D</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="btnAssignBuilding">Giao toà nhà</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>

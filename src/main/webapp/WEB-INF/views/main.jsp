<%--
  Created by IntelliJ IDEA.
  User: RAYANT
  Date: 26.01.2016
  Time: 17:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Todo list</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/resources/css/bootstrap.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-4">
            <h2 align="center">USERS <i class="fa fa-plus " data-toggle="modal" data-target="#myModal"></i></h2>
            <%--<div class="form-group">--%>
            <%--<input class="form-control" id="user_search" type="text">--%>
            <%--</div>--%>
            <br>
            <div>
                <ul class="list-group" id="user_list"></ul>
            </div>
        </div>
        <div class="col-md-8">
            <div class="row">
                <h2 align="center">TASKS <i class="fa fa-plus" data-toggle="modal" data-target="#addTaskModal"></i></h2>
                <%--<div class="form-group">--%>
                <%--<input class="form-control" id="task_search" type="text">--%>
                <%--</div>--%>
            </div>
            <br>
            <div class="row">
                <div class="col-sm-4">
                    <div class="card card-block">
                        <h4 class="card-title">Card No 1</h4>
                        <p class="card-text">The Size of the Card is 4 cells in Medium Devices.</p>
                        <button type="button" class="btn btn-primary btn-sm">
                            <i class="fa fa-pencil-square-o"></i></button>
                        <button type="button" class="btn btn-danger btn-sm">
                            <i class="fa fa-times"> </i></button>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="card card-block">
                        <h4 class="card-title">Card No 2</h4>
                        <p class="card-text">The Size of the Card is 3 Cells in Medium Devices.</p>
                        <button type="button" class="btn btn-primary btn-sm">
                            <i class="fa fa-pencil-square-o"></i></button>
                        <button type="button" class="btn btn-danger btn-sm">
                            <i class="fa fa-times"> </i></button>
                    </div>
                </div>
                <div class="col-sm-4">
                    <div class="card card-block">
                        <h4 class="card-title">Card No 3</h4>
                        <p class="card-text">The Size of the Card is 3 Cells in Medium Devices</p>
                        <button type="button" class="btn btn-primary btn-sm">
                            <i class="fa fa-pencil-square-o"></i></button>
                        <button type="button" class="btn btn-danger btn-sm">
                            <i class="fa fa-times"> </i></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title">Add user</h4>
                </div>
                <div class="modal-body">
                    <p><input class="form-control input-sm" name="firstName" placeholder="First name" type="text"></p>
                    <p><input class="form-control input-sm" name="lastName" placeholder="Last name" type="text"></p>
                </div>
                <div class="modal-footer">
                    <button id="add_user" type="button" class="btn btn-success" data-dismiss="modal">Add</button>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <!-- Modal -->
        <div class="modal fade" id="addTaskModal" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add task</h4>
                    </div>
                    <div class="modal-body">
                        <p><input class="form-control input-sm" name="Title" placeholder="Title" type="text"></p>
                        <p><input class="form-control input-sm" name="Date" placeholder="11.12.14" type="text"></p>
                        <p><input class="form-control input-sm" name="Comment" placeholder="Comment" type="text"></p>
                    </div>
                    <div class="modal-footer">
                        <button id="add_task" type="button" class="btn btn-success" data-dismiss="modal">Add</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

<script src='/resources/js/xls.core.min.js'></script>
<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/bootstrap.js"></script>
<script>

    $(document).ready(function () {
        getUsers();
    });

    var getUsers = function () {
        $.ajax({
            type: "GET",
            url: "/api/users/",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            statusCode: {
                200: function (data) {
                    createUsersList(data);
                }
            }
        });
    }

    var createUsersList = function (users) {
        document.getElementById("user_list").innerHTML = "";
        users.forEach(function (user) {
            $('#user_list').append('<li class="list-group-item">'
                    + '<div class="row">'
                    + '<div class="col-md-7">'
                    + user.firstName + ' '
                    + user.lastName + ' '
                    + '</div>'
                    + '<div class="col-md-5">'
                    + '<button type="button" name="' + user.id + '" class="view btn btn-success btn-xs">view</button>'
                    + '<button type="button" name="' + user.id + '" class="edit btn btn-info btn-xs">edit</button>'
                    + '<button type="button" name="' + user.id + '" class="delete btn btn-danger btn-xs">delete</button>'
                    + '</div>'
                    + '</div>'
                    + '</li>');
        });
        $('.view').click(function () {
            alert('view ' + this.name);
        });
        $('.edit').click(function () {
            alert('edit ' + this.name);
        });
        $('.delete').click(function () {
            $.ajax({
                type: "DELETE",
                url: "/api/users/delete/" + this.name,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                statusCode: {
                    200: function () {
                        getUsers();
                    }
                }
            });
        });
    };

    $('#add_user').click(function () {
        var user = {};
        user.firstName = $("input[name='firstName']").val();
        user.lastName = $("input[name='lastName']").val();

        $.ajax({
            type: "POST",
            url: "/api/users/save",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(user),
            statusCode: {
                200: function () {
                    getUsers();
                }
            }
        });
        $("input[name='firstName']").val('');
        $("input[name='lastName']").val('');

    });
</script>
</html>

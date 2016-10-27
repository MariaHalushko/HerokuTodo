<%--
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
            <div class="col-sm-4" id="open-tasks">
                <h3 align="center">Open</h3>
                <div id="todo"></div>
            </div>
            <div class="col-sm-4" id="in-progress-tasks">
                <h3 align="center">In progress</h3>
                <div id="progress"></div>
            </div>
            <div class="col-sm-4" id="done-tasks">
                <h3 align="center">Done</h3>
                <div id="done"></div>
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
        <div class="modal fade" id="editUser" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title">Add user</h4>
                    </div>
                    <div class="modal-body">
                        <p><input class="form-control input-sm" name="edit_firstName" placeholder="First name"
                                  type="text"></p>
                        <p><input class="form-control input-sm" name="edit_lastName" placeholder="Last name"
                                  type="text"></p>
                        <p><input class="form-control input-sm" style="display:none" name="edit_id" type="text"></p>
                    </div>
                    <div class="modal-footer">
                        <button id="edit_user" type="button" class="btn btn-success" data-dismiss="modal">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="container">
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
    };

    var createUsersList = function (users) {
        document.getElementById("user_list").innerHTML = "";
        users.forEach(function (user) {
            $('#user_list').append('<li class="list-group-item">'
                    + '<div class="row">'
                    + '<div class="col-md-7">'
                    + '<p name="firstName' + user.id + '">'
                    + user.firstName + ' '
                    + '</p>'
                    + '<p name="lastName' + user.id + '">'
                    + user.lastName + ' '
                    + '</p>'
                    + '</div>'
                    + '<div class="col-md-5">'
                    + '<button type="button" name="' + user.id + '" class="view btn btn-success btn-xs">view</button>'
                    + '<button type="button" name="' + user.id + '" class="edit btn btn-info btn-xs" data-toggle="modal" data-target="#editUser">edit</button>'
                    + '<button type="button" name="' + user.id + '" class="delete btn btn-danger btn-xs">delete</button>'
                    + '</div>'
                    + '</div>'
                    + '</li>');
        });

        $('.view').click(function () {
            getUsersTasks(this.name);
        });

        $('.edit').click(function () {
            $("input[name='edit_firstName']").val($("p[name='firstName" + this.name + "']").text());
            $("input[name='edit_lastName']").val($("p[name='lastName" + this.name + "']").text());
            $("input[name='edit_id']").val(this.name);
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

    var getUsersTasks = function (name) {
        $.ajax({
            type: "GET",
            url: "/api/users/" + name + "/tasks",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            statusCode: {
                200: function (data) {
                    console.dir(data);
                    displayUsersTask(data);
                }
            }
        });
    };

    var displayUsersTask = function (tasks) {
        document.getElementById("todo").innerHTML = "";
        document.getElementById("progress").innerHTML = "";
        document.getElementById("done").innerHTML = "";
        tasks.forEach(function (task) {
            if (task.status == "OPEN") {
                displayOpenTask(task);
            } else if (task.status == "IN_PROGRESS") {
                displayInProgressTask(task);
            } else if (task.status == "DONE") {
                displayDoneTask(task);
            }
        });
    };

    var displayOpenTask = function (task) {
        $('#todo').append('<div class="card card-block todo"> '
                + '<input  name = "' + task.id + '" class="form-control input-lg" style="border: none; overflow: auto; outline: none; box-shadow: none;" value="'
                + task.name + '"/>'
                + '<textarea  name = "' + task.id + '" class="form-control" rows = "4" style = "border: none; overflow: auto; outline: none; box-shadow: none;" > '
                + task.comment
                + '</textarea> '
                + '<br >'
                + '<p > <input class="form-control input-sm" style = "display:none" name = "task_id" type = "text" value="'+task.id+'" > </p>'
                + '<button type = "button"  name = "' + task.id + '" class = "remove-task btn btn-danger btn-sm" >'
                + '<i class = "fa fa-times" > </i> </button>'
                + ' </div>');
    };

    var displayInProgressTask = function (task) {
        $('#progress').append('<div class="card card-block in-progress" name = "' + task.id + '" > '
                + '<input name = "' + task.id + '" class="form-control input-lg" style="border: none; overflow: auto; outline: none; box-shadow: none;" value="'
                + task.name + '"/>'
                + '<textarea name = "' + task.id + '" class="form-control" rows = "4" style = "border: none; overflow: auto; outline: none; box-shadow: none;" > '
                + task.comment
                + '</textarea> '
                + '<br >'
                + '<p > <input class="form-control input-sm" style = "display:none" name = "task_id" type = "text" value="'+task.id+'" > </p>'
                + '<button type = "button"  name = "' + task.id + '" class = "remove-task btn btn-danger btn-sm" >'
                + '<i class = "fa fa-times" > </i> </button>'
                + ' </div>');

//        $('.remove-task').click(function () {
//            alert("sdfsdf "+this.name);
//            $.ajax({
//                type: "DELETE",
//                url: "/api/tasks/delete/" +567,
//                contentType: "application/json; charset=utf-8",
//                dataType: "json",
//                statusCode: {
//                    200: function () {
//                        console.dir("delete");
//                        getUsersTasks();
//                    }
//                }
//            });
//        });
    };

    var displayDoneTask = function (task) {
        $('#done').append('<div class="card card-block done" > '
                + '<input name = "' + task.id + '" class="form-control input-lg" style="border: none; overflow: auto; outline: none; box-shadow: none;" value="'
                + task.name + '"/>'
                + '<textarea name = "' + task.id + '" class="form-control" rows = "4" style = "border: none; overflow: auto; outline: none; box-shadow: none;" > '
                + task.comment
                + '</textarea> '
                + '<br>'
                + '<p> <input class="form-control input-sm" style = "display:none" name = "task_id" type = "text" value="'+task.id+'" > </p>'
                + '<button type = "button" name = "' + task.id + '" class = "remove-task btn btn-danger btn-sm" >'
                + '<i class = "fa fa-times" > </i> </button>'
                + ' </div>');
    };


    $('.remove-task').click(function () {
        var taskId = $("input[name='task_id']").val();
            alert("sdfsdf "+ taskId);
            $.ajax({
                type: "DELETE",
                url: "/api/tasks/delete/" +567,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                statusCode: {
                    200: function () {
                        console.dir("delete");
                        getUsersTasks();
                    }
                }
            });
        });

    //    $('.remove-task').click(function () {
//        var taskId = $("input[name='task_id']").val();
//        alert(taskId);
//        $.ajax({
//            type: "DELETE",
//            url: "/api/tasks/delete/" + taskId,
//            contentType: "application/json; charset=utf-8",
//            dataType: "json",
//            statusCode: {
//                200: function () {
//                    console.dir("delete")
//                    getUsersTasks();
//                }
//            }
//        });
//    });


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
        $("input[name='firstName']").val();
        $("input[name='lastName']").val('');

    });

    $('#edit_user').click(function () {
        var user = {};
        user.firstName = $("input[name='edit_firstName']").val();
        user.lastName = $("input[name='edit_lastName']").val();
        user.id = $("input[name='edit_id']").val();

        $.ajax({
            type: "PUT",
            url: "/api/users/update",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(user),
            statusCode: {
                200: function () {
                    getUsers();
                }
            }
        });
        $("input[name='edit_firstName']").val();
        $("input[name='edit_lastName']").val('');

    });
</script>
</html>

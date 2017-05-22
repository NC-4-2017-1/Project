--get user by id
select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email
from objects obj_user, attributes first_name,  attributes last_name,  attributes email
where obj_user.object_type_id =1
and obj_user.object_id = first_name.object_id
and first_name.attr_id = 2   
and obj_user.object_id = last_name.object_id
and last_name.attr_id = 3  
and  obj_user.object_id = email.object_id
and email.attr_id = 1
and obj_user.object_id=?;

--get user by full name
select obj_user.object_id id,  first_name.value first_name, last_name.value last_name, email.value email
from objects obj_user, attributes first_name,  attributes last_name,  attributes email
where obj_user.object_type_id = 1
and obj_user.object_id = first_name.object_id
and first_name.attr_id = 2
and  obj_user.object_id = last_name.object_id
and last_name.attr_id = 3
and  obj_user.object_id = email.object_id
and email.attr_id = 1
and obj_user.name=?;

--get user by email
select obj_user.object_id id, first_name.value first_name, last_name.value last_name
from objects obj_user, attributes first_name,  attributes last_name,  attributes email
where obj_user.object_type_id = 1
and obj_user.object_id = first_name.object_id
and first_name.attr_id = 2
and  obj_user.object_id = last_name.object_id
and last_name.attr_id = 3
and  obj_user.object_id = email.object_id
and email.attr_id = 1
and email.value=?;

--get user by email version for logging in through spring security
select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email, password.value password
from objects obj_user, attributes first_name,  attributes last_name,  attributes email, attributes password
where obj_user.object_type_id = 1
and obj_user.object_id = first_name.object_id
and first_name.attr_id = 2
and  obj_user.object_id = last_name.object_id
and last_name.attr_id = 3
and  obj_user.object_id = email.object_id
and obj_user.object_id = password.object_id
and password.attr_id = 4
and email.attr_id = 1
and email.value=?;




--get all users
select obj_user.object_id id, first_name.value first_name, last_name.value last_name, email.value email
from objects obj_user, attributes first_name,  attributes last_name,  attributes email
where obj_user.object_type_id = 1
and obj_user.object_id = first_name.object_id
and first_name.attr_id = 2
and  obj_user.object_id = last_name.object_id
and last_name.attr_id = 3
and  obj_user.object_id = email.object_id
and email.attr_id = 1
and obj_user.object_id!=1;


--update user's email
update attributes
set value = ?
where object_id= ?
and attr_id = ?;

--update user's name
update objects
set name=?
where object_id=?;
update attributes
set value = ?
where object_id= ?
and attr_id = ?;
update attributes
set value = ?
where object_id= ?
and attr_id = ?;

--update user's password
update attributes
set value = ?
where object_id= ?
and attr_id = ?;

--give user access to project
insert into objreference(attr_id, reference, object_id) values (?, ?, ?);

--remove access to project from user
delete from objreference
where object_id = ?
and reference = ?
and attr_id = 18;


--authorize user
select users.object_id id, email.value email, first_name.value first_name, last_name.value last_name, password.value password
from objects users,  attributes email,  attributes first_name,  attributes last_name,  attributes password
where users.object_id = email.object_id
and email.attr_id = 1
and  users.object_id = first_name.object_id
and first_name.attr_id = 2
and  users.object_id = last_name.object_id
and last_name.attr_id = 3
and  users.object_id = password.object_id
and password.attr_id = 4
and email.value=?
and password.value = ?;

--create user

insert into attributes(attr_id, object_id, value) values (?, ?, ?);
insert into attributes(attr_id, object_id, value) values (?, ?, ?);
insert into attributes(attr_id, object_id, value) values (?, ?, ?);
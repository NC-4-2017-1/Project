--get project by id
select hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date,author.object_id author,description.value description,
sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password
from objects hmproject, attributes creation_date,objects author, attributes description, attributes sid,
attributes port, attributes server_name, attributes user_name, attributes password, objreference ref 
where hmproject.object_type_id = 3
and hmproject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and hmproject.object_id = description.object_id
and description.attr_id = 7
and hmproject.object_id = sid.object_id
and sid.attr_id = 25
and hmproject.object_id = port.object_id
and port.attr_id = 24
and hmproject.object_id =  server_name.object_id
and server_name.attr_id = 23
and hmproject.object_id = user_name.object_id
and user_name.attr_id = 26
and hmproject.object_id = password.object_id
and password.attr_id = 4
and ref.attr_id = 17
and ref.object_id = hmproject.object_id
and ref.reference = author.object_id
and hmproject.object_id=?;

--get project by name
select hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date,author.object_id author,description.value description,
sid.value sid, port.value port, server_name.value server_name, user_name.value user_name, password.value password
from objects hmproject, attributes creation_date,objects author, attributes description, attributes sid,
attributes port, attributes server_name, attributes user_name, attributes password, objreference ref 
where hmproject.object_type_id = 3
and hmproject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and hmproject.object_id = description.object_id
and description.attr_id = 7
and hmproject.object_id = sid.object_id
and sid.attr_id = 25
and hmproject.object_id = port.object_id
and port.attr_id = 24
and hmproject.object_id =  server_name.object_id
and server_name.attr_id = 23
and hmproject.object_id = user_name.object_id
and user_name.attr_id = 26
and hmproject.object_id = password.object_id
and password.attr_id = 4
and ref.attr_id = 17
and ref.object_id = hmproject.object_id
and ref.reference = author.object_id
and hmproject.name = ?;

--get project graph
select graph.object_id id, graph.name name, json.big_value json, hour_count.value hour_count
from objects graph, objects project, attributes json, attributes hour_count, objreference reference
where graph.object_id = json.object_id
and json.attr_id = 13
and graph.object_id = hour_count.object_id
and hour_count.attr_id = 16
and reference.attr_id = 20
and reference.reference = graph.object_id
and reference.object_id = project.object_id
and project.object_id = ?;

--get projects by author
select hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date, author.object_id author, description.value description
from objects hmproject, attributes creation_date, objects author, attributes description, objreference ref
where hmproject.object_type_id = 3
and hmproject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and hmproject.object_id = description.object_id
and description.attr_id = 7
and ref.attr_id = 17
and ref.object_id = hmproject.object_id
and ref.reference = author.object_id
and author.object_id=?
order by creation_date.date_value;

--get project user have access to
select hmproject.object_id id, hmproject.name name, creation_date.date_value creation_date, author.object_id author, description.value description
from objects hmproject, attributes creation_date, objects have_access, attributes description, objects author, objreference ref_author, objreference ref_access
where hmproject.object_type_id = 3
and hmproject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and hmproject.object_id = description.object_id
and description.attr_id = 7
and ref_author.attr_id = 17
and ref_author.object_id = hmproject.object_id
and ref_author.reference = author.object_id
and ref_access.attr_id = 18
and ref_access.object_id = hmproject.object_id
and ref_access.reference = have_access.object_id
and have_access.object_id = ?
order by creation_date.date_value;


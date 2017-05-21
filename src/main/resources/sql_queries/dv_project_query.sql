--get project by id
select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description
from objects dvpoject, attributes creation_date, objects author, attributes description, objreference ref
where dvpoject.object_type_id = 4
and dvpoject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and dvpoject.object_id = description.object_id
and description.attr_id = 7
and ref.attr_id = 17
and ref.object_id = dvpoject.object_id
and ref.reference = author.object_id
and dvpoject.object_id = ?;

--get project by name
select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description
from objects dvpoject, attributes creation_date, objects author, attributes description, objreference ref
where dvpoject.object_type_id = 4
and dvpoject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and dvpoject.object_id = description.object_id
and description.attr_id = 7
and ref.attr_id = 17
and ref.object_id = dvpoject.object_id
and ref.reference = author.object_id
and dvpoject.name = ?;

-- get projects by author
select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description
from objects dvpoject, attributes creation_date, objects author, attributes description, objreference ref
where dvpoject.object_type_id = 4
and dvpoject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and dvpoject.object_id = description.object_id
and description.attr_id = 7
and ref.attr_id = 17
and ref.object_id = dvpoject.object_id
and ref.reference = author.object_id
and author.object_id=?
order by creation_date.date_value;

--get projects user have access to
select dvpoject.object_id id, dvpoject.name name, creation_date.date_value creation_date, author.object_id author, description.value description
from objects dvpoject, attributes creation_date, objects have_access, attributes description, objects author, objreference ref_author, objreference ref_access
where dvpoject.object_type_id = 4
and dvpoject.object_id = creation_date.object_id
and creation_date.attr_id = 6
and dvpoject.object_id = description.object_id
and description.attr_id = 7
and ref_author.attr_id = 17
and ref_author.object_id = dvpoject.object_id
and ref_author.reference = author.object_id
and ref_access.attr_id = 18
and ref_access.object_id = dvpoject.object_id
and ref_access.reference = have_access.object_id
and have_access.object_id = ?
order by creation_date.date_value;

--get project graphs
select graph.object_id id, graph.name name, json_res.big_value json,
average.value average, olympic_average.value olympic_average, math_expectation.value math_expectation, dispersion.value dispersion
from objects graph, objects dvproject, attributes json_res, attributes average, attributes olympic_average,
attributes math_expectation, attributes dispersion, objreference ref
where graph.object_id = json_res.object_id
and json_res.attr_id = 13
and graph.object_id = average.object_id
and average.attr_id = 9
and graph.object_id = olympic_average.object_id
and olympic_average.attr_id = 10
and graph.object_id = math_expectation.object_id
and math_expectation.attr_id = 11
and graph.object_id = dispersion.object_id
and dispersion.attr_id = 12
and ref.attr_id = 20
and ref.reference = graph.object_id
and ref.object_id = dvproject.object_id
and dvproject.object_id = ?;



SELECT services_types.id, services.s_name, services_types.st_id, services_types.st_info, services_types.st_price from services, services_types  WHERE services.id=services_types.st_id
 
 
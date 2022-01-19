package ru.geekbrains.dao;

import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DAOUtils {

    private final Class<Object> aClass;
    private final String entityName;
    List<Field> idFields;
    public DAOUtils(Class<Object> aClass){
        this.aClass=aClass;
        entityName = aClass.getName();
        Field[] fields = aClass.getDeclaredFields();
        idFields = Arrays.stream(fields)
                .filter(field->field.isAnnotationPresent(Id.class))
                .peek(field->field.setAccessible(true))
                .collect(Collectors.toList());
    }

    public String getEntityName(){
        return entityName;
    }

    public boolean idIsNotNull(Object o)  {
         return !idFields.stream().anyMatch(f->{
             try {
                 return f.get(o)==null;
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             }
             return false;
         });
    };

}

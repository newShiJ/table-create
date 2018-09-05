package mybatis.mybaitscreate.initDB;

import javax.persistence.Column;
import javax.persistence.Id;
import java.lang.reflect.Field;

/**
 * 列描述
 * @author chenmingming
 * @date 2018/9/4
 */
public abstract class ColumnDescript {

    protected String description;

    protected int length = 255;

    protected String type;

    protected boolean nullable;

    protected String columnName;

    protected boolean isPrimaryKey;

    ColumnDescript(Field field){
        this.columnName = field.getName();
        Column column = field.getAnnotation(Column.class);
        this.description = column.columnDefinition();
        this.nullable = column.nullable();
        this.length = column.length();
        this.isPrimaryKey = field.getAnnotationsByType(Id.class).length>0;
    }

    /**
     * 构建对应的属性描述
     * @param field
     * @return
     */
    public static ColumnDescript createColumnDescript(Field field){
        ColumnDescript descript = null;
        Class<?> type = field.getType();
        if(type.equals(int.class) || type.equals(Integer.class)){
            descript = new IntColumnDescript(field);
        }else if(type.equals(String.class)){
            descript = new VarcharColumnDescript(field);
        }else if(type.equals(long.class) || type.equals(Long.class)){
            descript = new BigIntColumnDescript(field);
        }else if(type.equals(double.class) || type.equals(Double.class)){
            descript = new DoubleColumnDescript(field);
        }else if(type.equals(float.class) || type.equals(Float.class)){
            descript = new FloatColumnDescript(field);
        }
        return descript;
    }

    /**
     * 获取当前属性描述生成的数据库列语句
     * @return
     */
    public String columnStr(){
        if(description.length() > 0){
            description = columnName + " " + description;
            description += ",";
            return description;
        }
        String str;
        str = columnName + " " + type + "(" + length + ") ";
        if(!nullable){
            str += "NOT NULL";
        }
        str += ",";
        return str;
    }

    /**
     * 获取列名称
     * @return
     */
    public String columnName(){
        return columnName;
    }


    public static boolean initable(Field field){
        return field.getAnnotationsByType(Column.class).length>0;
    }
}

package mybatis.mybaitscreate.initDB;

import mybatis.mybaitscreate.entity.Test;

import javax.persistence.Column;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
public class TableBuilder {

    private final Class clazz;

    private final Table table;

    public TableBuilder(Class clazz) {
        this.clazz = clazz;
        Annotation[] type = clazz.getAnnotationsByType(Table.class);
        if(type == null || type.length <1){
            throw new RuntimeException("不能被加载");
        }
        table = (Table) type[0];
    }

    /**
     * 当没有表存在时所执行的语句
     * @return
     */
    public String DDL(){

        String tableName = table.name();
        String ddl = "CREATE TABLE " + tableName + "(\r\n";
        Field[] fields = clazz.getDeclaredFields();
        List<ColumnDescript> primaryKeys = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Column annotation = field.getAnnotation(Column.class);
            if(annotation != null){
                ColumnDescript descript = ColumnDescript.createColumnDescript(field);
                boolean isPrimaryKey = descript.isPrimaryKey;
                if(isPrimaryKey){
                    primaryKeys.add(descript);
                }
                String columnStr = descript.columnStr();
                ddl += columnStr;
                ddl += "\r\n";
            }
        }
        if(primaryKeys.size()<1){
            throw new RuntimeException("没有主键");
        }
        ddl += "PRIMARY KEY(";
        for (int i = 0; i < primaryKeys.size(); i++) {
            ddl += primaryKeys.get(i).columnName;
            if(i<primaryKeys.size()-1){
                ddl += ",";
            }
        }
        ddl +="))";
        return ddl;
    }

    /**
     * 获取表名称
     * @return
     */
    public String tableName(){
        return table.name();
    }

    /**
     * 构建实体的列描述
     * @return
     */
    public List<ColumnDescript> columnDescriptList(){
        Field[] fields = clazz.getDeclaredFields();
        ArrayList<ColumnDescript> columnDescripts = new ArrayList<>();
        for (Field field : fields) {
            if(ColumnDescript.initable(field)){
                columnDescripts.add(ColumnDescript.createColumnDescript(field));
            }
        }
        return columnDescripts;
    }

    public String ALTER(Iterable<ColumnDescript> columnDescripts){
        String alter = String.format("ALTER TABLE %s ADD (",tableName());
        for (ColumnDescript descript : columnDescripts) {
            alter += descript.columnStr();
        }
        alter = alter.substring(0, alter.length() - 1);
        alter += ")";
        return alter;
    }

}






















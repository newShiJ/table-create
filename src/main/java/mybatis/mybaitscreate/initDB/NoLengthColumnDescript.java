package mybatis.mybaitscreate.initDB;

import java.lang.reflect.Field;

/**
 * @author chenmingming
 * @date 2018/9/4
 */
public abstract class NoLengthColumnDescript extends ColumnDescript{

    NoLengthColumnDescript(Field field) {
        super(field);
    }

    @Override
    public String columnStr() {
        if(description.length() > 0){
            description = columnName + " " + description;
            description += ",";
            return description;
        }
        String str;
        str = columnName + " " + type;
        if(!nullable){
            str += "NOT NULL";
        }
        str += ",";
        return str;
    }
}
